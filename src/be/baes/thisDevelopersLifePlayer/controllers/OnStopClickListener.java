package be.baes.thisDevelopersLifePlayer.controllers;

import android.util.Log;
import com.google.inject.Inject;

import android.view.View;
import android.view.View.OnClickListener;

public class OnStopClickListener implements OnClickListener {
	@Inject
    be.baes.thisDevelopersLifePlayer.facade.Player player;

	@Override
    public void onClick(View arg0) {
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "stop playing");
        player.stop();
  	}
}
