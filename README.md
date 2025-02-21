# Linebot

### 初めに
 javaに触れてみようの一環で、Linebotを使った簡単なサービスを作ってみた。コードが汚いなどあると思われるので、適宜修正していく。
 メインのソースコードは[/src/main/java/com/example/linebot](/java/com/example/linebot)

### 概要
 Lineから位置情報を送信すると、送信した位置から近い順に最大3つまで周辺の駅が表示される。
 
 __細かい機能：__
  + 各駅名を押すことでその駅を中心とした地図情報が表示される。
  + 返答メッセージ内で駅名以外の場所を押すと、最寄り駅を中心とした地図情報が表示される。
  + 表示された地図情報を押すことで、周辺の立地を確認できる。

※ ほかにもおみくじ機能やオウム返し機能、挨拶機能があるが、デモのために実装しただけなので割愛する。  
　(おみくじ機能は「おみくじ」、挨拶機能は「やあ」、オウム返し機能はそれ以外を送信することで動作する)

注意点：
 + 表示される地図は、右下付近の「･･･」を押すことで、Google mapやYahoo! mapなどに遷移させて表示できる。
 + pc版だと位置情報を送信できないので、スマホ限定となる。


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
