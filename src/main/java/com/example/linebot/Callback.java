package com.example.linebot;

import com.example.linebot.replier.*;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@LineMessageHandler
public class Callback {

    private static final Logger log = LoggerFactory.getLogger(Callback.class);

    @EventMapping
    public Message handleFollow(FollowEvent event){
        // 実際はこのタイミングでフォロワーのユーザIDをデータベースに格納しておくなど
        Follow follow = new Follow(event);
        return follow.reply();
    }

    @EventMapping
    public Message handleMessage(MessageEvent<TextMessageContent> event){
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();
        switch(text) {
            case "やあ":
                Greet greet = new Greet();
                return greet.reply();
            case "おみくじ":
                Omikuji omikuji = new Omikuji();
                return omikuji.reply();
            case "最も近い駅":
                try {
                    NearestStation nearestStation = new NearestStation();
                    return nearestStation.reply();
                }
                catch(RuntimeException e){
                    return new TextMessage("""
                            住所情報を取得できませんでした。
                            再度、行っても表示されない場合は、システムに問題が発生している可能性があります。
                            制作者に問い合わせてください。""");
                }
            default:
                Parrot parrot = new Parrot(event);
                return parrot.reply();
        }
    }
    @EventMapping
    public Message LocationMessage(MessageEvent<LocationMessageContent> event){
        try {
            ChooseStation chooseStation = new ChooseStation(event);
            return chooseStation.reply();
        } catch (IOException e) {
            return new TextMessage("日本の国土を設定して送信してください。");
        } catch (RuntimeException e){
            return new TextMessage("""
                    情報を取得する際に、正しく取得できませんでした。
                    再度、行っても表示されない場合は、システムに問題が発生している可能性があります。
                    制作者に問い合わせてください。""");
        }
    }
}
