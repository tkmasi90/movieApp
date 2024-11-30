package fi.tuni.swdesign.movienightplanner.models;

import com.google.gson.annotations.Expose;
import java.util.List;

/**
 * Represents the credits of a movie, including cast and crew.
 *
 * @author kian, Copilot
 */
public class Credits {
    /**
     * Unique identifier for the credits entry.
     */
    @Expose
    private int id;
    
    /**
     * List of cast members involved in the production.
     */
    @Expose
    private List<Cast> cast;
    
    /**
     * List of crew members involved in the production.
     */
    @Expose
    private List<Crew> crew;

    /**
     * Returns the ID of the credits.
     *
     * @return the ID of the credits
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the credits.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the list of cast members.
     *
     * @return the list of cast members
     */
    public List<Cast> getCast() {
        return cast;
    }

    /**
     * Sets the list of cast members.
     *
     * @param cast the list of cast members to set
     */
    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    /**
     * Returns the list of crew members.
     *
     * @return the list of crew members
     */
    public List<Crew> getCrew() {
        return crew;
    }

    /**
     * Sets the list of crew members.
     *
     * @param crew the list of crew members to set
     */
    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    /**
     * Returns the name of the director from the crew list.
     *
     * @return the name of the director, or null if not found
     */
    public String getDirector() {
        for (Crew crewMember : crew) {
            if (crewMember.getJob().equals("Director")) {
                return crewMember.getName();
            }
        }
        return null;
    }

    /**
     * Returns the name of the writer from the crew list.
     *
     * @return the name of the writer, or null if not found
     */
    public String getWriter() {
        for (Crew crewMember : crew) {
            if (crewMember.getJob().equals("Novel") && crewMember.getDepartment().equals("Writing")) {
                return crewMember.getName();
            }
        }
        return null;
    }
}