package org.example.project.screens.adaptive.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.rectangle_list
import maxipuls.composeapp.generated.resources.utp
import org.example.project.screens.tablet.utp.UtpScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object UTPTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 4.toUShort(),
            title = stringResource(resource = Res.string.utp),
            icon = painterResource(Res.drawable.rectangle_list)
        )

    @Composable
    override fun Content() {
        Navigator(UtpScreen())
    }
}