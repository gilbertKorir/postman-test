package models;

import java.util.Objects;

public class Review implements Comparable<Review>{
    private String content;
    private String writtenBy;
    private int rating;
    private int id;
    private long createdat;
    private String formattedCreatedAt;
    private int restaurantId; //will be used to connect Restaurant to Review (one-to-many)

    public Review(String content, String writtenBy, int rating, int restaurantId) {
        this.content = content;
        this.writtenBy = writtenBy;
        this.rating = rating;
        this.restaurantId = restaurantId;
        this.createdat = System.currentTimeMillis();
        setFormattedCreatedAt();
    }

    @Override
    public int compareTo(Review o) {
        if (this.createdat < o.createdat)
        {
            return -1; //this object was made earlier than the second object.
        }
        else if (this.createdat > o.createdat){ //this object was made later than the second object
            return 1;
        }
        else {
            return 0; //they were made at the same time, which is very unlikely, but mathematically not impossible.
        }
    }

    public String getContent() {
        return content;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public int getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }
    public long getCreatedat() {
        return createdat;
    }

    public void setCreatedat() {
        this.createdat = System.currentTimeMillis(); // It'll become clear soon why we need this explicit setter
    }

    public String getFormattedCreatedAt(){
        String someString = "";
        return someString; //more on this in a sec
    }

    public void setFormattedCreatedAt(){
        this.formattedCreatedAt = "some time";
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return rating == review.rating &&
                id == review.id &&
                restaurantId == review.restaurantId &&
                Objects.equals(content, review.content) &&
                Objects.equals(writtenBy, review.writtenBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, writtenBy, rating, id, restaurantId);
    }
}