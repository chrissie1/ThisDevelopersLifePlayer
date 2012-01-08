package be.baes.thisDevelopersLifePlayer.facade.task;

import android.os.AsyncTask;

import java.util.List;

public class FillListAsyncTask extends AsyncTask<Integer,Void, be.baes.thisDevelopersLifePlayer.model.FillListResult>{
    private final static Integer PAGE_SIZE = 30;
    private be.baes.thisDevelopersLifePlayer.dal.PodCastAdapter podCastAdapter;
    private be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport;
    private be.baes.thisDevelopersLifePlayer.facade.PodCastList podCastList;
    private be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;

    public FillListAsyncTask(be.baes.thisDevelopersLifePlayer.dal.PodCastAdapter podCastAdapter, be.baes.thisDevelopersLifePlayer.facade.PodCastList podCastList, be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport, be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources1) {
        this.podCastAdapter = podCastAdapter;
        this.progressReport = progressReport;
        this.podCastList = podCastList;
        this.stringResources = stringResources1;
    }

    @Override
    protected void onPreExecute()
    {
        progressReport.startProgress(stringResources.getLoading() + "List");
    }

    @Override
    protected void onPostExecute(be.baes.thisDevelopersLifePlayer.model.FillListResult result)
    {
        progressReport.endProgress();
        podCastList.updateList(result);
    }

    @Override
    protected be.baes.thisDevelopersLifePlayer.model.FillListResult doInBackground(Integer... integers) {
        be.baes.thisDevelopersLifePlayer.model.FillListResult fillListResult;
        if(integers.length == 1)
        {
            fillListResult = new be.baes.thisDevelopersLifePlayer.model.FillListResult(null,"", integers[0]*PAGE_SIZE - 1);
        }
        else
        {
            fillListResult = new be.baes.thisDevelopersLifePlayer.model.FillListResult(null,"", integers[1]);
        }
        List<be.baes.thisDevelopersLifePlayer.model.PodCast> podCasts;
        podCasts = podCastAdapter.getAllItems(0,PAGE_SIZE*(integers[0]+1));
        if(podCasts.size() > 0)
        {
            fillListResult.setNumberOfPodCasts(String.format(stringResources.getTotalLoaded(), podCastAdapter.numberOfPodcasts(), podCasts.size()));
            fillListResult.setPodCasts(podCasts);
        }
        else
        {
            fillListResult.setPodCasts(null);
            fillListResult.setNumberOfPodCasts(stringResources.getNoPodCasts());
        }
        return fillListResult;
    }
}