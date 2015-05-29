import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTable.PrintMode;
import java.awt.print.PrinterException;

/**
 * Panello a NORD del ::Main_Panel esso implementa funzionalità quali l'apertura di una nuova tab
 * il caricamento di una libreria, il salvataggio la stampa e la ricerca.
 * 
 * Essa implementa una array di tabelle e un array di modelli per far si che ogni tab della tabbedpane 
 * abbia una tabella nuova e sempre diversa.
 */
public class North_Panel extends JPanel implements ActionListener {
  
  private JButton nuovo,apri,salva,stampa,avanti,indietro;
  private JTextField cerca;
  private GridLayout north_layout;
  private JTabbedPane pnM;
  private int conta_panel = 0;
  private Main_Panel main;
  private Collezione_Multimediale [] archivio;
  private DefaultTableModel [] model;
  private String [] inst = {"Titolo","Autore","Anno","Percorso del file"};
  private int conta_tabelle = 0;
  private JTable [] table;
  private int indexRicerca = -1;
  //private int indexRicercaPrec = -1;
  
  /** Costruttore */
  public North_Panel(JTabbedPane pane,Main_Panel main,Collezione_Multimediale[] archivio) { 

    super();
    this.main = main;
    this.archivio = archivio;
    
    model = new DefaultTableModel[10];
    for(int i = 0 ; i < model.length;i++)
      model[i] = new DefaultTableModel(inst,0);
    //System.out.println(tabelle[0]);
    
    table = new JTable[10];
    for(int i = 0 ; i < table.length;i++)
    {
      table[i] = new JTable(model[i]){ 
        public boolean isCellEditable(int row, int column) {                
          return false;    
        };
      };
    }
    nuovo = new JButton();
    apri = new JButton();
    salva = new JButton();
    stampa = new JButton();
    avanti = new JButton();
    indietro = new JButton();
    
    nuovo.setIcon(new ImageIcon("../image/new.png"));
    apri.setIcon(new ImageIcon("../image/open.png"));
    salva.setIcon(new ImageIcon("../image/Save.png"));
    stampa.setIcon(new ImageIcon("../image/print.png"));
    avanti.setIcon(new ImageIcon("../image/next.png"));
    indietro.setIcon(new ImageIcon("../image/previous.png"));
    
    cerca = new JTextField("cerca");
    
    pnM = pane;
    
    north_layout = new GridLayout(1,7);
    
    setLayout(north_layout);
    
    nuovo.addActionListener(this);
    apri.addActionListener(this);
    salva.addActionListener(this);
    stampa.addActionListener(this);
    avanti.addActionListener(this);
    indietro.addActionListener(this);
    
    add(nuovo);
    add(apri);
    add(salva);
    add(stampa); 
    add(cerca);
    add(indietro);
    add(avanti);
  }
  /** Handler per la gestione della pressione dei pulsanti da parte dell'utente
    * 
    * Pulsante nuovo: aggiunge un nuova tab alla tabbedpane e gli assegna un titolo
    *                 l'indice della tab è usata per crare un oggetto all'interno dell'array
    *                 archivio(Collezione_Multimediale<File_Multimediale>).
    * 
    * Pulsante apri: carica una collezione multimediale da file
    * 
    * Pulsante salva: salva la collezione multimediale corrente cioè la tab selezionata dall'utente
    * 
    * Pulsante stampa: apre un form per la stampa della tabella corrente.
    * 
    * Pulsante avanti, indietro: effettuano una ricerca in base al testo contenuto dentro al casela cerca.
    * 
   */
  public void actionPerformed(ActionEvent e)
  {
   String titoloPanel;
   //System.out.println("DEBUG");
   Object pulsante = e.getSource();
   String nomeObj = e.getActionCommand();
   West_Panel pnWEST = main.getWEST();
   
   if(pulsante == nuovo || nomeObj.equals("nuovo"))
   {
     controlla_dimensione();
   //  System.out.println(table.length + " " + archivio.length);
     titoloPanel = "Senza titolo" + conta_panel;
     pnM.insertTab(titoloPanel,null,new JScrollPane(table[conta_tabelle]),titoloPanel,conta_panel);
     pnWEST.enabled();
     
     archivio[conta_panel] = new Collezione_Multimediale <File_Multimediale> ();
   
     conta_tabelle++;
     conta_panel++;
   }
   else if (pulsante == salva || nomeObj.equals("salva"))
   {
     if(pnM.getSelectedIndex() != -1)
     {
       JFileChooser jfile = new JFileChooser();
       jfile.setFileSelectionMode(jfile.DIRECTORIES_ONLY);
       int returnval = jfile.showSaveDialog(North_Panel.this);
       File f;
       if(returnval == jfile.APPROVE_OPTION)
       {
           f = jfile.getSelectedFile();
           JFrame frame = new JFrame("Salva");
           JLabel name = new JLabel("inserisci nome file: ");
           String s = (String) JOptionPane.showInputDialog(frame,name,"Informazioni file",JOptionPane.QUESTION_MESSAGE);
         //  System.out.println(s);
           if(s != null)
           {
             if(s.endsWith(".dat"))
               ;
             else
               s += ".dat";
             String path = f.getAbsolutePath() + "/";
             path += s;
             
             String [] names = f.list();
             //System.out.println(names);
             if(names == null)
             {
               JFrame frame3 = new JFrame("Errore");
               JOptionPane.showMessageDialog(frame3,"Cartella non esistente","Errore",JOptionPane.ERROR_MESSAGE);
             }
             else
             {
               boolean controlloPath = controllo_salvataggio(names,s);
               
               if(controlloPath == true)
               {
                 JFrame frame2 = new JFrame("Salva");
                 int n = JOptionPane.showConfirmDialog(frame2,"Vuoi sovrascrivere il file?","Sovrascrivi",JOptionPane.YES_NO_OPTION);
                 if(n == JOptionPane.YES_OPTION)
                 {
                   boolean ret = salva(path);
                   pnM.setTitleAt(pnM.getSelectedIndex(),s);
                 }
               }
               else
               {
                 boolean ret = salva(path);
                 if(ret == true)
                 {
                   pnM.setTitleAt(pnM.getSelectedIndex(),s);
                 }
               }
               archivio[pnM.getSelectedIndex()].setSalva(true);
             }
           }
       }
     }
   }
   else if(pulsante == apri || nomeObj.equals("apri"))
   {
     FileNameExtensionFilter filter = new FileNameExtensionFilter("*.dat","dat");
     JFileChooser jf = new JFileChooser();
     jf.setFileFilter(filter);
     
     int returnval = jf.showOpenDialog(North_Panel.this);
     File f;
     if(returnval == jf.APPROVE_OPTION)
     {
       f = jf.getSelectedFile();
       if(f != null)
       {
         if(f.getName().endsWith(".dat"))
         {
           boolean ret = carica(f.getAbsolutePath());
           if(ret == true)
           {
             controlla_dimensione();
             titoloPanel = f.getName();
             pnM.insertTab(titoloPanel,null,new JScrollPane(table[conta_tabelle]),titoloPanel,conta_panel);
             for(int i = 0 ; i < archivio[conta_panel].size();i++)
             {
               File_Multimediale app = (File_Multimediale) archivio[conta_panel].getEl(i);
               model[conta_tabelle].addRow(app.row());
             }
             archivio[conta_panel].setSalva(true);
             conta_tabelle++;
             conta_panel++;
             //pnWEST.enabled();
           }
         }
         else
         {
           JFrame frame4 = new JFrame("Errore");
           JOptionPane.showMessageDialog(frame4,"Selezionare file con estensione .dat","Errore",JOptionPane.ERROR_MESSAGE);
         }
        
       }
     }
   }
   else if(pulsante == stampa || nomeObj.equals("stampa"))
   {
     int i = pnM.getSelectedIndex();
     Boolean ret = false;
     
     if(i != -1)
     {
       try{
         ret = table[i].print(PrintMode.NORMAL);
        // for(int j = 0;j < archivio[i].size();j++)
         //{
           //File_Multimediale el = (File_Multimediale)archivio[i].getEl(j);
         //  System.out.println("Stampa valori collezzione: \n " + el.toString());
         //}
       }
       catch(PrinterException pe)
       {
         System.out.println("Stampa Fallita" + pe.getMessage());
         System.exit(0);
       }
       if(ret == true)
       {
         ;//System.out.println("Stampa riuscita");
       }
       else
         ;//System.out.println("Sei uscito");
      }
   }
   else if(pulsante == avanti)
   {
     int i = pnM.getSelectedIndex();
     boolean flagC = false;
     //System.out.println("idRicerca: " + indexRicerca + "dimArch " + archivio[i].size());
     if(i != -1 && indexRicerca != archivio[i].size() - 1)
     {
       String search = cerca.getText();
       if(search.equals(" ") || search.equals("cerca"))
         ;
       else
       {
         for(int j = indexRicerca + 1; j < archivio[i].size();j++)
         {
           File_Multimediale app = (File_Multimediale) archivio[i].getEl(j);
           if(app.getTitolo().indexOf(search) != -1 || app.getAutore().indexOf(search) != -1 || app.getAnno().indexOf(search) != -1)
           {
            // if(j != indexRicercaPrec)
             //{
               indexRicerca = j;
               flagC = true;
                //System.out.println("Nuovo index ricerca " + indexRicerca);
               break;
             //}
           }
         }
         if(flagC == true)
         {
           try{
             table[i].setRowSelectionInterval(indexRicerca,indexRicerca);
           }
           catch(IllegalArgumentException ex)
           {
             JFrame frame = new JFrame("Errore");
             JOptionPane.showMessageDialog(frame,"Errore ricerca","Errore",JOptionPane.ERROR_MESSAGE);
           }
       }
         else
         {
           if(indexRicerca != -1){
             File_Multimediale app = (File_Multimediale) archivio[i].getEl(indexRicerca);
             if(app.getTitolo().indexOf(search) != -1)
             {
               JFrame frame = new JFrame("Errore");
               JOptionPane.showMessageDialog(frame,"Non ci sono altri elementi corrispondenti al parametro cercato","Errore",JOptionPane.ERROR_MESSAGE);
             }
             
             else{
               JFrame frame = new JFrame("Errore");
               JOptionPane.showMessageDialog(frame,"Elemento non trovato","Errore",JOptionPane.ERROR_MESSAGE);
             }
           }
         }
       //indexRicercaPrec = indexRicerca;
       }
     }
   }
   else if(pulsante == indietro)
   {
     int i = pnM.getSelectedIndex();
      boolean flagC = false;
     //System.out.println("idRicerca: " + indexRicerca + "dimArch " + archivio[i].size());
     if(i != -1 && indexRicerca != 0 && table[i].getRowCount() != 0)
     {
       //System.out.println("entrata ricerca a ritroso");
       String search = cerca.getText();
       //System.out.println(search);
       
       if(search.equals(" ") || search.equals("cerca"))
       {
       //  System.out.println("valore cerca sbagliato");
       }
       else
       {
        // System.out.println("Preciclo e indice -1 " + (indexRicerca - 1));
         for(int j = indexRicerca - 1; j >= 0; j--)
         {
         //  System.out.println("interno ciclo di ricerca indice " + j);
           File_Multimediale app = (File_Multimediale) archivio[i].getEl(j);
           if(app.getTitolo().indexOf(search) != -1 || app.getAutore().indexOf(search) != -1 || app.getAnno().indexOf(search) != -1)
           {
           //  System.out.println("subString trovata");
               indexRicerca = j;
                flagC = true;
             //  System.out.println("Nuovo index ricerca " + indexRicerca);
               break;
           }
         }
         if(flagC == true){
         try{
           table[i].setRowSelectionInterval(indexRicerca,indexRicerca);
         }
         catch(IllegalArgumentException ex)
         {
           JFrame frame = new JFrame("Errore");
           JOptionPane.showMessageDialog(frame,"Impossibile effettuare la ricerca","Errore",JOptionPane.ERROR_MESSAGE);
         }
       }
         else
         {
           if(indexRicerca != -1){
             File_Multimediale app = (File_Multimediale) archivio[i].getEl(indexRicerca);
             if(app.getTitolo().indexOf(search) != -1)
             {
               JFrame frame = new JFrame("Errore");
               JOptionPane.showMessageDialog(frame,"Non ci sono altri elementi corrispondenti al parametro cercato","Errore",JOptionPane.ERROR_MESSAGE);
             }
             
             else{
               JFrame frame = new JFrame("Errore");
               JOptionPane.showMessageDialog(frame,"Elemento non trovato","Errore",JOptionPane.ERROR_MESSAGE);
             }
           }
         }
       //indexRicercaPrec = indexRicerca;
       }
     }
   }
  }
  /** ritorna il modello della tabella richiesto */
  public DefaultTableModel getModel(int i)
  {
   return model[i]; 
  }
  /** ritorna la tabella richiesta */
  public JTable getTable(int i)
  {
    return table[i];
  }
  /** controlla la dimensione dell'array di tabelle, di modelli e della struttura dati 
    * raddoppiandone la dimensione in caso di raggiunta del limite di grandezza
    * sia le tabelle che i modelli e la struttura dati hanno la stessa dimensione
    */
  private void controlla_dimensione()
  {
    if(conta_tabelle == table.length && archivio.length == conta_panel)
    {
      JTable [] app = new JTable[table.length*2];
      DefaultTableModel [] appM = new DefaultTableModel[model.length*2];
      
      //Collezione_Multimediale <File_Multimediale> [] appAR = new Collezione_Multimediale [archivio.length*2];
        Collezione_Multimediale [] appAR = new Collezione_Multimediale [archivio.length*2];
      for(int i = 0; i < conta_tabelle;i++)
      {
        appM[i] = model[i];//model[i];
        app[i] = table[i];//table[i];
      }
      for(int j = conta_tabelle; j < app.length;j++)
      {
        appM[j] = new DefaultTableModel(inst,0);
        app[j] = new JTable(appM[j]);
      }
      for(int i = 0; i < archivio.length;i++)
        appAR[i] = archivio[i];
      
      table = app;
      model = appM;
      archivio = appAR;
    }
  }
  /** Funzione privata che implementa il salvataggio seriale
    * di una certa collezione multimediale
    */
  private boolean salva(String path)
  {
    FileOutputStream f = null;
    ObjectOutputStream c = null;
   
    try
   {
     f = new FileOutputStream (path);
     c = new ObjectOutputStream(f);
   }
   catch(IOException e)
   {
      JFrame frame = new JFrame("Errore");
      JOptionPane.showMessageDialog(frame,"Errore creare file " + e,"Errore",JOptionPane.ERROR_MESSAGE);
      return false;
   }
   
   try
   {
     c.writeObject(archivio[pnM.getSelectedIndex()]);
     c.flush();
     c.close();
   }
   catch(IOException e)
   {
     JFrame frame = new JFrame("Errore");
     JOptionPane.showMessageDialog(frame,"Errore stampa oggetto " + e,"Errore",JOptionPane.ERROR_MESSAGE);
     return false;
   }
   
   //System.out.println("SERIALIZZAZIONE RIUSCITA");
   archivio[pnM.getSelectedIndex()].setSalva(true);
   return true;
  }
  /** funzione privata che implementa la carica di una certa collezione multimediale */
  private boolean carica(String path)
  {
    FileInputStream fin = null;
    ObjectInputStream cino = null;
   
   try
   {
     fin = new FileInputStream(path);
     cino = new ObjectInputStream(fin);
   }
   catch(IOException e)
   {
     JFrame frame = new JFrame("Errore");
     JOptionPane.showMessageDialog(frame,"Errore apertura file " + e,"Errore",JOptionPane.ERROR_MESSAGE);
     return false;
   }
   
   try
   {
     archivio[conta_panel] = (Collezione_Multimediale) cino.readObject();
     cino.close();
   }
   catch(IOException e)
   {
     JFrame frame = new JFrame("Errore");
     JOptionPane.showMessageDialog(frame,"Errore lettura file " + e,"Errore",JOptionPane.ERROR_MESSAGE);
     return false;
   }
   catch(ClassNotFoundException e)
   {
      JFrame frame = new JFrame("Errore");
      JOptionPane.showMessageDialog(frame,"Classe non trovata " + e,"Errore",JOptionPane.ERROR_MESSAGE);
      return false;
   }
   
   //System.out.println("DESERIALIZZAZIONE RIUSCITA");
   
   //for(int i = 0; i < archivio[pnM.getSelectedIndex() + 1].size();i++)
  // {
     //File_Multimediale app = (File_Multimediale) archivio[pnM.getSelectedIndex() + 1].getEl(i);
    // System.out.println(app.toString());
   //}
   return true;
  }
  /** Funzione di supporto che controlla che all'interno di una stessa directory non ci siano file 
    * con lo stesso nome
    */
   private boolean controllo_salvataggio(String [] files,String fileName)
  {
     for(String name : files)
     {
       if(name.equals(fileName))
         return true;
     }
     return false;
  }
   /** Funzione usata dal @Main_Panel per garantire l'integrità della struttura dati, delle tabelle e dei modelli
     * dopo la chiusara di una tab
     */
   public void decreaseContaTlb(int index)
   {
    //aggiungi tracing struttura dati archivio per controllo integrità e coerenza dopo cancellazione di una collezione
    for(int i = index; i < conta_panel; i++)
    {
      model[i] = model[i + 1];
      table[i] = table[i + 1];
      archivio[i] = archivio[i + 1];
    }
    conta_panel--;
    conta_tabelle--;
    model[conta_panel] = new DefaultTableModel(inst,0);
    table[conta_panel] = new JTable(model[conta_panel]);
    // System.out.println("numero panel: " + conta_panel + " numero tabelle " + conta_tabelle);
   }
   public int getNumPanel()
   {
     return conta_panel;
   }
}
