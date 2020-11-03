package com.itstep.alarmapp.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("weather?appid=310659a9009b2511d2aac82af68132d4&units=metric")


    Call<Example> getWeatherData(@Query("q") String name);


}
