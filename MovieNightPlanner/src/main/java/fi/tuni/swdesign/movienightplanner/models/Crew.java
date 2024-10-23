package fi.tuni.swdesign.movienightplanner.models;

import com.google.gson.annotations.Expose;

/**
 * Represents a crew member in a movie.
 */
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

    /**
     * Returns the credit ID of the crew member.
     *
     * @return the credit ID of the crew member
     */
    public String getCreditId() {
        return credit_id;
    }

    /**
     * Sets the credit ID of the crew member.
     *
     * @param credit_id the credit ID to set
     */
    public void setCreditId(String credit_id) {
        this.credit_id = credit_id;
    }

    /**
     * Returns the department of the crew member.
     *
     * @return the department of the crew member
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the department of the crew member.
     *
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Returns the gender of the crew member.
     *
     * @return the gender of the crew member
     */
    public int getGender() {
        return gender;
    }

    /**
     * Sets the gender of the crew member.
     *
     * @param gender the gender to set
     */
    public void setGender(int gender) {
        this.gender = gender;
    }

    /**
     * Returns the ID of the crew member.
     *
     * @return the ID of the crew member
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the crew member.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the job of the crew member.
     *
     * @return the job of the crew member
     */
    public String getJob() {
        return job;
    }

    /**
     * Sets the job of the crew member.
     *
     * @param job the job to set
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * Returns the name of the crew member.
     *
     * @return the name of the crew member
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the crew member.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the profile path of the crew member.
     *
     * @return the profile path of the crew member
     */
    public String getProfilePath() {
        return profile_path;
    }

    /**
     * Sets the profile path of the crew member.
     *
     * @param profile_path the profile path to set
     */
    public void setProfilePath(String profile_path) {
        this.profile_path = profile_path;
    }
}