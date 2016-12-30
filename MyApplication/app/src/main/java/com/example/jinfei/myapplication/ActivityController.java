package com.example.jinfei.myapplication;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityController {

    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static Activity getTopActivity() {
        return activityList.isEmpty() ? null :  activityList.get(activityList.size() - 1);
    }

}
