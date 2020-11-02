package com.itstep.alarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    Button getTime=findViewById(R.id.setAlarm);
    TimePicker tp=findViewById(R.id.timePick);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void setAlarm(View v){
        int hour= tp.getHour();
        int minutes=tp.getMinute();
        int totalMilliseconds=(hour*60+minutes)*60000;
        String time =Integer.toString(hour)+':'+ Integer.toString(minutes);

        SharedPreferences shf=getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = shf.edit();
        editor.putString("value", time);
        editor.apply();

        TextView textView = findViewById(R.id.setTime);
        String value = shf.getString("value","");
        textView.setText(value);

        Intent i=new Intent(this, MyAlarm.class);
        PendingIntent pendI=PendingIntent.getBroadcast(getApplicationContext(),0, i,0);
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+totalMilliseconds, pendI);


    }
}