package com.rxsearchdemo;

import com.rxsearchdemo.ReminderHistory.ReminderHistoryRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by whiz-010 on 24-Oct-17 at 11:49 AM.
 */

public interface ApiService {

    @POST("reminder_history")
    Call<ResponseBody> reminder_history(@Header("Content-Type") String headerName1, @Body ReminderHistoryRequest request);

}
