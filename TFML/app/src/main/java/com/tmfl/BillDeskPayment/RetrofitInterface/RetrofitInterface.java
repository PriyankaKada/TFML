package com.tmfl.BillDeskPayment.RetrofitInterface;

import com.tmfl.BillDeskPayment.Models.Example;
import com.tmfl.BillDeskPayment.Models.JSONData;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by webwerks on 2/1/17.
 */
public interface RetrofitInterface {


	@POST( "customer/myContracts" )
	Call<Example> getListData(@Body JSONData jsonData);
}
