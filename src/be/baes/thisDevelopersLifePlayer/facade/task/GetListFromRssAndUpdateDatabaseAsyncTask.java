package be.baes.thisDevelopersLifePlayer.facade.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import be.baes.thisDevelopersLifePlayer.Constants;
import be.baes.thisDevelopersLifePlayer.facade.Settings;
import be.baes.thisDevelopersLifePlayer.rss.ThisDevelopersLifeFeed;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class GetListFromRssAndUpdateDatabaseAsyncTask extends AsyncTask<String,String,Void> {
    private be.baes.thisDevelopersLifePlayer.dal.PodCastAdapter podCastAdapter;
    private be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    private ThisDevelopersLifeFeed hanselFeed;
    private be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport;
    private be.baes.thisDevelopersLifePlayer.facade.PodCastList podCastList;
    private Settings settings;

    public GetListFromRssAndUpdateDatabaseAsyncTask(be.baes.thisDevelopersLifePlayer.dal.PodCastAdapter podCastAdapter, ThisDevelopersLifeFeed hanselFeed, be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport, be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources, be.baes.thisDevelopersLifePlayer.facade.PodCastList podCastList, Settings settings) {
        this.settings = settings;
        this.hanselFeed = hanselFeed;
        this.progressReport = progressReport;
        this.podCastAdapter = podCastAdapter;
        this.stringResources =  stringResources;
        this.podCastList = podCastList;
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
    protected Void doInBackground(String... feeds) {
        be.baes.thisDevelopersLifePlayer.rss.RSSFeed feed = null;
        try {
            feed = hanselFeed.getFeed(feeds[0]);
        } catch (IOException e) {
            Log.e(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("Error: %s", e.getMessage()),e);
            e.printStackTrace();
            publishProgress("Error: " + e.getMessage());
        } catch (SAXException e) {
            Log.e(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("Error: %s", e.getMessage()),e);
            e.printStackTrace();
            publishProgress("Error: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            Log.e(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("Error: %s", e.getMessage()),e);
            e.printStackTrace();
            publishProgress("Error: " + e.getMessage());
        }
        if (feed == null)
        {
            Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "Feed is null");
        } else {
            List<be.baes.thisDevelopersLifePlayer.rss.RSSItem> rssItems = feed.getAllItems();
            List<be.baes.thisDevelopersLifePlayer.model.PodCast> podCastItems = podCastAdapter.getAllItems();
            int count = 0;
            int total = rssItems.size();
            for (be.baes.thisDevelopersLifePlayer.rss.RSSItem rssItem : rssItems) {
                count++;
                be.baes.thisDevelopersLifePlayer.model.PodCast podCast = new be.baes.thisDevelopersLifePlayer.model.PodCast(rssItem.getTitle(), rssItem.getPubDate(), rssItem.getLink(), rssItem.getMp3Link(), rssItem.getDescription());
                if (podCastItems.contains(podCast)) {
                    if(!podCast.getTitle().equals(podCastItems.get(podCastItems.indexOf(podCast)).getTitle())||!podCast.getPubDate().equals(podCastItems.get(podCastItems.indexOf(podCast)).getPubDate())||!podCast.getDescription().equals(podCastItems.get(podCastItems.indexOf(podCast)).getDescription()))
                    {
                        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("Updating podcast: %s", podCast.getLink()));
                        if(podCast.getLink().startsWith(be.baes.thisDevelopersLifePlayer.Constants.PREFIX))
                        {
                            this.publishProgress("Updating podcast: " + count + " of " + total);
                            podCastAdapter.updatePodCast(podCast);
                            saveImage(podCast.getFileName(), rssItem.getImageLink());
                        }
                    }
                } else {
                    Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("Inserting podcast: %s", podCast.getLink()));
                    if(podCast.getLink().startsWith(Constants.PREFIX))
                    {
                        this.publishProgress("Inserting podcast: " + count + " of " + total);
                        podCastAdapter.insertPodCast(podCast);
                        saveImage(podCast.getFileName(), rssItem.getImageLink());
                    }
                }
            }
        }
        return null;
    }

    private void saveImage(String filename, String imageLink)
    {
        Log.i(Constants.LOG_ID,"imageLink:"+ imageLink);
        try {
            URL url = new URL(imageLink);
            URLConnection urlConnection  = url.openConnection();
            File tempMp3 = new File(settings.getImagesDirectory(), filename);
            Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("cachingdirectory:%s", settings.getImagesDirectory().getPath()));
            Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("Mp3:%s", tempMp3.getPath()));
            urlConnection.connect();
            int length = urlConnection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(tempMp3);
            byte[] imageData = new byte[length];
            int buffersize = (int) Math.ceil(length / (double) 100);
            int downloaded = 0;
            int read;
            while (downloaded < length) {
                if (length < buffersize) {
                    read = input.read(imageData, downloaded, length);
                } else if ((length - downloaded) <= buffersize) {
                    read = input.read(imageData, downloaded, length
                            - downloaded);
                } else {
                    read = input.read(imageData, downloaded, buffersize);
                }
                downloaded += read;
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, length);
            Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,72,72,true);
            bitmap1.compress(Bitmap.CompressFormat.PNG, 95, output);
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("Error: %s", e.getMessage()),e);
            e.printStackTrace();
        }
    }
}
