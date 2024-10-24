package fi.tuni.swdesign.movienightplanner.models;

import com.google.gson.annotations.Expose;

/**
 * Represents a cast member in a movie.
 *
 * @author kian, Copilot
 */
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

    /**
     * Returns whether the cast member is an adult.
     *
     * @return true if the cast member is an adult, false otherwise
     */
    public boolean isAdult() {
        return adult;
    }

    /**
     * Sets whether the cast member is an adult.
     *
     * @param adult true if the cast member is an adult, false otherwise
     */
    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    /**
     * Returns the gender of the cast member.
     *
     * @return the gender of the cast member
     */
    public int getGender() {
        return gender;
    }

    /**
     * Sets the gender of the cast member.
     *
     * @param gender the gender to set
     */
    public void setGender(int gender) {
        this.gender = gender;
    }

    /**
     * Returns the ID of the cast member.
     *
     * @return the ID of the cast member
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the cast member.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the department the cast member is known for.
     *
     * @return the department the cast member is known for
     */
    public String getKnownForDepartment() {
        return known_for_department;
    }

    /**
     * Sets the department the cast member is known for.
     *
     * @param known_for_department the department to set
     */
    public void setKnownForDepartment(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    /**
     * Returns the name of the cast member.
     *
     * @return the name of the cast member
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the cast member.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the original name of the cast member.
     *
     * @return the original name of the cast member
     */
    public String getOriginalName() {
        return original_name;
    }

    /**
     * Sets the original name of the cast member.
     *
     * @param original_name the original name to set
     */
    public void setOriginalName(String original_name) {
        this.original_name = original_name;
    }

    /**
     * Returns the popularity of the cast member.
     *
     * @return the popularity of the cast member
     */
    public double getPopularity() {
        return popularity;
    }

    /**
     * Sets the popularity of the cast member.
     *
     * @param popularity the popularity to set
     */
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    /**
     * Returns the profile path of the cast member.
     *
     * @return the profile path of the cast member
     */
    public String getProfilePath() {
        return profile_path;
    }

    /**
     * Sets the profile path of the cast member.
     *
     * @param profile_path the profile path to set
     */
    public void setProfilePath(String profile_path) {
        this.profile_path = profile_path;
    }

    /**
     * Returns the cast ID of the cast member.
     *
     * @return the cast ID of the cast member
     */
    public int getCastId() {
        return cast_id;
    }

    /**
     * Sets the cast ID of the cast member.
     *
     * @param cast_id the cast ID to set
     */
    public void setCastId(int cast_id) {
        this.cast_id = cast_id;
    }

    /**
     * Returns the character played by the cast member.
     *
     * @return the character played by the cast member
     */
    public String getCharacter() {
        return character;
    }

    /**
     * Sets the character played by the cast member.
     *
     * @param character the character to set
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    /**
     * Returns the credit ID of the cast member.
     *
     * @return the credit ID of the cast member
     */
    public String getCreditId() {
        return credit_id;
    }

    /**
     * Sets the credit ID of the cast member.
     *
     * @param credit_id the credit ID to set
     */
    public void setCreditId(String credit_id) {
        this.credit_id = credit_id;
    }

    /**
     * Returns the order of the cast member.
     *
     * @return the order of the cast member
     */
    public int getOrder() {
        return order;
    }

    /**
     * Sets the order of the cast member.
     *
     * @param order the order to set
     */
    public void setOrder(int order) {
        this.order = order;
    }
}