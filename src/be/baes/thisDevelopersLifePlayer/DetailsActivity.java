package be.baes.thisDevelopersLifePlayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import be.baes.thisDevelopersLifePlayer.controllers.*;
import be.baes.thisDevelopersLifePlayer.facade.Settings;
import com.google.inject.Inject;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 27/12/11
 * Time: 10:16
 */
public class DetailsActivity extends RoboActivity implements Observer{
    @InjectView(R.id.detailsButton) Button detailsButton;
    @InjectView(R.id.currentPodCast) TextView currentPodCast;
    @InjectView(R.id.playButton) Button playButton;
    @InjectView(R.id.stopButton) Button stopButton;
    @InjectView(R.id.pauseButton) Button pauseButton;
    @InjectView(R.id.detailsDeleteDownloadButton) Button deleteDownloadButton;
    @InjectView(R.id.detailsDownloadButton) Button downloadButton;
    @InjectView(R.id.seekBar) SeekBar seekbar;
    @InjectView(R.id.timer) TextView timer;
    @InjectView(R.id.detailscurrentPodCast) TextView detailsCurrentPodCast;
    @InjectView(R.id.detailsDescription) TextView description;
    @InjectView(R.id.detailsView) View mainView;
    @InjectView(R.id.detailsPubDate) TextView pubDate;
    @InjectView(R.id.viewInBrowserButton) Button vieInBrowser;
    @InjectView(R.id.detailimg) ImageView img;
    @Inject
    be.baes.thisDevelopersLifePlayer.controllers.OnPlayClickListener onPlayClickListener;
    @Inject
    be.baes.thisDevelopersLifePlayer.controllers.OnStopClickListener onStopClickListener;
    @Inject
    be.baes.thisDevelopersLifePlayer.controllers.OnPauseClickListener onPauseClickListener;
    @Inject
    be.baes.thisDevelopersLifePlayer.controllers.OnSeekChangeListener onSeekChangeListener;
    @Inject
    be.baes.thisDevelopersLifePlayer.facade.PositionUpdater positionUpdater;
    @Inject
    be.baes.thisDevelopersLifePlayer.controllers.OnDeleteDownloadedPodCastWithAlertDialogClickListener onDeleteDownloadedPodCastWithAlertDialogClickListener;
    @Inject
    be.baes.thisDevelopersLifePlayer.controllers.OnDownloadPodCastWithAlertDialogClickListener onDownloadPodCastWithAlertDialogClickListener;
    @Inject
    be.baes.thisDevelopersLifePlayer.facade.Player player;
    @Inject
    be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport;
    @Inject OnFlingDetailsOpenMainOnTouchListener onFlingDetailsOpenMainOnTouchListener;
    @Inject OnFlingDetailsOpenSearchOnTouchListener onFlingDetailsOpenSearchOnTouchListener;
    @Inject OnViewInBrowserOnClickListener onViewInBrowserOnClickListener;
    @Inject Settings settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        currentPodCast.setVisibility(TextView.GONE);
        detailsButton.setVisibility(Button.GONE);
        progressReport.setActivity(this);
        setListeners();
        setObservers();
        if(player.getCurrentPodCast()!=null && savedInstanceState==null)
        {
            Log.i(Constants.LOG_ID, "OnResume with saved instance state");
            setPosition(positionUpdater.getCurrentPosition());
        }
        else if(savedInstanceState!=null)
        {
            setPosition((be.baes.thisDevelopersLifePlayer.model.Position)savedInstanceState.getSerializable(Constants.POSITION));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle)
    {
        Log.i(Constants.LOG_ID, "Saving instance state");
        bundle.putSerializable(Constants.POSITION, positionUpdater.getCurrentPosition());
        super.onSaveInstanceState(bundle);
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        positionUpdater.deleteObserver(this);
    }


    private void setObservers() {
        positionUpdater.addObserver(this);
    }

    private void setListeners() {
        playButton.setOnClickListener(onPlayClickListener);
        stopButton.setOnClickListener(onStopClickListener);
        pauseButton.setOnClickListener(onPauseClickListener);
        seekbar.setOnSeekBarChangeListener(onSeekChangeListener);
        deleteDownloadButton.setOnClickListener(onDeleteDownloadedPodCastWithAlertDialogClickListener);
        downloadButton.setOnClickListener(onDownloadPodCastWithAlertDialogClickListener);
        vieInBrowser.setOnClickListener(onViewInBrowserOnClickListener);
        if(getIntent().getExtras().getString(Constants.PARENT).equals(Constants.SEARCH_ACTIVITY))
        {
            mainView.setOnTouchListener(onFlingDetailsOpenSearchOnTouchListener);
        }
        else
        {
            mainView.setOnTouchListener(onFlingDetailsOpenMainOnTouchListener);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable.getClass().equals(be.baes.thisDevelopersLifePlayer.facade.PositionUpdater.class))
        {
            setPosition((be.baes.thisDevelopersLifePlayer.model.Position) o);
        }
    }

    private void setPosition(be.baes.thisDevelopersLifePlayer.model.Position position) {
        deleteDownloadButton.setEnabled(player.hasCurrentPodCastDownloadedMp3());
        downloadButton.setEnabled(!player.hasCurrentPodCastDownloadedMp3());
        playButton.setEnabled(position.getHasPodCast());
        stopButton.setEnabled(position.getHasPodCast());
        pauseButton.setEnabled(position.getHasPodCast());
        seekbar.setEnabled(position.getHasPodCast());
        seekbar.setMax(position.getMaxDuration());
        seekbar.setProgress(position.getProgress());
        timer.setText(position.getTimer());
        if(player.getCurrentPodCast()!=null) pubDate.setText(player.getCurrentPodCast().getPubDate());
        description.setText(position.getDescription());
        detailsCurrentPodCast.setText(position.getMessage());
        File imgFile = new  File(settings.getImagesDirectory(), position.getFileName());
        Log.i(Constants.LOG_ID, "Image path: " + imgFile.getPath());
        if(imgFile.exists()){
            Log.i(Constants.LOG_ID, "Loading image");
            Bitmap bmImg = BitmapFactory.decodeFile(imgFile.getPath());
            img.setImageBitmap(bmImg);
        }
    }
}
