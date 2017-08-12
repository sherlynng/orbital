package com.example.btw.whatsup;

import java.util.Comparator;

/**
 * Created by sherl on 21/7/2017.
 */

public class LeadershipComparator implements Comparator<User> {
    @Override
    public int compare(User u1, User u2){
        return -(u1.getScore() - u2.getScore());
    }

    @Override
    public boolean equals(Object obj){
        return this == obj;
    }
}
