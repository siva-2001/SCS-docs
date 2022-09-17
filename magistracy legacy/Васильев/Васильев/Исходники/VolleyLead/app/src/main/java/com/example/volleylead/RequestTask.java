package com.example.volleylead;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class RequestTask extends AsyncTask<String, String, String> {
    static String resultString;
    @Override
    protected String doInBackground(String... strings) {
        try {

            String parammetrs = "teamName1="+"TestTeam1"+"&teamName2="+"TestTeam2"
                    +"&Part="+"3"+"&Round="+"20"+"&scoreRound1="+"10"+"&scoreRound2="+"10"
                    +"&scorePart1="+"2"+"&scorePart2="+"2";
            String myURL = "http://testphp/server.php";
            byte[] data = null;
            InputStream is = null;
            try {
                URL url = new URL(myURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Length", "" + Integer.toString(parammetrs.getBytes().length));
                OutputStream os = conn.getOutputStream();
                data = parammetrs.getBytes("UTF-8");
                os.write(data);
                data = null;
                conn.connect();
                int responseCode= conn.getResponseCode();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                if (responseCode == 200) {
                    is = conn.getInputStream();
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, bytesRead);
                    }
                    data = baos.toByteArray();
                    resultString = new String(data, "UTF-8");
                } else {
                }
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    @Override
    protected void onPostExecute(String result) {


        super.onPostExecute(result);
    }

    @Override
    protected void onPreExecute() {


        super.onPreExecute();
    }
}
