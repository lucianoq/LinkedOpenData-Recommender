/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Simone
 */
public class FilmProperties {
   
   private String title;
   private String uri;

   public FilmProperties(String title,String uri) {
      this.title=title;
      this.uri=uri;
   }

   public FilmProperties() {
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

}
