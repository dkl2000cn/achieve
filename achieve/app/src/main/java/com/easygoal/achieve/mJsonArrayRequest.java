package com.easygoal.achieve;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;

import android.util.Log;

public class mJsonArrayRequest extends JsonArrayRequest {
 
    Map<String, String> headers;
    private JSONArray joarray;
    Listener<JSONArray> listener;
    ErrorListener errorListener;
	public mJsonArrayRequest(String url,JSONArray joarray,Listener<JSONArray> listener, ErrorListener errorListener) {
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
		  Map<String, String> params = null;
		  try {
			params=(Map)joarray.get(0);
			Log.d("params", params.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return params;
	}

	@Override
	protected String getParamsEncoding() {
		// TODO Auto-generated method stub
		Log.d("get paramsencoding", "getparamsencoding");
		return super.getParamsEncoding();
		
	}

	@Override
	public byte[] getBody() {
		 Map<String, String> params = null;
		try {
			params = getParams();
		} catch (AuthFailureError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		    if (params != null && params.size() > 0) {
		    	Log.d("body", params.toString());
		        return params.toString().getBytes();  
		        
		    }  
		    return null;  
		
		

	}

	@Override
	public String getPostBodyContentType() {
		// TODO Auto-generated method stub
		Log.d("getPostBodyContentType()", "getPostBodyContentType()");
		return super.getPostBodyContentType();
	}

	@Override
	public byte[] getPostBody() {
		// TODO Auto-generated method stub
		Log.d("getpostbody", this.joarray.toString());
		return super.getPostBody();
	}

	@Override
	public String getBodyContentType() {
		// TODO Auto-generated method stub
		Log.d("getBodyContentType()", "getBodyContentType()");
		return super.getBodyContentType();
	}

	 //发送请求时，往Header中添加cookie，可以一并发送
    public void setCookie(String cookie) throws AuthFailureError {
        headers.put("User-Agent", "Android:1.0:2009chenqc@163.com");
        headers.put("Cookie",cookie);
    }
}
