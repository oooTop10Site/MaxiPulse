package org.example.project.screens.adaptive.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.database
import maxipuls.composeapp.generated.resources.tests
import org.example.project.screens.tablet.tests.TestsScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object TestTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2.toUShort(),
            title = stringResource(resource = Res.string.tests),
            icon = painterResource(Res.drawable.database)
        )

    @Composable
    override fun Content() {
        Navigator(TestsScreen())
    }
}