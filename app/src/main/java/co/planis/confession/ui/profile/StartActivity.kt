package co.planis.confession.ui.profile

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import co.planis.confession.R
import co.planis.confession.ui.feed.ConfessionsActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_start.*


class StartActivity : FragmentActivity() {



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }

    var callbackManager : CallbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FacebookSdk.sdkInitialize(this)



        setContentView(R.layout.activity_start)

        login_button.registerCallback(callbackManager,object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult) {
                startActivity(Intent(this@StartActivity,ConfessionsActivity::class.java))
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {

            }
        } )
    }
}
