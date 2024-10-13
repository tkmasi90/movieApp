/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.swdesign.movienightplanner.models;

import com.google.gson.annotations.Expose;
import java.util.List;

/** 
 *
 * @author janii
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
    
    public SubtitleService() {
    }
    


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public List<Audio> getAudios() {
        return audios;
    }

    public void setAudios(List<Audio> audios) {
        this.audios = audios;
    }

    public List<Subtitle> getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(List<Subtitle> subtitles) {
        this.subtitles = subtitles;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public boolean isExpiresSoon() {
        return expiresSoon;
    }

    public void setExpiresSoon(boolean expiresSoon) {
        this.expiresSoon = expiresSoon;
    }

    public long getAvailableSince() {
        return availableSince;
    }

    public void setAvailableSince(long availableSince) {
        this.availableSince = availableSince;
    }
    


    // Getters and Setters
 
    public static class Service {
        @Expose
        public String id;

        public Service() {
        }
        
        @Expose
        public String name;
        @Expose
        public String homePage;
        @Expose
        public String themeColorCode;
        @Expose
        public ImageSet imageSet;

        // Getters and Setters

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHomePage() {
            return homePage;
        }

        public void setHomePage(String homePage) {
            this.homePage = homePage;
        }

        public String getThemeColorCode() {
            return themeColorCode;
        }

        public void setThemeColorCode(String themeColorCode) {
            this.themeColorCode = themeColorCode;
        }

        public ImageSet getImageSet() {
            return imageSet;
        }

        public void setImageSet(ImageSet imageSet) {
            this.imageSet = imageSet;
        }
    }

    public static class ImageSet {
        @Expose
        public String lightThemeImage;
        @Expose
        public String darkThemeImage;

        public ImageSet() {
        }
        @Expose
        public String whiteImage;

        // Getters and Setters

        public String getLightThemeImage() {
            return lightThemeImage;
        }

        public void setLightThemeImage(String lightThemeImage) {
            this.lightThemeImage = lightThemeImage;
        }

        public String getDarkThemeImage() {
            return darkThemeImage;
        }

        public void setDarkThemeImage(String darkThemeImage) {
            this.darkThemeImage = darkThemeImage;
        }

        public String getWhiteImage() {
            return whiteImage;
        }

        public void setWhiteImage(String whiteImage) {
            this.whiteImage = whiteImage;
        }
    }

    public static class Audio {
        @Expose
        public String language;

        public Audio() {
        }

        // Getters and Setters

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }
    }

    public static class Subtitle {
        @Expose
        public boolean closedCaptions;
        @Expose
public Locale locale;
        public Subtitle() {
        }

        public boolean isClosedCaptions() {
            return closedCaptions;
        }

        public void setClosedCaptions(boolean closedCaptions) {
            this.closedCaptions = closedCaptions;
        }

        public Locale getLocale() {
            return locale;
        }

        public void setLocale(Locale locale) {
            this.locale = locale;
        }
        

        // Getters and Setters
    }

    public static class Locale {
        @Expose
        public String language;

        public Locale() {
        }

        // Getters and Setters

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }
    }

    public static class Price {
        @Expose
        public String amount;

        public Price() {
        }
        @Expose
        public String currency;
        @Expose
        public String formatted;

        // Getters and Setters

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getFormatted() {
            return formatted;
        }

        public void setFormatted(String formatted) {
            this.formatted = formatted;
        }
    }
}
