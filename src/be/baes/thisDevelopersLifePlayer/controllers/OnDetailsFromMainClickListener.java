package be.baes.thisDevelopersLifePlayer.controllers;

import android.view.View;
import com.google.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 27/12/11
 * Time: 10:14
 */
public class OnDetailsFromMainClickListener implements View.OnClickListener {
    @Inject
    be.baes.thisDevelopersLifePlayer.view.Navigation navigation;

    @Override
    public void onClick(View view) {
        navigation.openDetails(be.baes.thisDevelopersLifePlayer.Constants.HANSELMINUTESPLAYER_ACTIVITY);
    }
}
