package be.baes.thisDevelopersLifePlayer.facade;

import be.baes.thisDevelopersLifePlayer.facade.task.FillSearchListAsyncTask;
import com.google.inject.Inject;

import java.util.Observable;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 31/12/11
 * Time: 8:57
 */
public class SearchListImpl extends Observable implements SearchList{
    private FillSearchListAsyncTask fillSearchListAsyncTask;
    @Inject
    be.baes.thisDevelopersLifePlayer.dal.PodCastAdapter podCastAdapter;
    @Inject
    be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport;
    @Inject
    be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    
    @Override
    public void search(String query) {
        fillSearchListAsyncTask = new FillSearchListAsyncTask(podCastAdapter,this, progressReport, stringResources);
        fillSearchListAsyncTask.execute(query);
    }

    @Override
    public void updateList(be.baes.thisDevelopersLifePlayer.model.FillListResult result) {
        setChanged();
        notifyObservers(result);
    }

    @Override
    public void search(String query, int position) {
        fillSearchListAsyncTask = new FillSearchListAsyncTask(podCastAdapter,this, progressReport, stringResources);
        String ints[] = {query, String.valueOf(position)};
        fillSearchListAsyncTask.execute(ints);
    }
}
