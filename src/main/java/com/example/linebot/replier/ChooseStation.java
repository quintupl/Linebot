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

// 取得した周辺の駅を表示するためのテンプレートを作成するクラス
public class ChooseStation implements Replier {
    // 返答に利用する駅リスト
    public static ArrayList<ArrayList<JSONObject>> dataList;

    public ChooseStation(MessageEvent<LocationMessageContent> event) throws IOException {
        try {
            dataList = new ArrayList<>();
            
            // GetNearStationクラスを作成
            GetNearStation getNearStation = new GetNearStation(event);
            
            // getNearStaionが保持している周辺の駅リストの長さを取得
            int length = getNearStation.numberOfItemList();
            
            // 0はリスポンスの中身がないことになるので、エラーを発生させる
            if(length == 0){
                throw new IOException();
            }

            // 東京のような場所だと路線名が違うだけで、駅名は同じという状況が発生し、
            // 返答メッセージで同じ駅名が列挙されてしまうため、駅名の重複をしないように処理
            for (int i = 0; i < length; i++) {
                // 重複を防ぐための仮置き用のArrayListを作成
                ArrayList<JSONObject> sameName = new ArrayList<>();

                // getNearStationが保持する駅をインデックス指定で取得
                JSONObject data = getNearStation.informationOfStation(i);
                sameName.add(data);
                for (int j = i + 1; j < length - 1; j++) {
                    // 直前で取得した駅(data)以降で取得される駅(nextdata)を取得
                    JSONObject nextData = getNearStation.informationOfStation(j);
                    
                    // dataとnextDataを比較して同じ駅名であるときsameNameにnextDataを追加し、
                    // 同じ駅名を1つのJSONListとしてまとめる
                    if (nextData.get("name").equals(data.get("name"))) {
                        sameName.add(nextData);
                    }
                }

                // 重複は気にせずdataListに要素を加える(初回のみdataListは空であるため)
                if (dataList.isEmpty()) {
                    dataList.add(sameName);
                }

                // dataListの要素と加えようとしている要素が重複しているかを判定
                int size = 0;
                for (int l = 0; l < dataList.size(); l++) {
                    // 重複しているとき、sizeの値を増やさない
                    // これまでの処理で同じ駅名を1つのJSONObjectとしてまとめたことにより、各JSONObjectの最初の要素を指定することだけで重複しない駅リストの作成を実現
                    // すなわち、取得した駅(data)[JSONObject]を同じ駅名で1つにまとめ(sameName)[JSONList]、このJSONListを要素に持つ駅リスト(dataList)[JSONList]を作成
                    if (sameName.get(0).get("name").equals(dataList.get(l).get(0).get("name"))) {
                        break;
                    }
                    size += 1;
                }
                
                // dataListのサイズの値と直前のsizeの値が一致し、sameNameに何かしらの要素があるとき、dataListに要素を追加
                // 直前の処理で重複してるときにsizeの値が増えないようにしているため、重複があればdataListのサイズの値が一致せずDataListに要素が追加されない
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
        // 取得した緯度と経度を元に、Google mapのリンクを作成(距離が近い順)
        for (int l = 0; l < dataList.size(); l++) {
            URI uri = URI.create("https://www.google.com/maps/@" + dataList.get(l).get(0).get("y")
                    + "," + dataList.get(l).get(0).get("x") + ",18z?hl=ja&entry=ttu");
            actions.add(new URIAction("候補" + (l + 1) + "：" + dataList.get(l).get(0).get("name") + "駅", uri, null));
            count += 1;
            // リンクを3つ作成したらリンク作成を停止
            if (count == 4) {
                break;
            }
        }

        // ボタンテンプレートの作成(Readmeの出力イメージその1に表示されている返答メッセージの作成)
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

    // このクラスが保持する駅情報をほかのクラスにも共有
    public static ArrayList<Object> getCoordinate() {
        ArrayList<Object> coordinate = new ArrayList<>();
        coordinate.add(dataList.get(0).get(0).get("x"));
        coordinate.add(dataList.get(0).get(0).get("y"));
        return coordinate;
    }
}
