package com.tmfl.common;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.tmfl.auth.Constant;
import com.tmfl.auth.TmflApi;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Sandeep on 6/2/17.
 */

public class ComplaintSoapApiService {

	static         ComplaintSoapApiService apiService;
	private static Retrofit                retrofit;
	TmflApi services;

	public static ComplaintSoapApiService getInstance() {
		if ( apiService == null ) {
			synchronized ( SoapApiService.class ) {
				apiService = new ComplaintSoapApiService();
				HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

				interceptor.setLevel( HttpLoggingInterceptor.Level.BODY );
				Strategy strategy = new AnnotationStrategy();

				Serializer serializer = new Persister( strategy );

				OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
				httpClient.networkInterceptors().add( new StethoInterceptor() );
				httpClient.addInterceptor( interceptor );

				retrofit = new Retrofit.Builder()
						.baseUrl( Constant.COMPLAINT_BASE )
						.client( httpClient.build() )
						.addConverterFactory( SimpleXmlConverterFactory.create( serializer ) )
						.build();
			}
		}
		return apiService;
	}

	public TmflApi call() {
		services = retrofit.create( TmflApi.class );
		return services;
	}
}
