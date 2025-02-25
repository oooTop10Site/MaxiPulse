package org.example.project.screens.adaptive.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.profile
import maxipuls.composeapp.generated.resources.sportsman
import org.example.project.screens.tablet.sportsman.SportsmanScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object SportsmanTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 7.toUShort(),
            title = stringResource(resource = Res.string.sportsman),
            icon = painterResource(Res.drawable.profile)
        )

    @Composable
    override fun Content() {
        Navigator(SportsmanScreen())
    }
}