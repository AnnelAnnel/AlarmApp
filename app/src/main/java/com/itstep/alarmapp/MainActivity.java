package com.itstep.alarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.itstep.alarmapp.Retrofit.ApiClient;
import com.itstep.alarmapp.Retrofit.ApiInterface;
import com.itstep.alarmapp.Retrofit.Example;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button getTime=findViewById(R.id.setAlarm);
    Button getWeather=findViewById(R.id.getWeather);
    TimePicker tp=findViewById(R.id.timePick);
    TextView city=findViewById(R.id.cityName);
    TextView tmp=findViewById(R.id.temperature);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeatherData(city.getText().toString().trim());

            }
        });

        getTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });
    }

    private void getWeatherData(String name){

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Example> call = apiInterface.getWeatherData(name);

        ((Call) call).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                tmp.setText("Temp"+" "+response.body().getMain().getTemp()+" C");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

    }

    private void setAlarm(){
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
        PendingIntent pendI=PendingIntent.getBroadcast(this,0, i,0);
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+totalMilliseconds, pendI);

    }

}