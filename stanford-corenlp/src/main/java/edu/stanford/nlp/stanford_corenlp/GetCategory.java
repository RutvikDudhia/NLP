package edu.stanford.nlp.stanford_corenlp;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.lang.StringBuffer;
public class GetCategory {
	
	public void getCategory(String queryString)
	{
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL("https://api.monkeylearn.com/v2/classifiers/cl_5icAVzKR/classify/?");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization",
                "token fbd15c3dbbc513a3485a2ac8250cd93b91d942cc");
            connection.setRequestProperty("Content-Type",
                "application/json");
 
            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
 
            //Send request
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            String s="{\"text_list\": ["
            		+ "\"Restaurants Nearby\""
            		+ "]}";
            System.out.println("The string is "+s);
            wr.write (s);
            wr.flush ();
            wr.close ();
 
            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
              response.append(line);
              response.append('\r');
            }
            rd.close();
            System.out.println(response.toString());
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }
	}


