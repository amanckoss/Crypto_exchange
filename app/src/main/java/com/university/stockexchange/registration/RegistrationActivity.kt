package com.university.stockexchange.registration

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.razir.progressbutton.*
import com.university.stockexchange.login.LoginActivity
import com.university.stockexchange.R
import com.university.stockexchange.ServiceApp
import com.university.stockexchange.model.remote.service.model.RegistrationData
import kotlinx.android.synthetic.main.activity_registration.*


class RegistrationActivity : AppCompatActivity() {

    private lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)

        bindProgressButton(login_button)
        login_button.attachTextChangeAnimator()

        login_button.setOnClickListener {
            login_button.showProgress {
                progressColor = Color.WHITE
                gravity = DrawableButton.GRAVITY_CENTER
            }
            registrationViewModel.fetchQuestList((application as? ServiceApp)?.serviceApi,
                registrationInputName.text.toString(),
                registrationInputSurname.text.toString(),
                email_text_input.text.toString(),
                registrationInputPassword.text.toString(),
                registrationInputPasswordConf.text.toString()
                )
        }
        registrationViewModel.getRegisterData()?.observe(this, Observer {
            it?.let {
                login_button.hideProgress(R.string.next)
                if (it.status) {
                    Toast.makeText(applicationContext, "регістація успішна", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                else analyticError(it)
            }
        })

        errorListener()
    }

    private fun errorListener() {
        registrationInputName.setOnFocusChangeListener { view, b -> registrationInputNameL.error = null }
        registrationInputSurname.setOnFocusChangeListener { view, b -> registrationInputSurnameL.error = null }
        email_text_input.setOnFocusChangeListener { view, b -> registrationInputEmailL.error = null }
        registrationInputPassword.setOnFocusChangeListener { view, b -> registrationInputPasswordL.error = null }
    }

    private fun analyticError(data  : RegistrationData) {
        if(data.first_name != null) {
            registrationInputNameL.error = data.first_name[0]
        }
        if (data.surname != null) {
            registrationInputSurnameL.error = data.surname[0]
        }
        if (data.email != null) {
            registrationInputEmailL.error = data.email[0]
        }
        if (data.password != null) {
            registrationInputPasswordL.error = data.password[0]
        }
    }
}