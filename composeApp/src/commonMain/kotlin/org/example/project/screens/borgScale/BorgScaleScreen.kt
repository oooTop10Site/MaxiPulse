package org.example.project.screens.borgScale

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.back_ic
import maxipuls.composeapp.generated.resources.back_mobile_ic
import maxipuls.composeapp.generated.resources.borg_scale
import org.example.project.screens.root.RootNavigator
import org.example.project.theme.uiKit.MaxiPageContainerMobile
import org.example.project.theme.uiKit.TopBarMobile
import org.example.project.utils.safeAreaHorizontal
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class BorgScaleScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = RootNavigator.currentOrThrow
        MaxiPageContainerMobile(modifier = Modifier.fillMaxSize(), topBar = {
            TopBarMobile(modifier = Modifier.fillMaxWidth().padding(horizontal = safeAreaHorizontal()),leadingIcon = {
                MobileBackIcon(modifier = Modifier.size(40.dp)) {
                    navigator.pop()
                }
            }, title = stringResource(Res.string.borg_scale))
        }) {

        }
    }
}

@Composable
fun MobileBackIcon(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(onClick = { onClick() }, modifier = modifier) {
        Icon(painter = painterResource(Res.drawable.back_mobile_ic), modifier = Modifier.size(24.dp), contentDescription = null)
    }
}