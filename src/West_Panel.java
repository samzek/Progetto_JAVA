import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.*;

/** Pannello a ovest del ::Main_Panel, esso implementa le funzionalità specifiche di una singola
  * libreria, esso permette l'aggiunta di file, la rimozione, la modifica,la riproduzione ecc..
  */
public class West_Panel extends JPanel implements ActionListener {
  
  private JButton agg,elimina,modifica,riproduci,info;
  private GridLayout west_layout;
  private DefaultTableModel tbl;
  private JFrame fr;
  private Collezione_Multimediale []archivio;
  private JTabbedPane jtab;
  private North_Panel pnN;
  private JTable tabella;
  private int index;
  
  /** Costruttore */
  public West_Panel(JFrame frame,Collezione_Multimediale []archivio,JTabbedPane jtab,North_Panel pn) { 
   
    super();
    
    pnN = pn;
    
    this.jtab = jtab;
    
    this.archivio = archivio;
    
    fr = frame;
    
    agg = new JButton("aggiungi");
    elimina = new JButton("elimina");
    modifica = new JButton("modifica");
    riproduci = new JButton("riproduci");
    info = new JButton("informazioni");
    
    west_layout = new GridLayout(5,1);
    setLayout(west_layout);

    agg.addActionListener(this);
    elimina.addActionListener(this);
    modifica.addActionListener(this);
    riproduci.addActionListener(this);
    info.addActionListener(this);
    
    add(agg);
    add(elimina);
    add(modifica);
    add(riproduci);
    add(info);
    
   //disabled();
  }
  /** handler di gestione dei pulsanti:
    * 
    * Pulsante aggiungi:  apre un filechooser per la scelta del file da inserire, successivamente
    *                     apre un form per l'aggiunta dei campi specifici del file;
    * 
    * Pulsante elimina: elimina un file selezionato dalla collezione multimediale;
    * 
    * Pulsante modifica: apre lo stesso form di aggiungi ma consente la modifica dei valori di una riga
    *                    selezionata;
    * 
    * Pulsante informazioni: apre una piccola finestra contenente le informazioni specifiche e non di un singolo
    *                        file selezionato;
    * 
    * Pulsante riproduci: apre un piccolo player che riproduce singole traccie selezionate dall'utente;
    */
  public void actionPerformed(ActionEvent e)
  {
    Object o = e.getSource();
    index = jtab.getSelectedIndex();
    //System.out.println(index);
   if(index != -1)
   {
    tbl = pnN.getModel(index);
    tabella = pnN.getTable(index);
    InserPanel ap = null;
   
    if (agg == o)
    { 
      File[] f = creaChooser();
      if (f != null)
      {
        fr.setVisible(false);
        for(int i = 0 ;i < f.length;i++)
        {
          @SuppressWarnings("unchecked")
          InserPanel p = new InserPanel(f[i],fr,archivio[index],tbl,f[i].getName());
          ap = p;
          if(i == 0)
            ap.setFlag(f.length);
        }
        //ap.setFlag(f.length);
      //  fr.setVisible(true);
        //fr.setEnabled(true);
      }
    }
    else if(elimina == o)
    {
      int []i = tabella.getSelectedRows();
      File_Multimediale app;
      
      int k = 1;
      for(int h = 0;h < i.length;h++)
      {
       // System.out.println("idx riga: " + i[h] + "dimensione vector: "+archivio[index].size());
        if(h != 0)
        {
          app = (File_Multimediale)archivio[index].getEl(i[h] - k);
          tbl.removeRow(i[h]-k);
          k++;
        }
        else
        {
          app = (File_Multimediale)archivio[index].getEl(i[h]);
          tbl.removeRow(i[h]);
        }
        archivio[index].rimuovi(app);
        archivio[index].setSalva(false);
        //for(int j = 0; j < archivio[index].size();j++)
       // {
         // app =(File_Multimediale) archivio[index].getEl(j);
         // System.out.println("dopo rimuovi: "+ app.toString());
        //}
      }
    }
    else if(modifica == o)
    {
      int i = tabella.getSelectedRow();
      if(i != -1)
      {
        File_Multimediale app = (File_Multimediale) archivio[index].getEl(i);
        @SuppressWarnings("unchecked")
        PanelModifica pnMod = new PanelModifica(null,fr,archivio[index],tbl,i,app.getPdf());
      }
    }
    else if(info == o)
    {
      int i = tabella.getSelectedRow();
      if(i != -1)
      {
      File_Multimediale app = (File_Multimediale) archivio[index].getEl(i);
      JFrame frame = new JFrame("Informazioni");
      //polimorfismo
      JOptionPane.showMessageDialog(frame,app.toString(),"Informazioni file",JOptionPane.INFORMATION_MESSAGE);
      }
    }
   
   else if(riproduci == o)
   {
     int idTbl = tabella.getSelectedRow();
     if(idTbl != -1)
     {
       File_Multimediale app =(File_Multimediale) archivio[index].getEl(idTbl);
    //   System.out.println("PATH: " + app.getPdf());
       File f = new File(app.getPdf());
       if(f.getName().endsWith(".mp3"))
       {
        // System.out.println("file name " + f.getName());
         fr.setVisible(false);
         JMusic msc = new JMusic(f,fr);
       }
       else
       {
         try
         {
           Desktop.getDesktop().open(f);
         }
         catch(IOException ep)
         {
           JFrame frame = new JFrame("Errore");
           JOptionPane.showMessageDialog(frame,"Errore esecuzione file " + ep,"Errore",JOptionPane.ERROR_MESSAGE);
         }
       }
     }
   }
  }
 }
  /** crea un file chooser con i filtri per la selezione corretta del file 
    * effettua comunque una ricerca sull'estensione per verificare la presenza
    * di un errato input dell'utente
    */
  private File[] creaChooser()
  {
    JFileChooser fileChooser = new JFileChooser();
    
    fileChooser.setMultiSelectionEnabled(true);
   
     FileNameExtensionFilter filter = new FileNameExtensionFilter("*.mp3","mp3");
     FileNameExtensionFilter filter2 = new FileNameExtensionFilter("*.mp4","mp4");
     FileNameExtensionFilter filter3 = new FileNameExtensionFilter("*.pdf","pdf");
    fileChooser.addChoosableFileFilter(filter);
    fileChooser.addChoosableFileFilter(filter2);
    fileChooser.addChoosableFileFilter(filter3);

    JComponent parent = null;
    int returnVal = fileChooser.showOpenDialog(parent);
    
    if(returnVal == fileChooser.APPROVE_OPTION)
    {
      File [] f = fileChooser.getSelectedFiles();
      int conta = 0;
        for(int i = 0; i < f.length;i++)
        {
         @SuppressWarnings("unchecked")
          boolean cerca = cerca_valore(archivio[index],f[i]);
            if((f[i].getName().endsWith("mp3") || f[i].getName().endsWith("mp4") || f[i].getName().endsWith("pdf")) && !cerca)
            {  
            //  System.out.println(f[i].getName());
              String percorso = f[i].getAbsolutePath();
            //  System.out.println(percorso); 
              conta++;
            }
            else
            {
              String messaggio = "Selezionare file multimediale (*.mp3,*.mp4,*.pdf)";
              if (cerca == true)
                messaggio = "File già inserito";
                
              JFrame frame = new JFrame("Errore");
              JOptionPane.showMessageDialog(frame,messaggio,"Errore",JOptionPane.ERROR_MESSAGE);
            }  
        }
        if (conta == f.length)
          return f;
    }
    else
        ;//System.out.println("annulla");
    return null;
  }
  
private boolean cerca_valore(Collezione_Multimediale <File_Multimediale> fl,File file)
{
  for(int i = 0; i < fl.size();i++)
  {
    File_Multimediale a = fl.getEl(i);
    if(a.getPdf().endsWith(file.getName()))
    {
      return true;
    }
  }
  return false;

}
  public void enabled()
  {
    agg.setEnabled(true);
    elimina.setEnabled(true);
    modifica.setEnabled(true);
    riproduci.setEnabled(true);
    info.setEnabled(true);
  }
  public void disabled()
  {
    agg.setEnabled(false);
    elimina.setEnabled(false);
    modifica.setEnabled(false);
    riproduci.setEnabled(false);
    info.setEnabled(false);
  }
}
