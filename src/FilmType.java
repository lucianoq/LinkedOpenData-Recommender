/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Simone
 */
public class FilmType {

   private int id;
   private String title;
   private String uri;

   public FilmType(int id,String title,String uri) {
      this.id=id;
      this.title=title;
      this.uri=uri;
   }

   public FilmType() {
   }
   
   public int get_id() {
      return this.id;
   }

   public String get_uri() {
      return this.uri;
   }

   public String get_title() {
      return this.title;
   }

   public void set_title(String title) {
      this.title = title;
   }

   public void set_uri(String uri) {
      this.uri = uri;
   }

   public void set_id(int id) {
      this.id = id;
   }
}
