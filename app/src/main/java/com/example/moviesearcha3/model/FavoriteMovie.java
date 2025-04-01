package com.example.moviesearcha3.model;

public class FavoriteMovie {
    private String title;
    private String type;
    private String poster;
    private String rated;
    private String description;
    private String imdbID;

    public FavoriteMovie(){};

    public FavoriteMovie(String title, String type, String poster, String rated, String description, String imdbID) {
        this.title = title;
        this.type = type;
        this.poster = poster;
        this.rated = rated;
        this.description = description;
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
