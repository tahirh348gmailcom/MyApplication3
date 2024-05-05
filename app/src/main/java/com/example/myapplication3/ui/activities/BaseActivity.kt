package com.example.myapplication3

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication3.models.MyExtraData
import com.example.myapplication3.interfaces.IDialog
import com.example.myapplication3.network.viewmodel.BaseViewModel
import com.example.myapplication3.network.viewmodel.MyViewModel
import com.example.myapplication3.ui.activities.MainActivity
import com.example.myapplication3.ui.dialogs.DialogInfo
import com.example.myapplication3.utils.*

open class BaseActivity : AppCompatActivity(), View.OnClickListener, IDialog {

    var progressDialog: ProgressDialog? = null
    lateinit var myViewModel: MyViewModel
    var page = 1
    var nextPageAvailable = false
    //var PAGE_LIMIT = Constants.PAGE_LIMIT
    var REQUEST_CODE_SETTINGS = 10001
    var TAG_GUEST_USER_ALERT = 10002
    var TAG_SHOULD_FINISH_ACTIVITY = 10003

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        observeLoaderAndError(myViewModel)
    }

    override fun onResume() {
        super.onResume()
        log("================================================================================== onResume - Activity Name " + this.localClassName)
    }

    fun transparentBottomNavigationBar() {
        window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

    fun transparentStatusBar() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or 0
            statusBarColor = Color.TRANSPARENT
        }
    }


    fun initialize()
    {
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java!!)
        page = 1
        nextPageAvailable = false
    }

    fun observeLoaderAndError(viewModel: BaseViewModel) {
        viewModel.loading.observe(this,
            Observer<Boolean> { aBoolean -> enableLoadingBar(aBoolean!!) })

        viewModel.error.observe(this,
            Observer<String> { value ->
                onError(value)
            })

        viewModel.forceUpdate.observe(this,
            Observer<String> { value -> onError(value) })
        viewModel.hideKeyBoard.observe(this,
            Observer<String> { Utils.hideKeyboard(this@BaseActivity) })
        viewModel.forceLogout.observe(this,
            Observer<String> { value ->
                onForceLogout(value)
            })
    }

    /*To show progress bar*/
    fun enableLoadingBar(enable: Boolean) {
        if (enable && !isFinishing) {
            loadProgressBar(null, getString(R.string.loading_), false)
        } else {
            dismissProgressBar()
        }
    }

    /*To dismiss progress bar*/
    private fun dismissProgressBar() {
        if (progressDialog != null && progressDialog!!.isShowing()) {
            progressDialog!!.dismiss()
        }
        progressDialog = null
    }

    fun onForceLogout(message: String)
    {
        Utils.onForceLogout(this, message, this)
    }

    /*To show error message*/
    fun onError(reason: String) {
        if (reason != null && StringUtility.validateString(reason) && !reason.equals("null", ignoreCase = true)) {
            toast(reason)
        } else {
            toast(getString(R.string.default_error))
        }
    }

    fun toast(message: String?) {
        Utils.toast(message!!, this)
    }

    fun log(message: String) {
        Utils.log(javaClass.simpleName, message)
    }

    /*To show progress bar*/
    private fun loadProgressBar(title: String?, message: String, cancellable: Boolean) {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)//ProgressDialog.show(this, title, message, false, cancellable);
        progressDialog!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog!!.setIndeterminate(true)
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
        progressDialog!!.setContentView(R.layout.layout_progress_dialog)
    }

    /*To show simple dialog*/
    fun onInfo(message: String) {
        DialogInfo(message, getString(R.string.ok), "", this, 0, MyExtraData(), 3,false).show(supportFragmentManager, "")
    }

    /*To show simple dialog*/
    fun onInfo(message: String, shouldFinish: Boolean) {
        DialogInfo(message, getString(R.string.ok), "", this, TAG_SHOULD_FINISH_ACTIVITY, MyExtraData(), 3,false).show(supportFragmentManager, "")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Utils.animateActivity(this)
    }

    fun switchActivity(intent: Intent) {
        Utils.switchActivity(intent, this)
    }

    fun switchActivity(intent: Intent, requestCode: Int) {
        Utils.switchActivity(intent, requestCode, this)
    }

    fun switchActivityWithClearFlag(intent: Intent) {
        Utils.switchActivityWithClearFlag(intent, this)
    }


    override fun onClick(view: View?) {
        disableViewForFewSeconds(view)
    }

    fun disableViewForFewSeconds(view: View?) {
        view?.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({
            view?.isEnabled = true
        }, 1000)
    }

    override fun onDialogClick(isOk: Boolean, tag: Int, extraData: MyExtraData?) {
        if(isOk == true && tag == REQUEST_CODE_SETTINGS) {
            switchActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
            }, REQUEST_CODE_SETTINGS)
        }else if (tag == Constants.FORCE_UPDATE && isOk) {
            Utils.openUrl(extraData!!.appLink, this)
        }else if(tag == TAG_GUEST_USER_ALERT && isOk)
        {
            switchActivityWithClearFlag(Intent(this, MainActivity::class.java))
        }else if(tag == TAG_SHOULD_FINISH_ACTIVITY && isOk)
        {
            onBackPressed()
        }
    }

    fun validateNextPageAvailableOrNot(listSize: Int) {
        nextPageAvailable = Utils.validateNextPageAvailableOrNot(listSize)
        if (nextPageAvailable == true) {
            page = page + 1
        }
    }

    fun showSettingsAlert(message: String)
    {
        Utils.onInfoWithResult(message, this, REQUEST_CODE_SETTINGS, getString(R.string.settings), getString(R.string.later), MyExtraData(),"", true, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SETTINGS -> {

            }
        }
    }


}