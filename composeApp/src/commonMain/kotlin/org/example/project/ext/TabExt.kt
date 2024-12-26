package org.example.project.ext

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.performance
import maxipuls.composeapp.generated.resources.readies_for_upload
import maxipuls.composeapp.generated.resources.ready_to_load
import org.example.project.domain.model.PointsTabUI
import org.example.project.domain.model.sportsman.Performance
import org.example.project.domain.model.test.TestUI
import org.example.project.screens.mainTab.tabs.FormTab
import org.example.project.screens.mainTab.tabs.MainTab
import org.example.project.screens.mainTab.tabs.TestTab
import org.example.project.screens.tests.TestsScreen
import org.jetbrains.compose.resources.stringResource


@Composable
fun Tab.getPointsOfTab(navigator: Navigator, tabNavigator: TabNavigator): List<PointsTabUI> {
    return when (this) {
        is FormTab -> {
            listOf(PointsTabUI(title = "PRE", openTab = MainTab()))
        }

        is TestTab -> {
            listOf(
                PointsTabUI(
                    title = stringResource(Res.string.performance),
                    openTab = MainTab(TestUI.ShuttleRun)
                ), PointsTabUI(
                    title = stringResource(Res.string.ready_to_load),
                    openTab = MainTab(TestUI.ReadiesForUpload)
                )
            )
        }

        else -> {
            emptyList()
        }
    }
}

