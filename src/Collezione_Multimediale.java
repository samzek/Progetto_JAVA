import java.util.Vector;
import java.io.*;

/**
 * Struttura dati contenitore creata sfruttando le potenzialità dei vector e dei generics
 * da un punto di vista pratico dovrebbe creare un "array di generics", che permette di gestire 
 * più collezioni multimediali, all'interno di una collezione il vector mantine un array di veri file multimediali
 * rappresentanti il contenuto della collezione.
 */
public class Collezione_Multimediale<E> implements Serializable {
  
  private Vector<E> file;
  /** flag di stato che mi dice se la collezione è salvata o meno */
  private boolean salvata = true;
  
  /** costruttore */
  public Collezione_Multimediale() { 
    file = new Vector<E>(5);
  }
  /** aggiunge un el. al vector */
  public void add(E el)
  {
    file.add(el);
  }
  /** remuove un el. dal vector */ 
 public void rimuovi(E el)
 {
   file.remove(el);
 }
 /** ritorna una elemento di tipo E */
 public E getEl(int index)
 {
   return (E)file.get(index);
 }
 /** ritorna la grandezza del vector */
 public int size()
 {
   return file.size();
 }
 public void setSalva(boolean val)
 {
   salvata = val;
 }
 public boolean getSalvata()
 {
   return salvata;
 }
}
