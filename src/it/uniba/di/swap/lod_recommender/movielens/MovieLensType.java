package it.uniba.di.swap.lod_recommender.movielens;

public class MovieLensType {
    private int idUser;
    private int idItem;
    private int rating;

    public MovieLensType(int idUser, int idItem, int rating) {
        this.idUser = idUser;
        this.idItem = idItem;
        this.rating = rating;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdItem() {
        return idItem;
    }

    public int getRating() {
        return rating;
    }
}
