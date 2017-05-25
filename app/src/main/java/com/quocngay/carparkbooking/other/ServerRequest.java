package com.quocngay.carparkbooking.other;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by ninhh on 5/17/2017.
 */

public class ServerRequest {
    private static final String TAG = ServerRequest.class.getSimpleName();
    private static final int CONNECTION_TIMEOUT = 15000;
    private static final int DATA_RETRIEVAL_TIMEOUT = 15000;

    public ServerRequest() {
    }

    public JSONObject getResponse(String url, JSONObject postParameters) {
        HttpURLConnection urlConnection = null;
        try {
            // create connection
            URL urlToRequest = new URL(url);
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.setReadTimeout(DATA_RETRIEVAL_TIMEOUT);

            // handle POST parameters
            if (postParameters != null) {
                if (Log.isLoggable(TAG, Log.INFO)) {
                    Log.i(TAG, "POST parameters: " + postParameters);
                }

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                //send the POST out
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postParameters));
                writer.flush();
                writer.close();
                os.close();
//                urlConnection.setFixedLengthStreamingMode(postParameters.getBytes().length);
//                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String res = convertStreamToString(in);
                JSONObject json = new JSONObject(res);
                return json;
            }

            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                // throw some exception
            }

            // read output (only for GET)
            if (postParameters != null) {
                return null;
            } else {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String res = convertStreamToString(in);
                JSONObject json = new JSONObject(res);
                return json;
            }
        } catch (JSONException e) {
            // handle error parser json
        }catch (MalformedURLException e) {
            // handle invalid URL
        } catch (SocketTimeoutException e) {
            // hadle timeout    
        } catch (IOException e) {
            // handle I/0
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}
