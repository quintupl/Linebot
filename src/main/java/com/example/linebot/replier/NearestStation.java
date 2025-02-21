package com.example.linebot.replier;

import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

// 最寄り駅を取得して表示するクラス
public class NearestStation implements Replier{
    private String id;
    private ArrayList<Object> coordinate;
    private double latitude;
    private double longitude;

    public NearestStation(){
        this.coordinate = ChooseStation.getCoordinate();
        this.longitude = Double.valueOf(coordinate.get(0).toString());
        this.latitude = Double.valueOf(coordinate.get(1).toString());
    }

    @Override
    public Message reply() {
        this.id = "dj00aiZpPUlXWDQ5Rm1DWGlFTSZzPWNvbnN1bWVyc2VjcmV0Jng9OTQ-";
        String stringUri = "https://map.yahooapis.jp/geoapi/V1/reverseGeoCoder?lat=" + this.latitude
                            + "&lon=" + this.longitude + "&appid=" + id + "&output=json";
        URI uri = URI.create(stringUri);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            JSONArray item = json.getJSONArray("Feature");
            JSONObject item2 = item.getJSONObject(0);
            JSONObject item3 = item2.getJSONObject("Property");
            Object addressName = item3.get("Address");
            return new LocationMessage("最短駅" , addressName.toString() , this.latitude , this.longitude);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
