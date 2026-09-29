#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/android"
rm -rf build && mkdir -p build/classes build/dex
"$ANDROID_BT/aapt2" link -o build/unsigned.apk -I "$ANDROID_JAR" --manifest AndroidManifest.xml --auto-add-overlay
javac --release 8 -classpath "$ANDROID_JAR" -d build/classes src/com/gamal/slingshot/*.java
"$ANDROID_BT/d8" --min-api 24 --lib "$ANDROID_JAR" --output build/dex $(find build/classes -name '*.class')
(cd build/dex && zip -q ../unsigned.apk classes.dex)
"$ANDROID_BT/zipalign" -f -p 4 build/unsigned.apk build/aligned.apk
"$ANDROID_BT/apksigner" sign --ks "$KEYSTORE" --ks-key-alias slingshot --ks-pass env:KSP --key-pass env:KSP --out build/slingshot-flight.apk build/aligned.apk
"$ANDROID_BT/apksigner" verify --verbose build/slingshot-flight.apk
echo 'built android/build/slingshot-flight.apk'
