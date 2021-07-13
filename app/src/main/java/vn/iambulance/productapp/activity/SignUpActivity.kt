package vn.iambulance.productapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import vn.iambulance.productapp.*
import vn.iambulance.productapp.databinding.ActivitySignUpBinding
import vn.iambulance.productapp.room.RoomDB
import vn.iambulance.productapp.room.RoomEntity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnSignUp.setOnClickListener { signUp() }
        binding.email.addTextChangedListener {
            if (validEmail(binding.email.text.toString())) {
                binding.imgCheckEmail.visibility
            }
        }
    }

    private fun signUp() {
        val roomEntity = RoomEntity()
        val account = binding.account.text.toString()
        val email = binding.email.text.toString()
        val password = binding.passwordTop.text.toString()
        roomEntity.account = account
        roomEntity.email = email
        roomEntity.password = password
        if (account.isNotEmpty() &&
            binding.passwordTop.text.toString() ==
            binding.passwordBottom.text.toString()
        ) {
            if (validEmail(email) && validPassword(password)) {
                val roomDB = RoomDB.getData(applicationContext)
                val roomDAO = roomDB?.roomDao()
                Thread {
                    roomDAO?.signUp(roomEntity)
                }.start()
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