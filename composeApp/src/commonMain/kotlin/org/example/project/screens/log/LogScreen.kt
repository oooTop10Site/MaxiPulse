package org.example.project.screens.log

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.add_ic
import maxipuls.composeapp.generated.resources.composition
import maxipuls.composeapp.generated.resources.drop_ic
import maxipuls.composeapp.generated.resources.log
import maxipuls.composeapp.generated.resources.rectangle_listv2
import maxipuls.composeapp.generated.resources.search
import org.example.project.screens.composition.CompositionViewModel
import org.example.project.screens.composition.components.CompositionCard
import org.example.project.screens.root.ScreenSize
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiPageContainer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.collections.chunked

class LogScreen: Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            LogViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        val screenSize = ScreenSize.currentOrThrow
        val chunkSize = when (screenSize.widthSizeClass) {
            WindowWidthSizeClass.Medium -> 2
            WindowWidthSizeClass.Expanded -> 2
            WindowWidthSizeClass.Compact -> 1
            else -> 1
        }
        MaxiPageContainer(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(20.dp))
                    Text(
                        text = stringResource(Res.string.log),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 20.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        )
                    )
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp)
            ) {
                items(state.logs.chunked(chunkSize)) { chunk ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
//                        chunk.forEach {
//                            CompositionCard(
//                                modifier = Modifier.weight(1f).height(100.dp),
//                                title = it.title,
//                                members = it.member
//                            ) {
//                                //onClick()
//                            }
//                        }
//                        if (chunk.size != chunkSize) {
//                            for (i in 1..chunkSize - chunk.size) {
//                                Box(modifier = Modifier.weight(1f).height(100.dp))
//                            }
//                        }
                    }
                }
            }

        }
    }
}