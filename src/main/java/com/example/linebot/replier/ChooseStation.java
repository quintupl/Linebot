package com.example.linebot.replier;

import com.linecorp.bot.model.action.*;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import org.apache.coyote.BadRequestException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ChooseStation implements Replier {
    public static ArrayList<ArrayList<JSONObject>> dataList;

    public ChooseStation(MessageEvent<LocationMessageContent> event) throws IOException {
        try {
            dataList = new ArrayList<>();
            GetNearStation getNearStation = new GetNearStation(event);
            int length = getNearStation.numberOfItemList();
            if(length == 0){
                throw new IOException();
            }
            for (int i = 0; i < length; i++) {
                ArrayList<JSONObject> sameName = new ArrayList<>();
                JSONObject data = getNearStation.informationOfStation(i);
                sameName.add(data);
                for (int j = i + 1; j < length - 1; j++) {
                    JSONObject nextData = getNearStation.informationOfStation(j);
                    if (nextData.get("name").equals(data.get("name"))) {
                        sameName.add(nextData);
                    }
                }
                if (dataList.isEmpty()) {
                    dataList.add(sameName);
                }
                int size = 0;
                for (int l = 0; l < dataList.size(); l++) {
                    if (sameName.get(0).get("name").equals(dataList.get(l).get(0).get("name"))) {
                        break;
                    }
                    size += 1;
                }
                if (dataList.size() == size && (!sameName.isEmpty())) {
                    dataList.add(sameName);
                }
            }
        } catch (BadRequestException e) {
            throw new BadRequestException();
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Message reply() {
        List<Action> actions = new ArrayList<>();
        int count = 0;
        for (int l = 0; l < dataList.size(); l++) {
            URI uri = URI.create("https://www.google.com/maps/@" + dataList.get(l).get(0).get("y")
                    + "," + dataList.get(l).get(0).get("x") + ",18z?hl=ja&entry=ttu");
            actions.add(new URIAction("候補" + (l + 1) + "：" + dataList.get(l).get(0).get("name") + "駅", uri, null));
            count += 1;
            if (count == 4) {
                break;
            }
        }

        ButtonsTemplate buttonsTemplate = ButtonsTemplate
                .builder()
                .thumbnailImageUrl(URI.create("https://1.bp.blogspot.com/-5S8rmtezagQ/UnyHakT62TI/AAAAAAAAajc/TsAKmkq0wIE/s800/nihonchizu_area.png"))
                .imageAspectRatio("rectangle")
                .imageSize("contain")
                .imageBackgroundColor("#0000FF")
                .title("送信位置から近い駅 ※最大４つ")
                .text("""
                        各駅を選択：各駅が中心のGoogle Mapが開く
                                                
                        各駅の表示以外を押す：最も近い駅の表示
                        表示なし → 再度タップ""")
                .defaultAction(new MessageAction("最も近い駅の表示", "最も近い駅"))
                .actions(actions)
                .build();

        return new TemplateMessage("a", buttonsTemplate);
    }

    public static ArrayList<Object> getCoordinate() {
        ArrayList<Object> coordinate = new ArrayList<>();
        coordinate.add(dataList.get(0).get(0).get("x"));
        coordinate.add(dataList.get(0).get(0).get("y"));
        return coordinate;
    }
}