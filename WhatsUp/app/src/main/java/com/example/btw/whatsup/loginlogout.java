package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by BTW on 6/29/2017.
 */

public class loginlogout extends AppCompatActivity {
    private static final int RC_SIGN_IN = 0;
    FirebaseAuth auth;
    Button authButton;
    View start;

    DatabaseReference databaseGamerUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlogout);
        auth = FirebaseAuth.getInstance();
        databaseGamerUsers = FirebaseDatabase.getInstance().getReference("users");

        if (auth.getCurrentUser() != null) {
            //user already signed in
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("AUTH", "user logged out");
                        }
                    });
        } else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setProviders(
                            AuthUI.FACEBOOK_PROVIDER,
                            AuthUI.EMAIL_PROVIDER,
                            AuthUI.GOOGLE_PROVIDER)
                    .build(), RC_SIGN_IN);

        }
        if (auth.getCurrentUser() != null) {
            FirebaseUser user = auth.getCurrentUser();
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
             //   initaliseUser(providerId, uid, name, email);
            }
            ;
        }
        finish();

    }
/*
    private void initaliseUser(String providerId, String uid, String name, String email) {
        //  String gameID = auth.getCurrentUser().getDisplayName();
        String id = auth.getCurrentUser().getUid();

        User user = new User(providerId, uid, name, email);

        databaseGamerUsers.child(id).setValue(user);

        Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show();
    }
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                //user logged in
                Log.d("AUTH", auth.getCurrentUser().getEmail());
            } else {
                //user not authenticated
                Log.d("AUTH", "not authenticated");
            }
        }
    }

}
