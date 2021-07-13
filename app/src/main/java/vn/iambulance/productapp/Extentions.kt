package vn.iambulance.productapp

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern

const val dbName = "users"
const val eMail = "email"
const val eAccount = "account"
const val ePassword = "password"

infix fun AppCompatActivity.toast(string: String){
    Toast.makeText(this, string,Toast.LENGTH_SHORT).show()
}

infix fun AppCompatActivity.nextActivity(cls: Class<*>?) {
    Intent(this, cls).also { startActivity(it) }
    this.finish()
}

val EMAIL_PATTERN: Pattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)
fun isValidString(str: String): Boolean = EMAIL_PATTERN.matcher(str).matches()

fun validEmail(email: String?): Boolean = email != null && email.isNotBlank() && isValidString(email)

fun validPassword(password: String?): Boolean = password != null && password.isNotBlank() && password.length in 5..19