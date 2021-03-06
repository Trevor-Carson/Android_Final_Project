package com.example.final_project_rss_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    /**
     * String to store the title of the shared preferences file
     */
    private static final String SHARED_PREFS = "rssReader";
    /**
     * String to store the key in the shared preferences file
     */
    private static final String EMAIL_PREF_KEY = "email";

    /**
     * EditText to store the UI value for email
     */
    private EditText emailText;
    /**
     * Button to hold the UI value for logging in
     */
    private Button login;
    /**
     * SharedPreferences variable to store a shared preferences object
     */
    private SharedPreferences prefs;
    /**
     * String to store the entered email
     */
    private String savedEmail;

    /**
     * Override method to set the initial layout to the MainActivity when the application initially opens
     *
     * @param savedInstanceState - Object to hold the bundled information to pass to main activity
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences pref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String savedEmail = pref.getString(EMAIL_PREF_KEY, "");
        ((EditText) findViewById(R.id.email)).setText(savedEmail);

        // When the login button is clicked, transition to the Profile activity.
        findViewById(R.id.login).setOnClickListener(clk -> {
            // Saving the email to the shared preferences while clicking the button wasn't required, but it's helpful.
            saveEmailToPreferences();
            startActivity(new Intent(this, MainActivity.class));
        });
    }
    /**
     * Overridden pause method to ensure the contents of the email box are saved in the shared preferences
     */
    @Override
    protected void onPause() {
        super.onPause();

        saveEmailToPreferences();
    }
    /**
     * Method to save the entered email into a string and commit it to shared preferences
     */
    private void saveEmailToPreferences() {
        SharedPreferences pref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String enteredEmail = ((EditText) findViewById(R.id.email)).getText().toString();
        pref.edit().putString(EMAIL_PREF_KEY, enteredEmail).commit();
    }
}