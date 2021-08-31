# WereWolf

## このプラグインについて

YoutubeでMinecraftなどを実況している「○○の主役は我々だ」さんが、動画内で遊んでいた「Minecraft人狼」を誰でも簡単に遊べるようにしたSpigotプラグインです。

## 遊び方

### プラグインの導入

1. Spigotサーバーを建ててください
2. pluginsフォルダの中へWereWolf-○○.jarを入れます
3. 一度サーバーを起動し、また終了します
4. plugins/WereWolf/config.yml を必要に応じて編集してください
5. wolf.*でpermissionを設定できます。
   defaultでwolf.join, wolf.cancel, wolf.job, wolf.workがtrueになっています。
   opは追加でwolf.host, wolf.reloadConfig, wolf.ready, wolf.start, wolf.resetもtrueになります。

### ゲーム開始前にしておくべきこと

1. サーバーを立て、ポート解放をして、全員がサーバーに参加しておく
2. ステージ、ロビー、死亡後の待機場所を準備しておく
3. 全員がDiscordなどで通話できるようにしておく

### 募集、参加

1. 誰か一人がゲーム内で /wolf host と入力します。その時点で入力した人は参加するので、2.の作業は飛ばしてください
2. 入力した人以外の全員の画面に「人狼ゲームに参加できます」と表示されるので、
   Tキーを押し、緑色の「＞＞＞このメッセージを押して参加＜＜＜」を押して参加します
3. ロビーへテレポートされていることを確認してください
4. 参加を取り消す場合は、Tキーを押し、緑色の「＞＞＞このメッセージを押してキャンセル＜＜＜」を押してください

### 開始

1. メンバーが揃ったら、誰か一人がゲーム内で /wolf ready と入力します
2. この時点で参加、参加取り消しができなくなるので、メンバーを確認してください
3. このタイミングで、参加者全員がDiscordなどでやりとりできる状態になっていることを確認してください
4. 誰か一人がゲーム内で /wolf start と入力すると、ゲームが開始します
5. 全員で話し合い、人狼だと思う人がいれば殺しましょう

### ゲーム中

1. ゲーム開始時に、一人一つランダムで特殊アイテムが配られます
2. 自分の役職は、スタート時にチャット欄で見ることができます
3. ルールがわからない場合、/wolf job と入力することで何をすればいいか知ることができます
4. 予言者、霊媒師になった場合、ゲームで一度だけ、/wolf work (プレイヤー名) と打つことで占うことができます
5. Discordのミュートは自由です。死んだふりをする際にお使いください

### 死んでしまった場合

1. 死んでしまった場合、待機場所へ飛ばされます (※現状の動作は「リスポーン地点に戻る」です)
2. DiscordのVCでミュートにしましょう。通話を切るのではなく、ミュートです
3. Tabキーを押した時 死んでいるのがバレてしまうため、スペクテイターモードで観戦する事はできません

### ゲーム終了

1. どちらかの陣営が全滅した場合、画面に大きく「○○陣営の勝利」と表示され、全員スペクテイターモードになります
2. 役職一覧が表示されるので、結果を見ましょう
3. 誰か一人がゲーム内で /wolf reset と実行することで、/wolf host 実行前の状態に戻ります (※現状は終了時に自動でresetされます)

## エラーが出た場合・バグを見つけた場合

zoi@zoizoi.net へ、発生時の状況も合わせて、メールでお知らせください。エラーメッセージがわかる場合、それも伝えてもらえると嬉しいです







------







## 開発用メモ



###  (ここから下は開発時のためのメモなので読まなくていいです)







参考 : https://qiita.com/rushuu_r/items/677bf24db821838a7569

DiscordのBotに関して : https://qiita.com/itsu_dev/items/825e4ed32bf32e4b64b2

### プラグイン読み込み時

- ログ : WereWolf Pluginが読み込まれました



- Discord Bot 起動

### 募集時

- op : /wolf host
- チャット(global) : 人狼ゲームの募集が開始されました ここを押して参加
- 画面(global) : 「人狼ゲームの募集が開始されました」「/wolf join で参加」



- 参加プレイヤーの名前はString型の連想配列(String Name, String Job = null)"PlayerData"へ入れられる

### 参加時

- Player : /wolf join
- チャット(private) : ゲームに参加しました。
- 画面(private) : 「ゲームに参加しました」「ゲームの開始を待ってください」



- pvp : false
- 全員アドベンチャー
- ロビーへtp

###  

### 準備時(募集締め切り時)

- op : /wolf ready
- チャット(global) : 募集が締め切られました
- チャット(player) : ゲームの準備をしています
- 画面(global) : 「募集が締め切られました」
- 画面(player) : 「募集が締め切られました」「ゲームの準備をしています」



- フィールドにtp
- スコアボード(n人)

### 開始時

- op : /wolf start
- チャット(player) : 人狼ゲームが開始されました
- チャット(private) : あなたの役職は です
- 画面(player) : 「人狼ゲーム 開始」



- pvp : true
- discord : ボイチャ参加
- アイテム支給
- 無敵時間10秒(bossbar)
- scoreboad追加

### JobEvent時

- /wolf job

### デス時

- チャット(private) : あなたは死亡しました
- 画面(private) : あなたは死亡しました



- スペクテーター
- ロビーへtp
- 墓VCへ
- Discord : Mute



- ジャッジ判定

### リセット時

- /wolf reset

### 終了時

- 全員スペクテーターモード