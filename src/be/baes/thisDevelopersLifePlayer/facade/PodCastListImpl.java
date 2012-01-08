package be.baes.thisDevelopersLifePlayer.facade;

import be.baes.thisDevelopersLifePlayer.dal.PodCastAdapter;
import be.baes.thisDevelopersLifePlayer.rss.ThisDevelopersLifeFeed;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.File;
import java.util.Observable;

@Singleton
public class PodCastListImpl extends Observable implements PodCastList {
    @Inject
    PodCastAdapter podCastAdapter;
    @Inject
    ThisDevelopersLifeFeed hanselFeed;
    @Inject
    be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport;
    @Inject
    be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    @Inject
    be.baes.thisDevelopersLifePlayer.facade.Settings settings;
    be.baes.thisDevelopersLifePlayer.facade.task.GetListFromRssAndUpdateDatabaseAsyncTask task;
    be.baes.thisDevelopersLifePlayer.facade.task.FillListAsyncTask fillListAsyncTask;
    private int currentPage;

    public PodCastListImpl()
    {

    }

    @Override
    public void load(int page)
    {
        currentPage = page;
        fillListAsyncTask = new be.baes.thisDevelopersLifePlayer.facade.task.FillListAsyncTask(podCastAdapter,this, progressReport, stringResources);
        fillListAsyncTask.execute(page);
    }
    
    @Override
    public void load(int page, int position)
    {
        currentPage = page;

        fillListAsyncTask = new be.baes.thisDevelopersLifePlayer.facade.task.FillListAsyncTask(podCastAdapter,this, progressReport, stringResources);
        Integer ints[] = {page, position};
        fillListAsyncTask.execute(ints);
    }

    @Override
    public void getListFromRssAndUpdateDatabase(String feed)
    {
        task = new be.baes.thisDevelopersLifePlayer.facade.task.GetListFromRssAndUpdateDatabaseAsyncTask(podCastAdapter,hanselFeed, progressReport, stringResources,this, settings);
        task.execute(feed);
    }

    @Override
    public void updateList(be.baes.thisDevelopersLifePlayer.model.FillListResult result)
    {
        setChanged();
        result.setNumberOfDownloadedPodCasts(numberOfDownloadedPodCasts());
        result.setNumberOfDownloadedImages(numberOfDownloadedImages());
        notifyObservers(result);
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }
    
    private int numberOfDownloadedPodCasts()
    {
        return new File(settings.getPodCastsDirectory().getPath()).list().length;
    }

    private int numberOfDownloadedImages()
    {
        return new File(settings.getImagesDirectory().getPath()).list().length;
    }
}
