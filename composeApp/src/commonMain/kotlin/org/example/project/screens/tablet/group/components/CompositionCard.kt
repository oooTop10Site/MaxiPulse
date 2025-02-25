package org.example.project.screens.tablet.group.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import maxipuls.composeapp.generated.resources.Res
import maxipuls.composeapp.generated.resources.members
import maxipuls.composeapp.generated.resources.pencil
import org.example.project.ext.clickableBlank
import org.example.project.theme.MaxiPulsTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun CompositionCard(
    modifier: Modifier = Modifier,
    title: String,
    members: Int,
    isSelect: Boolean = false,
    isEdit: Boolean = true,
    onClick: () -> Unit,
    onEdit: () -> Unit,
) {
    Column(
        modifier.background(
            color = if (isSelect) MaxiPulsTheme.colors.uiKit.grey800 else MaxiPulsTheme.colors.uiKit.card,
            shape = RoundedCornerShape(25.dp)
        ).clip(RoundedCornerShape(25.dp)).clickableBlank() {
            onClick()
        }) {
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = title, style = MaxiPulsTheme.typography.bold.copy(
                    fontSize = 16.sp,
                    color = if (isSelect) MaxiPulsTheme.colors.uiKit.lightTextColor else MaxiPulsTheme.colors.uiKit.textColor
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.size(5.dp))
            if (isEdit) {
                Icon(
                    painterResource(Res.drawable.pencil),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp).clickableBlank {
                        onEdit()
                    },
                    tint = MaxiPulsTheme.colors.uiKit.primary
                )
            }


        }
        Spacer(modifier = Modifier.size(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(Res.string.members),
                style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 16.sp,
                    color = if (isSelect) MaxiPulsTheme.colors.uiKit.lightTextColor else MaxiPulsTheme.colors.uiKit.textColor
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.size(5.dp))

            Text(
                text = members.toString(), style = MaxiPulsTheme.typography.regular.copy(
                    fontSize = 16.sp,
                    color = if (isSelect) MaxiPulsTheme.colors.uiKit.lightTextColor else MaxiPulsTheme.colors.uiKit.textColor
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )


        }

        Spacer(modifier = Modifier.size(20.dp))
    }

}