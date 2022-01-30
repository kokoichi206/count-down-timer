package jp.mydns.kokoichi0206.countdowntimer.module.main.router

import android.app.Activity
import android.content.Intent
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import jp.mydns.kokoichi0206.countdowntimer.module.main.contract.MainContract
import jp.mydns.kokoichi0206.countdowntimer.module.menu.view.PrivacyPolicyActivity
import jp.mydns.kokoichi0206.countdowntimer.util.Constants

class MainRouter(
    var context: Activity
) : MainContract.Router {
    override fun launchLicenseActivity() {
        OssLicensesMenuActivity.setActivityTitle(Constants.LICENSE_TITLE)
        context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
    }

    override fun launchPrivacyPolicyActivity() {
        context.startActivity(Intent(context, PrivacyPolicyActivity::class.java))
    }

    override fun onDisassemble() {

    }
}