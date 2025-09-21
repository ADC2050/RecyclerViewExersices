package com.example.recyclerviewexersices;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyDialogHelper {

    public static void showAlertDialogWithListener(Context context, String title, String message, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", okListener);
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}