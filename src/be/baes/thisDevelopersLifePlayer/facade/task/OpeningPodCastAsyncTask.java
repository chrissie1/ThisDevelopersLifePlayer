package be.baes.thisDevelopersLifePlayer.facade.task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;

public class OpeningPodCastAsyncTask extends AsyncTask<File,String,Void>{
    private be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport;
    private be.baes.thisDevelopersLifePlayer.facade.Player player;
    private be.baes.thisDevelopersLifePlayer.model.PodCast currentPodCast;
    private be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    private be.baes.thisDevelopersLifePlayer.facade.PositionUpdater positionUpdater;

    public OpeningPodCastAsyncTask(be.baes.thisDevelopersLifePlayer.facade.Player player, be.baes.thisDevelopersLifePlayer.model.PodCast currentPodCast, be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport, be.baes.thisDevelopersLifePlayer.facade.PositionUpdater positionUpdater, be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources1)
    {
        this.positionUpdater = positionUpdater;
        this.progressReport = progressReport;
        this.player = player;
        this.currentPodCast = currentPodCast;
        this.stringResources = stringResources1;
    }

    @Override
    protected void onPreExecute()
    {
        progressReport.startProgress(stringResources.getLoading() + "PodCast");
    }

    @Override
    protected void onPostExecute(Void result)
    {
        progressReport.endProgress();
        positionUpdater.startPosition();
    }

    @Override
    protected void onProgressUpdate(String... messages)
    {
        progressReport.updateProgess(messages[0]);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(File... voids) {
        try {
            File downloadedFile = new File(voids[0] , currentPodCast.getPodCastName());
            if (downloadedFile.exists()) {
                Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("Playing local file: %s", downloadedFile.getPath()));
                player.setDataSource(downloadedFile.getPath());
            }
            else
            {
                Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("Playing remote file: %s", currentPodCast.getMP3Link()));
                player.setDataSource(currentPodCast.getMP3Link());
            }
        } catch (Exception e) {
            Log.e(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("Error: %s", e.getMessage()),e);
            e.printStackTrace();
            publishProgress("Error: " + e.getMessage());
        }
        return null;
    }
}
