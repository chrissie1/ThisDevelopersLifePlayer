package be.baes.thisDevelopersLifePlayer.model;

import java.io.Serializable;

public class PodCast implements Serializable{
    private String title;
    private String pubDate;
    private String link;
    private String mP3Link;
    private String description;
    
    public PodCast(String title, String pubDate, String link, String mP3Link, String description) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.mP3Link = mP3Link;
    }
    
    public String getDescription()
    {
        return description;
    }

    public String getPodCastName()
    {
        return mP3Link.substring(mP3Link.lastIndexOf("/")+1);
    }
    
    public String getMP3Link() {
        return mP3Link;
    }

    public String getTitle() {
        return title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getLink() {
        return link;
    }

    public String getFileName()
    {
        String filename = title.replace(' ','-');
        filename = removeChar(filename , '.')  + ".png";
        return filename;
    }

    public static String removeChar(String s, char c) {

        String r = "";

        for (int i = 0; i < s.length(); i ++) {
            if (s.charAt(i) != c) r += s.charAt(i);
        }

        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PodCast podCast = (PodCast) o;

        return !(link != null ? !link.equals(podCast.link) : podCast.link != null);

    }

    @Override
    public int hashCode() {
        return link != null ? link.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("PodCast{title='%s', pubDate=%s}", title, pubDate);
    }
}
