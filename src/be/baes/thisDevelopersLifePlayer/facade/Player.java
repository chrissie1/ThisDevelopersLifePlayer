package be.baes.thisDevelopersLifePlayer.facade;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 12/12/11
 * Time: 10:42
 */
public interface Player {
    void play();

    String getCurrentTitle();

    void setCurrentFile(be.baes.thisDevelopersLifePlayer.model.PodCast currentPodCast);

    void stop();

    void pause();

    int getCurrentPosition();

    int getDuration();

    void seekTo(int progress);

    void destroy();

    be.baes.thisDevelopersLifePlayer.model.PodCast getCurrentPodCast();

    void setDataSource(String path);

    void downloadMp3();

    boolean hasCurrentPodCastDownloadedMp3();

    void deleteCurrentPodCastDownLoadedMp3();

    boolean hasPodCastDownloadedMp3(be.baes.thisDevelopersLifePlayer.model.PodCast podCast);

    String getCurrentDescription();

    String getCurrentFileName();
}
