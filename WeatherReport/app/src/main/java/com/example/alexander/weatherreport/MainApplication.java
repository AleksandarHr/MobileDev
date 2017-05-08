package com.example.alexander.weatherreport;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainApplication extends Application {

    private Realm realmCity;

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
        realmCity = Realm.getInstance(config);
    }

    public void closeRealm() {
        realmCity.close();
    }

    public Realm getRealmTodo() {
        return realmCity;
    }
}
