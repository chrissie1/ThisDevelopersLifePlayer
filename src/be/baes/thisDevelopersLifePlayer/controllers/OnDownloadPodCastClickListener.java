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
* Time: 9:11
* To change this template use File | Settings | File Templates.
*/
class OnDownloadPodCastClickListener implements DialogInterface.OnClickListener {
    @Inject Context context;
    @Inject
    be.baes.thisDevelopersLifePlayer.facade.Player player;
    @Inject
    be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    @Inject
    be.baes.thisDevelopersLifePlayer.helpers.Network network;

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(network.haveInternet(context))
        {
            player.downloadMp3();
        }
        else
        {
            Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "No internet connection");
            Toast.makeText(context, stringResources.NoInternetConnection(), Toast.LENGTH_LONG).show();
        }
    }
}
