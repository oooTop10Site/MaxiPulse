package org.example.project.screens.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.compositions
import maxipuls.composeapp.generated.resources.database
import maxipuls.composeapp.generated.resources.users_medical
import org.example.project.screens.composition.CompositionScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object CompositionsTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 6.toUShort(),
            title = stringResource(resource = Res.string.compositions),
            icon = painterResource(Res.drawable.users_medical)
        )

    @Composable
    override fun Content() {
        Navigator(CompositionScreen())
    }
}