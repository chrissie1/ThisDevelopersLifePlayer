package be.baes.thisDevelopersLifePlayer.controllers;

import android.view.View;
import com.google.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 1/01/12
 * Time: 14:37
 */
public class OnViewInBrowserOnClickListener implements View.OnClickListener {
    @Inject
    be.baes.thisDevelopersLifePlayer.view.Navigation navigation;
    @Inject
    be.baes.thisDevelopersLifePlayer.facade.Player player;

    @Override
    public void onClick(View view) {
        navigation.openBrowser(player.getCurrentPodCast().getLink());
    }
}
