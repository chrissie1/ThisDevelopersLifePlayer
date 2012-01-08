package be.baes.thisDevelopersLifePlayer.controllers;

import android.util.Log;
import android.view.View;
import com.google.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 13/12/11
 * Time: 9:31
 */
public class OnDownloadPodCastWithAlertDialogClickListener implements View.OnClickListener {
    @Inject
    be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    @Inject
    be.baes.thisDevelopersLifePlayer.view.YesNoAlertDialog yesNoAlertDialog;
    @Inject
    OnDownloadPodCastClickListener onDownloadPodCastClickListener;

    @Override
    public void onClick(View view) {
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "Clicked download podcast");
        yesNoAlertDialog.show(view, stringResources.getDownloadPodCastTitle(), stringResources.getDownloadPodcastMessage(), onDownloadPodCastClickListener, null);
    }

}
