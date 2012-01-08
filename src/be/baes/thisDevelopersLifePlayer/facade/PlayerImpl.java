package be.baes.thisDevelopersLifePlayer.facade;

import android.util.Log;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

@Singleton
public class PlayerImpl implements Player {
	@Inject PositionUpdater positionUpdater;
    @Inject
    be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport;
    @Inject
    be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    @Inject Settings settings;
	private MediaPlayer mediaPlayer;
	private be.baes.thisDevelopersLifePlayer.model.PodCast currentPodCast;
    @Inject
    PodCastList podCastList;

    public PlayerImpl()
	{
        mediaPlayer = new MediaPlayer();
	}

   	@Override
    public void play()
	{
	    positionUpdater.updatePosition();
		mediaPlayer.start();
	}
	
	@Override
    public String getCurrentTitle() {
        if(currentPodCast==null) {
            return "No current podcast.";
        }
        else
        {
            return currentPodCast.getTitle();
        }
	}

    @Override
    public be.baes.thisDevelopersLifePlayer.model.PodCast getCurrentPodCast()
    {
        return currentPodCast;
    }

    @Override
    public void setDataSource(String path) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setCurrentFile(be.baes.thisDevelopersLifePlayer.model.PodCast currentPodCast) {
        if(this.currentPodCast != currentPodCast)
        {
            stop();
            this.currentPodCast = currentPodCast;
            if(currentPodCast == null)
            {
                Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "currentPodCast is null");
                positionUpdater.emptyFile();
            }
            else
            {
                Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "currentPodCast is filled");
                be.baes.thisDevelopersLifePlayer.facade.task.OpeningPodCastAsyncTask task = new be.baes.thisDevelopersLifePlayer.facade.task.OpeningPodCastAsyncTask(this,currentPodCast, progressReport, positionUpdater, stringResources);
                task.execute(settings.getPodCastsDirectory(),null,null);
            }
        }
        else
        {
            Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "currentPodCast will not be updated because it did not change.");
        }
	}

	@Override
    public void stop()
	{
        positionUpdater.stopPosition();
        if(mediaPlayer!=null)
        {
		    mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
	}
	
	@Override
    public void pause()
	{
	    positionUpdater.pausePosition();
		mediaPlayer.pause();
	}
	
	@Override
    public int getCurrentPosition()
	{
		if(mediaPlayer==null) return 0;
		return mediaPlayer.getCurrentPosition();
	}
	
	@Override
    public int getDuration()
	{
		if(mediaPlayer==null) return 0;
		return mediaPlayer.getDuration();
	}

	@Override
    public void seekTo(int progress) {
		mediaPlayer.seekTo(progress);
	}
	
	@Override
    public void destroy()
	{
        positionUpdater.pausePosition();
        if(mediaPlayer!=null)
        {
		    mediaPlayer.stop();
		    mediaPlayer.reset();
		    mediaPlayer.release();
		    mediaPlayer = null;
        }
	}

    @Override
    public void downloadMp3() {
        if(currentPodCast==null) return;
        be.baes.thisDevelopersLifePlayer.facade.task.DownloadMp3AsyncTask downloadMp3AsyncTask = new be.baes.thisDevelopersLifePlayer.facade.task.DownloadMp3AsyncTask(progressReport, currentPodCast, podCastList, stringResources, settings);
        downloadMp3AsyncTask.execute(null,null,null);
    }

    @Override
    public boolean hasCurrentPodCastDownloadedMp3() {
        return hasPodCastDownloadedMp3(currentPodCast);
    }

    @Override
    public void deleteCurrentPodCastDownLoadedMp3() {
        if(currentPodCast==null) return;
        if(!hasCurrentPodCastDownloadedMp3()) return;
        File file = new File(settings.getPodCastsDirectory(),currentPodCast.getPodCastName());
        file.delete();
    }

    @Override
    public boolean hasPodCastDownloadedMp3(be.baes.thisDevelopersLifePlayer.model.PodCast podCast) {
        if(podCast==null) return false;
        File file = new File(settings.getPodCastsDirectory(),podCast.getPodCastName());
        return file.exists();
    }

    @Override
    public String getCurrentDescription() {
        if(currentPodCast==null) return "";
        return currentPodCast.getDescription();
    }

    @Override
    public String getCurrentFileName() {
        if(currentPodCast==null) return "";
        return currentPodCast.getFileName();
    }

}
