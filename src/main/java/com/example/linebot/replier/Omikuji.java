package com.example.linebot.replier;

import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;

import java.net.URI;
import java.util.Random;

// おみくじの結果を表示するクラス
public class Omikuji implements Replier{
    @Override
    public Message reply(){
        int ranNum = new Random().nextInt(3);
        String uriString = "";
        switch(ranNum){
            case 2:
                uriString = "https://3.bp.blogspot.com/-vQSPQf-ytsc/T3K7QM3qaQI/AAAAAAAAE-s/6SB2q7ltxwg/s1600/omikuji_daikichi.png";
                break;
            case 1:
                uriString = "https://2.bp.blogspot.com/-27IG0CNV-ZE/VKYfn_1-ycI/AAAAAAAAqXw/fr6Y72lOP9s/s800/omikuji_kichi.png";
                break;
            case 0:
            default:
                uriString = "https://4.bp.blogspot.com/-qCfF4H7YOvE/T3K7R5ZjQVI/AAAAAAAAE-4/Hd1u2tzMG3Q/s1600/omikuji_kyou.png";
        }
        URI uri = URI.create(uriString);
        return new ImageMessage(uri,uri);
    }
}
