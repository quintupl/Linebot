package com.example.linebot.replier;

import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

// botを友達追加したときのメッセージを表示するクラス
public class Follow implements Replier {
    private FollowEvent event;

    public Follow(FollowEvent event){
        this.event = event;
    }

    @Override
    public Message reply() {
        String userId = this.event.getSource().getUserId();
        String text = String.format("あなたのユーザーID：%s" , userId);
        return new TextMessage(text);
    }
}
