package be.baes.thisDevelopersLifePlayer.controllers;

import android.content.DialogInterface;
import com.google.inject.Inject;

/**
* Created by IntelliJ IDEA.
* User: christiaan
* Date: 26/12/11
* Time: 9:03
* To change this template use File | Settings | File Templates.
*/
class OnDeleteAllClickListener implements DialogInterface.OnClickListener {
    @Inject
    be.baes.thisDevelopersLifePlayer.dal.PodCastAdapter podCastAdapter;
    @Inject
    be.baes.thisDevelopersLifePlayer.facade.PodCastList podCastList;
    @Inject
    be.baes.thisDevelopersLifePlayer.facade.Player player;

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        podCastAdapter.deleteAll();
        podCastList.load(0);
        player.setCurrentFile(null);
    }
}
