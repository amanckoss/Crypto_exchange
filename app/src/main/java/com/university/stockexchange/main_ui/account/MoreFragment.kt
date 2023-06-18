package com.university.stockexchange.main_ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.university.stockexchange.R
import com.university.stockexchange.ServiceApp
import com.university.stockexchange.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_more.*
import kotlin.math.log

class MoreFragment : Fragment() {

  private lateinit var moreViewModel: MoreViewModel
  private lateinit var rootView: View

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    moreViewModel = ViewModelProvider(this).get(MoreViewModel::class.java)
    rootView = inflater.inflate(R.layout.fragment_more, container, false)
    val prefs = context?.getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)

    moreViewModel.fetchQuestList((activity?.application as? ServiceApp)?.serviceApi, prefs?.getString("api_token", "-1")!!)
    moreViewModel.getAccountData()?.observe(viewLifecycleOwner) {
      it?.let {
        tvEmail.text = it.email
      }
    }
    val exitButton = rootView.findViewById<Button>(R.id.login_button)
    val radioButton = rootView.findViewById<Button>(R.id.change_mode_switch)
    radioButton.setOnClickListener { changeMode()}
    exitButton.setOnClickListener { logout() }
    return rootView
  }

  private fun isDark(): Boolean {
    if(AppCompatDelegate.MODE_NIGHT_YES == AppCompatDelegate.getDefaultNightMode()) {
      return true
    }
    return false
  }

  private fun changeMode() {
    if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
  }

  private fun logout() {
      val edit = context?.getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)?.edit()
      edit?.putString("api_token", null)?.apply()
      val intent = Intent(context, LoginActivity::class.java)
      startActivity(intent)
      activity?.finish()

  }


}