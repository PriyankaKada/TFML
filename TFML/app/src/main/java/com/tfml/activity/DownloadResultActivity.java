package com.tfml.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tfml.R;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadResultActivity extends BaseActivity {

    ImageView  imgResult;
    String fileUrl;
    TmflApi tmflApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_result);
        Bundle bundle=this.getIntent().getExtras();
        fileUrl=bundle.getString("URL");
        init();
    }
    public void init()
    {
        imgResult=(ImageView)findViewById(R.id.img_download);
        callDownloadImageFromUri(fileUrl);
    }

    public void callDownloadImageFromUri(String URL)
    {


        Picasso.with(this).load(URL).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.d("abc","onbitmap loaded called");
                imgResult.setImageBitmap(bitmap);
                String path =   Environment.getExternalStorageDirectory().toString()
                        + "/TMFL/Download/"+"Tmfl.jpg";
              //  String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "Tmfl.jpg";
              //  Log.d("abcc","apth "+path);

                FileOutputStream outputStream = null;
                try {
                    File file = new File(path);
                    outputStream = new FileOutputStream(file);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.d("abc","onbitmap failed called");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.d("abc","onbitmap prepareload called");
            }
        });

       /* tmflApi= ApiService.getInstance().call();
        tmflApi.getFile(URL).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("onResponse", response.message());
            *//*    if(!response.isSuccessful()){
                    Log.e("onResponse", "Something's gone wrong");
                    // TODO: show error message
                    return;
                }
                DownloadFileAsyncTask downloadFileAsyncTask = new DownloadFileAsyncTask();
                downloadFileAsyncTask.execute(response.body().byteStream());*//*

                try {

                    Log.d("onResponse", "Response came from server");

                    boolean FileDownloaded = DownloadImage(response.body());

                    Log.d("onResponse", "Image is downloaded and saved ? " + FileDownloaded);

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });*/
    }



}
