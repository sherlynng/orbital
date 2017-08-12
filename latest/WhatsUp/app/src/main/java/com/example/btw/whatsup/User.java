package com.example.btw.whatsup;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.api.model.GetTokenResponse;

import java.util.List;

/**
 * Created by sherl on 4/7/2017.
 */

public class User extends FirebaseUser {
    private String name;
    private int score;
    private String uid;

    public User(){

    }

    public User(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUid() {
        return uid;
    }

    @NonNull
    @Override
    public String getProviderId() {
        return null;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }

    @Nullable
    @Override
    public List<String> getProviders() {
        return null;
    }

    @NonNull
    @Override
    public List<? extends UserInfo> getProviderData() {
        return null;
    }

    @NonNull
    @Override
    public FirebaseUser zzaq(@NonNull List<? extends UserInfo> list) {
        return null;
    }

    @Override
    public FirebaseUser zzcu(boolean b) {
        return null;
    }

    @NonNull
    @Override
    public FirebaseApp zzcow() {
        return null;
    }

    @Nullable
    @Override
    public String getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public Uri getPhotoUrl() {
        return null;
    }

    @Nullable
    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public boolean isEmailVerified() {
        return false;
    }

    @NonNull
    @Override
    public GetTokenResponse zzcox() {
        return null;
    }

    @Override
    public void zza(@NonNull GetTokenResponse getTokenResponse) {

    }

    @NonNull
    @Override
    public String zzcoy() {
        return null;
    }

    @NonNull
    @Override
    public String zzcoz() {
        return null;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
