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
* Time: 9:08
* To change this template use File | Settings | File Templates.
*/
class OnDeleteDownloadedPodCastClickListener implements DialogInterface.OnClickListener {
    @Inject Context context;
    @Inject
    be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    @Inject
    be.baes.thisDevelopersLifePlayer.facade.Player player;

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(!player.hasCurrentPodCastDownloadedMp3())
        {
            Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "No file to delete");
            Toast.makeText(context, stringResources.NoFileToDelete(), Toast.LENGTH_LONG).show();
        }
        else
        {
            player.deleteCurrentPodCastDownLoadedMp3();
        }
    }
}
