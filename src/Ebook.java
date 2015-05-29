import java.io.*;
/**
 * Classe ebook rappresentante un generico file pdf essa specializza 
 * la classe ::File_Multimediale e ne ridefinisce alcuni metodi.
 */
public class Ebook extends File_Multimediale implements Serializable {
  
  private int nr_pagine;
  
  public Ebook(String titolo,String autore,int anno,String pdfile,int nr_pagine) { 
    super(titolo,autore,anno,pdfile);
    this.nr_pagine = nr_pagine;
  }
  /** Override della classe presente in ::File_Multimediale ritorna gli attributi
    * di classe di un certo file pdf
    */
  public String get_attributi()
  {
    return "\n Numero pagine:" + nr_pagine;
  }
  public void set_numPagine(int numPagine)
  {
    nr_pagine = numPagine;
  }  
  /** Override della classe presente in ::File_Multimediale setta gli attributi 
    * della classse
    */
  public void set_attributi(String [] valori)
  {
    nr_pagine = Integer.parseInt(valori[0]);
  }
}
