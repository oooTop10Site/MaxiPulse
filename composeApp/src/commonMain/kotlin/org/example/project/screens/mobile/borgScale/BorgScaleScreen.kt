package org.example.project.screens.mobile.borgScale

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.currentOrThrow
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.back_mobile_ic
import maxipuls.composeapp.generated.resources.borg_scale
import org.example.project.screens.adaptive.root.RootNavigator
import org.example.project.theme.uiKit.MaxiButton
import org.example.project.theme.uiKit.MaxiPageContainerMobile
import org.example.project.theme.uiKit.TopBarMobile
import org.example.project.utils.safeAreaHorizontal
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import maxipuls.composeapp.generated.resources.borg_scale_bg
import maxipuls.composeapp.generated.resources.rating
import maxipuls.composeapp.generated.resources.send
import org.example.project.domain.model.scaleBorg.ScaleBorg
import org.example.project.ext.clickableBlank
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiRadioButton

class BorgScaleScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = RootNavigator.currentOrThrow
        val viewModel = rememberScreenModel {
            BorgScaleViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        MaxiPageContainerMobile(modifier = Modifier.fillMaxSize(), topBar = {
            TopBarMobile(
                modifier = Modifier.fillMaxWidth().padding(horizontal = safeAreaHorizontal()),
                leadingIcon = {
                    MobileBackIcon(modifier = Modifier.size(40.dp)) {
                        navigator.pop()
                    }
                },
                title = stringResource(Res.string.borg_scale)
            )
        }) {
            Image(
                painter = painterResource(Res.drawable.borg_scale_bg),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter),
                contentPadding = PaddingValues(
                    start = safeAreaHorizontal(),
                    end = safeAreaHorizontal(),
                    top = 20.dp,
                    bottom = 80.dp
                ),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                itemsIndexed(state.items) { index, it ->
                    ScaleBorgItem(
                        item = it,
                        onClick = {
                            viewModel.selectItem(it)
                        },
                        isSelect = state.selectItem == it,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                    )
                    if (index != state.items.lastIndex) {
                        Spacer(Modifier.size(5.dp))
                        HorizontalDivider(modifier = Modifier.fillMaxWidth())
                    }
                }
            }

            MaxiButton(
                enabled = state.selectItem != null,
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier.align(
                    Alignment
                        .BottomCenter
                ).padding(bottom = 20.dp).height(40.dp).fillMaxWidth()
                    .padding(horizontal = safeAreaHorizontal()),
                onClick = {},
                text = stringResource(Res.string.send)
            )

        }
    }
}

@Composable
fun MobileBackIcon(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(onClick = { onClick() }, modifier = modifier) {
        Icon(
            painter = painterResource(Res.drawable.back_mobile_ic),
            modifier = Modifier.size(24.dp),
            contentDescription = null
        )
    }
}

@Composable
private fun ScaleBorgItem(
    modifier: Modifier = Modifier,
    item: ScaleBorg,
    isSelect: Boolean,
    onClick: () -> Unit
) {

    Row(modifier = modifier.clickableBlank {
        onClick()
    }, verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(item.icon),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )

        Spacer(Modifier.size(20.dp))

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = stringResource(item.title), style = MaxiPulsTheme.typography.bold.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "${stringResource(Res.string.rating)}: ${item.rating.joinToString("-")}",
                style = MaxiPulsTheme.typography.medium.copy(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.size(10.dp))
        MaxiRadioButton(modifier = Modifier.size(24.dp), selected = isSelect) {
            onClick()
        }
    }

}