package org.example.project.screens.adaptive.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.form
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object FormTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 11.toUShort(),
            title = stringResource(resource = Res.string.form),
            icon = painterResource(Res.drawable.form)
        )

    @Composable
    override fun Content() {
//        Navigator(GroupScreen())
    }
}