package com.cs203.test1;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

public class SettingsActivity extends AppCompatActivity {

    /***
     * boilerplate for settings activity
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /***
     * facades for getting preferences
     * @param c context
     * @return value of preference
     */
    public static boolean getMusicPreference(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("MUSIC_PREFERENCE_OPTION", false);
    }

    public static boolean getAnimationPreference(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("ANIMATION_PREFERENCE_OPTION", false);
    }

    public static boolean getUndoPreference(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("UNDO_PREFERENCE_OPTION", false);
    }

    public static int getStartingPreference(Context c) {
        String starting = PreferenceManager.getDefaultSharedPreferences(c).getString("STARTING_COLOR_OPTION", "0");
        return Integer.parseInt(starting);
    }

    public static float getAnimationSpeedPreference(Context c) {
        String animationSpeed = PreferenceManager.getDefaultSharedPreferences(c).getString("ANIMATION_SPEED_OPTION", "4");
        return Float.parseFloat(animationSpeed);
    }

    public static int getChipPreference(Context c) {
        String chipPrefs = PreferenceManager.getDefaultSharedPreferences(c).getString("CHIP_POWER_OPTION", "0");
        return Integer.parseInt(chipPrefs);
    }

    public static Theme getThemePreference(Context c) {
        String themePrefs = PreferenceManager.getDefaultSharedPreferences(c).getString("THEME_PREFERENCE_OPTION", "Classic");
        return switch(themePrefs) {
            case "Classic" -> new ClassicTheme();
            case "Dark" -> new DarkTheme();
            case "Party" -> new PartyTheme();
            default -> new ClassicTheme();
        };
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        /***
         * boilerplate for settings fragment
         * initialize preferences and show on screen
         * @param b bundle
         * @param s string
         */
        @Override
        public void onCreatePreferences(Bundle b, String s) {
            Context context = getPreferenceManager().getContext();
            PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);

            ListPreference theme = new ListPreference(context);
            theme.setTitle("Set theme");
            theme.setSummary("Change the current theme");
            theme.setKey("THEME_PREFERENCE_OPTION");
            String[] themeOption = {"Classic", "Dark", "Party"};
            String[] themeValue = {"Classic", "Dark", "Party"};
            theme.setEntries(themeOption);
            theme.setEntryValues(themeValue);
            theme.setDefaultValue("Classic");
            screen.addPreference(theme);

            SwitchPreference music = new SwitchPreference(context);
            music.setTitle("Play background music");
            music.setSummaryOn("Music will play");
            music.setSummaryOff("Music will not play");
            music.setKey("MUSIC_PREFERENCE_OPTION");
            music.setDefaultValue(false);
            screen.addPreference(music);

            ListPreference starting = new ListPreference(context);
            starting.setTitle("Starting color");
            starting.setSummary("Which color goes first");
            starting.setKey("STARTING_COLOR_OPTION");
            String[] startColor = {"Random", "Red", "Blue"};
            String[] startValue = {"0", "1", "2"};
            starting.setEntries(startColor);
            starting.setEntryValues(startValue);
            starting.setDefaultValue("0");
            screen.addPreference(starting);

            ListPreference chipPrefs = new ListPreference(context);
            chipPrefs.setTitle("Chip options");
            chipPrefs.setSummary("Customize chip powers");
            chipPrefs.setKey("CHIP_POWER_OPTION");
            String[] chipPower = {"Default", "All Normal Chips", "All Power Chips"};
            String[] chipPowerValue = {"0", "1", "2"};
            chipPrefs.setEntries(chipPower);
            chipPrefs.setEntryValues(chipPowerValue);
            chipPrefs.setDefaultValue("0");
            screen.addPreference(chipPrefs);

            SwitchPreference undo = new SwitchPreference(context);
            undo.setTitle("Disable undo moves");
            undo.setSummaryOn("Move cannot be undone");
            undo.setSummaryOff("Moves can be undone");
            undo.setKey("UNDO_PREFERENCE_OPTION");
            undo.setDefaultValue(false);
            screen.addPreference(undo);

            ListPreference animationSpeed = new ListPreference(context);
            animationSpeed.setTitle("Animation speed");
            animationSpeed.setSummary("Set chip's animation speed");
            animationSpeed.setKey("ANIMATION_SPEED_OPTION");
            String[] animationOption = {"Slow", "Medium", "Fast"};
            String[] animationValue = {"6", "4", "2"};
            animationSpeed.setEntries(animationOption);
            animationSpeed.setEntryValues(animationValue);
            animationSpeed.setDefaultValue("4");
            screen.addPreference(animationSpeed);

            SwitchPreference animation = new SwitchPreference(context);
            animation.setTitle("Disable chip animation");
            animation.setSummaryOn("Chip will move instantly");
            animation.setSummaryOff("Chip will animate and move");
            animation.setKey("ANIMATION_PREFERENCE_OPTION");
            animation.setDefaultValue(false);
            screen.addPreference(animation);

            setPreferenceScreen(screen);
        }
    }
}