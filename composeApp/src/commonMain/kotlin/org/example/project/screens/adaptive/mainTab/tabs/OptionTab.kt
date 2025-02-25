package org.example.project.screens.adaptive.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.options
import maxipuls.composeapp.generated.resources.rectangle_list
import org.example.project.screens.tablet.options.OptionsScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object OptionTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 4.toUShort(),
            title = stringResource(resource = Res.string.options),
            icon = painterResource(Res.drawable.rectangle_list)
        )

    @Composable
    override fun Content() {
        Navigator(OptionsScreen())
    }
}