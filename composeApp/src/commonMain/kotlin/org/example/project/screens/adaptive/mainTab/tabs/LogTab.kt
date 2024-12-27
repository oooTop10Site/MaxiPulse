package org.example.project.screens.adaptive.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.book_bookmark
import maxipuls.composeapp.generated.resources.log
import org.example.project.screens.tablet.log.LogScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object LogTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 3.toUShort(),
            title = stringResource(resource = Res.string.log),
            icon = painterResource(Res.drawable.book_bookmark)
        )

    @Composable
    override fun Content() {
        Navigator(LogScreen())
    }
}