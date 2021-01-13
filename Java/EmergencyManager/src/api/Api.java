package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class Api {

    protected String baseURL = "http://emergency-api.local";
    protected HashMap<String, String> headers;
    HashMap<String, String> getHeaders = new HashMap<String, String>(){{
        put("Accept", "application/json");
    }};

    HashMap<String, String> postHeaders = new HashMap<String, String>(){{
        put("Accept", "application/json");
        put("Content-type", "application/json; UTF-8");
    }};

    public Api() {}

    public Api(String url) {
        this.setBaseURL(url);
    }

    public Api(String url, HashMap<String, String> headersArray) {
        this.setBaseURL(url);
        this.setHeaders(headersArray);
    }

    public String getRequest(String path) {

        String urlString = this.baseURL+path;
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            setHeaders(getHeaders);
            this.manageHeaders(con);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())
            );

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return content.toString();
    }

 public String postRequest(String path, String jsonBody) {
        String urlString = this.baseURL+path;
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            setHeaders(postHeaders);
            manageHeaders(con);
            con.setDoOutput(true);

            try {
                OutputStream os = con.getOutputStream();
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8)
            );

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return content.toString();
    }

    /**
     * Fills the headers of the request, according to the data in <b>attr <i>headers</i></b>
     *
     * @param con
     */
    protected void manageHeaders(HttpURLConnection con) {
        for (Map.Entry<String, String> headerMap : this.headers.entrySet()) { //foreach avec hashmap
            String header = headerMap.getKey();
            String value  = headerMap.getValue();
            con.setRequestProperty(header,value);
        }
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        if (!(baseURL.startsWith("http://") || baseURL.startsWith("https://"))) {
            this.baseURL = "http://"+baseURL;
        } else {
            this.baseURL = baseURL;
        }
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }
}
