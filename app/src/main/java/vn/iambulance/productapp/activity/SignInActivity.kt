package vn.iambulance.productapp.activity

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import vn.iambulance.productapp.*
import vn.iambulance.productapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var activity: SignInActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        activity = this
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        with(binding) {
            btnSignIn.setOnClickListener { signIn() }
            txtCreateNew.setOnClickListener { activity nextActivity SignUpActivity::class.java }
            imgVisibilityPassword.setOnClickListener {
                this.imgVisibilityPassword.setImageResource(R.drawable.eye_on)
                this.password.inputType = InputType.TYPE_CLASS_TEXT
            }
            imgFacebook.setOnClickListener { activity toast getString(R.string.facebook) }
            imgGooglePlus.setOnClickListener { activity toast getString(R.string.google_plus) }
            imgTwitter.setOnClickListener { activity toast getString(R.string.twitter) }
            txtForgotPassword.setOnClickListener { activity toast getString(R.string.forgot_password) }
            cbRemember.setOnClickListener { activity toast getString(R.string.remember_me) }
        }
        viewModel.vmStatus.observe(this, {
            when (it) {
                getString(R.string.success) -> {
                    activity nextActivity MainActivity::class.java
                }
                getString(R.string.unreg_email) -> {
                    activity toast it
                }
                getString(R.string.incorrect_email) -> {
                    activity toast it
                }
                getString(R.string.incorrect_password) -> {
                    activity toast it
                }
            }
        })
    }

    private fun signIn() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        viewModel.singIn(email, password)
    }
}