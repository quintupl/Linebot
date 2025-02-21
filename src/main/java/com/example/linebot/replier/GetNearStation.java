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
            MapNow mapNow = new MapNow(event);
            ArrayList<Double> coordinate = mapNow.getCoordinate();
            this.latitude = coordinate.get(0);
            this.longitude = coordinate.get(1);
            String stringUri = "http://express.heartrails.com/api/json?method=getStations&x=" + this.longitude + "&y=" + this.latitude;
            URI uri = URI.create(stringUri);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            JSONObject item = json.getJSONObject("response");
            this.itemList = item.getJSONArray("station");
        } catch (BadRequestException e){
            throw new BadRequestException();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException();
        }
    }

    public JSONObject informationOfStation(int index) {
        return this.itemList.getJSONObject(index);
    }

    public int numberOfItemList(){
        return this.itemList.length();
    }
}
