package api;

import java.util.HashMap;

public class ApiSimulator extends Api {

    public ApiSimulator(String url) {
        super(url);
    }

    public ApiSimulator(String url, HashMap<String, String> headersArray) {
        super(url, headersArray);
    }
    public String getListIncidents() {
        return this.makeRequest("GET", "/api/incidents/list");
    }


}
