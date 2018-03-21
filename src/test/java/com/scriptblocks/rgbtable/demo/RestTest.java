package com.scriptblocks.rgbtable.demo;

import com.scriptblocks.rgbtable.demo.model.TableFrame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Scriptblocks on 3/2/2018.
 * Try https://stackoverflow.com/questions/21404252/post-request-send-json-data-java-httpurlconnection
 */
public class RestTest {
    public static void main(String[] args) {
        RestTest rt = new RestTest();
        rt.test();
    }
    public void test() {

//        $ curl localhost:8080/hello-world
//        {"id":1,"content":"Hello, Stranger!"}


        //escape the double quotes in json string
        String payload = "{\"var1\":\"test\",\"tablePixelList\":[ {\"red\":255,\"green\":0,\"blue\":0 }]}";
//        String requestUrl = "http://localhost:9090/json";
        String requestUrl = "http://192.168.0.55:8080/json";
//        String requestUrl = "http://localhost:9090/raw";
//        String payload = "{\"id\":1,\"content\":\"Hello, Stranger!\"}";
//        String requestUrl = "http://localhost:8080/hello-world";
        sendPostRequest(requestUrl, payload);
    }

    public static String sendPostRequest(String requestUrl, String payload) {
        StringBuffer jsonString = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(payload);
            writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
            connection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return jsonString.toString();
    }
}
