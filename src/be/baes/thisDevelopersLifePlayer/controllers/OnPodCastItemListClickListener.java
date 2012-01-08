package be.baes.thisDevelopersLifePlayer.controllers;

import android.util.Log;
import android.widget.*;
import com.google.inject.Inject;

import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

public class OnPodCastItemListClickListener implements OnItemClickListener {
	@Inject
    be.baes.thisDevelopersLifePlayer.facade.Player player;
    @Inject
    be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    @Inject
    be.baes.thisDevelopersLifePlayer.helpers.Network network;

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "PodCast item selected");
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, String.format("Selected file: %s", ((be.baes.thisDevelopersLifePlayer.model.PodCast) arg0.getAdapter().getItem(arg2)).getTitle()));
        if(!player.hasPodCastDownloadedMp3((be.baes.thisDevelopersLifePlayer.model.PodCast)arg0.getAdapter().getItem(arg2)) && !network.haveInternet(arg0.getContext()))
        {
            Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "No internet connection");
            Toast.makeText(arg0.getContext(), stringResources.NoInternetConnection(), Toast.LENGTH_LONG).show();
        }
        else
        {
            player.setCurrentFile(((be.baes.thisDevelopersLifePlayer.model.PodCast) arg0.getAdapter().getItem(arg2)));
        }
    }
}
