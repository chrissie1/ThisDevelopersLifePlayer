package be.baes.thisDevelopersLifePlayer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.google.inject.Inject;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by IntelliJ IDEA.
 * User: christiaan
 * Date: 20/12/11
 * Time: 10:01
 */
public class SettingsActivity extends RoboActivity implements Observer{
    @InjectView(R.id.refreshListButton) Button refreshListButton;
    @InjectView(R.id.deleteAllButton) Button deleteAllButton;
    @InjectView(R.id.totalInDatabase) TextView totalInDatabase;
    @InjectView(R.id.totalDowloadedFiles) TextView totalDownloadedFiles;
    @InjectView(R.id.totalDowloadedImages) TextView totalDownloadedImages;
    @Inject
    be.baes.thisDevelopersLifePlayer.controllers.OnRefreshListWithAlertDialogClickListener onRefreshListWithAlertDialogClickListener;
    @Inject
    be.baes.thisDevelopersLifePlayer.controllers.OnDeleteAllWithAlertDialogClickListener onDeleteAllWithAlertDialogClickListener;
    @Inject
    be.baes.thisDevelopersLifePlayer.facade.PodCastList podCastList;
    @Inject
    be.baes.thisDevelopersLifePlayer.view.ProgressReport progressReport;
    @Inject
    be.baes.thisDevelopersLifePlayer.resources.StringResources stringResources;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        progressReport.setActivity(this);
        setObservers();
        setListeners();
        podCastList.load(0);
    }

    private void setObservers() {
        podCastList.addObserver(this);
    }

    private void setListeners() {
        refreshListButton.setOnClickListener(onRefreshListWithAlertDialogClickListener);
        deleteAllButton.setOnClickListener(onDeleteAllWithAlertDialogClickListener);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        progressReport.setActivity(this);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        podCastList.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        totalInDatabase.setText(((be.baes.thisDevelopersLifePlayer.model.FillListResult)o).getNumberOfPodCasts());
        totalDownloadedFiles.setText(stringResources.getTotalDownloadedFiles() + " " + ((be.baes.thisDevelopersLifePlayer.model.FillListResult) o).getNumberOfDownloadedPodCasts());
        totalDownloadedImages.setText(stringResources.getTotalDownloadedImages() + " " + ((be.baes.thisDevelopersLifePlayer.model.FillListResult) o).getNumberOfDownloadedImages());
    }
}