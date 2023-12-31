package com.company.inventory.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public class MensajeResponseRest {
    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();
    public void setMetadata(String type, String date) {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("date", date);

        metadata.add(map);
    }

    private MensajeResponse mensajeResponse = new MensajeResponse();
}
