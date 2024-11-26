package org.example.project.screens.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.analyse
import maxipuls.composeapp.generated.resources.database
import maxipuls.composeapp.generated.resources.load_analysis
import org.example.project.screens.loadAnalize.LoadAnalizeScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object LoadAnalizeTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 5.toUShort(),
            title = stringResource(resource = Res.string.load_analysis),
            icon = painterResource(Res.drawable.analyse)
        )

    @Composable
    override fun Content() {
        Navigator(LoadAnalizeScreen())
    }
}