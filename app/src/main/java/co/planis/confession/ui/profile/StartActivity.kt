package co.planis.confession.ui.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import co.planis.confession.R
import co.planis.confession.ui.feed.ConfessionsActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_start.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug


class StartActivity : FragmentActivity(),AnkoLogger {



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }

    lateinit var callbackManager : CallbackManager

    lateinit var firebaseAuth: FirebaseAuth

    lateinit var authStateListener: FirebaseAuth.AuthStateListener



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(this)
        setContentView(R.layout.activity_start)


        firebaseAuth = FirebaseAuth.getInstance()

        authStateListener = FirebaseAuth.AuthStateListener {
            if (firebaseAuth.currentUser != null){
                finish()
                startActivity(Intent(this@StartActivity,ConfessionsActivity::class.java))
            }
        }


        callbackManager = CallbackManager.Factory.create()


        login_button.setReadPermissions("email","public_profile")
        login_button.registerCallback(callbackManager,object : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult) {
                    handleFacebookAccessToken(result.accessToken)
                    finish()
                    startActivity(Intent(this@StartActivity,ConfessionsActivity::class.java))
                }
                override fun onCancel() { onLoginCancel() }
                override fun onError(error: FacebookException) { onLoginCancel() }
            } )

    }

    fun handleFacebookAccessToken(token: AccessToken){
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
        .addOnCompleteListener(this,object :OnCompleteListener<AuthResult>{
            override fun onComplete(p0: Task<AuthResult>) {
                finish()
                startActivity(Intent(this@StartActivity,ConfessionsActivity::class.java))
            }
        })

    }

    fun onLoginCancel(){

    }


    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener { authStateListener }
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener { authStateListener }
    }
}
