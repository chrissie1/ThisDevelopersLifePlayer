package be.baes.thisDevelopersLifePlayer.controllers;

import android.view.GestureDetector;
import android.view.MotionEvent;
import com.google.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 28/12/11
 * Time: 21:39
 */
public class OnFlingMainListener extends GestureDetector.SimpleOnGestureListener {
    @Inject
    be.baes.thisDevelopersLifePlayer.view.Navigation navigation;

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        navigation.openDetailsWitFlingAnimation(be.baes.thisDevelopersLifePlayer.Constants.HANSELMINUTESPLAYER_ACTIVITY);
        return false;
    }

    // It is necessary to return true from onDown for the onFling event to register
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }
}
