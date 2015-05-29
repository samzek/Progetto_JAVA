import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.table.DefaultTableModel;


/**
 * Frame di inserimento dei dati caratteristici di ogni file si apre dopo aver 
 * scelto che file aggiungere alla libreria tramite l'opportuna JFileChooser.
 */
public class InserPanel implements ActionListener,WindowListener {
  
  private JFrame frame;
  private JPanel panel;
  private JLabel nome,autore,anno,durata,fdq;
  private JTextField txtnome,txtautore,txtanno,txtdurata,txtfdq;
  private JButton conferma,annulla;
  private JFrame main_fr;
  private File file;
  private Collezione_Multimediale<File_Multimediale> fl;
  private DefaultTableModel tbl;
  private static int flag = 0;
  
  public InserPanel(File f,JFrame fr,Collezione_Multimediale <File_Multimediale> fl,DefaultTableModel tab,String nameF) {
    
  //  flag = flg;
    this.fl = fl;
    tbl = tab;
    
    main_fr = fr;
    main_fr.setVisible(false);
    
    String n;
    file = f;
    if(file == null)
      n = "Modifica file " + nameF;
    else
      n = "Inserimento " + f.getName();
    
    frame = new JFrame(n);
    conferma = new JButton("conferma");
    annulla = new JButton("annulla");
    nome = new JLabel("titolo");
    autore = new JLabel("autore");
    anno = new JLabel("anno");
    durata = new JLabel("durata");
    fdq = new JLabel("freq di campionamento");
    
    txtnome = new JTextField("");
    txtautore = new JTextField("");
    txtanno = new JTextField("");
    txtdurata = new JTextField("");
    txtfdq = new JTextField("");
    
    if(nameF.endsWith("mp4") || nameF.endsWith("pdf"))
    {
     panel = new JPanel(new GridLayout(5,2));
     addEl();
     if(nameF.endsWith("pdf"))
       durata.setText("numero pagine");
     panel.add(conferma);
     panel.add(annulla);
    }
    else if(nameF.endsWith("mp3"))
    {
      panel = new JPanel(new GridLayout(6,2));
      addEl();
      panel.add(fdq);
      panel.add(txtfdq);
      panel.add(conferma);
      panel.add(annulla);
    }
    if(file != null)
    {
      conferma.addActionListener(this);
      annulla.addActionListener(this);
      frame.addWindowListener(this);
    }
    
    frame.add(panel);
    frame.setVisible(true);
    frame.setSize(new Dimension(400,400));
    frame.setLocationRelativeTo(null);
  }
  private void addEl()
  {
     panel.add(nome);
     panel.add(txtnome);
     panel.add(autore);
     panel.add(txtautore);
     panel.add(anno);
     panel.add(txtanno);
     panel.add(durata);
     panel.add(txtdurata);
  }
  public void actionPerformed(ActionEvent e)
  {
    Object o = e.getSource();
    if(o == annulla)
    {
      flag--;
      if(flag == 0)
      {
      main_fr.setVisible(true);
      }
      frame.setVisible(false);
    }
    else if(o == conferma)
    {
      if(file.getName().endsWith("mp4") || file.getName().endsWith("pdf"))
      {
        if(controlla_txt() == false)
        {
        JFrame frame = new JFrame("Errore");
        JOptionPane.showMessageDialog(frame,"Occore inserire tutti gli attributi del file","Errore",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
          if(file.getName().endsWith("mp4"))
          {
            try
            {
              Video vd = new Video(txtnome.getText(),txtautore.getText(),Integer.parseInt(txtanno.getText()),file.getAbsolutePath(),Float.parseFloat(txtdurata.getText()));
              fl.add(vd);
              File_Multimediale filM = fl.getEl(fl.size()-1);
              tbl.addRow(filM.row());
              frame.setVisible(false);
              flag--;
              fl.setSalva(false);
              if(flag == 0)
                main_fr.setVisible(true);
             
            }
            catch(NumberFormatException ex)
            {
               JFrame frame = new JFrame("Errore");
               JOptionPane.showMessageDialog(frame,"inserire valori numeri nei campi: anno e durata","Errore",JOptionPane.ERROR_MESSAGE);
            }
          }
         else
            {
              try
              {
                Ebook eb = new Ebook(txtnome.getText(),txtautore.getText(),Integer.parseInt(txtanno.getText()),file.getAbsolutePath(),Integer.parseInt(txtdurata.getText()));
                fl.add(eb);
                File_Multimediale filM = fl.getEl(fl.size()-1);
                tbl.addRow(filM.row());
                frame.setVisible(false);
                flag--;
                fl.setSalva(false);
                if(flag == 0)
                  main_fr.setVisible(true);
               
              }
              catch(NumberFormatException ex)
              {
               JFrame frame = new JFrame("Errore");
               JOptionPane.showMessageDialog(frame,"inserire valori numeri nei campi: anno e numero pagine","Errore",JOptionPane.ERROR_MESSAGE);
              }
            }
          }  
        }
      
      else
      {
         if(controlla_txt() == false && txtfdq.getText().equals(""))
         {
           JFrame frame = new JFrame("Errore");
           JOptionPane.showMessageDialog(frame,"Occore inserire tutti gli attributi del file","Errore",JOptionPane.ERROR_MESSAGE);
         }
         else
         {
           try
            {
              Brani br = new Brani(txtnome.getText(),txtautore.getText(),Integer.parseInt(txtanno.getText()),file.getAbsolutePath(),Float.parseFloat(txtdurata.getText()),Float.parseFloat(txtfdq.getText()));
              fl.add(br);
              File_Multimediale filM = fl.getEl(fl.size()-1);
              tbl.addRow(filM.row());
               frame.setVisible(false);
               flag--;
               fl.setSalva(false);
               if(flag == 0)
                 main_fr.setVisible(true);
               
            }
            catch(NumberFormatException ex)
            {
               JFrame frame = new JFrame("Errore");
               JOptionPane.showMessageDialog(frame,"inserire valori numeri nei campi: anno,durata,frequenza di campionamento","Errore",JOptionPane.ERROR_MESSAGE);
            }      
         }
      }
    }
   }

  
private boolean controlla_txt()
{
  if(txtnome.getText().equals("") && txtanno.getText().equals("") && txtautore.getText().equals("") && txtdurata.getText().equals(""))
    return false;
  else
    return true;
}
public JButton [] getBtn()
{
  JButton [] btn = {conferma,annulla};
  return btn;
}
public JTextField [] getTxt()
{
  JTextField [] txt = {txtnome,txtautore,txtanno,txtdurata,txtfdq};
  return txt;
}
public JFrame getFrame()
{
  return frame;
}
public void setFlag(int n)
{
  flag = n;
}

  public void windowClosed(WindowEvent e){}
  public void windowDeactivated(WindowEvent e){}
  public void windowActivated(WindowEvent e){}
  public void windowDeiconified(WindowEvent e){}
  public void windowIconified(WindowEvent e){}
  public void windowClosing(WindowEvent e){ flag --; if(flag == 0)main_fr.setVisible(true);}
  public void windowOpened(WindowEvent e){}
}