package org.example.project.screens.adaptive.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.compositions
import maxipuls.composeapp.generated.resources.groups
import maxipuls.composeapp.generated.resources.users_medical
import org.example.project.screens.tablet.group.GroupScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object CompositionsTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 6.toUShort(),
            title = stringResource(resource = Res.string.groups),
            icon = painterResource(Res.drawable.users_medical)
        )

    @Composable
    override fun Content() {
        Navigator(GroupScreen())
    }
}