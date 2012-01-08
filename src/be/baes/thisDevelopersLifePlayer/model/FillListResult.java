package be.baes.thisDevelopersLifePlayer.model;

import java.util.List;

public class FillListResult
{
    private List<PodCast> podCasts;
    private String numberOfPodCasts;
    private int position;
    private int numberOfDownloadedPodCasts;
    private int numberOfDownloadedImages;

    public FillListResult(List<PodCast> podCasts, String numberOfPodCasts, int position) {
        this.podCasts = podCasts;
        this.numberOfPodCasts = numberOfPodCasts;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public List<PodCast> getPodCasts() {
        return podCasts;
    }

    public void setPodCasts(List<PodCast> podCasts) {
        this.podCasts = podCasts;
    }

    public String getNumberOfPodCasts() {
        return numberOfPodCasts;
    }

    public void setNumberOfPodCasts(String numberOfPodCasts) {
        this.numberOfPodCasts = numberOfPodCasts;
    }

    public void setNumberOfDownloadedPodCasts(int numberOfDownloadedPodCasts) {
        this.numberOfDownloadedPodCasts = numberOfDownloadedPodCasts;
    }
    
    public int getNumberOfDownloadedPodCasts()
    {
        return numberOfDownloadedPodCasts;
    }

    public void setNumberOfDownloadedImages(int numberOfDownloadedImages) {
        this.numberOfDownloadedImages = numberOfDownloadedImages;
    }
    
    public int getNumberOfDownloadedImages()
    {
        return this.numberOfDownloadedImages;
    }

}
