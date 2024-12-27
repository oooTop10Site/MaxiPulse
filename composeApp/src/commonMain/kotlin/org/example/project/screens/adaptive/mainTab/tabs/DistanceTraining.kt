package org.example.project.screens.adaptive.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.running_ic
import maxipuls.composeapp.generated.resources.training
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object DistanceTraining : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 10.toUShort(),
            title = stringResource(resource = Res.string.training),
            icon = painterResource(Res.drawable.running_ic)
        )

    @Composable
    override fun Content() {
//        Navigator(GroupScreen())
    }
}