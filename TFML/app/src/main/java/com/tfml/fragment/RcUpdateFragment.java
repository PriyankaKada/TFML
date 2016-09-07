package com.tfml.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.model.uploadRcResponseModel.RcUploadDataInputModel;
import com.tfml.model.uploadRcResponseModel.RcUploadResponseModel;
import com.tfml.util.ImageDecoding;
import com.tfml.util.SetFonts;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RcUpdateFragment extends Fragment implements View.OnClickListener {

    private EditText txtRcNo;
    private Button btnRcUpload;
    private View view;
    private ImageView img_upload;
    Uri selectedImage;
    TmflApi tmflApi;
    String imgPhotoUrl,imgExt;
    static final int REQUEST_TAKE_PHOTO = 1;
    int mtype;
    RcUploadDataInputModel rcUploadDataInputModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_rc_update, container, false);
        tmflApi= ApiService.getInstance().call();
        init();
        return view;
    }
    public void init()
    {

       txtRcNo=(EditText)view.findViewById(R.id.txt_rc_no);
        btnRcUpload=(Button)view.findViewById(R.id.btn_rc_upload);
        img_upload=(ImageView)view.findViewById(R.id.img_upload);
        SetFonts.setFonts(getActivity(),btnRcUpload,2);
        btnRcUpload.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_rc_upload:

                if(!TextUtils.isEmpty(txtRcNo.getText().toString())) {
                    mtype = REQUEST_TAKE_PHOTO;
                    upLoadRCdoc();
                }
                else
                {
                    Toast.makeText(getActivity(),"Please Enter RC Number",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public void upLoadRCdoc()
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Pictures Option");
        alertDialog.setIcon(getResources().getDrawable(R.drawable.ic_image));
        alertDialog.setPositiveButton("GALLARY",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI) , 1);
            }
        });
        alertDialog.setNegativeButton("CAMERA",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(ImageDecoding.isDeviceSupportCamera(getActivity())){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    selectedImage = ImageDecoding.getOutputMediaFileUri(1);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImage);
                    startActivityForResult(intent, 0);
                }
            }
        });
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path="";
        switch(requestCode) {
            case 0:
                if(resultCode == Activity.RESULT_OK) {
                    path=selectedImage.getPath();
                }
                break;

            case 1:
                if(resultCode == Activity.RESULT_OK){
                    selectedImage=data.getData();
                    path=getRealPathFromURI(selectedImage);
                }
                break;
        }

        if(selectedImage!=null) {
            if (mtype == 1) {
                imgPhotoUrl = path;
                if (TextUtils.isEmpty(imgPhotoUrl)) {
                    img_upload.setImageResource(R.drawable.ic_image);
                } else {
                    img_upload.setImageBitmap(ImageDecoding.decodeBitmapFromFile(imgPhotoUrl, 100, 100));
                    imgExt = imgPhotoUrl.substring(imgPhotoUrl.lastIndexOf(".") + 1, imgPhotoUrl.length());
                    Log.e("ImageUrl",imgPhotoUrl);
                    rcUploadDataInputModel=new RcUploadDataInputModel();
                    rcUploadDataInputModel.setUserId("11");
                    rcUploadDataInputModel.setContractNo("C124");
                    rcUploadDataInputModel.setRcNo(txtRcNo.getText().toString());
                    File file = new File(imgPhotoUrl);
                    rcUploadDataInputModel.setImage(file);
                    if(CommonUtils.isNetworkAvailable(getActivity()))
                    {
                        CommonUtils.showProgressDialog(getActivity(),"Please Wait Uploading Data.....");

                            callRcUploadData(rcUploadDataInputModel);
                    }
                    else
                    {
                        CommonUtils.showAlert1(getActivity(),"","No Internet Connection",false);
                    }

                }
            }
        }
    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] path = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri,path, null, null, null);
        cursor.moveToFirst();
        String picturePath = cursor.getString(cursor.getColumnIndex(path[0]));
        cursor.close();
        return picturePath;
    }
    public void callRcUploadData(RcUploadDataInputModel rcUploadDataInputModel)
    {
        tmflApi.getRcUploadData(rcUploadDataInputModel).enqueue(new Callback<RcUploadResponseModel>() {
            @Override
            public void onResponse(Call<RcUploadResponseModel> call, Response<RcUploadResponseModel> response) {
                if (response.body().getStatus().contains("success")) {
                    CommonUtils.closeProgressDialog();
                    Log.e("getRcUploadData", response.body().getStatus());
                    CommonUtils.showAlert1(getActivity(), "", "RC Uploaded Successfully", false);
                } else {
                 //   Log.e("getApplyloanErr", response.body().getMessages());
                    CommonUtils.closeProgressDialog();
                    CommonUtils.showAlert1(getActivity(), "", response.body().getMessages(), false);
                }
            }

            @Override
            public void onFailure(Call<RcUploadResponseModel> call, Throwable t) {
                CommonUtils.closeProgressDialog();
            }
        });
    }
}
