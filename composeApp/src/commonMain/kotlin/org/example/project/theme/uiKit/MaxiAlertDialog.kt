package org.example.project.theme.uiKit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.example.project.theme.MaxiPulsTheme
import org.example.project.theme.uiKit.MaxiAlertDialogButtons.*

@Composable
fun MaxiAlertDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    descriptionContent: (@Composable () -> Unit)? = null,
    onDismiss: () -> Unit,
    accept: () -> Unit,
    acceptText: String,
    cancel: () -> Unit = onDismiss,
    cancelText: String? = null,
    paddingAfterTitle: Boolean = true,
    paddingValues: PaddingValues = PaddingValues(40.dp),
    paddingValuesButton: PaddingValues = PaddingValues(0.dp),
    alertDialogButtons: MaxiAlertDialogButtons? = MaxiAlertDialogButtons.CancelAccept
) {
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = { onDismiss() },
    ) {

        Box(
            modifier = modifier.background(
                MaxiPulsTheme.colors.uiKit.background,
                shape = RoundedCornerShape(50.dp)
            ).clip(RoundedCornerShape(50.dp))
        ) {
            Column(
                Modifier.fillMaxWidth().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                title?.let { text ->
                    Text(
                        text = text, style = MaxiPulsTheme.typography.bold.copy(
                            color = MaxiPulsTheme.colors.uiKit.textColor,
                            fontSize = 20.sp,
                            lineHeight = 20.sp
                        ),
                        modifier = Modifier.padding(bottom = if (paddingAfterTitle) 40.dp else 0.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Box(modifier = Modifier.weight(1f, false)) {
                    descriptionContent?.invoke() ?: Text(
                        text = description.orEmpty(),
                        style = MaxiPulsTheme.typography.regular.copy(
                            color = MaxiPulsTheme.colors.uiKit.textColor,
                            fontSize = 20.sp,
                            lineHeight = 20.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 40.dp)
                    )
                }
                when (alertDialogButtons) {
                    Accept -> {
                        MaxiButton(
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier.padding(paddingValuesButton)
                                .height(54.dp).width(160.dp),
                            onClick = {
                                accept()
                            },
                            text = acceptText,
                        )
                    }

                    CancelAccept -> {
                        AlertButtons(
                            modifier = Modifier.fillMaxWidth()
                                .padding(paddingValuesButton),
                            accept = {
                                accept()
                            },
                            acceptText = acceptText,
                            cancel = {
                                cancel()
                            },
                            cancelText = cancelText.orEmpty()
                        )
                    }

                    null -> {}
                }

            }
        }
    }
}

@Composable
fun MaxiAlertDialog(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(50.dp),
    shape: Dp = 50.dp,
    onDismiss: () -> Unit,
    content: (@Composable ColumnScope.() -> Unit)? = null,
) {
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = { onDismiss() },
    ) {

        Box(
            modifier = modifier.background(
                MaxiPulsTheme.colors.uiKit.background,
                shape = RoundedCornerShape(shape)
            ).clip(RoundedCornerShape(shape))
        ) {
            Column(
                Modifier.fillMaxWidth().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content?.invoke(this)
            }
        }
    }
}


@Composable
fun AlertButtons(
    modifier: Modifier = Modifier,
    accept: () -> Unit,
    acceptText: String,
    cancel: () -> Unit,
    cancelText: String
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        MaxiOutlinedButton(
            modifier = Modifier.height(54.dp).width(170.dp),
            text = cancelText,
            onClick = {
                cancel()
            }
        )

        MaxiButton(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.height(54.dp).width(170.dp),
            onClick = {
                accept()
            },
            text = acceptText
        )

    }
}

enum class MaxiAlertDialogButtons {
    Accept,
    CancelAccept
}