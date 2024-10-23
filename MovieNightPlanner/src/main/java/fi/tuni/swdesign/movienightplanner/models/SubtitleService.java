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

    @Expose
    public Service service;
    @Expose
    public String type;
    @Expose
    public String link;
    @Expose
    public String quality;
    @Expose
    public List<Audio> audios;
    @Expose
    public List<Subtitle> subtitles;
    @Expose
    public Price price;
    @Expose
    public boolean expiresSoon;
    @Expose
    public long availableSince;

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
     * Gets the quality of the subtitle service.
     *
     * @return the quality of the subtitle service
     */
    public String getQuality() {
        return quality;
    }

    /**
     * Sets the quality of the subtitle service.
     *
     * @param quality the new quality of the subtitle service
     */
    public void setQuality(String quality) {
        this.quality = quality;
    }

    /**
     * Gets the list of audios associated with the subtitle service.
     *
     * @return the list of audios
     */
    public List<Audio> getAudios() {
        return audios;
    }

    /**
     * Sets the list of audios associated with the subtitle service.
     *
     * @param audios the new list of audios
     */
    public void setAudios(List<Audio> audios) {
        this.audios = audios;
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
     * Gets the price of the subtitle service.
     *
     * @return the price of the subtitle service
     */
    public Price getPrice() {
        return price;
    }

    /**
     * Sets the price of the subtitle service.
     *
     * @param price the new price of the subtitle service
     */
    public void setPrice(Price price) {
        this.price = price;
    }

    /**
     * Checks if the subtitle service expires soon.
     *
     * @return true if the subtitle service expires soon, false otherwise
     */
    public boolean isExpiresSoon() {
        return expiresSoon;
    }

    /**
     * Sets the expiration status of the subtitle service.
     *
     * @param expiresSoon the new expiration status
     */
    public void setExpiresSoon(boolean expiresSoon) {
        this.expiresSoon = expiresSoon;
    }

    /**
     * Gets the availability date of the subtitle service.
     *
     * @return the availability date
     */
    public long getAvailableSince() {
        return availableSince;
    }

    /**
     * Sets the availability date of the subtitle service.
     *
     * @param availableSince the new availability date
     */
    public void setAvailableSince(long availableSince) {
        this.availableSince = availableSince;
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
        @Expose
        public ImageSet imageSet;

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

        /**
         * Gets the image set of the service.
         *
         * @return the image set of the service
         */
        public ImageSet getImageSet() {
            return imageSet;
        }

        /**
         * Sets the image set of the service.
         *
         * @param imageSet the new image set of the service
         */
        public void setImageSet(ImageSet imageSet) {
            this.imageSet = imageSet;
        }
    }

    /**
     * Represents an image set with different themes.
     */
    public static class ImageSet {

        @Expose
        public String lightThemeImage;
        @Expose
        public String darkThemeImage;
        @Expose
        public String whiteImage;

        /**
         * Default constructor for ImageSet.
         */
        public ImageSet() {
        }

        /**
         * Gets the light theme image.
         *
         * @return the light theme image
         */
        public String getLightThemeImage() {
            return lightThemeImage;
        }

        /**
         * Sets the light theme image.
         *
         * @param lightThemeImage the new light theme image
         */
        public void setLightThemeImage(String lightThemeImage) {
            this.lightThemeImage = lightThemeImage;
        }

        /**
         * Gets the dark theme image.
         *
         * @return the dark theme image
         */
        public String getDarkThemeImage() {
            return darkThemeImage;
        }

        /**
         * Sets the dark theme image.
         *
         * @param darkThemeImage the new dark theme image
         */
        public void setDarkThemeImage(String darkThemeImage) {
            this.darkThemeImage = darkThemeImage;
        }

        /**
         * Gets the white image.
         *
         * @return the white image
         */
        public String getWhiteImage() {
            return whiteImage;
        }

        /**
         * Sets the white image.
         *
         * @param whiteImage the new white image
         */
        public void setWhiteImage(String whiteImage) {
            this.whiteImage = whiteImage;
        }
    }

    /**
     * Represents an audio with a specific language.
     */
    public static class Audio {

        @Expose
        public String language;

        /**
         * Default constructor for Audio.
         */
        public Audio() {
        }

        /**
         * Gets the language of the audio.
         *
         * @return the language of the audio
         */
        public String getLanguage() {
            return language;
        }

        /**
         * Sets the language of the audio.
         *
         * @param language the new language of the audio
         */
        public void setLanguage(String language) {
            this.language = language;
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

    /**
     * Represents a price with amount, currency, and formatted value.
     */
    public static class Price {

        @Expose
        public String amount;
        @Expose
        public String currency;
        @Expose
        public String formatted;

        /**
         * Default constructor for Price.
         */
        public Price() {
        }

        /**
         * Gets the amount of the price.
         *
         * @return the amount of the price
         */
        public String getAmount() {
            return amount;
        }

        /**
         * Sets the amount of the price.
         *
         * @param amount the new amount of the price
         */
        public void setAmount(String amount) {
            this.amount = amount;
        }

        /**
         * Gets the currency of the price.
         *
         * @return the currency of the price
         */
        public String getCurrency() {
            return currency;
        }

        /**
         * Sets the currency of the price.
         *
         * @param currency the new currency of the price
         */
        public void setCurrency(String currency) {
            this.currency = currency;
        }

        /**
         * Gets the formatted value of the price.
         *
         * @return the formatted value of the price
         */
        public String getFormatted() {
            return formatted;
        }

        /**
         * Sets the formatted value of the price.
         *
         * @param formatted the new formatted value of the price
         */
        public void setFormatted(String formatted) {
            this.formatted = formatted;
        }
    }

}
