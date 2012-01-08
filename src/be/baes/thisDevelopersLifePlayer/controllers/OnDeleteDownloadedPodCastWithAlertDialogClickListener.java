package be.baes.thisDevelopersLifePlayer.controllers;

import android.util.Log;
import android.view.View;
import com.google.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 21/12/11
 * Time: 15:22
 */
public class OnDeleteDownloadedPodCastWithAlertDialogClickListener implements View.OnClickListener {
    @Inject
    be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    @Inject
    be.baes.thisDevelopersLifePlayer.view.YesNoAlertDialog yesNoAlertDialog;
    @Inject OnDeleteDownloadedPodCastClickListener onDeleteDownloadedPodCastClickListener;

    @Override
    public void onClick(View view) {
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "PodCast item selected");
        yesNoAlertDialog.show(view, stringResources.getDeleteDownloadTitle(), stringResources.getDeleteDownloadMessage(), onDeleteDownloadedPodCastClickListener, null);
    }

}
