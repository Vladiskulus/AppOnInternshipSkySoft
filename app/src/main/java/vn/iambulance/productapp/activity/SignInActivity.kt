package vn.iambulance.productapp.activity

import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
                StatusEnum.SUCCESS.status -> {
                    activity nextActivity MapsActivity::class.java
                }
                StatusEnum.EMAIL_NOT_REGISTERED.status -> {
                    activity toast getString(R.string.unreg_email)
                }
                StatusEnum.EMAIL_WRONG.status -> {
                    activity toast getString(R.string.incorrect_email)
                }
                StatusEnum.PASSWORD_WRONG.status -> {
                    activity toast getString(R.string.incorrect_password)
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