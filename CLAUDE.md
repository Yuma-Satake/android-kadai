# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## プロジェクト概要

このリポジトリは複数のAndroid課題プロジェクトを含むマルチプロジェクトリポジトリです。各サブディレクトリは独立したAndroidアプリケーションプロジェクトとして構成されています。

### プロジェクト構成

- **Flower_ct4a14**: 花の成長シミュレーションアプリケーション
  - Package: `jp.ac.nkc_ct4a14.flower_ct4a14`
  - 水やりボタンで植物が成長し、段階的に画像が変化する機能を実装
  - プロジェクト固有のCLAUDE.mdが存在（詳細はそちらを参照）

- **Hayakuchi_ct4a14**: 早口言葉再生アプリケーション
  - Package: `jp.ac.nkc_ct4a14.hayakuchi_ct4a14`
  - TextToSpeechを使用した音声再生機能
  - 複数の再生スピード（達人/普通/簡単）をサポート
  - 実装完了済み

- **sample**: テンプレート/サンプルプロジェクト
  - Package: `jp.ac.nkc_ct4a14.sample_ct4a14`
  - 基本的なActivityのみを含む最小構成

### 技術スタック

- **Kotlin**: 2.0.21
- **Android Gradle Plugin**: 8.13.0
- **compileSdk/targetSdk**: 36
- **minSdk**: 24
- **Java/Kotlin バージョン**: Java 11

## ビルドとテスト

各プロジェクトは独立してビルド・テスト可能です。プロジェクトディレクトリに移動してから実行してください。

### Flower_ct4a14プロジェクトでの作業

```bash
cd Flower_ct4a14
./gradlew build
./gradlew assembleDebug
./gradlew test
./gradlew lint
```

### Hayakuchi_ct4a14プロジェクトでの作業

```bash
cd Hayakuchi_ct4a14
./gradlew build
./gradlew assembleDebug
./gradlew test
./gradlew lint
```

### sampleプロジェクトでの作業

```bash
cd sample
./gradlew build
./gradlew assembleDebug
./gradlew test
./gradlew lint
```

### 単一テストの実行例

```bash
# ユニットテスト
./gradlew test --tests <TestClassName>

# Instrumentedテスト（エミュレータまたは実機が必要）
./gradlew connectedAndroidTest
```

## 開発環境

### Android SDK設定

この環境のAndroid SDKは以下のパスにインストールされています：

```bash
ANDROID_HOME=~/Library/Android/sdk
```

主要なツールのパス：
- エミュレーター: `~/Library/Android/sdk/emulator/emulator`
- ADB: `~/Library/Android/sdk/platform-tools/adb`
- AVD Manager: `~/Library/Android/sdk/cmdline-tools/latest/bin/avdmanager`

### エミュレーター管理

#### 利用可能なAVDの確認

```bash
~/Library/Android/sdk/emulator/emulator -list-avds
```

現在利用可能なAVD：
- `Pixel_7_Pro_API_34` - Android 14 (API 34)、推奨デバイス

#### エミュレーターの起動

```bash
# バックグラウンドで起動
~/Library/Android/sdk/emulator/emulator -avd Pixel_7_Pro_API_34 &

# デバイスが準備できるまで待機
~/Library/Android/sdk/platform-tools/adb wait-for-device
```

#### エミュレーターの終了

```bash
# 接続されているデバイスを確認
~/Library/Android/sdk/platform-tools/adb devices

# エミュレーターを終了
~/Library/Android/sdk/platform-tools/adb emu kill

# プロセスが残っている場合の強制終了
pkill -9 -f "emulator.*Pixel_7_Pro_API_34"
```

### アプリのインストールと起動

#### デバッグビルドとインストール

```bash
# プロジェクトディレクトリに移動
cd <project_directory>

# デバッグビルド
./gradlew assembleDebug

# エミュレーターにインストール
./gradlew installDebug

# アプリを起動（パッケージ名を指定）
~/Library/Android/sdk/platform-tools/adb shell am start -n jp.ac.nkc_ct4a14.<app_name>/.MainActivity
```

### ログとデバッグ

#### ログの確認

```bash
# すべてのログを表示
~/Library/Android/sdk/platform-tools/adb logcat

# 特定のタグでフィルタリング
~/Library/Android/sdk/platform-tools/adb logcat -d | grep -E "MainActivity"

# ログをクリア
~/Library/Android/sdk/platform-tools/adb logcat -c
```

#### デバイス情報の確認

```bash
# 接続されているデバイス一覧
~/Library/Android/sdk/platform-tools/adb devices

# デバイスの音量設定を確認
~/Library/Android/sdk/platform-tools/adb shell settings get system volume_music

# オーディオ設定の詳細
~/Library/Android/sdk/platform-tools/adb shell dumpsys audio | grep -E "volume"
```

### トラブルシューティング

#### エミュレーターの音声が聞こえない場合

1. エミュレーターを再起動する
2. macOSのオーディオ設定を確認する
3. エミュレーターの音量設定を確認する

#### アプリのインストールに失敗する場合

```bash
# 既存のアプリをアンインストール
~/Library/Android/sdk/platform-tools/adb uninstall jp.ac.nkc_ct4a14.<app_name>

# 再度インストール
./gradlew installDebug
```

#### エミュレーターが起動しない場合

1. 既存のエミュレータープロセスを終了する
2. AVDのキャッシュをクリアする（Android Studio経由）
3. エミュレーターを再起動する

## プロジェクト間の違いに注意

各プロジェクトは独立したApplicationIDとパッケージ名を持っています。プロジェクト間でコードを移植する際は、パッケージ名とリソース参照を適切に更新してください。
