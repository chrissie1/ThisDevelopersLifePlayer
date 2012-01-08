package be.baes.thisDevelopersLifePlayer.controllers;

import android.util.Log;
import com.google.inject.Inject;
import android.view.View;
import android.view.View.OnClickListener;

public class OnRefreshListWithAlertDialogClickListener implements OnClickListener {
	@Inject
    be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;
    @Inject
    be.baes.thisDevelopersLifePlayer.view.YesNoAlertDialog yesNoAlertDialog;
    @Inject OnRefreshListClickListener onRefreshListClickListener;

    @Override
    public void onClick(View arg0) {
        Log.i(be.baes.thisDevelopersLifePlayer.Constants.LOG_ID, "Clicked refresh list");
        yesNoAlertDialog.show(arg0, stringResources.getRefreshListTitle(), stringResources.getRefreshListMessage(), onRefreshListClickListener, null);
    }

}
