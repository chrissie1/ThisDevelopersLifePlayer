package be.baes.thisDevelopersLifePlayer.controllers;

import android.util.Log;
import android.view.View;
import com.google.inject.Inject;

public class OnDeleteAllWithAlertDialogClickListener implements View.OnClickListener {
    @Inject
    be.baes.thisDevelopersLifePlayer.view.YesNoAlertDialog yesNoAlertDialog;
    @Inject
    be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    @Inject OnDeleteAllClickListener onDeleteAllClickListener;

    @Override
    public void onClick(View view) {
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "Clicked delete all");
        yesNoAlertDialog.show(view, stringResources.getDeleteAllTitle(), stringResources.getDeleteAllMessage(), onDeleteAllClickListener, null);
    }

}
