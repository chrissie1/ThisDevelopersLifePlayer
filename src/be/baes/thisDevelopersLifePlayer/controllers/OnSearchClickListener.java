package be.baes.thisDevelopersLifePlayer.controllers;

import android.view.MenuItem;
import com.google.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 30/12/11
 * Time: 20:36
 */
public class OnSearchClickListener implements MenuItem.OnMenuItemClickListener {
    @Inject
    be.baes.thisDevelopersLifePlayer.view.Navigation navigation;

   @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        navigation.openSearch();
        return true;
    }
}
