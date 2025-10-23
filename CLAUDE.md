# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## プロジェクト概要

このリポジトリは複数のAndroid課題プロジェクトを含むマルチプロジェクトリポジトリです。各サブディレクトリは独立したAndroidアプリケーションプロジェクトとして構成されています。

### プロジェクト構成

- **Flower_ct4a14**: 花の成長シミュレーションアプリケーション（メインプロジェクト）
  - Package: `jp.ac.nkc_ct4a14.flower_ct4a14`
  - 水やりボタンで植物が成長し、段階的に画像が変化する機能を実装
  - プロジェクト固有のCLAUDE.mdが存在（詳細はそちらを参照）

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

## アーキテクチャと設計パターン

### 関数定義スタイル

このプロジェクトでは、関数をラムダ式として `val functionName = { ... }` の形式で定義するパターンを使用しています。

```kotlin
private val initializeViews = {
    statusText = findViewById(R.id.statusText)
    plantImage = findViewById(R.id.plantImage)
}

private val setupListeners = {
    waterButton.setOnClickListener { giveWater() }
}
```

この設計は以下の特徴があります:
- 関数を値として扱い、イミュータブルな定数として定義
- 関数型プログラミングのアプローチを採用
- 引数や返り値を持たない場合に使用される傾向

### UI更新パターン

状態（currentStep）が変更されたら `updateUI()` を呼び出して、画像とテキストを更新する一元化されたパターンを使用しています。

## 依存関係管理

両プロジェクトともVersion Catalogsを使用して依存関係を管理しています。

依存関係の追加手順:
1. プロジェクトの `gradle/libs.versions.toml` にバージョンとライブラリを定義
2. `app/build.gradle.kts` の `dependencies` ブロックで `libs.<library-name>` として参照

主な共通依存関係:
- AndroidX Core KTX
- AppCompat
- Material Components
- ConstraintLayout
- JUnit (テスト)
- Espresso (UIテスト)

## リソース管理

### 文字列リソース

UI表示テキストは `res/values/strings.xml` で管理し、ハードコードを避けています。

### ドローアブルリソース

画像リソースは `res/drawable/` に配置し、コード内では `R.drawable.<resource_name>` で参照します。

## プロジェクト間の違いに注意

各プロジェクトは独立したApplicationIDとパッケージ名を持っています。プロジェクト間でコードを移植する際は、パッケージ名とリソース参照を適切に更新してください。
