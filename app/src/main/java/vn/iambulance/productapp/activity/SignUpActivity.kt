package vn.iambulance.productapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import vn.iambulance.productapp.*
import vn.iambulance.productapp.databinding.ActivitySignUpBinding
import vn.iambulance.productapp.room.RoomEntity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        with(binding) {
            btnSignUp.setOnClickListener { signUp() }
            email.addTextChangedListener {
                if (validEmail(this.email.text.toString())) {
                    this.imgCheckEmail.visibility
                }
            }
        }
    }

    private fun signUp() {
        val roomEntity = RoomEntity()
        val account = binding.account.text.toString()
        val email = binding.email.text.toString()
        val password = binding.passwordTop.text.toString()
        with(roomEntity) {
            this.account = account
            this.email = email
            this.password = password
        }
        if (account.isNotEmpty() &&
            binding.passwordTop.text.toString() ==
            binding.passwordBottom.text.toString()
        ) {
            if (validEmail(email) && validPassword(password)) {
                viewModel.singUp(roomEntity)
                this nextActivity SignInActivity::class.java
            } else if (!validEmail(email)) {
                this toast getString(R.string.incorrect_email)
            } else if (!validPassword(password)) {
                this toast getString(R.string.incorrect_password)
            }
        } else {
            this toast getString(R.string.incorrect)
        }
    }
}