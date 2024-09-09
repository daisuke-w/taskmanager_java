## ローカル開発
以下環境で両方起動することで動作確認が可能
### フロントエンド
- VSCode
- frontend/に移動してnpm startで起動
- localhost:3000にアクセス

### バックエンド
- eclipse
- プロジェクトを開いてspring boot実行で起動

## フルスタック統合
### フロントエンド
- VSCode
- frontend/に移動してnpm run buildを実行
  - build/配下にstatic/が生成される
  - エラーが出た場合(build失敗した場合)はログの内容確認して修正
- cp -r ./build/* ../backend/src/main/resources/static/でbackendにコピー

### バックエンド
- コマンドプロンプトを開いてbackend/に移動
- mvn clean packageを実行
  - エラーが出た場合(build失敗した場合)はログの内容確認して修正
- java -jar target/taskmanager-0.0.1-SNAPSHOT.jarを実行
- localhost:8080にアクセス