package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class Api {

    protected String baseURL = "localhost";
    protected HashMap<String, String> headers = new HashMap<String, String>(){{
        put("Accept", "*/*");
        put("Accept-Encoding", "gzip, deflate, br");
        put("Connection", "keep-alive");
    }};

    public Api(String url) {
        this.setBaseURL(url);
    }

    public Api(String url, HashMap<String, String> headersArray) {
        this.setBaseURL(url);
        this.setHeaders(headersArray);
    }

    public String makeRequest(String method, String path) {

        String urlString = this.baseURL+path;
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);

            this.manageHeaders(con);

//            con.setRequestProperty("Accept", "*/*");
//            con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
//            con.setRequestProperty("Connection", "keep-alive");

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
