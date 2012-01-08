package be.baes.thisDevelopersLifePlayer.controllers;

import android.util.Log;
import android.widget.AbsListView;
import com.google.inject.Inject;

public class OnScrollPodCastListListener implements AbsListView.OnScrollListener {
    @Inject
    be.baes.thisDevelopersLifePlayer.facade.PodCastList podCastList;
    @Inject
    be.baes.thisDevelopersLifePlayer.dal.PodCastAdapter podCastAdapter;

    public OnScrollPodCastListListener() {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view.isEnabled() && totalItemCount> 0 && (firstVisibleItem + visibleItemCount) == totalItemCount)
        {
            if(totalItemCount < podCastAdapter.numberOfPodcasts())
            {
                int currentPage = podCastList.getCurrentPage();
                Log.d(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("totalItemCount: %d", totalItemCount));
                Log.d(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("currentPage: %s", currentPage));
                Log.d(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("visibleItemCount: %d", visibleItemCount));
                Log.d(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("firstVisibleItem: %d", firstVisibleItem));
                Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "Loading podcasts on scroll");
                currentPage++;
                view.setEnabled(false);
                podCastList.load(currentPage);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
}
