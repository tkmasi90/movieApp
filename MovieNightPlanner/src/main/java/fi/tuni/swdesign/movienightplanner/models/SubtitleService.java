/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

import com.google.gson.annotations.Expose;
import java.util.List;

/**
 * Represents a subtitle service with various attributes such as service
 * details, type, link, etc.
 *
 * @author janii, Copilot
 */
public class SubtitleService {

    /**
     * The service providing the subtitles.
     */
    @Expose
    public Service service;
    
    /**
     * The type of subtitle service (e.g., closed captions, subtitles for the deaf and hard of hearing).
     */
    @Expose
    public String type;
    
    /**
     * The link to the subtitle service.
     */
    @Expose
    public String link;
    
    /**
     * List of subtitles provided by the service.
     */
    @Expose
    public List<Subtitle> subtitles;

    /**
     * Default constructor for SubtitleService.
     */
    public SubtitleService() {
    }

    /**
     * Gets the service details.
     *
     * @return the service details
     */
    public Service getService() {
        return service;
    }

    /**
     * Sets the service details.
     *
     * @param service the new service details
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * Gets the type of the subtitle service.
     *
     * @return the type of the subtitle service
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the subtitle service.
     *
     * @param type the new type of the subtitle service
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the link to the subtitle service.
     *
     * @return the link to the subtitle service
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the link to the subtitle service.
     *
     * @param link the new link to the subtitle service
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets the list of subtitles associated with the subtitle service.
     *
     * @return the list of subtitles
     */
    public List<Subtitle> getSubtitles() {
        return subtitles;
    }

    /**
     * Sets the list of subtitles associated with the subtitle service.
     *
     * @param subtitles the new list of subtitles
     */
    public void setSubtitles(List<Subtitle> subtitles) {
        this.subtitles = subtitles;
    }

    /**
     * Represents a service with various attributes such as ID, name, homepage,
     * theme color code, and image set.
     */
    public static class Service {

        @Expose
        public String id;
        @Expose
        public String name;
        @Expose
        public String homePage;
        @Expose
        public String themeColorCode;

        /**
         * Default constructor for Service.
         */
        public Service() {
        }

        /**
         * Gets the ID of the service.
         *
         * @return the ID of the service
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the ID of the service.
         *
         * @param id the new ID of the service
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Gets the name of the service.
         *
         * @return the name of the service
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name of the service.
         *
         * @param name the new name of the service
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Gets the homepage of the service.
         *
         * @return the homepage of the service
         */
        public String getHomePage() {
            return homePage;
        }

        /**
         * Sets the homepage of the service.
         *
         * @param homePage the new homepage of the service
         */
        public void setHomePage(String homePage) {
            this.homePage = homePage;
        }

        /**
         * Gets the theme color code of the service.
         *
         * @return the theme color code of the service
         */
        public String getThemeColorCode() {
            return themeColorCode;
        }

        /**
         * Sets the theme color code of the service.
         *
         * @param themeColorCode the new theme color code of the service
         */
        public void setThemeColorCode(String themeColorCode) {
            this.themeColorCode = themeColorCode;
        }

    }

    /**
     * Represents a subtitle with closed captions and locale information.
     */
    public static class Subtitle {

        @Expose
        public boolean closedCaptions;
        @Expose
        public Locale locale;

        /**
         * Default constructor for Subtitle.
         */
        public Subtitle() {
        }

        /**
         * Checks if the subtitle has closed captions.
         *
         * @return true if the subtitle has closed captions, false otherwise
         */
        public boolean isClosedCaptions() {
            return closedCaptions;
        }

        /**
         * Sets the closed captions status of the subtitle.
         *
         * @param closedCaptions the new closed captions status
         */
        public void setClosedCaptions(boolean closedCaptions) {
            this.closedCaptions = closedCaptions;
        }

        /**
         * Gets the locale of the subtitle.
         *
         * @return the locale of the subtitle
         */
        public Locale getLocale() {
            return locale;
        }

        /**
         * Sets the locale of the subtitle.
         *
         * @param locale the new locale of the subtitle
         */
        public void setLocale(Locale locale) {
            this.locale = locale;
        }
    }

    /**
     * Represents a locale with a specific language.
     */
    public static class Locale {

        @Expose
        public String language;

        /**
         * Default constructor for Locale.
         */
        public Locale() {
        }

        /**
         * Gets the language of the locale.
         *
         * @return the language of the locale
         */
        public String getLanguage() {
            return language;
        }

        /**
         * Sets the language of the locale.
         *
         * @param language the new language of the locale
         */
        public void setLanguage(String language) {
            this.language = language;
        }
    }
}
