package com.tmfl.BillDeskPayment.ApiService;

import com.tmfl.BillDeskPayment.RetrofitInterface.RetrofitInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by webwerks on 2/1/17.
 */
public class ApiService {


	public static Retrofit retrofit;

	public static RetrofitInterface getRetrofit() {
		OkHttpClient.Builder client             = new OkHttpClient.Builder();
		client.connectTimeout( 10, TimeUnit.SECONDS );
		HttpLoggingInterceptor       loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BODY );

		retrofit = new Retrofit.Builder()
				.baseUrl( "http://staging.php-dev.in:8844/tatamotors/public/" )
				.addConverterFactory( GsonConverterFactory.create() )
				.client( client.build() )
				.build();


		RetrofitInterface service = retrofit.create( RetrofitInterface.class );
		return service;
	}
}
