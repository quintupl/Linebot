package com.example.linebot.replier;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import org.apache.coyote.BadRequestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

// 周辺の駅を取得するクラス
public class GetNearStation {
    private JSONArray itemList;
    private double latitude;
    private double longitude;

    public GetNearStation(MessageEvent<LocationMessageContent> event) throws BadRequestException {
        try {
            // 送信した位置情報から位置情報を取得するMapNowクラスから緯度と経度を取得
            MapNow mapNow = new MapNow(event);
            // 緯度と経度をまとめているArrayListの取得
            ArrayList<Double> coordinate = mapNow.getCoordinate();
            // 取得したArrayListから緯度と経度を取得
            this.latitude = coordinate.get(0);
            this.longitude = coordinate.get(1);
            
            // 「HeartRails Express」の最寄駅情報取得 APIを利用して上記の緯度と経度周辺の駅を取得
            String stringUri = "http://express.heartrails.com/api/json?method=getStations&x=" + this.longitude + "&y=" + this.latitude;
            URI uri = URI.create(stringUri);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // JSON形式に変換
            JSONObject json = new JSONObject(response.body());
            // データの整理(responseに紐づくデータ群を取得し、stationに紐づくデータ群を取得)
            JSONObject item = json.getJSONObject("response");
            this.itemList = item.getJSONArray("station");
        } catch (BadRequestException e){
            throw new BadRequestException();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException();
        }
    }

    // インデックス番号を指定して要素を取得
    public JSONObject informationOfStation(int index) {
        return this.itemList.getJSONObject(index);
    }

    // 整理したデータ群の長さを取得
    public int numberOfItemList(){
        return this.itemList.length();
    }
}
