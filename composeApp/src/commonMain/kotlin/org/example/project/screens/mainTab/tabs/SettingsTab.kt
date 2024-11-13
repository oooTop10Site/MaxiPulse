package org.example.project.screens.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.api
import maxipuls.composeapp.generated.resources.database
import maxipuls.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object SettingsTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 8.toUShort(),
            title = stringResource(resource = Res.string.settings),
            icon = painterResource(Res.drawable.api)
        )

    @Composable
    override fun Content() {

    }
}