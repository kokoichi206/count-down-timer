package jp.mydns.kokoichi0206.countdowntimer.module.main.view.components

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    onDone: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    val titleStyle = TextStyle(
        color = Color.White.copy(alpha = 0.7f),
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
    )

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
                style = titleStyle,
            )
        },
        onValueChange = {
            onValueChanged(it)
        },
        textStyle = titleStyle,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onDone()
            },
        ),
        maxLines = 1,
    )
}
