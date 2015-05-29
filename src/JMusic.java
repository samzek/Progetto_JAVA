import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.Player;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.*;
import javazoom.jl.decoder.JavaLayerException;
import java.lang.Thread;
import java.lang.Thread.State;

/**
 * Player musicale, riproduce il file .mp3 scelto dall'utente
 */
public class JMusic extends JFrame implements ActionListener,WindowListener {
  
  private JButton start,pause,stop;
  private JLabel txtTitolo;
  private JPanel pn;
  private File nameF;
  private JPanel panelSud;
  private Player pl;
  private FileInputStream str;
  private JFrame frPrinc;
  private Thread thread;
  private boolean bStart = false;
  private boolean bPause = false;
  private boolean op = false;
  private Boolean flag = true;
  
  public JMusic(File file,JFrame frPr) { 
    super();
    
    frPrinc = frPr;
    nameF = file;
    
    op = open();
   
    if (op != false)
    {
    start = new JButton(new ImageIcon("../image/Play.png"));
    stop = new JButton(new ImageIcon("../image/Stop.png"));
    pause = new JButton(new ImageIcon("../image/Pause.png"));
    txtTitolo = new JLabel(nameF.getName());
   
   
    pn = new JPanel(new GridLayout(2,1));
    panelSud = new JPanel(new FlowLayout());
    panelSud.add(start);panelSud.add(pause); panelSud.add(stop); panelSud.add(stop);
    
    start.addActionListener(this);
    pause.addActionListener(this);
    stop.addActionListener(this);
    
    pn.add(txtTitolo);
    pn.add(panelSud);
    //JFrame
    pack();
    addWindowListener(this);
    setSize(new Dimension(300,200));
    setLocationRelativeTo(null);
    setVisible(true);
    add(pn);
    }
    else
       frPrinc.setVisible(true);
  }
  /** Funzione che gestisce la pressione dei pulsanti da parte dell'utente
    * il pulsante "start" lancia in esecuzione un certo file mp3 creando un thread apposito
    * gli altri 2 pulsanti semplicemente gestiscono il comportamente del thread fermandolo(pause) o bloccandolo(stop)
    */
  public void actionPerformed(ActionEvent e)
  {
    Object o = e.getSource();

      if(o == start)
      {
        if(bPause == true)
        {
          thread.resume();
        }
        else
        {
          if(bStart != true)
          {
            op = open();
          thread = new Thread(){
      
          public void run(){
            try{
              pl.play();
            }
            catch(JavaLayerException ep)
            {
               JFrame frame = new JFrame("Errore");
               JOptionPane.showMessageDialog(frame,"File non trovato","Errore",JOptionPane.ERROR_MESSAGE);
               flag = false;
            }
          };
          };
          //op = open();
          if(op == true && flag == true)
          {
            bStart = true;
            thread.start();
          }
        }
        }
      }     
      else if(o == stop)
      {
        //thread.resume();
        pl.close();
        bStart = false;
        bPause = false;
      }
      else if(o == pause)
      {
        bPause = true;
       thread.suspend();
      }
  }
 /** funzione privata che apre il un FileInputStream e lo passa al player sono
   * presenti inoltre controlli sull'integrità del path e sulla validità del file
   */
  private boolean open()
  {
    try{
    str = new FileInputStream(nameF.getAbsolutePath());
    }
    catch(IOException e)
    {
      JFrame frame = new JFrame("Errore");
       JOptionPane.showMessageDialog(frame,"Impossibile aprire il file","Errore",JOptionPane.ERROR_MESSAGE);
       return false;
    }
    catch(NullPointerException ex)
    {
       JFrame frame = new JFrame("Errore");
       JOptionPane.showMessageDialog(frame,"Percorso del file errato","Errore",JOptionPane.ERROR_MESSAGE);
       return false;
    }
    try{
    pl = new Player(str);
    }
    catch(JavaLayerException ex)
    {
      JFrame frame = new JFrame("Errore");
       JOptionPane.showMessageDialog(frame,"Errore caricametno player","Errore",JOptionPane.ERROR_MESSAGE);
       return false;
    } 
    catch(NullPointerException ex)
    {
       JFrame frame = new JFrame("Errore");
       JOptionPane.showMessageDialog(frame,"Percorso del file errato","Errore",JOptionPane.ERROR_MESSAGE);
       return false;
    }
    return true;
  }
  public void windowClosed(WindowEvent e){}
  public void windowDeactivated(WindowEvent e){}
  public void windowActivated(WindowEvent e){}
  public void windowDeiconified(WindowEvent e){}
  public void windowIconified(WindowEvent e){}
  public void windowClosing(WindowEvent e){ pl.close(); frPrinc.setVisible(true);}
  public void windowOpened(WindowEvent e){}
}
