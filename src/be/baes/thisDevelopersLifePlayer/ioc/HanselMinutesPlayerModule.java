package be.baes.thisDevelopersLifePlayer.ioc;

import be.baes.thisDevelopersLifePlayer.facade.*;
import be.baes.thisDevelopersLifePlayer.view.*;
import com.google.inject.AbstractModule;

public class HanselMinutesPlayerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(be.baes.thisDevelopersLifePlayer.dal.PodCastAdapter.class).to(be.baes.thisDevelopersLifePlayer.dal.sqliteandroid.PodCastSQLiteAdapter.class);
        bind(be.baes.thisDevelopersLifePlayer.view.ProgressReport.class).to(be.baes.thisDevelopersLifePlayer.view.ProgressReportAndroid.class);
        bind(be.baes.thisDevelopersLifePlayer.facade.Player.class).to(be.baes.thisDevelopersLifePlayer.facade.PlayerImpl.class);
        bind(be.baes.thisDevelopersLifePlayer.facade.PodCastList.class).to(be.baes.thisDevelopersLifePlayer.facade.PodCastListImpl.class);
        bind(be.baes.thisDevelopersLifePlayer.facade.Settings.class).to(be.baes.thisDevelopersLifePlayer.facade.SettingsImpl.class);
        bind(be.baes.thisDevelopersLifePlayer.helpers.Network.class).to(be.baes.thisDevelopersLifePlayer.helpers.NetworkImpl.class);
        bind(be.baes.thisDevelopersLifePlayer.view.YesNoAlertDialog.class).to(be.baes.thisDevelopersLifePlayer.view.YesNoAlertDialogImpl.class);
        bind(be.baes.thisDevelopersLifePlayer.resources.StringResources.class).to(be.baes.thisDevelopersLifePlayer.resources.StringResourcesImpl.class);
        bind(be.baes.thisDevelopersLifePlayer.resources.ColorResources.class).to(be.baes.thisDevelopersLifePlayer.resources.ColorResourcesImpl.class);
        bind(be.baes.thisDevelopersLifePlayer.view.Navigation.class).to(NavigationImpl.class);
        bind(SearchList.class).to(SearchListImpl.class);
    }
}
