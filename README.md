# Linebot

### 初めに
 javaに触れてみようの一環で、Linebotを使った簡単なサービスを作ってみた。コードが汚いなどあると思われるので、適宜修正していく。
 メインのソースコードは[/src/main/java/com/example/linebot](https://github.com/quintupl/Linebot/tree/main/src/main/java/com/example/linebot)下にある。

### 概要
 Lineから位置情報を送信すると、送信した位置から近い順に最大3つまで周辺の駅が表示される。
 
 __細かい機能：__
  + 各駅名を押すことでその駅を中心とした地図情報が表示される。
  + 返答メッセージ内で駅名以外の場所を押すと、最寄り駅を中心とした地図情報が表示される。
  + 表示された地図情報を押すことで、周辺の立地を確認できる。

※ ほかにもおみくじ機能やオウム返し機能、挨拶機能があるが、デモのために実装しただけなので割愛する。  
　(おみくじ機能は「おみくじ」、挨拶機能は「やあ」、オウム返し機能はそれ以外を送信することで動作する)

__注意点：__
 + 表示される地図は、右下付近の「･･･」を押すことで、Google mapやYahoo! mapなどに遷移させて表示できる。
 + pc版だと位置情報を送信できないので、スマホ限定となる。
 + 利用したAPIが日本国内の駅しか対応していないため、__日本限定__ の機能となる。

__最寄り駅の取得に利用したもの：__
 + [「HeartRails Express」の最寄駅情報取得 API](https://express.heartrails.com/api.html#nearest)
 + [Yahoo!リバースジオコーダAPI](https://developer.yahoo.co.jp/webapi/map/openlocalplatform/v1/reversegeocoder.html)

__懸念点：__ 

上記の「HeartRails Express」は2021年でサイトの更新が止まっているため、情報が古い可能性がありしばらくしたら別のものに変更する必要がありそう。  

__背景：__
 + 本当は海外でも利用できるようにしたかったのでGoogle maps platformなどを利用した方が手っ取り早かったのだが、諸事業によりクレジットカードの登録ができず(有名どころは)利用できなかった。→ __海外対応を断念した。__
 + なるべくお金を気にしない(理想：完全無料)で開発したかった。
 +  Yahoo!リバースジオコーダAPIを利用したのは、「HeartRails Express」の最寄駅情報取得 APIは緯度と経度などがレスポンスとして返ってくるが、表示するときに緯度と経度だけではどこを指しているのかわかりずらいと思った。そのため、緯度と経度から住所を特定する必要があると考えたものの「HeartRails Express」には緯度と経度から住所を特定するAPIがなかったため。


## 出力イメージ
<p align="center">
  <img src="https://github.com/user-attachments/assets/c79b36bc-b87e-4cff-821c-99aeccbeeba7" />
</p>

<p align="center">
  その1：位置情報を送信したときの返信メッセージ例
</p>

***
<p align="center">
  <img src="https://github.com/user-attachments/assets/8cd6208c-1c97-4bcb-89e8-742fb245465b" />
</p>

<p align="center">
  その2：その1での返答メッセージで駅名以外の場所を押したときの返答メッセージ例
</p>

***

<p align="center">
  <img src="https://github.com/user-attachments/assets/a077d913-1972-4ab7-aa9c-6629f4c53bc2" />
</p>

<p align="center">
  その3：その2の返答メッセージを押したときの画面表示例
</p>

***

<p align="center">
  <img src="https://github.com/user-attachments/assets/4fd75317-bac1-4a0b-aaf0-2df763699af0" />
</p>

<p align="center">
  その4：その3の表示で右下の「･･･」を押したときの画面表示例
</p>
