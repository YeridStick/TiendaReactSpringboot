package com.company.inventory.response;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseRest {
    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();

    public void setMetadata(String type, String date) {
        HashMap<String, String> map = new HashMap<String,  String>();
        map.put("type", type);
        map.put("date", date);

        metadata.add(map);
    }
}
