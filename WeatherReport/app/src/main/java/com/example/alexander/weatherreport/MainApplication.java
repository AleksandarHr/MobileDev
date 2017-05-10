package com.example.alexander.weatherreport;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication extends Application {

    private Realm realmWeather;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }

    public void openRealm() {
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realmWeather = Realm.getInstance(config);
    }

    public void closeRealm() {
        realmWeather.close();
    }

    public Realm getRealmWeather() {
        return realmWeather;
    }
}
