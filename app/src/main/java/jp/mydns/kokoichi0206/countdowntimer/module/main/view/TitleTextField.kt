package jp.mydns.kokoichi0206.countdowntimer.module.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import jp.mydns.kokoichi0206.countdowntimer.util.TestTags

/**
 * タイマーのタイトルを表すComposable関数。
 */
@Composable
fun TitleTextField(
    modifier: Modifier = Modifier,
    onFocusChanged: () -> Unit = {},
    title: String,
    placeHolder: String,
    onValueChanged: (String) -> Unit = {},
    textStyle: TextStyle = TextStyle(),
    onDone: () -> Unit = {},
) {
    TextField(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .onFocusChanged {
                onFocusChanged()
            }
            .padding(top = 16.dp)
            .testTag(TestTags.HOME_TITLE),
        value = title,
        placeholder = {
            Text(
                text = placeHolder,
                style = textStyle,
            )
        },
        onValueChange = {
            onValueChanged(it)
        },
        textStyle = textStyle,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            },
        ),
        maxLines = 1,
    )
}
