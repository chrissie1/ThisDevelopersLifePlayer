package be.baes.thisDevelopersLifePlayer.view;

import android.content.DialogInterface;
import android.view.View;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 21/12/11
 * Time: 18:40
 */
public interface YesNoAlertDialog {
    void show(View view, String title, String message, DialogInterface.OnClickListener positiveClickListener, DialogInterface.OnClickListener negativeClickListener);
}
