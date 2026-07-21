`ipv5` is an app that does some sweet silly stuff please check it out!

## android install via the google app store
- visit [play.google.com/store/apps/details?id=net.notipv6.ipv5](https://play.google.com/store/apps/details?id=net.notipv6.ipv5) on your android 7+ device
- enjoy the chaos

## android install, manually
- enable usb debugging on your android device
- plug in your android device
- git clone this repository
- `cd ipv5`
- `./gradlew installdebug` 
- enjoy the chaos

## iOS install, manually
- enable developer mode on your ios device and plug it in
- git clone this repository
- `cd ipv5/iosApp`
- `xcodegen generate`
- `xcodebuild -project iosApp.xcodeproj -scheme iosApp -configuration Debug -sdk iphoneos build`
- this will give you a nice .app file
- use [`ios-deploy`](https://github.com/ios-control/ios-deploy) like this `ios-deploy --bundle /path/to/iosApp.app`
- enjoy the chaos
  
