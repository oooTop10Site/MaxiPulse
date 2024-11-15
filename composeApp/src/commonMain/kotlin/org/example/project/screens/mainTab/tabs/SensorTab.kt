package org.example.project.screens.mainTab.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.circle
import maxipuls.composeapp.generated.resources.database
import maxipuls.composeapp.generated.resources.sensor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object SensorTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 8.toUShort(),
            title = stringResource(resource = Res.string.sensor),
            icon = painterResource(Res.drawable.circle)
        )

    @Composable
    override fun Content() {

    }
}