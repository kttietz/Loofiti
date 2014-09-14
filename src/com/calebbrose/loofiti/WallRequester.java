package com.calebbrose.loofiti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class WallRequester 
{

	
	
	public String connect(String url, String[] keys, String[] vals)
	{   
		try {
			return new Requestor(url, keys, vals).execute().get();
		} catch (Exception e) { return "";} 
	}

	private static String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try 
	    {
	        while ((line = reader.readLine()) != null) 
	        {
	            sb.append(line + "\n");
	        }
	    } 
	    catch (IOException e) { } 
	    finally 
	    {
	        try 
	        {
	            is.close();
	        } 
	        catch (IOException e) { }
	    }
	    return sb.toString();
	}
	    
	private class Requestor extends AsyncTask<Void, Void, String>
	{
		String dataUrl;
		List<NameValuePair> postParams;
		
		public Requestor (String url, String[] keys, String[] vals) {
			dataUrl = url;
			postParams = new ArrayList<NameValuePair>(keys.length);
			
			for ( int i = 0; i < keys.length; ++i ) {
				postParams.add(new BasicNameValuePair(keys[i], vals[i]));
			}
		}
		
		@Override
		protected String doInBackground(Void... params) {
		    String result = "";
			
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(dataUrl);
		    
		    try { httppost.setEntity(new UrlEncodedFormEntity(postParams, "UTF-8")); } 
		    catch (UnsupportedEncodingException e1) { }
		    
		    try {
		    	HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity = response.getEntity();

		        if (entity != null) 
		        {
		            InputStream instream = entity.getContent();
		            result= convertStreamToString(instream);

		            instream.close();
		        }

		    } catch (Exception e) 
		    {
		    	 result = e.getMessage();
		    }
		    
		    return result;
		}
		
	}
}
