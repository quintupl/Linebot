package com.example.linebot.replier;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import org.apache.coyote.BadRequestException;

import java.util.ArrayList;

// 送信された位置情報から緯度と経度を取得するクラス
public class MapNow {
    private MessageEvent<LocationMessageContent> event;
    private double latitude;
    private double longitude;

    public MapNow(MessageEvent<LocationMessageContent> event) {
        this.event = event;
    }

    public ArrayList<Double> getCoordinate() throws BadRequestException {
        // 緯度と経度の値を持つArrayListの作成
        ArrayList<Double> coordinate = new ArrayList<>();
        this.latitude = this.event.getMessage().getLatitude();
        this.longitude = this.event.getMessage().getLongitude();
        //
        if(event.getMessage().getAddress().isEmpty()) {
            throw new BadRequestException();
        }
        coordinate.add(this.latitude);
        coordinate.add(this.longitude);
        return coordinate;
    }
}
