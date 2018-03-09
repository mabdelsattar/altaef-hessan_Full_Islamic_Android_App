package com.daralmathour.altaefhessan.API;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daralmathour.altaefhessan.Utils.Constant;

import org.json.JSONObject;

/**
 * Created by mohamed_3ntar on 2/19/2018.
 */

public class ConnectionManager {

    public interface OnGetRequestFinishListener {
        void onSuccess(JSONObject response);

        void onFailure(String message);
    }

    public static void httpGetRequest(Context context , String url ,final OnGetRequestFinishListener listener)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){

                        VolleyLog.d(Constant.TAG, "Error: " + error.getMessage());
                        listener.onFailure(error.getMessage());

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
