package com.cs203.test1;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;

/***
 * MainActivity class
 */
public class MainActivity extends AppCompatActivity {

    private Aview a;
    private MediaPlayer mp;

    /***
     * Override onCreate() method to set content view to Aview
     * initialize MediaPlayer
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a = new Aview(this);
        mp = MediaPlayer.create(this, R.raw.study_and_relax);
        mp.setLooping(true);
        mp.setVolume(1,1);
        setContentView(a);
    }

    /***
     * onBackPressed() close when back button is pressed
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    /***
     * onPause() method to pause music
     */
    @Override
    public void onPause() {
        super.onPause();
        if (SettingsActivity.getMusicPreference(this)) {
            mp.pause();
        }
    }

    /***
     * onResume() method to resume music
     */
    @Override
    public void onResume() {
        super.onResume();
        if (SettingsActivity.getMusicPreference(this)) {
            mp.start();
        }
    }
}