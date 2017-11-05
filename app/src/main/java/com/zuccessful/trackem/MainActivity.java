package com.zuccessful.trackem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.callbacks.HyperTrackCallback;
import com.hypertrack.lib.models.Action;
import com.hypertrack.lib.models.ActionParams;
import com.hypertrack.lib.models.ActionParamsBuilder;
import com.hypertrack.lib.models.ErrorResponse;
import com.hypertrack.lib.models.SuccessResponse;
import com.hypertrack.lib.models.User;
import com.hypertrack.lib.models.UserParams;

import java.util.ArrayList;
import java.util.UUID;

import static com.zuccessful.trackem.LoginActivity.HT_QUICK_START_SHARED_PREFS_KEY;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private TextView textView;
    private Context context;

    private static ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button2);
        textView = findViewById(R.id.textView2);
        users = new ArrayList<>();
        HyperTrack.startTracking();
    }

    public void addUser(View view) {
        String num = editText.getText().toString();
//        Log.e("TEST", num);
        if (!num.equals("")) {
            HyperTrack.getUsersForLookupId(num, new HyperTrackCallback() {
                @Override
                public void onSuccess(@NonNull SuccessResponse successResponse) {
                    User u = (User) ((ArrayList) successResponse.getResponseObject()).get(0);
                    users.add(u);
                    Log.d("X", u.toString());
//                    Action action = new Action();
//                    action.setUser(users.get(0));
//                    Log.d("X", action.getTrackingURL().toString());
                    textView.setText(textView.getText() + "\n" + u.getName());
                }

                @Override
                public void onError(@NonNull ErrorResponse errorResponse) {
                    Log.d("X", errorResponse.toString());
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        HyperTrack.startTracking();
        super.onDestroy();
    }

    public void startTrackEm(View view) {
        final ArrayList<Action> actions = new ArrayList<>();
        for (User u: users) {
            ActionParams actionParams = new ActionParamsBuilder().setCollectionId(UUID.randomUUID().toString())
                    .setType(Action.ACTION_SUB_STATUS_USER_ON_THE_WAY)
                    .setUserId(u.getId()).build();
            HyperTrack.createAndAssignAction(actionParams, new HyperTrackCallback() {
                @Override
                public void onSuccess(@NonNull SuccessResponse successResponse) {
                    if (successResponse.getResponseObject() != null) {
                        Action action = (Action) successResponse.getResponseObject();
                        actions.add(action);
                        // Handle createAndAssign Action API success here
                        Toast.makeText(context, "Live Location successful shared back", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(@NonNull ErrorResponse errorResponse) {
                    Toast.makeText(context, "Live Location successful shared back", Toast.LENGTH_SHORT).show();
                }
            });
        }
        Intent intent = new Intent(MainActivity.this, TrackingActivity.class);
        startActivity(intent);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}