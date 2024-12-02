package org.example.project.screens.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.house_blank
import maxipuls.composeapp.generated.resources.main
import org.example.project.domain.model.test.TestUI
import org.example.project.screens.main.MainScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class MainTab(val testUI: TestUI? = null) : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1.toUShort(),
            title = stringResource(resource = Res.string.main),
            icon = painterResource(Res.drawable.house_blank)
        )

    @Composable
    override fun Content() {
        Navigator(
            MainScreen(testUI = testUI)
        )
    }
}