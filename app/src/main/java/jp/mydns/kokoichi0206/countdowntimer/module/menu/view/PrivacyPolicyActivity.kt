package jp.mydns.kokoichi0206.countdowntimer.module.menu.view

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import androidx.compose.ui.viewinterop.AndroidView
import jp.mydns.kokoichi0206.countdowntimer.R
import jp.mydns.kokoichi0206.countdowntimer.ui.theme.CountDownTimerTheme

/**
 * プライバシーポリシーのActivityクラス。
 */
class PrivacyPolicyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountDownTimerTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PrivacyPolicy(this)
                }
            }
        }
    }
}

/**
 * プライバシーポリシーを表示するComposable関数。
 */
@Composable
fun PrivacyPolicy(context: Context) {
    AndroidView(factory = {
        WebView(context).apply {
            webViewClient = WebViewClient()
            isDebugInspectorInfoEnabled = false
            loadDataWithBaseURL(null, getPrivacyPolicyString(context), "text/HTML", "UTF-8", null)
        }
    })
}

/**
 * リソースファイルからプライバシーポリシーのHTMLを取得する関数。
 *
 * @param[context] コンテキスト。
 * @return プライバシーポリシー（HTML）をStringにしたもの。
 */
fun getPrivacyPolicyString(context: Context): String {
    val asset = context.resources.openRawResource(R.raw.privacy_policy)
    return asset.bufferedReader().use { it.readText() }
}
