package com.university.stockexchange.password_auth

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.razir.progressbutton.*
import com.university.stockexchange.MainActivity
import com.university.stockexchange.R
import com.university.stockexchange.ServiceApp
import kotlinx.android.synthetic.main.activity_password.*

class PasswordActivity : AppCompatActivity() {

    private lateinit var passwordViewModel: PasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        passwordViewModel = ViewModelProvider(this).get(PasswordViewModel::class.java)

        bindProgressButton(next_button)
        next_button.attachTextChangeAnimator()

        next_button.setOnClickListener {
            next_button.showProgress {
                progressColor = Color.WHITE
                gravity = DrawableButton.GRAVITY_CENTER
            }
            passwordViewModel.fetchQuestList((application as? ServiceApp)?.serviceApi,
                    amount_action_input.text.toString(), intent.getStringExtra("api_token"))
            passwordViewModel.getPasswordData()?.observe(this, {
                it?.let {
                    next_button.hideProgress(R.string.next)
                    if (it.status) {
                        val prefs = getSharedPreferences("MyPref", MODE_PRIVATE)
                        val ed: SharedPreferences.Editor = prefs.edit()
                        ed.putString("api_token", it.api_token).apply()

                        val intent = Intent(this, MainActivity::class.java).putExtra("api_token", it.api_token)
                        startActivity(intent)
                        finish()
                    } else {
                        amount_action_layout.error = "Неправильний пароль"
                    }
                }
            })
        }
        amount_action_input.setOnFocusChangeListener{ veiw, b -> amount_action_layout.error = null}
    }
}