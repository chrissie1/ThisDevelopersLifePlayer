package be.baes.thisDevelopersLifePlayer.facade.task;

import android.os.AsyncTask;
import be.baes.thisDevelopersLifePlayer.facade.SearchList;

import java.util.List;

public class FillSearchListAsyncTask extends AsyncTask<String,Void, be.baes.thisDevelopersLifePlayer.model.FillListResult>{
    private final static Integer PAGE_SIZE = 30;
    private be.baes.thisDevelopersLifePlayer.dal.PodCastAdapter podCastAdapter;
    private be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport;
    private SearchList searchList;
    private be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;

    public FillSearchListAsyncTask(be.baes.thisDevelopersLifePlayer.dal.PodCastAdapter podCastAdapter, SearchList searchList1, be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport, be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources1) {
        this.podCastAdapter = podCastAdapter;
        this.progressReport = progressReport;
        this.searchList = searchList1;
        this.stringResources = stringResources1;
    }

    @Override
    protected void onPreExecute()
    {
        progressReport.startProgress(stringResources.getLoading() + " SearchList");
    }

    @Override
    protected void onPostExecute(be.baes.thisDevelopersLifePlayer.model.FillListResult result)
    {
        progressReport.endProgress();
        searchList.updateList(result);
    }

    @Override
    protected be.baes.thisDevelopersLifePlayer.model.FillListResult doInBackground(String... query) {
        be.baes.thisDevelopersLifePlayer.model.FillListResult fillListResult;
        if(query.length == 2 && query[1]!=null && Integer.getInteger(query[1]) !=null)
        {
            fillListResult = new be.baes.thisDevelopersLifePlayer.model.FillListResult(null,"", Integer.getInteger(query[1]));
        }
        else
        {
            fillListResult = new be.baes.thisDevelopersLifePlayer.model.FillListResult(null,"", 0);
        }
        List<be.baes.thisDevelopersLifePlayer.model.PodCast> podCasts;
        podCasts = podCastAdapter.findItems(query[0]);
        if(podCasts.size() > 0)
        {
            fillListResult.setNumberOfPodCasts("Found: " + podCasts.size());
            fillListResult.setPodCasts(podCasts);
        }
        else
        {
            fillListResult.setPodCasts(null);
            fillListResult.setNumberOfPodCasts("Nothing found");
        }
        return fillListResult;
    }
}