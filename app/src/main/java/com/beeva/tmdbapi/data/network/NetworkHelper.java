package com.beeva.tmdbapi.data.network;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.beeva.tmdbapi.data.Constants;

public class NetworkHelper {

    private static NetworkHelper singleton;

    private final RequestQueue requestQueue;

    public static synchronized NetworkHelper get(@NonNull Context context) {
        if (singleton == null) {
            singleton = new NetworkHelper(context);
        }
        return singleton;
    }

    private NetworkHelper(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public JSONObject request(@NonNull Operation operation) throws ExecutionException, InterruptedException {
        Uri.Builder uriBuilder = Uri.parse(operation.url).buildUpon();
        uriBuilder.appendQueryParameter("api_key", Constants.API_KEY);
        uriBuilder.appendQueryParameter("language", Constants.API_LANGUAGE);

        if (operation.queryParams != null && operation.queryParams.size() > 0) {
            for (Map.Entry<String, String> queryParam : operation.queryParams.entrySet()) {
                uriBuilder.appendQueryParameter(queryParam.getKey(), queryParam.getValue());
            }
        }

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(getMethod(operation),
                uriBuilder.toString(),
                null,
                future,
                future);
        requestQueue.add(request);
        return future.get();
    }

    private int getMethod(Operation operation) {
        switch (operation.type) {
        case GET:
            return Request.Method.GET;
        case POST:
            return Request.Method.POST;
        }
        return -1;
    }
}
