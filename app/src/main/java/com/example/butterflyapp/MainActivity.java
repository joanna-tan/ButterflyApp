package com.example.butterflyapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    ImageView butterfly1;
    ImageView butterfly2;
    ImageView butterfly3;
    TextView tvQuote;

    public static final String QUOTES_URL = "https://quotes.rest/qod";
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butterfly1 = findViewById(R.id.ivButterfly1);
        butterfly2 = findViewById(R.id.ivButterfly2);
        butterfly3 = findViewById(R.id.ivButterfly3);
        tvQuote = findViewById(R.id.tvQuote);

        AsyncHttpClient client = new AsyncHttpClient();

        //use JSON handler because the Movie API is returning a JSON object
        client.get(QUOTES_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject contents = response.getJSONObject("contents");
                    JSONArray quotes = contents.getJSONArray("quotes");
                    String quote = quotes.getJSONObject(0).getString("quote");
                    tvQuote.setText(quote.isEmpty() ? "Quote of the day" : quote);

                } catch (JSONException e) {
                    Log.e("onSuccess", "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i(TAG, "onFailure");
            }
        });

        Log.i("yup", "ok");

//            public void onSuccess(int statusCode, Headers headers, JSON json) {
//                Log.d(TAG, "onSuccess");
//                JSONObject jsonObject = json.jsonObject;
//
//                //unhandled exception, the key "results" might not exist
//                try {
//                    JSONArray results  = jsonObject.getJSONArray("results");
//                    Log.i(TAG, "Results: " + results.toString());
//                    movies = Movie.fromJsonArray(results);
//                    Log.i(TAG, "Movies: " + movies.size());
//                } catch (JSONException e) {
//                    Log.e(TAG, "Hit json exception", e);
//                }
//            }
    }

}