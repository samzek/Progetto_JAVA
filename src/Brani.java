import java.io.*;
/**
 * Classe brani rappresentante un oggetto musicale di una data estensione
 * essa è una estensione della classa astratta ::File_Multimediale ridefinisce alcune 
 * funzioni della super classe.
 */
public class Brani extends File_Multimediale implements Serializable {
  
  private float durata;
  private float fdc;
  
  public Brani(String titolo,String autore,int anno,String pdfile,float durata,float fdc) { 
    super(titolo,autore,anno,pdfile);
    this.durata = durata;
    this.fdc = fdc;
  }
  /** Override della funzione definita in ::File_Multimediale ritorna una stringa
    * contenente gli attributi del brano
    */
  public String get_attributi()
  {
    return "\n Durata:" + durata + "\n Frequenza di campionamento:" + fdc;
  }
  public void set_durata(int durata)
  {
    this.durata = durata;
  }
  public void set_fdc(int fdc)
  {
    this.fdc = fdc;
  }
  /** Override della funzione della funzione definita in ::File_Multimediale: setta i campi
    * caratteristici del brano
    */
  public void set_attributi(String [] valori)
  {
    durata = Float.parseFloat(valori[0]);
    fdc = Float.parseFloat(valori[1]);
  }
}
