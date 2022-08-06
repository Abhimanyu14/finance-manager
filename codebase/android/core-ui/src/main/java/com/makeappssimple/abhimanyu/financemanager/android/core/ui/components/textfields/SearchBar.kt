package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

data class SearchBarData(
    val autoFocus: Boolean = true,
    val searchText: String,
    val placeholderText: String,
    val onValueChange: (updatedSearchText: String) -> Unit,
    val onSearch: (() -> Unit)? = null,
)

@Composable
fun SearchBarContainer(
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background,
            )
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            ),
    ) {
        content()
    }
}

@Composable
fun SearchBar(
    data: SearchBarData,
    modifier: Modifier = Modifier,
) {
    val focusRequester: FocusRequester = remember {
        FocusRequester()
    }

    if (data.autoFocus) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            focusRequester.requestFocus()
        }
    }

    TextField(
        value = data.searchText,
        onValueChange = {
            data.onValueChange(it)
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                data.onSearch?.invoke()
            },
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        shape = RoundedCornerShape(
            percent = 50,
        ),
        singleLine = true,
        colors = TextFieldDefaults
            .textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
            ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
            )
        },
        trailingIcon = if (data.searchText.isNotBlank()) {
            {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(
                            shape = CircleShape,
                        )
                        .clickable {
                            data.onValueChange("")
                        }
                        .padding(
                            all = 8.dp,
                        ),
                )
            }
        } else {
            null
        },
        placeholder = {
            MyText(
                text = data.placeholderText,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold,
                ),
            )
        },
        modifier = modifier
            .focusRequester(
                focusRequester = focusRequester,
            )
            .fillMaxWidth(),
    )
}

@Composable
fun MySearchBar(
    data: SearchBarData,
    modifier: Modifier = Modifier,
) {
    val focusRequester: FocusRequester = remember {
        FocusRequester()
    }

    if (data.autoFocus) {
        LaunchedEffect(
            key1 = Unit,
        ) {
            focusRequester.requestFocus()
        }
    }

    BasicTextField(
        value = data.searchText,
        onValueChange = {
            data.onValueChange(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape,
            )
            .padding(
                horizontal = 0.dp,
                vertical = 4.dp,
            )
            .height(
                height = 40.dp,
            )
            .focusRequester(
                focusRequester = focusRequester,
            ),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                data.onSearch?.invoke()
            },
        ),
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = {
            TextFieldDefaults.TextFieldDecorationBox(
                value = data.searchText,
                innerTextField = it,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                placeholder = {
                    MyText(
                        text = data.placeholderText,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(
                                all = 6.dp,
                            )
                            .size(
                                size = 20.dp,
                            ),
                    )
                },
                trailingIcon = if (data.searchText.isNotBlank()) {
                    {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(
                                    shape = CircleShape,
                                )
                                .clickable {
                                    data.onValueChange("")
                                }
                                .padding(
                                    all = 6.dp,
                                )
                                .size(
                                    size = 20.dp,
                                ),
                        )
                    }
                } else {
                    null
                },
                contentPadding = TextFieldDefaults
                    .textFieldWithoutLabelPadding(
                        top = 0.dp,
                        bottom = 0.dp,
                    ),
            )
        },
    )
}
