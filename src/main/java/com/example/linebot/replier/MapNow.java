package com.example.linebot.replier;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import org.apache.coyote.BadRequestException;

import java.util.ArrayList;

public class MapNow {
    private MessageEvent<LocationMessageContent> event;
    private double latitude;
    private double longitude;

    public MapNow(MessageEvent<LocationMessageContent> event) {
        this.event = event;
    }

    public ArrayList<Double> getCoordinate() throws BadRequestException {
        ArrayList<Double> coordinate = new ArrayList<>();
        this.latitude = this.event.getMessage().getLatitude();
        this.longitude = this.event.getMessage().getLongitude();
        if(event.getMessage().getAddress().isEmpty()) {
            throw new BadRequestException();
        }
        coordinate.add(this.latitude);
        coordinate.add(this.longitude);
        return coordinate;
    }
}
