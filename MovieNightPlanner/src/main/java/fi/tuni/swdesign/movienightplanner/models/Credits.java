package fi.tuni.swdesign.movienightplanner.models;

import com.google.gson.annotations.Expose;
import java.util.List;

public class Credits {
    @Expose
    private int id;
    @Expose
    private List<Cast> cast;
    @Expose
    private List<Crew> crew;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public String getDirector() {
        for (Crew crewMember : crew) {
            if (crewMember.getJob().equals("Director")) {
                return crewMember.getName();
            }
        }
        return null;
    }

    public String getWriter() {
        for (Crew crewMember : crew) {
            if (crewMember.getJob().equals("Novel") && crewMember.getDepartment().equals("Writing")) {
                return crewMember.getName();
            }
        }
        return null;
    }
}