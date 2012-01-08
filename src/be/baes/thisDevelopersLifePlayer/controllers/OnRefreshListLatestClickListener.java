package be.baes.thisDevelopersLifePlayer.controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;
import com.google.inject.Inject;

/**
* Created by IntelliJ IDEA.
* User: christiaan
* Date: 26/12/11
* Time: 9:53
* To change this template use File | Settings | File Templates.
*/
class OnRefreshListLatestClickListener implements DialogInterface.OnClickListener {
    @Inject Context context;
    @Inject
    be.baes.thisDevelopersLifePlayer.helpers.Network network;
    @Inject
    be.baes.thisDevelopersLifePlayer.facade.PodCastList podCastList;
    @Inject
    be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(network.haveInternet(context))
        {
            podCastList.getListFromRssAndUpdateDatabase(be.baes.thisDevelopersLifePlayer.Constants.urlToRssFeedLatest);
        }
        else
        {
            Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "No internet connection");
            Toast.makeText(context, stringResources.NoInternetConnection(), Toast.LENGTH_LONG).show();
        }
    }
}
