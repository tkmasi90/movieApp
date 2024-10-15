package fi.tuni.swdesign.movienightplanner.models;

import com.google.gson.annotations.Expose;

public class Crew {
    @Expose
    private String credit_id;
    @Expose
    private String department;
    @Expose
    private int gender;
    @Expose
    private int id;
    @Expose
    private String job;
    @Expose
    private String name;
    @Expose
    private String profile_path;

    // Getters and Setters
    public String getCreditId() {
        return credit_id;
    }

    public void setCreditId(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePath() {
        return profile_path;
    }

    public void setProfilePath(String profile_path) {
        this.profile_path = profile_path;
    }
}