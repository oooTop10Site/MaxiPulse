package org.example.project.screens.adaptive.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.api
import maxipuls.composeapp.generated.resources.settings
import org.example.project.screens.tablet.settings.SettingsScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object SettingsTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 9.toUShort(),
            title = stringResource(resource = Res.string.settings),
            icon = painterResource(Res.drawable.api)
        )

    @Composable
    override fun Content() {
        Navigator(SettingsScreen())
    }
}