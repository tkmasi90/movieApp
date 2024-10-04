/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;

/**
 *
 * @author janii, Copilot
 */
public class Movie {
        @Expose
        public boolean adult;
        @Expose
        public String backdrop_path;
        @Expose
        public List<Integer> genre_ids;
        @Expose
        public int id;
        @Expose
        public String original_language;
        @Expose
        public String original_title;
        @Expose
        public String overview;
        @Expose
        public double popularity;
        @Expose
        public String poster_path;
        @Expose
        public String release_date;
        @Expose
        public String title;
        @Expose
        public boolean video;
        @Expose
        public double vote_average;
        @Expose
        public int vote_count;
        
        private ArrayList<StreamingProvider> streamingProviders;
        
        public Movie() {
            this.streamingProviders = new ArrayList<>();
        }

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
        
        public void addStreamingProvider(StreamingProvider provider) {
            if (this.streamingProviders == null) {
                this.streamingProviders = new ArrayList<>();
            }
            this.streamingProviders.add(provider);
        }
        
        public List<StreamingProvider> getStreamingProviders() {
            return this.streamingProviders;
        }
    }
