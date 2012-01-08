package be.baes.thisDevelopersLifePlayer.controllers;

import android.util.Log;
import com.google.inject.Inject;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class OnSeekChangeListener implements OnSeekBarChangeListener {
	@Inject
    be.baes.thisDevelopersLifePlayer.facade.Player player;
	private boolean isMovingSeekBar;

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "Stop tracking");
		isMovingSeekBar = false;
        player.play();
    }

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "start tracking");
		isMovingSeekBar = true;
		player.pause();
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
        if(!fromUser) return;
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "Progress changed");
		if(isMovingSeekBar)
		{
			player.seekTo(progress);
		}
	}
}
