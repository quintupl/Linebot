package com.example.linebot.replier;

import com.linecorp.bot.model.message.Message;

// reply()を持つinterface[Replier]の定義
public interface Replier {

    Message reply();
}
