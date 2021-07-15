package vn.iambulance.productapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import androidx.lifecycle.ViewModelProvider
import vn.iambulance.productapp.*
import vn.iambulance.productapp.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        binding.also {
            it.btnSignIn.setOnClickListener { signIn() }
            it.txtCreateNew.setOnClickListener { this nextActivity SignUpActivity::class.java }
            it.imgVisibilityPassword.setOnClickListener {
                binding.imgVisibilityPassword.setImageResource(R.drawable.eye_on)
                binding.password.inputType = InputType.TYPE_CLASS_TEXT
            }
            it.imgFacebook.setOnClickListener { this toast getString(R.string.facebook) }
            it.imgGooglePlus.setOnClickListener { this toast getString(R.string.google_plus) }
            it.imgTwitter.setOnClickListener { this toast getString(R.string.twitter) }
            it.txtForgotPassword.setOnClickListener { this toast getString(R.string.forgot_password) }
            it.cbRemember.setOnClickListener { this toast getString(R.string.remember_me) }
        }
    }

    private fun signIn() {
        val emailText = binding.email.text.toString()
        val passwordText = binding.password.text.toString()
        viewModel.singIn(emailText, passwordText)
        if (validEmail(emailText) && validPassword(passwordText)) {
            this nextActivity MainActivity::class.java
        } else if (!validEmail(emailText)) {
            this toast getString(R.string.incorrect_email)
        } else if (!validPassword(passwordText)) {
            this toast getString(R.string.incorrect_password)
        }
    }
}