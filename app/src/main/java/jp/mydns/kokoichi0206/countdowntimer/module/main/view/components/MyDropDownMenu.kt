package jp.mydns.kokoichi0206.countdowntimer.module.main.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.mydns.kokoichi0206.countdowntimer.util.Constants
import jp.mydns.kokoichi0206.countdowntimer.util.TestTags

@Composable
fun MyDropDownMenu(
    onLicenseMenuClick: () -> Unit = {},
    onPrivacyPolicyMenuClick: () -> Unit = {},
) {
    // More の三点ボタンのドロップダウンメニュー
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(
            modifier = Modifier
                .padding(vertical = 0.dp)
                .testTag(TestTags.MORE_VERT_ICON),
            onClick = {
                expanded = true
            }
        ) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = Constants.DESCRIPTION_MORE_VERT_ICON,
                tint = MaterialTheme.colors.surface,
            )
        }
        DropdownMenu(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.onSurface),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // License menu
            DropdownMenuItem(
                modifier = Modifier
                    .testTag(TestTags.LICENSE_MENU),
                onClick = {
                    expanded = false
                    onLicenseMenuClick()
                }
            ) {
                Text(
                    text = Constants.LICENSE_MENU,
                    fontSize = 24.sp,
                    color = Color.White,
                )
            }

            // Privacy Policy menu
            DropdownMenuItem(
                modifier = Modifier
                    .testTag(TestTags.PRIVACY_POLICY_MENU),
                onClick = {
                    expanded = false
                    onPrivacyPolicyMenuClick()
                }
            ) {
                Text(
                    text = Constants.PRIVACY_POLICY_MENU,
                    fontSize = 24.sp,
                    color = Color.White,
                )
            }
        }
    }
}
