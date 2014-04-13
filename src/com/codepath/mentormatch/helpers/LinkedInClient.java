package com.codepath.mentormatch.helpers;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.LinkedInApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class LinkedInClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = LinkedInApi.class; 
    public static final String REST_URL = "http://api.linkedin.com/v1"; 
    public static final String REST_CONSUMER_KEY = "75tao9nsgcabjl";
    public static final String REST_CONSUMER_SECRET = "bFMTB5PkqTWr67Cd";
    public static final String REST_CALLBACK_URL = "https://mylinkedinapp"; 
        
    public LinkedInClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    //************************ GET METHODS **********************************/    
    public void getUserInfo(AsyncHttpResponseHandler handler) {
    	String fieldsToRetrieve = ":(id,first-name,last-name,location:(name),picture-url,email-address,skills,three-current-positions)";
    	String url = getApiUrl("people/~" + fieldsToRetrieve);
    	RequestParams params = new RequestParams();
    	params.put("format", "json"); 
        Log.d("DEBUG", "About to send request to: " + url + " with params: " + params);
        client.get(url, params, handler);	
    }   
}