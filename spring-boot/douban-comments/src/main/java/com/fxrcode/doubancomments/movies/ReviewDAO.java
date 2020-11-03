package com.fxrcode.doubancomments.movies;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;

@Entity(name="SHORT_REVIEW")
public class ReviewDAO {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String user_id;

    @NotNull
    private String user_name;

    @NotNull
    private int stars;
    @NotNull
    private LocalDate create_time;
    private int vote_count;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String short_review;

    protected ReviewDAO(){}
    public ReviewDAO(String user_id, String user_name, int stars, LocalDate create_time, String short_review, int vote_count) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.stars = stars;
        this.create_time = create_time;
        this.short_review = short_review;
        this.vote_count = vote_count;
    }

    @Override
    public String toString() {
        return String.format(
                "Review[user_id=%s, user=%s, stars=%d, time=%s, short_review=%s, votes=%d",
                user_id, user_name, stars, create_time, short_review, vote_count
        );
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public LocalDate getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDate create_time) {
        this.create_time = create_time;
    }

    public String getShort_review() {
        return short_review;
    }

    public void setShort_review(String short_review) {
        this.short_review = short_review;
    }
}
