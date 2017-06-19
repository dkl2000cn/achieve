package com.easygoal.achieve;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;

import android.util.Log;

public class myJsonArrayRequest extends JsonArrayRequest {
   
    Map<String, String> headers;
    Listener<JSONArray> listener;
    JSONArray joarray;
    ErrorListener errorListener;
	public myJsonArrayRequest(String url, JSONArray joarray,Listener<JSONArray> listener, ErrorListener errorListener) {
		super(url, listener, errorListener);
		// TODO Auto-generated constructor stub
		this.joarray=joarray;
		this.listener=listener;
	}

	@Override
	protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
		// TODO Auto-generated method stub
		 try {
	            String jsonString =new String(response.data, HttpHeaderParser.parseCharset(response.headers));
	            return Response.success(new JSONArray(jsonString),HttpHeaderParser.parseCacheHeaders(response));
	        } catch (UnsupportedEncodingException e) {
	            return Response.error(new ParseError(e));
	        } catch (JSONException je) {
	            return Response.error(new ParseError(je));
	        }
	}

	@Override
	protected void deliverResponse(JSONArray response) {
		// TODO Auto-generated method stub
		super.deliverResponse(response);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		// TODO Auto-generated method stub
		 headers = new HashMap<String, String>();  
		 headers.put("Accept", "application/json");
	     headers.put("Content-Type", "application/json; charset=UTF-8"); 
		//headers.put("Accept-Encoding", "gzip,deflate");  
		Log.d("headers", headers.toString());
		return headers;  
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		// TODO Auto-generated method stub
		HashMap<String, String> params = new HashMap<String,String>();
        //params.put("name", "value");
		Log.d("headers","dddd");
        return params;
	}

	 @Override  
	    public byte[] getBody() {  
	  
	        try {  
	        	Log.d("get body", joarray.toString());
	        	
	            return joarray.toString().getBytes("UTF-8");  
	              
	        } catch (Exception e) {  
	        }  
	        return null;  
	    }

	@Override
	public byte[] getPostBody() {
		// TODO Auto-generated method stub
		try {  
        	Log.d("get body", joarray.toString());
            return joarray.toString().getBytes("UTF-8");  
              
        } catch (Exception e) {  
        }  
        return null; 
	}

	@Override
	public int getMethod() {
		// TODO Auto-generated method stub
		Log.d("get body", "done");
		return Request.Method.POST;
	}  
}
