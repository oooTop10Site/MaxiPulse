package org.example.project.screens.composition

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import maxipuls.composeapp.generated.resources.Res
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiOutlinedTextField
import org.example.project.theme.uiKit.MaxiPageContainer
import org.jetbrains.compose.resources.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import maxipuls.composeapp.generated.resources.add_ic
import maxipuls.composeapp.generated.resources.composition
import maxipuls.composeapp.generated.resources.drop_ic
import maxipuls.composeapp.generated.resources.rectangle_listv2
import maxipuls.composeapp.generated.resources.search
import org.example.project.screens.composition.components.CompositionCard
import org.jetbrains.compose.resources.painterResource

class CompositionScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel {
            CompositionViewModel()
        }
        val state by viewModel.stateFlow.collectAsState()
        MaxiPageContainer(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(20.dp))
                    Text(
                        text = stringResource(Res.string.composition),
                        style = MaxiPulsTheme.typography.bold.copy(
                            fontSize = 20.sp,
                            color = MaxiPulsTheme.colors.uiKit.textColor
                        )
                    )
                    Spacer(
                        Modifier.size(20.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        MaxiOutlinedTextField(
                            value = state.search,
                            onValueChange = {
                                viewModel.changeSearch(it)
                            },
                            modifier = Modifier.height(40.dp).weight(1f),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(Res.drawable.search),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = MaxiPulsTheme.colors.uiKit.textColor
                                )
                            }
                        )

                        MaxiOutlinedTextField(
                            value = "Кальчик",
                            onValueChange = {
//                                viewModel.changeSearch(it)
                            },
                            modifier = Modifier.animateContentSize().height(40.dp).weight(0.7f),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(Res.drawable.drop_ic),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = MaxiPulsTheme.colors.uiKit.textColor
                                )
                            }
                        )

                        Icon(
                            painterResource(Res.drawable.rectangle_listv2),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = MaxiPulsTheme.colors.uiKit.textColor
                        )

                        Box(
                            modifier = Modifier.background(
                                MaxiPulsTheme.colors.uiKit.primary,
                                shape = CircleShape
                            ).clip(CircleShape).size(40.dp), contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painterResource(Res.drawable.add_ic),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MaxiPulsTheme.colors.uiKit.lightTextColor
                            )
                        }
                    }

                    Spacer(
                        Modifier.size(20.dp)
                    )


                }
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp)
            ) {
                items(state.compositions.chunked(3)) { chunk ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(25.dp)
                    ) {
                        chunk.forEach {
                            CompositionCard(
                                modifier = Modifier.weight(1f).height(100.dp),
                                title = it.title,
                                members = it.member
                            ) {
                                //onClick()
                            }
                        }
                        if (chunk.size != 3) {
                            for (i in 1..3 - chunk.size) {
                                Box(modifier = Modifier.weight(1f).height(100.dp))
                            }
                        }
                    }
                }
            }

        }
    }
}