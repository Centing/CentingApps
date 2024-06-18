package com.c241ps220.centingapps.data


import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.c241ps220.centingapps.BuildConfig
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


//import net.gotev.uploadservice.BuildConfig;
/**
 * Created by rr on 11/27/18.
 */
class Initializer : Application() {
    var okHttpClient: OkHttpClient? = null

    override fun onCreate() {
        super.onCreate()

        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        //okHttpClient = new OkHttpClient.Builder()
        //        .addInterceptor(interceptor)
        //        .build();
        okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
        AndroidNetworking.initialize(this, okHttpClient)
    }


}
