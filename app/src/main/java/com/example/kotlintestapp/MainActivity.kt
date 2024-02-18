package com.example.kotlintestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.kotlintestapp.constants.Constants
import com.example.kotlintestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding

    private var login: String = "empty"
    private var password: String = "empty"
    private var name: String = "empty"
    private var lastName: String = "empty"
    private var patronymic: String = "empty"
    private var avatarImageId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.REQUEST_CODE_SIGN_IN){
            val dataLogin = data?.getStringExtra(Constants.LOGIN)
            val dataPassword = data?.getStringExtra(Constants.PASSWORD)

            if(login == dataLogin && password == dataPassword){
                bindingClass.imageView.visibility = View.VISIBLE
                bindingClass.imageView.setImageResource(avatarImageId)
                val textInfo = "$name $patronymic $lastName"
                bindingClass.tvInfo.text = textInfo

                bindingClass.btnHide.visibility = View.GONE  //Прячем кнопку
                bindingClass.btnExit.text = "Logout"
            }else{
                bindingClass.imageView.visibility = View.VISIBLE
                bindingClass.tvInfo.text = "Такого аккаунта не существует!"
                bindingClass.imageView.setImageResource(R.drawable.fail)
            }

        }else if(requestCode == Constants.REQUEST_CODE_SIGN_UP){
            // Если у нас регистрация
            login = data?.getStringExtra(Constants.LOGIN)!!
            password = data?.getStringExtra(Constants.PASSWORD)!!
            name = data?.getStringExtra(Constants.NAME)!!
            lastName = data?.getStringExtra(Constants.LASTNAME)!!
            patronymic = data?.getStringExtra(Constants.PATRONYMIC)!!
            avatarImageId = data?.getIntExtra(Constants.AVATAR_ID, 0)!!
            bindingClass.imageView.visibility = View.VISIBLE


            bindingClass.imageView.setImageResource(avatarImageId)
            val textInfo = "$name $patronymic $lastName"
            bindingClass.tvInfo.text = textInfo

            bindingClass.btnHide.visibility = View.GONE  //Прячем кнопку
            bindingClass.btnExit.text = "Logout"
        }
    }

    fun onClickSignIn(view: View){
        if (bindingClass.imageView.isVisible && bindingClass.tvInfo.text.toString() != "Такого аккаунта не существует!"){
            bindingClass.imageView.visibility = View.INVISIBLE
            bindingClass.tvInfo.text = ""

            bindingClass.btnHide.visibility = View.VISIBLE  //Показываем кнопку
            bindingClass.btnExit.text = getString(R.string.sign_in)
        }else{
            val intent = Intent(this, SignInUpActivity::class.java)
            intent.putExtra(Constants.SIGN_STATE, Constants.SIGN_IN_STATE)//Передаем в активити ключ входа
            startActivityForResult(intent, Constants.REQUEST_CODE_SIGN_IN)
        }

    }
    fun onClickSignUp(view: View){
        val intent = Intent(this, SignInUpActivity::class.java)
        intent.putExtra(Constants.SIGN_STATE, Constants.SIGN_UP_STATE)//Передаем в активити ключ регистрации
        startActivityForResult(intent, Constants.REQUEST_CODE_SIGN_UP)
    }
}