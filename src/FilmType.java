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
   
   public int getId() {
      return this.id;
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

   public void setId(int id) {
      this.id = id;
   }
}
