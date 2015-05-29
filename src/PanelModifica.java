import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.table.DefaultTableModel;
/**
 * Pannello di modifca dei dati di un certo file, esso è un estensione della classe ::InserPanel
 * Questa classe sfrutta la GUI fornita dalla classe InserPanel e cambia l'handler dei 2 pulsanti
 * facendogli modificare i valori presenti nella tabella
 */
public class PanelModifica extends InserPanel implements ActionListener,WindowListener {
  
  private int idtbl;
  private Collezione_Multimediale <File_Multimediale> cm;
  private JFrame main_frame;
  private JButton [] btn;
  private DefaultTableModel model;
  
  public PanelModifica(File f,JFrame fr,Collezione_Multimediale <File_Multimediale> fl,DefaultTableModel tab,int id,String str) { 
    
    super(f,fr,fl,tab,str);
    
    idtbl = id;
    cm = fl;
    main_frame = fr;
    btn = super.getBtn();
    //System.out.println(btn[0]);
    btn[0].addActionListener(this);
    btn[1].addActionListener(this);
    model = tab;
  }
  /** handler dei pulsanti il btn[0](Conferma) sfrutta il comportamento polimorfico per modificare
    * i campi di classi diversi utilizzando solo la classe ::File_Multimediale
    * in caso di errore di inserimento dei valori visualizza una dialog di notifica dell'errore
    */
  public void actionPerformed(ActionEvent e)
  {
    Object o = e.getSource();
    
    JTextField [] txt = super.getTxt();
    if(o == btn[0])
    {
      //Polimorfismo
      try
      {
        File_Multimediale app = cm.getEl(idtbl);
        app.set_titolo(txt[0].getText());
        app.set_autore(txt[1].getText());
        app.set_anno(Integer.parseInt(txt[2].getText()));
        String [] appstr = {txt[3].getText(),txt[4].getText()}; 
        app.set_attributi(appstr);
        //for(int j = 0; j < cm.size();j++)
        //{
         // app = cm.getEl(j);
         // System.out.println("dopo modifica: "+ app.toString());
        //}
        
        super.getFrame().setVisible(false);
        main_frame.setVisible(true);
        cm.setSalva(false);
        app = cm.getEl(idtbl);
        String [] str = app.row();
        for(int i = 0; i < str.length;i++)
          model.setValueAt(str[i],idtbl,i);
      }
      catch(NumberFormatException ex)
      {
         JFrame frame = new JFrame("Errore");
         JOptionPane.showMessageDialog(frame,"inserire valori numeri nei campi: anno/durata/Frequenza di campionamento","Errore",JOptionPane.ERROR_MESSAGE);
      }
     
    }
    else
    {
      super.getFrame().setVisible(false);
      main_frame.setVisible(true);
    }
  }
  public void windowClosed(WindowEvent e){}
  public void windowDeactivated(WindowEvent e){}
  public void windowActivated(WindowEvent e){}
  public void windowDeiconified(WindowEvent e){}
  public void windowIconified(WindowEvent e){}
  public void windowClosing(WindowEvent e){ main_frame.setVisible(true);}
  public void windowOpened(WindowEvent e){}
}
