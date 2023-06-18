package com.university.stockexchange.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.lifecycle.ViewModelProvider
import com.github.razir.progressbutton.*
import com.university.stockexchange.MainActivity
import com.university.stockexchange.password_auth.PasswordActivity
import com.university.stockexchange.R
import com.university.stockexchange.ServiceApp
import com.university.stockexchange.registration.RegistrationActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.email_text_input
import kotlinx.android.synthetic.main.activity_login.login_button

class LoginActivity : AppCompatActivity() {
    var isDarkMode : Boolean = true
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val apiToken = getSharedPreferences("MyPref", MODE_PRIVATE).getString("api_token", "-1")
        if (apiToken != "-1") {
            startActivity(Intent(this, MainActivity::class.java))
        }
        else {
            loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

            bindProgressButton(login_button)
            login_button.attachTextChangeAnimator()

            login_button.setOnClickListener {
                login_button.showProgress {
                    progressColor = Color.WHITE
                    gravity = DrawableButton.GRAVITY_CENTER
                }
                loginViewModel.fetchQuestList((application as? ServiceApp)?.serviceApi,
                        email_text_input.text.toString())
                loginViewModel.getLoginData()?.observe(this) {
                    it?.let {
                        login_button.hideProgress(R.string.next)
                        if (it.status) {
                            val intent = Intent(this, PasswordActivity::class.java).putExtra(
                                "api_token",
                                it.api_token
                            )
                            startActivity(intent)
                        } else {
                            login_input.error = "Неправильний емейл"
                        }
                    }
                }
            }
        }
        email_text_input.setOnFocusChangeListener { view, b -> login_input.error = null }
    }

    fun createAccount(view: View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }
}