package vn.iambulance.productapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vn.iambulance.productapp.R
import vn.iambulance.productapp.nextActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        this nextActivity SignInActivity::class.java
    }
}