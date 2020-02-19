package com.sanved.blankwallpaper1;

import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class StartScreen extends AppCompatActivity {

    FloatingActionButton fab;
    int width, height;
    Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        // Obtain the shared Tracker instance.

        fab = (FloatingActionButton) findViewById(R.id.udtahuyaButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Taking screen dimensions
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                width = size.x;
                height = size.y;

                //Making a black bitmap
                Bitmap bitblack = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                bitblack.eraseColor(Color.BLACK);

                //Applying Wallpaper
                WallpaperManager myWallpaperManager
                        = WallpaperManager.getInstance(getApplicationContext());
                try {
                    myWallpaperManager.setBitmap(bitblack);
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(StartScreen.this);
                    builder2.setMessage("Wallpaper has been set :-)")
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    mTracker.send(new HitBuilders.EventBuilder()
                                            .setCategory("Wallpaper Applied")
                                            .setAction("Applied")
                                            .build());
                                }
                            });
                    AlertDialog alert = builder2.create();
                    alert.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(StartScreen.this, "Oops ! Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
