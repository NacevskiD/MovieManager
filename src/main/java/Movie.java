import javax.swing.*;
import java.util.ArrayList;

public class Movie {
    String title;
    boolean adult;
    String description;
    int id;
    String date;
    String pictureURL;
    String tagLine;
    String imdb;
    String rating;
    String ytLink;
    String voteCount;
    ArrayList<String> actors;
    ImageIcon icon;
    double userRating;

    public Movie() {


    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public ImageIcon getIcon() {

        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getYtLink() {
        return ytLink;
    }

    public void setYtLink(String ytLink) {
        this.ytLink = ytLink;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
}