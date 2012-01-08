package be.baes.thisDevelopersLifePlayer.facade;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 20/12/11
 * Time: 21:47
 */
public interface Settings {
    void initialize(File podCastsDirectory, File imagesDirectory);

    File getPodCastsDirectory();
    File getImagesDirectory();
}
