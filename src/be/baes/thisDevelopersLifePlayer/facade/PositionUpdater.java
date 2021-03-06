package be.baes.thisDevelopersLifePlayer.facade;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Observable;

import android.os.Handler;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class PositionUpdater extends Observable {
	@Inject
    Player player;
	@Inject
    be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;

	private final Handler handler = new Handler();
    private be.baes.thisDevelopersLifePlayer.model.Position position;

    public PositionUpdater()
    {
        position = new be.baes.thisDevelopersLifePlayer.model.Position("","",0,0,false,"",null);
    }

    private final Runnable updatePositionRunnable = new Runnable() {
            public void run() {
                    updatePosition();
            }
    };
    
    public be.baes.thisDevelopersLifePlayer.model.Position getCurrentPosition()
    {
        return position;
    }

    public void emptyFile()
    {
        position.setProgress(0);
        position.setMaxDuration(0);
        position.setTimer(String.format(stringResources.getTimerWithTime(), toMinutes(0), toMinutes(0)));
        position.setMessage(stringResources.getNoFileSelected());
        position.setHasPodCast(false);
        position.setDescription("");
        position.setFileName(null);
        setChanged();
        notifyObservers(position);
    }

    public void startPosition()
    {
        position.setProgress(0);
        position.setMaxDuration(player.getDuration());
        position.setTimer(String.format(stringResources.getTimerWithTime(), toMinutes(0), toMinutes(player.getDuration())));
        position.setMessage(String.format(stringResources.getSelected(), player.getCurrentTitle()));
        position.setDescription(player.getCurrentDescription());
        position.setFileName(player.getCurrentFileName());
        position.setHasPodCast(true);
        setChanged();
        notifyObservers(position);
    }
    
    public void stopPosition()
    {
        handler.removeCallbacks(updatePositionRunnable);
        position.setTimer(String.format(stringResources.getTimerWithTime(), toMinutes(0), toMinutes(player.getDuration())));
        position.setMessage(String.format(stringResources.getStopped(), player.getCurrentTitle()));
        position.setMaxDuration(player.getDuration());
        position.setDescription(player.getCurrentDescription());
        position.setHasPodCast(true);
        position.setProgress(0);
        position.setFileName(player.getCurrentFileName());
        setChanged();
        notifyObservers(position);
    }
    
    public void pausePosition()
    {
        position.setProgress(player.getCurrentPosition());
        position.setTimer(String.format(stringResources.getTimerWithTime(), toMinutes(player.getCurrentPosition()), toMinutes(player.getDuration())));
        position.setMessage(String.format(stringResources.getPausing(), player.getCurrentTitle()));
        position.setMaxDuration(player.getDuration());
        position.setDescription(player.getCurrentDescription());
        position.setHasPodCast(true);
        handler.removeCallbacks(updatePositionRunnable);
        position.setFileName(player.getCurrentFileName());
        setChanged();
        notifyObservers(position);
    }
    
    public void updatePosition(){
        handler.removeCallbacks(updatePositionRunnable);
        position.setProgress(player.getCurrentPosition());
        position.setTimer(String.format(stringResources.getTimerWithTime(), toMinutes(player.getCurrentPosition()), toMinutes(player.getDuration())));
        position.setMessage(String.format(stringResources.getPlaying(), player.getCurrentTitle()));
        position.setMaxDuration(player.getDuration());
        position.setDescription(player.getCurrentDescription());
        position.setHasPodCast(true);
        handler.postDelayed(updatePositionRunnable, 500);
        position.setFileName(player.getCurrentFileName());
        setChanged();
        notifyObservers(position);
    }
    
    private String toMinutes(int milliSeconds)
    {
    	double durationInMin = ((double)milliSeconds/1000.0)/60.0;
        durationInMin = new BigDecimal(Double.toString(durationInMin)).setScale(2, BigDecimal.ROUND_UP).doubleValue();
        DecimalFormat myFormat = new DecimalFormat("00");
        String minutes = myFormat.format((int)durationInMin);
        String seconds = myFormat.format((int)((durationInMin - (double)((int)durationInMin))*60));
        return minutes + ":" + seconds;
       
    }

}
