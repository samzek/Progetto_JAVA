import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;


/**
 * Panel principale del programma diviso in 3 sezioni sfruttando un BorderLayout 
 * esso rappresenta la GUI di base con cui l'utente si interaggisce
 */
public class Main_Panel extends JPanel implements MouseListener,ActionListener{
  
  private JTabbedPane tabbedPane;
  private BorderLayout main_layout;
  private North_Panel pnNORD;
  private West_Panel pnWEST;
  private JMenuBar menu;
  private JMenu file;
  private Collezione_Multimediale [] archivio;
  private JMenuItem nuovo;
  private JMenuItem apri;
  private JMenuItem salva;
  private JMenuItem stampa;
  private JMenuItem esci;
 
  /** costruttore */
  public Main_Panel(JFrame frame,Collezione_Multimediale [] archivio) {    
    super();
    
    this.archivio = archivio;
    menu = new JMenuBar();
    file = new JMenu("File");
   
    nuovo = new JMenuItem("nuovo");
    apri = new JMenuItem("apri");
    salva = new JMenuItem("salva");
    stampa = new JMenuItem("stampa");
    esci = new JMenuItem("esci");
   
    file.add(nuovo);file.add(apri);file.add(salva);file.add(stampa);file.insertSeparator(6);file.add(esci);
    
    
    nuovo.addActionListener(this);
    apri.addActionListener(this);
    salva.addActionListener(this);
    stampa.addActionListener(this);
    esci.addActionListener(this);
   
    
    menu.add(file);
  
    frame.setJMenuBar(menu);
    
    tabbedPane = new JTabbedPane();
    //tabbedPane.add("tab1",scrol);
    main_layout = new BorderLayout();
    
    tabbedPane.addMouseListener(this);
    setLayout(main_layout);
    
    
    pnNORD = new North_Panel(tabbedPane,this,archivio);
    
    pnWEST = new West_Panel(frame,archivio,tabbedPane,pnNORD); 
       
   // add(tabbedPane,main_layout.CENTER);
    add(tabbedPane,main_layout.CENTER);
    add(pnNORD,main_layout.NORTH);
    add(pnWEST,main_layout.WEST);
  }
  public West_Panel getWEST()
  {
    return pnWEST;
  }
  public North_Panel getNORTH()
  {
    return pnNORD;
  }
  /** gestione del doppio click sulla tabbedpane permettendo la chiususra di uno specifica tab 
    * essa invoca la funzione @decreaseContaTlb essa è una funzione definita nella classe ::North_Panel
    * e mi garantisce l'integrità della struttura dati e la gui dopo un eliminazione 
    */
  public void mouseClicked(MouseEvent e)
  {
    if(e.getClickCount() == 2)
    {
      int j = tabbedPane.getSelectedIndex();
      if(j != -1)
      {
      tabbedPane.remove(j);
      pnNORD.decreaseContaTlb(j);
      }
    }
  }
  /** Gestione dei menu della JMenuBar ogni menu in realtà non implementa nessuna funzionalità essi sono solo
    * un wrapper per le funzionalità usate dai pulsanti del ::North_Panel tutto questo è fatto sfruttando la stringa
    * rappresentante il comando non il riferimento al comando stesso
    */
  public void actionPerformed(ActionEvent e)
  {
    Object opt = e.getSource();
    if(opt == esci)
    {
     
       boolean noExit = false;
       int contaPanel = pnNORD.getNumPanel();
       if(contaPanel == 0)
       {
        System.exit(0);
       }
       for(int i = 0; i < contaPanel;i++)
       {
         if(archivio[i].getSalvata() == false)
         {
           JFrame frame2 = new JFrame("Salva");
           int n = JOptionPane.showConfirmDialog(frame2,"Ogni collezione non salvata andrà persa vuoi uscire?","Uscita",JOptionPane.YES_NO_OPTION);
           if(n == JOptionPane.YES_OPTION)
             break;
           else{
             noExit = true;
             break;
           }
         }
       }
       if(noExit != true)
                System.exit(0);
    }
    else if(opt == nuovo)
    {
      pnNORD.actionPerformed(e);
    }
    else if(opt == apri)
    {
      pnNORD.actionPerformed(e);
    }
    else if(opt == salva)
    {
      pnNORD.actionPerformed(e);
    }
    else if(opt == stampa)
    {
      pnNORD.actionPerformed(e);
    }
  }
  public void mouseExited(MouseEvent e){}
  public void mouseEntered(MouseEvent e){}
  public void mouseReleased(MouseEvent e){}
  public void mousePressed(MouseEvent e){}
  
}
