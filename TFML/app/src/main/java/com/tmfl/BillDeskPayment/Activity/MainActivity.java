package com.tmfl.BillDeskPayment.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;


import com.tmfl.BillDeskPayment.Adapter.CustomAdapter;
import com.tmfl.BillDeskPayment.Models.Contract;
import com.tmfl.BillDeskPayment.Models.Example;
import com.tmfl.BillDeskPayment.Models.JSONData;
import com.tmfl.BillDeskPayment.RetrofitInterface.RetrofitInterface;
import com.tmfl.R;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

	ListView listView;
	Contract contractModel;
	ProgressDialog dialog;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.list_layout );
		listView = ( ListView ) findViewById( R.id.lstView );
		getData();
	}

	public void getData() {

		 TmflApi service = ApiService.getInstance().call();

		dialog= new ProgressDialog( MainActivity.this );
		dialog.setMessage( "Loading" );
		dialog.show();

		JSONData jsonData = new JSONData();
		jsonData.setUser_id( "2002680345" );
		jsonData.setApi_token( "jSYNilbEhNWdKBvFzbcCEpARI8LnUYa8CQpGhHLVJsPlLa6goAab4ReueylI" );

		service.getListData( jsonData ).enqueue( new Callback<Example>() {
			@Override
			public void onResponse( Call< Example > call, Response< Example > response ) {

				List< Contract > list = new ArrayList< Contract >();
				list.addAll( response.body().getData().getActive().getContracts() );
				Log.e( "List Size", String.valueOf( response.body().getData().getActive().getContracts().size() ) );
				for(int i=0;i<list.size();i++){
					list.get( i ).setSelected( false );
				}
				CustomAdapter adapter = new CustomAdapter( MainActivity.this, list );
				listView.setAdapter( adapter );
				dialog.dismiss();
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure( Call< Example > call, Throwable t ) {
				Log.e("Error Log","Error:"+t.getMessage());
			}
		} );

	}


}
