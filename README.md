`ipv5` is an app that does some sweet silly stuff please check it out!

## manual android install (build from source)
- enable usb debugging on your android device
- plug in your android device
- git clone this repository
- `cd ipv5`
- `./gradlew installdebug` 
- enjoy the chaos

## google play android install (signed aab)
- email `sudo at jordanlenchitz dot org` to get on the google play `closed testing` list
- visit `https://play.google.com/store/apps/details?id=net.notipv6.ipv5` on your android 7+ device
- enjoy the chaos

## manual ios install (build from source)
- enable developer mode on your ios device and plug it in
- git clone this repository
- `cd ipv5/iosApp`
- `xcodegen generate`
- `xcodebuild -project iosApp.xcodeproj -scheme iosApp -configuration Debug -sdk iphoneos build`
- this will give you a nice .app file
- use [`ios-deploy`](https://github.com/ios-control/ios-deploy) like this `ios-deploy --bundle /path/to/iosApp.app`
- enjoy the chaos

## manual ios install (pre-built)
- [download](https://drive.google.com/file/d/1tYEvrT8OZZJVwid0V0swIj3QTSC949y3/view?usp=sharing) `ipv5-ios.tar.gz`
- verify that `sha256sum ipv5-ios.tar.gz` is `8f2e19347f16ba6187c6ce29a4da03aa717097f2410e2a6acdd216f733352c7f`
- extract the `iosApp.app` archive from the tarball
- plug in your ios device
- use [`ios-deploy`](https://github.com/ios-control/ios-deploy) like this `ios-deploy --bundle /path/to/iosApp.app`
- enjoy the chaos
