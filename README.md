# Linebot

### 初めに
 javaに触れてみようの一環で、Linebotを使った簡単なサービスを作ってみた。コードが汚いなどあると思われるので、適宜修正していく。

### 概要
 Lineから位置情報を送信すると、送信した位置周辺の駅が近い順に最大3つまで表示される。
 細かい機能：
  + 各駅名を押すことでその駅を中心とした地図情報が表示される。
  + 返答メッセージ内で駅名以外の場所を押すと、最寄り駅を中心とした地図情報が表示される。
  + 表示された地図情報を押すことで、周辺の立地を確認できる。

※ ほかにもおみくじ機能やオウム返し機能、挨拶機能があるが、デモのために実装しただけなので割愛する。  
　(おみくじ機能は「おみくじ」、挨拶機能は「やあ」、オウム返し機能はそれ以外を送信することで動作する)

注意点：
 + 表示される地図は、右下付近の「･･･」を押すことで、Google mapやYahoo! mapなどに遷移させて表示できる。
 + pc版だと位置情報を送信できないので、スマホ限定である。


## 出力イメージ
<p align="center">
  <img src="https://github.com/user-attachments/assets/fbb07b27-d823-4dca-b08e-d3487caaa697" />
</p>



