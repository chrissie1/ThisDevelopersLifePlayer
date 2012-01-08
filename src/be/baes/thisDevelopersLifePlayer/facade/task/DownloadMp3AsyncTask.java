package be.baes.thisDevelopersLifePlayer.facade.task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 16/12/11
 * Time: 14:29
 */
public class DownloadMp3AsyncTask extends AsyncTask<Void,String,Void> {
    private be.baes.thisDevelopersLifePlayer.model.PodCast currentPodCast;
    private be.baes.thisDevelopersLifePlayer.facade.PodCastList podCastList;
    private be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport;
    private be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    private be.baes.thisDevelopersLifePlayer.facade.Settings settings;
    
    public DownloadMp3AsyncTask(be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport, be.baes.thisDevelopersLifePlayer.model.PodCast currentPodCast, be.baes.thisDevelopersLifePlayer.facade.PodCastList podCastList, be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources1, be.baes.thisDevelopersLifePlayer.facade.Settings settings1)
    {
        this.stringResources = stringResources1;
        this.progressReport = progressReport;
        this.currentPodCast = currentPodCast;
        this.podCastList = podCastList;
        this.settings = settings1;
    }

    @Override
    protected void onPreExecute()
    {
        progressReport.startProgress(stringResources.getDownloading());
    }

    @Override
    protected void onPostExecute(Void result)
    {
        progressReport.endProgress();
        podCastList.load(0);
    }

    @Override
    protected void onProgressUpdate(String... messages)
    {
        progressReport.updateProgess(messages[0]);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int count;
        try {
            URL url = new URL(currentPodCast.getMP3Link());
            URLConnection urlConnection  = url.openConnection();
            File tempMp3 = new File(settings.getPodCastsDirectory(), currentPodCast.getPodCastName());
            Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "Podcastname: " + currentPodCast.getPodCastName());
            Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("cachingdirectory:%s", settings.getPodCastsDirectory().getPath()));
            Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("Mp3:%s", tempMp3.getPath()));
            urlConnection.connect();
            int lenghtOfFile = urlConnection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(tempMp3);
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress(String.format(stringResources.getProgressPercentage(), total * 100 / lenghtOfFile));
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("Error: %s", e.getMessage()),e);
            e.printStackTrace();
        }
        return null;
    }
}
