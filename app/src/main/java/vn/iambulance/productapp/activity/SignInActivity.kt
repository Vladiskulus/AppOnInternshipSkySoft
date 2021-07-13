package vn.iambulance.productapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import vn.iambulance.productapp.*
import vn.iambulance.productapp.databinding.ActivitySignInBinding
import vn.iambulance.productapp.room.RoomDB

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnSignIn.setOnClickListener { signIn() }
        binding.txtCreateNew.setOnClickListener { this nextActivity SignUpActivity::class.java }
        binding.imgVisibilityPassword.setOnClickListener {
            binding.imgVisibilityPassword.setImageResource(R.drawable.eye_on)
            binding.password.inputType = InputType.TYPE_CLASS_TEXT
        }
        binding.imgFacebook.setOnClickListener { this toast getString(R.string.facebook) }
        binding.imgGooglePlus.setOnClickListener { this toast getString(R.string.google_plus) }
        binding.imgTwitter.setOnClickListener { this toast getString(R.string.twitter) }
        binding.txtForgotPassword.setOnClickListener { this toast getString(R.string.forgot_password) }
        binding.cbRemember.setOnClickListener { this toast getString(R.string.remember_me) }
    }

    private fun signIn() {
        val emailText = binding.email.text.toString()
        val passwordText = binding.password.text.toString()
        if (validEmail(emailText) && validPassword(passwordText)) {
            val roomDB = RoomDB.getData(applicationContext)
            val roomDAO = roomDB?.roomDao()
            Thread {
                val roomEntity = roomDAO?.signIn(emailText, passwordText)
                if (roomEntity == null) {
                    runOnUiThread { this toast getString(R.string.incorrect) }
                } else {
                    this nextActivity MainActivity::class.java
                }
            }.start()
        } else if (!validEmail(emailText)) {
            this toast getString(R.string.incorrect_email)
        } else if (!validPassword(passwordText)) {
            this toast getString(R.string.incorrect_password)
        }
    }
}