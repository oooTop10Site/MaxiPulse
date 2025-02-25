package org.example.project.platform


//@Composable
//actual fun BackHandlerPlatform(backHandler: (Navigator, Navigator, () -> Unit) -> Unit) {
//    val context = LocalContext.current
//    val navigator = LocalNavigator.currentOrThrow
//    val rootNavigator = RootNavigator.currentOrThrow
//    BackHandler {
//        backHandler(navigator, rootNavigator) {
//            (context as Activity?)?.moveTaskToBack(true)
//        }
//    }
//}