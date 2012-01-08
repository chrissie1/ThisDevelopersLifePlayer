package be.baes.thisDevelopersLifePlayer.controllers;

import android.view.MenuItem;
import com.google.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 20/12/11
 * Time: 9:58
 */
public class OnSettingsClickListener implements MenuItem.OnMenuItemClickListener {
    @Inject
    be.baes.thisDevelopersLifePlayer.view.Navigation navigation;

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        navigation.openSettings();
        return true;
    }
}
