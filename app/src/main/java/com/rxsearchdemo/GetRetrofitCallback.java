package com.rxsearchdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.rxsearchdemo.ReminderHistory.ReminderHistoryRequest;

import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by whiz-010 on 19-Apr-17 at 05:51 PM.
 */

public class GetRetrofitCallback {

    private OnUpdateRetrofitListener OnUpdateRetrofitListener;
    private Context context;
    private ProgressDialog progressDialog;
    private Object jsonObject;
    private int intDialogShow = 0;
    String strApiMode;
    Activity contextAc;

    public GetRetrofitCallback(Context context, Object jsonObject,
                               int intDialogShow, String strApiMode, OnUpdateRetrofitListener OnUpdateRetrofitListener) {
        this.OnUpdateRetrofitListener = OnUpdateRetrofitListener;
        this.jsonObject = jsonObject;
        this.strApiMode = strApiMode;
        this.intDialogShow = intDialogShow;
        this.context = context;
        contextAc = (Activity) context;
        progressDialog = new ProgressDialog(context);
        if (intDialogShow == 1 || intDialogShow == 2) {
            progressDialog.setMessage("Loading..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        getData();
    }

    private void getData() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebField.BASE_IP)
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build();
        ApiService request = retrofit.create(ApiService.class);
        Call<ResponseBody> call = null;
        if (strApiMode.equalsIgnoreCase("reminder_history")) {
            call = request.reminder_history(WebField.headerOne, (ReminderHistoryRequest) jsonObject);
        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String abc = response.body().string();
                        if (abc != null) {
                            JSONObject jsonResult = new JSONObject(abc);
                            if (jsonResult.getString(WebField.STATUS).equals("1")) {
                                OnUpdateRetrofitListener.onUpdateCompleteRetrofit(abc, true, "Success");
                            } else {
                                OnUpdateRetrofitListener.onUpdateCompleteRetrofit(abc, false, "Success");
                            }
                        } else {
                            OnUpdateRetrofitListener.onUpdateCompleteRetrofit(null, false, "Fail");
                        }
                        if (intDialogShow == 1 || intDialogShow == 2) {
                            progressDialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    OnUpdateRetrofitListener.onUpdateCompleteRetrofit(null, false, "timeout");
                    if (intDialogShow == 1 || intDialogShow == 2) {
                        progressDialog.dismiss();
                    }
                } else if (t instanceof ConnectException) {
                    //Utils.showToastyRed(contextAc, "Attention, Internet connection slow");
                    OnUpdateRetrofitListener.onUpdateCompleteRetrofit(null, false, "");
                    if (intDialogShow == 1 || intDialogShow == 2) {
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }
}
