package com.test.sybo;

import static android.content.Context.ACTIVITY_SERVICE;
import android.app.ActivityManager;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class MyPlugin {


    FirebaseDatabase database;
    DatabaseReference reference;



    private Context context;

    public MyPlugin(Context context) {
        this.context = context;

    }

    public String GetDeviceName() {

        try {
            return android.os.Build.MODEL;

        } catch (Exception e) {
            Log.e("MyPlugin", "Exception in gatheringDeviceName", e);
            return "Error";
        }

    }


    public String GetDeviceID() {
        try {
            String deviceID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            if (deviceID != null && !deviceID.trim().isEmpty()) {
                return deviceID;
            }
        } catch (SecurityException e) {
            // SecurityException is thrown if the required permission is not granted
            Log.e("MyPlugin", "SecurityException in getDeviceID", e);
        } catch (Exception e) {
            // Handle other possible exceptions
            Log.e("MyPlugin", "Exception in getDeviceID", e);
        }
        // If we couldn't get the device ID or an exception occurred, return the model
         return android.os.Build.MODEL;
    }



    public String GetDeviceCpu() {


        try {

            return String.valueOf(Runtime.getRuntime().availableProcessors());


        } catch (Exception e) {
            Log.e("MyPlugin", "Exception in gatheringDeviceCPU", e);
            return "Error";
        }

    }



    public String GetDeviceMemory() {

        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            long totalMemory = memoryInfo.totalMem;
            double totalMemoryGB = totalMemory / 1024.0 / 1024.0 / 1024.0;

            return String.format("%.2f GB", totalMemoryGB);


        } catch (Exception e) {
            Log.e("MyPlugin", "Exception in getTotalMemory", e);
            return "Error";
        }

    }



    public String GetDeviceOs() {


        try {

            return android.os.Build.VERSION.RELEASE;


        } catch (Exception e) {
            Log.e("MyPlugin", "Exception in gatheringDeviceOS", e);
            return "Error";
        }

    }



    public void SaveDatasToFirebaseDB() {
        Log.d("MyPlugin", "Attempting to save to database.");

        if (!NetworkUtil.isNetworkAvailable(context)) {
            Log.d("MyPlugin", "Save failed: Device is not connected to the internet.");
             return;
        }

        String deviceid = GetDeviceID();
        if (deviceid == null || deviceid.isEmpty()) {
            Log.d("MyPlugin", "Save failed: Device ID is null or empty.");
             return;
        }



         database = FirebaseDatabase.getInstance();
         reference = database.getReference("Devices");

         HelperClass helperClass = new HelperClass(
                 deviceid,
                 GetDeviceName(),
                 GetDeviceCpu(),
                 GetDeviceMemory(),
                 GetDeviceOs());
         reference.child(deviceid).setValue(helperClass)
                .addOnSuccessListener(aVoid -> Log.d("MyPlugin", "Data saved successfully!"))
                .addOnFailureListener(e -> Log.d("MyPlugin", "Failed to save data.", e));


    }


}

