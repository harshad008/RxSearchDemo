package com.rxsearchdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.rxsearchdemo.ReminderHistory.ReminderHistoryRequest;
import com.rxsearchdemo.ReminderHistory.ReminderHistoryResponse;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    public String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = (SearchView) findViewById(R.id.searchView);

        setUpSearchObservable();
    }

    private void setUpSearchObservable() {
        RxSearchObservable.fromView(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String text) throws Exception {
                        if (text.isEmpty()) {
                            Log.e(TAG, "test: " + text);
                            return false;
                        } else {
                            return true;
                        }
                    }
                })
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String query) throws Exception {
                        return dataFromNetwork(query);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String result) throws Exception {
                        Log.e(TAG, "accept: " + result);
                    }
                });
    }

    private Observable<String> dataFromNetwork(final String query) {
        return Observable.just(true)
                .delay(2, TimeUnit.SECONDS)
                .map(new Function<Boolean, String>() {
                    @Override
                    public String apply(@NonNull Boolean value) throws Exception {
                        getHistoryData(query);
                        return query;
                    }
                });
    }

    private void getHistoryData(String search) {
        try {
            //handle api call and update data here
            //check commit
            ReminderHistoryRequest historyRequest = new ReminderHistoryRequest();
            historyRequest.setUserId("4");
            historyRequest.setSearch(search);

            String mode = "reminder_history";
            new GetRetrofitCallback(MainActivity.this, historyRequest, 3, mode, new OnUpdateRetrofitListener() {
                @Override
                public void onUpdateCompleteRetrofit(String jsonObject, boolean isSuccess, String message) {
                    try {
                        JSONObject abc = new JSONObject(jsonObject);
                        if (isSuccess) {
                            if (abc.getString("status").equalsIgnoreCase("1")) {
                                ReminderHistoryResponse historyResponse = new Gson().fromJson(abc.toString(), ReminderHistoryResponse.class);
                                Log.e(TAG, "onUpdateCompleteRetrofit: " + historyResponse.getData().toString());
                            }
                        } else {
                            Log.e(TAG, "no data: " + "error from api");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
