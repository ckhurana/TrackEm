package com.zuccessful.trackem;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.HyperTrackMapFragment;
import com.hypertrack.lib.MapFragmentCallback;
import com.hypertrack.lib.models.Action;
import com.hypertrack.lib.models.HyperTrackLocation;
import com.hypertrack.lib.models.User;

import java.util.List;

public class TrackingActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        HyperTrackMapFragment htFragment = (HyperTrackMapFragment) getSupportFragmentManager().findFragmentById(R.id.htMapfragment);
        htFragment.setMapFragmentCallback(mapFragmentCallback);

    }

    private MapFragmentCallback mapFragmentCallback = new MapFragmentCallback() {
        @Override
        public void onMapReadyCallback(HyperTrackMapFragment hyperTrackMapFragment, GoogleMap map) {
            Toast.makeText(context, "On Map Ready callback", Toast.LENGTH_SHORT).show();
            for (User u : MainActivity.getUsers()) {
                hyperTrackMapFragment.startPulse(true, u.getId());
                MarkerOptions m = new MarkerOptions();
                HyperTrackLocation hl = u.getLastLocation();
//            MainActivity.getUsers().get(0).getLastLocation()
                if(hl != null) {
                    m.position(hl.getLatLng());
                    hyperTrackMapFragment.addCustomMarker(m);
                }

            }
//            hyperTrackMapFragment.startPulse(true, MainActivity.getUsers().get(0).getId());
//            MarkerOptions m = new MarkerOptions();
//            HyperTrackLocation hl = MainActivity.getUsers().get(0).getLastLocation();
////            MainActivity.getUsers().get(0).getLastLocation()
//            m.position(hl.getLatLng());
//            hyperTrackMapFragment.addCustomMarker(m);
            super.onMapReadyCallback(hyperTrackMapFragment, map);
        }

        @Override
        public void onHeroMarkerAdded(HyperTrackMapFragment hyperTrackMapFragment, String actionID, Marker heroMarker) {
            Toast.makeText(context, "Hero Marker Added callback", Toast.LENGTH_SHORT).show();
            super.onHeroMarkerAdded(hyperTrackMapFragment, actionID, heroMarker);
        }

        @Override
        public void onActionStatusChanged(List<String> changedStatusActionIds, List<Action> changedStatusActions) {
            super.onActionStatusChanged(changedStatusActionIds, changedStatusActions);
        }

        @Override
        public void onActionRefreshed(List<String> refreshedActionIds, List<Action> refreshedActions) {
            super.onActionRefreshed(refreshedActionIds, refreshedActions);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HyperTrack.removeActions(null);
    }
}
