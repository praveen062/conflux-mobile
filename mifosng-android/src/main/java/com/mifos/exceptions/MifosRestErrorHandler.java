package com.mifos.exceptions;

import android.util.Log;

import com.google.gson.Gson;
import com.mifos.services.API;
import com.mifos.utils.MFError;
import com.mifos.utils.MFErrorResponse;

import org.apache.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

/**
 * Created by conflux37 on 11/24/2015.
 */
public class MifosRestErrorHandler implements ErrorHandler {
    String errorStringJSON = null;
    final String TAG=getClass().getSimpleName();
    List<MFError> errorResponses;
    public MifosRestErrorHandler()
    {
        Log.i(TAG,"MifosRestErrorHandler Object Created");
    }
    @Override
    public Throwable handleError(RetrofitError retrofitError) {

        Response response = retrofitError.getResponse();
        String errorStringJSON = null;
        if (response != null) {

            if (response.getStatus() == HttpStatus.SC_UNAUTHORIZED) {
                Log.e("Status", "Authentication Error.");


            } else if (response.getStatus() == HttpStatus.SC_BAD_REQUEST) {
                try {
                    errorStringJSON=getStringFromInputStream(response.getBody().in());
                } catch (IOException e) {
                    Log.e("ERROR", "BAD Request -Invalid Parameter of Data Integrity Issue");
                }
                MFErrorResponse mfErrorResponse = new Gson().fromJson(errorStringJSON, MFErrorResponse.class);
                errorResponses=mfErrorResponse.getErrors();
                MFError error=errorResponses.get(0);
                API.userErrorMessage=error.getDefaultUserMessage();
                    /*userErrorMessage=mfErrorResponse.getDefaultUserMessage();*/
                Log.d("Status", "Bad Request - Invalid Parameter or Data Integrity Issue.");
                Log.d("URL", response.getUrl());
                List<retrofit.client.Header> headersList = response.getHeaders();
                Iterator<Header> iterator = headersList.iterator();
                while (iterator.hasNext()) {
                    retrofit.client.Header header = iterator.next();
                    Log.d("Header ", header.toString());
                }
            } else if (response.getStatus() == HttpStatus.SC_FORBIDDEN) {

                try {
                    System.out.println("the error message "+getStringFromInputStream(response.getBody().in()));
                    errorStringJSON=getStringFromInputStream(response.getBody().in());
                } catch (IOException e) {
                    Log.e("ERROR", "BAD Request -Invalid Parameter of Data Integrity Issue");
                }
                MFErrorResponse mfErrorResponse = null;
                    /*try {*/
                mfErrorResponse = new Gson().fromJson(errorStringJSON, MFErrorResponse.class);
                System.out.println("the default user message is " + mfErrorResponse.getDefaultUserMessage());
                  /*Toast.makeText(Constants.applicationContext.getApplicationContext(), mfErrorResponse.getDefaultUserMessage(), Toast.LENGTH_LONG).show();*/
                API.userErrorMessage=mfErrorResponse.getDefaultUserMessage();
                Log.d("Status", "Bad Request - Invalid Parameter or Data Integrity Issue.");
                Log.d("URL", response.getUrl());
                   /* } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                List<retrofit.client.Header> headersList = response.getHeaders();
                Iterator<retrofit.client.Header> iterator = headersList.iterator();
                while (iterator.hasNext()) {
                    retrofit.client.Header header = iterator.next();
                    Log.d("Header ", header.toString());
                }

            }

        }
        else
        {
            API.userErrorMessage="No Connection...";
        }



        return retrofitError;
    }
    String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

}