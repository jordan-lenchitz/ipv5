`ipv5` is an app that does some sweet silly stuff please check it out!

## manual android install
- enable usb debugging on your android device
- plug in your android device
- git clone this repository
- `cd ipv5`
- `./gradlew installdebug` 
- enjoy the chaos

## google play android install (signed aab)
`https://play.google.com/store/apps/details?id=net.notipv6.ipv5` to enjoy the chaos

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
- [download](https://drive.google.com/file/d/1tYEvrT8OZZJVwid0V0swIj3QTSC949y3/view?usp=sharing) `ipv5-ios.tar.gz` and extract it 
- plug in your ios device
- use [`ios-deploy`](https://github.com/ios-control/ios-deploy) like this `ios-deploy --bundle /path/to/iosApp.app`
- enjoy the chaos

### apple app store ios install
coming soon!
