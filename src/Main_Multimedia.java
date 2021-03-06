import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Toolkit;

/**
 * Librerie Multimediali 
 * @mainpage
 * @author Samuele Zecchini<BR>
 *
 * Questo progetto permette la gestione di molteplici librerie multimediali.
 * Per libreria multimediale si intende un insieme di file di tipo: audio, video, ebook.
 * Il programma permette la creazione di una libreria e la relativa gestione di essa permettendo 
 * di inserire di un file, cancellazione di un file, modifica, riproduzione, ricerca, stampa ecc.
 */

/** Main class del progetto crea il frame principale e il relativo panel
  * effuttua un override della funzione windowClosing sfruttando una inner class
  * di modo da potere gestire in maniera efficace e coerente l'uscita dal programma
  */
public class Main_Multimedia{
  
  private static Main_Panel p;
  private static Collezione_Multimediale [] archivio;
  private static JFrame f;
  
  public static void main(String[] args) { 
    
    f = new JFrame("Libreria Multimediale");
    f.pack();
 
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int height = screenSize.height;
    int width = screenSize.width;
    f.setSize(width/2, height/2);
    f.setLocationRelativeTo(null);

    archivio = new Collezione_Multimediale[10];
    
    p = new Main_Panel(f,archivio);
    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
              North_Panel pn = p.getNORTH();
              boolean noExit = false;
              int contaPanel = pn.getNumPanel();
              if(contaPanel == 0)
              {
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              }
              for(int i = 0; i < contaPanel;i++)
              {
                if(archivio[i].getSalvata() == false)
                {
                  JFrame frame2 = new JFrame("Salva");
                  int n = JOptionPane.showConfirmDialog(frame2,"Ogni collezione non salvata andra persa vuoi uscire?","Uscita",JOptionPane.YES_NO_OPTION);
                  if(n == JOptionPane.YES_OPTION)
                   break;
                  else{
                    noExit = true;
                    break;
                  }
                }
              }
              if(noExit != true)
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
    };
    f.addWindowListener(exitListener);
    f.add(p);
    f.setVisible(true);
  }  

}
