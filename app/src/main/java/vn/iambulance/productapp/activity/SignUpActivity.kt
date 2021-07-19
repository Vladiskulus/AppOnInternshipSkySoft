package vn.iambulance.productapp.activity

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import vn.iambulance.productapp.*
import vn.iambulance.productapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var activity: SignUpActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        activity = this
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        val clickablePrivacy: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                p0.setOnClickListener { activity toast getString(R.string.privacy_policy) }
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = getColor(R.color.button)
            }
        }
        val clickableTerm: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                p0.setOnClickListener { activity toast getString(R.string.term_of_service) }
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = getColor(R.color.button)
            }
        }
        val privacyPolicy = SpannableString(getString(R.string.privacy_policy))
        privacyPolicy.setSpan(
            clickablePrivacy,
            0,
            privacyPolicy.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val term = SpannableString(getString(R.string.term_of_service))
        term.setSpan(clickableTerm, 0, term.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val privacy = TextUtils.expandTemplate(
            getString(R.string.privacy) + " ^1 ,\nand " + "^2",
            privacyPolicy,
            term
        )

        with(binding) {
            btnSignUp.setOnClickListener { signUp() }
            email.addTextChangedListener {
                if (validEmail(this.email.text.toString())) {
                    this.imgCheckEmail.visibility
                }
            }
            with(cbRemember) {
                text = privacy
                movementMethod = LinkMovementMethod.getInstance()
            }
        }
        viewModel.vmStatus.observe(activity, {
            when (it) {
                getString(R.string.success) -> {
                    activity nextActivity MainActivity::class.java
                }
                getString(R.string.both_password_error) -> {
                    activity toast it
                }
                getString(R.string.error) -> {
                    activity toast getString(R.string.reg_email)
                }
                getString(R.string.incorrect_email) -> {
                    activity toast it
                }
                getString(R.string.incorrect_password) -> {
                    activity toast it
                }
                getString(R.string.account_error) -> {
                    activity toast it
                }
            }
        })
    }

    private fun signUp() {
        val account = binding.account.text.toString()
        val email = binding.email.text.toString()
        val passwordTop = binding.passwordTop.text.toString()
        val passwordBottom = binding.passwordBottom.text.toString()
        viewModel.singUp(account, email, passwordTop, passwordBottom)
    }
}