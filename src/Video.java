import java.io.*;
/**
 * Classe brani rappresentante un oggetto video di una data estensione
 * essa è una estensione della classa astratta ::File_Multimediale ridefinisce alcune 
 * funzioni della super classe.
 */
public class Video extends File_Multimediale implements Serializable {
   
  private float durata;
  
  public Video(String titolo,String autore,int anno,String pdfile,float durata) { 
    super(titolo,autore,anno,pdfile);
    this.durata = durata;
  }
  /** ritorna gli attributi del della classe */
  public String get_attributi()
  {
    return "\n Durata:" + durata;
  }
  public void set_durata(Float durata)
  {
    this.durata = durata;
  } 
  /** setta gli attributi della classe */
  public void set_attributi(String [] valori)
  {
    set_durata(Float.parseFloat(valori[0]));
  }
}
