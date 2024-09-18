/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner;

import java.util.List;
import com.google.gson.annotations.Expose;

/**
 *
 * @author janii, Copilot
 */
public class Movie {
private boolean adult;
        @Expose
        private String backdrop_path;
        @Expose
        private List<Integer> genre_ids;
        @Expose
        private int id;
        @Expose
        private String original_language;
        @Expose
        private String original_title;
        @Expose
        private String overview;
        @Expose
        private double popularity;
        @Expose
        private String poster_path;
        @Expose
        private String release_date;
        @Expose
        private String title;
        @Expose
        private boolean video;
        @Expose
        private double vote_average;
        @Expose
        private int vote_count;

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public String getBackdropPath() {
            return backdrop_path;
        }

        public void setBackdropPath(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public List<Integer> getGenreIds() {
            return genre_ids;
        }

        public void setGenreIds(List<Integer> genre_ids) {
            this.genre_ids = genre_ids;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOriginalLanguage() {
            return original_language;
        }

        public void setOriginalLanguage(String original_language) {
            this.original_language = original_language;
        }

        public String getOriginalTitle() {
            return original_title;
        }

        public void setOriginalTitle(String original_title) {
            this.original_title = original_title;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public String getPosterPath() {
            return poster_path;
        }

        public void setPosterPath(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getReleaseDate() {
            return release_date;
        }

        public void setReleaseDate(String release_date) {
            this.release_date = release_date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isVideo() {
            return video;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public double getVoteAverage() {
            return vote_average;
        }

        public void setVoteAverage(double vote_average) {
            this.vote_average = vote_average;
        }

        public int getVoteCount() {
            return vote_count;
        }

        public void setVoteCount(int vote_count) {
            this.vote_count = vote_count;
        }
    }
