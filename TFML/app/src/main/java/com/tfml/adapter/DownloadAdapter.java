package com.tfml.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.tfml.R;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.DownloadFileAsyncTask;
import com.tfml.model.downloadResponseModel.Datum;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.data;

/**
 * Created by webwerks on 1/9/16.
 */

public class DownloadAdapter extends BaseAdapter {
    List<Datum>downloadList;
    Context context;
    TmflApi tmflApi;
    public DownloadAdapter(Context context, List<Datum>downloadList) {
        this.context=context;
        this.downloadList=downloadList;
    }

    @Override
    public int getCount() {
        return downloadList.size();
    }

    @Override
    public Object getItem(int position) {
        return downloadList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(convertView==null)
        {
            LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
            convertView=layoutInflater.inflate(R.layout.download_item_list,null);
            holder=new Holder();
            holder.txtFile=(TextView)convertView.findViewById(R.id.txt_file_name);
            holder.txtFile.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            convertView.setTag(holder);
        }
        else

            holder=(DownloadAdapter.Holder)convertView.getTag();
            holder.txtFile.setText(downloadList.get(position).getName());
         holder.txtFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String fileUrl=downloadList.get(position).getFile();
                Toast.makeText(context,fileUrl,Toast.LENGTH_SHORT).show();
                callDownloadImageFromUri(fileUrl);
            }
        });
            return convertView;


    }
    public class Holder
    {
      TextView txtFile;
    }

    public void callDownloadImageFromUri(String URL)
    {
        tmflApi= ApiService.getInstance().call();
        tmflApi.getFile(URL).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("onResponse", response.message());
                if(!response.isSuccessful()){
                    Log.e("onResponse", "Something's gone wrong");
                    // TODO: show error message
                    return;
                }
                DownloadFileAsyncTask downloadFileAsyncTask = new DownloadFileAsyncTask();
                downloadFileAsyncTask.execute(response.body().byteStream());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
             Log.e("onFailure",t.getMessage());
            }
        });
    }
}
