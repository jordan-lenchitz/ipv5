`ipv5` is an app that does some sweet silly stuff please check it out!

## manual android install
- enable usb debugging on your android device
- plug in your android device
- git clone this repository
- `cd ipv5`
- `./gradlew installdebug` 
- enjoy the chaos

## google play install (signed aab)
`https://play.google.com/store/apps/details?id=net.notipv6.ipv5`

## manual ios install
- enable developer mode on your ios device
- plug in your ios device
- git clone this repository
- `cd ipv5/iosApp`
- `xcodegen generate`
- `xcodebuild -project iosApp.xcodeproj -scheme iosApp -configuration Debug -sdk iphoneos build`
- this will give you a nice .app file, I personally then use [`ios-deploy`](https://github.com/ios-control/ios-deploy) like this `ios-deploy --bundle /path/to/iosApp.app`
- enjoy the chaos

### apple app store install (`apple signed aab analog` i suppose)
coming soon!
