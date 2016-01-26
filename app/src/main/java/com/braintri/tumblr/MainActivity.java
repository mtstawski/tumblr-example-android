package com.braintri.tumblr;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.braintri.tumblr.activities.PostListActivity;


/**
 * Created by mike on 12.01.16.
 */
public class MainActivity extends AppCompatActivity {


    private ImageView logo;
    private EditText username;
    private TextView bottomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        logo = (ImageView) findViewById(R.id.login_logo);
        username = (EditText) findViewById(R.id.login_username);
        bottomButton = (TextView) findViewById(R.id.login_bottom_button);

        String userNameStr = username.getText().toString();
        if (!userNameStr.isEmpty()){
            saveUsername(userNameStr);

        }
        bottomButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN ){
                    String userNameStr = username.getText().toString();
                    if (!userNameStr.isEmpty()) {
                        saveUsername(userNameStr);

                        Intent i = new Intent(getApplicationContext(), PostListActivity.class);
                        startActivity(i);

                    }else{
                        Toast.makeText(getApplicationContext(), "Type username" , Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

    }

    public void saveUsername(String userName){

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("userName", userName);
        editor.commit();
    }
    /**
     * Hide keyboard on touch at any point on the screen
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            Log.d("Activity", "Touch event " + event.getRawX() + "," + event.getRawY() + " " + x + "," + y + " rect " + w.getLeft() + "," + w.getTop() + "," + w.getRight() + "," + w.getBottom() + " coords " + scrcoords[0] + "," + scrcoords[1]);
            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom()) ) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }


}
