# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## プロジェクト概要

Flower_ct4a14は標準的なAndroidアプリケーションプロジェクトです。Kotlinで実装されており、Android Gradle Plugin 8.13.0とKotlin 2.0.21を使用しています。

## ビルドとテスト

### ビルド

```bash
./gradlew build
```

### デバッグビルドの作成

```bash
./gradlew assembleDebug
```

### リリースビルドの作成

```bash
./gradlew assembleRelease
```

### クリーンビルド

```bash
./gradlew clean build
```

### テストの実行

```bash
# ユニットテストを実行
./gradlew test

# 単一のテストを実行
./gradlew test --tests jp.ac.nkc_ct4a14.flower_ct4a14.ExampleUnitTest

# Instrumentedテスト（エミュレータまたは実機が必要）
./gradlew connectedAndroidTest
```

### Lintチェック

```bash
./gradlew lint
```

## プロジェクト設定

- **Package名**: `jp.ac.nkc_ct4a14.flower_ct4a14`
- **compileSdk**: 36
- **minSdk**: 24
- **targetSdk**: 36
- **Java/Kotlin バージョン**: Java 11

## アーキテクチャ

- 標準的なAndroidプロジェクト構造
- メインアクティビティ: `MainActivity` (app/src/main/java/jp/ac/nkc_ct4a14/flower_ct4a14/MainActivity.kt)
- レイアウトファイル: `activity_main.xml` (app/src/main/res/layout/activity_main.xml) - LinearLayoutを使用し、中央に要素を配置
- エッジツーエッジUIを有効化

## 依存関係管理

依存関係はVersion Catalogsを使用して管理されています (`gradle/libs.versions.toml`)。

新しい依存関係を追加する場合:
1. `gradle/libs.versions.toml`にバージョンとライブラリを定義
2. `app/build.gradle.kts`の`dependencies`ブロックで参照

主な依存関係:
- AndroidX Core KTX
- AppCompat
- Material Components
- ConstraintLayout
- JUnit (テスト用)
- Espresso (UIテスト用)
