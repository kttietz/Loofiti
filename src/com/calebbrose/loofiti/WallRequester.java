package com.calebbrose.loofiti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class WallRequester 
{

	public String connect(String url)
	{   
		try {
			return new Requestor().execute().get();
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

		@Override
		protected String doInBackground(Void... params) {
		    String result = "";
			
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpGet httpget = new HttpGet("http://www.google.com"); 

		    try {
		    	HttpResponse response = httpclient.execute(httpget);
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
