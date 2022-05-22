package com.makeappssimple.abhimanyu.financemanager.android.ui.components.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.MyAppTheme

enum class MyExtendedLoadingFloatingActionButtonState {
    DEFAULT,
    LOADING,
    SUCCESS,
    FAILURE,
}

@ExperimentalAnimationApi
@Composable
fun MyExtendedLoadingFloatingActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember {
        MutableInteractionSource()
    },
    elevation: ButtonElevation? = ButtonDefaults
        .buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 12.dp,
        ),
    shape: Shape = shapes.small
        .copy(
            all = CornerSize(
                percent = 50,
            ),
        ),
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 20.dp,
        vertical = 16.dp,
    ),
    buttonState: MyExtendedLoadingFloatingActionButtonState = MyExtendedLoadingFloatingActionButtonState.DEFAULT,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val isDefaultState = buttonState == MyExtendedLoadingFloatingActionButtonState.DEFAULT

    val transition = updateTransition(
        targetState = isDefaultState,
        label = "loadingButton",
    )

    val animationSpec = tween<Dp>(
        durationMillis = 5000,
        easing = LinearOutSlowInEasing,
    )
    val buttonWidth by transition.animateDp(
        transitionSpec = {
            animationSpec
        },
        label = "buttonWidth",
    ) {
        if (it) {
            Dp.Unspecified
        } else {
            48.dp
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(Red),
    ) {

        Button(
            onClick = onClick,
            enabled = isDefaultState && enabled,
            interactionSource = interactionSource,
            elevation = if (isDefaultState) {
                elevation
            } else {
                ButtonDefaults
                    .buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                    )
            },
            shape = shape,
            border = border,
            colors = colors,
            contentPadding = if (isDefaultState) {
                contentPadding
            } else {
                PaddingValues(
                    horizontal = 0.dp,
                    vertical = 0.dp,
                )
            },
            modifier = modifier
                .size(
                    width = buttonWidth,
                    height = 48.dp,
                ),
        ) {
            when (buttonState) {
                MyExtendedLoadingFloatingActionButtonState.DEFAULT -> {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(
                            initialAlpha = 0.3f,
                            animationSpec = tween(
                                durationMillis = 5000,
                            )
                        ),
                        exit = fadeOut()
                    ) {
                        content()
                    }
                }
                MyExtendedLoadingFloatingActionButtonState.LOADING -> {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(
                            initialAlpha = 0.3f,
                            animationSpec = tween(
                                durationMillis = 5000,
                            )
                        ),
                        exit = fadeOut()
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier
                                .size(
                                    24.dp
                                ),
                        )
                    }

                }
                MyExtendedLoadingFloatingActionButtonState.SUCCESS -> {
                    content()
                }
                MyExtendedLoadingFloatingActionButtonState.FAILURE -> {
                    content()
                }
            }
        }
    }
}

@Composable
fun MyExtendedFloatingActionButton(
    onClickLabel: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember {
        MutableInteractionSource()
    },
    elevation: ButtonElevation? = ButtonDefaults
        .buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 12.dp,
        ),
    shape: Shape = MaterialTheme.shapes.small
        .copy(
            all = CornerSize(
                percent = 50,
            ),
        ),
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 20.dp,
        vertical = 16.dp,
    ),
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        modifier = modifier
            .semantics {
                onClick(
                    label = onClickLabel,
                    action = null,
                )
            },
        content = content,
    )
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview
@Composable
private fun MyExtendedLoadingFloatingActionButtonLoadingPreview() {
    MyAppTheme {
        MyExtendedLoadingFloatingActionButton(
            buttonState = MyExtendedLoadingFloatingActionButtonState.LOADING,
            onClick = {},
        ) {
            MyText(
                text = "Create Barcode",
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
private fun MyExtendedFloatingActionButtonPreview() {
    MyAppTheme {
        MyExtendedFloatingActionButton(
            onClickLabel = "",
            onClick = {},
        ) {
            MyText(
                text = "Create Barcode",
            )
        }
    }
}
