package fi.tuni.swdesign.movienightplanner.models;

import com.google.gson.annotations.Expose;

public class Cast {
    @Expose
    private boolean adult;
    @Expose
    private int gender;
    @Expose
    private int id;
    @Expose
    private String known_for_department;
    @Expose
    private String name;
    @Expose
    private String original_name;
    @Expose
    private double popularity;
    @Expose
    private String profile_path;
    @Expose
    private int cast_id;
    @Expose
    private String character;
    @Expose
    private String credit_id;
    @Expose
    private int order;

    // Getters and Setters
    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKnownForDepartment() {
        return known_for_department;
    }

    public void setKnownForDepartment(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return original_name;
    }

    public void setOriginalName(String original_name) {
        this.original_name = original_name;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getProfilePath() {
        return profile_path;
    }

    public void setProfilePath(String profile_path) {
        this.profile_path = profile_path;
    }

    public int getCastId() {
        return cast_id;
    }

    public void setCastId(int cast_id) {
        this.cast_id = cast_id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCreditId() {
        return credit_id;
    }

    public void setCreditId(String credit_id) {
        this.credit_id = credit_id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}