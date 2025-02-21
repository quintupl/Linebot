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

    // 周辺の駅を表示するChooseStationクラスが保持している駅情報の取得
    public NearestStation(){
        this.coordinate = ChooseStation.getCoordinate();
        this.longitude = Double.valueOf(coordinate.get(0).toString());
        this.latitude = Double.valueOf(coordinate.get(1).toString());
    }

    @Override
    public Message reply() {
        // Yahoo!リバースジオコーダAPIを利用して、緯度と経度から住所を特定する
        this.id = "Yahoo!リバースジオコーダAPIを利用するための各自のClient ID";
        String stringUri = "https://map.yahooapis.jp/geoapi/V1/reverseGeoCoder?lat=" + this.latitude
                            + "&lon=" + this.longitude + "&appid=" + id + "&output=json";
        URI uri = URI.create(stringUri);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            // APIからのレスポンスのJSONObjectを作成
            JSONObject json = new JSONObject(response.body());

            // データの整理(Addressに住所が紐づいているので、そこに至るまでの整理)
            JSONArray item = json.getJSONArray("Feature");
            JSONObject item2 = item.getJSONObject(0);
            JSONObject item3 = item2.getJSONObject("Property");
            Object addressName = item3.get("Address");

            // LocationMessageで返答
            return new LocationMessage("最短駅" , addressName.toString() , this.latitude , this.longitude);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
