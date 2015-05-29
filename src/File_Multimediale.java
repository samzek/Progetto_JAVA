import java.io.*;
/**
 * Classe padre delle classi: ::Ebook,::Video,::Brani essa rappresenta un sorta di interfaccia
 * a tutte le classi figle, sfruttando l'eridatierà e il poliforfismo, essa è un punto di accesso
 * comune per tutte le classi figle tramite essa modifico i parametri delle singole classi e ne stampo i valori.
 * Inoltre contiene i campi comuni a tutte le classi
 */
public abstract class File_Multimediale implements Serializable {
  
  /** campi comuni */
  private String titolo,autore,pdfile;
  private int anno;
  static final long serialVersionUID = 7452715190087742323L;
  
  /** Costruttore della classe astratta */
  public File_Multimediale(String titolo,String autore,int anno,String pdfile) { 
   this.titolo = titolo;
   this.autore = autore;
   this.anno = anno;
   this.pdfile = pdfile;
  }
 /** setta il titolo del file */
  public void set_titolo(String titolo)
  {
    this.titolo = titolo;
  }
  /** setta l'autore del file */
  public void set_autore(String autore)
  {
    this.autore = autore;
  }
  /** setta l'anno del file */
  public void set_anno(int anno)
  {
    this.anno = anno;
  }
  /** classe astatta redifinita in maniera specifica in ogni classe figli */
  public abstract String get_attributi();
  /** classe astatta redifinita in maniera specifica in ogni classe figli */
  public abstract void set_attributi(String [] valori);
  
  /** ritorna un array di stringhe rappresentante gli attributi di una file(quelli comuni a tutti) 
    * essa viene usata per la scrittura delle righe nella tabella
    */
  public String [] row()
  {
    String anno1 = "" + anno;
    String [] str = {titolo,autore,anno1,pdfile};
    return str;
  }
  /** funzione di stampa unificata che stampi i campi di un certo file distinguendoli in base alla classe di appertenenza
    * sfrutta la funzione ::get_attributi() che essendo ridefinita in ogni classe figli stampa un valore diverso a seconda
    * di chi la invoca
    */
  public String toString()
  {
    return "Titolo: " + titolo + "\n Autore: " + autore + "\n Anno: " + anno + get_attributi() + "\n PdF: " + pdfile;
  }
  public String getPdf()
  {
    return pdfile;
  }
  public String getTitolo()
  {
    return titolo;
  }
  public String getAutore()
  {
    return autore;
  }
  public String getAnno()
  {
    return "" + anno;
  }
}
