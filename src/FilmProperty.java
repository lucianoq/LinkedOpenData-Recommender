/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Simone
 */
public class FilmProperty {
   
   private String title;
   private String uri;

   public FilmProperty(String title,String uri) {
      this.title=title;
      this.uri=uri;
   }

   public FilmProperty() {
   }
   
   public String getUri() {
      return this.uri;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public void setUri(String uri) {
      this.uri = uri;
   }

}
