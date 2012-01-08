package be.baes.thisDevelopersLifePlayer.facade;

import com.google.inject.Singleton;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 19/12/11
 * Time: 21:15
 */
@Singleton
public class SettingsImpl implements Settings {
    private File podCastsDirectory;
    private File imagesDirectory;

    @Override
    public void initialize(File podCastsDirectory, File imagesDirectory)
    {
        this.podCastsDirectory = podCastsDirectory;
        this.imagesDirectory = imagesDirectory;
    }

    @Override
    public File getPodCastsDirectory() {
        return podCastsDirectory;
    }

    @Override
    public File getImagesDirectory() {
        return imagesDirectory;
    }
}
