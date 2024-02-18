package com.example.kotlintestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.kotlintestapp.constants.Constants
import com.example.kotlintestapp.databinding.ActivitySignInUpBinding


class SignInUpActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivitySignInUpBinding
    private var signState: String = "empty"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivitySignInUpBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        // Получаем данные по ключу
        signState = intent.getStringExtra(Constants.SIGN_STATE)!! //Указали !!, потому что значение не nullable, а точно строка

        if (signState == Constants.SIGN_IN_STATE){
            /*
            Если форма входа, то убираем их верстки поля имени, фимилии и отчества.
            Также делаем невидимой кнопку аватара*/
            bindingClass.edName.visibility = View.GONE
            bindingClass.edLastName.visibility = View.GONE
            bindingClass.edPatronymic.visibility = View.GONE
            bindingClass.btnAvatar.visibility = View.INVISIBLE

        }
    }

    fun onClickDone(view: View){

        if (signState == Constants.SIGN_UP_STATE){ //Если регистрируемся
            val intent = Intent()
            //Передаем данные с формы в главный активити
            intent.putExtra(Constants.LOGIN, bindingClass.edLogin.text.toString())
            intent.putExtra(Constants.PASSWORD, bindingClass.edPassword.text.toString())
            intent.putExtra(Constants.NAME, bindingClass.edName.text.toString())
            intent.putExtra(Constants.LASTNAME, bindingClass.edLastName.text.toString())
            intent.putExtra(Constants.PATRONYMIC, bindingClass.edPatronymic.text.toString())
            //Передадим аватарку, если лна видна
            if(bindingClass.imageAvatar.isVisible) intent.putExtra(Constants.AVATAR_ID, R.drawable.sergey_400)
            setResult(RESULT_OK, intent) //Передаем успешный результат и данные

            finish()//Програмно закрываем текущий активити
        }else if(signState == Constants.SIGN_IN_STATE){ //Если логинимся
            val intent = Intent()
            //Передаем данные с формы в главный активити
            intent.putExtra(Constants.LOGIN, bindingClass.edLogin.text.toString())
            intent.putExtra(Constants.PASSWORD, bindingClass.edPassword.text.toString())
            setResult(RESULT_OK, intent) //Передаем успешный результат и данные

            finish()//Програмно закрываем текущий активити
        }
    }

    fun onClickAvatar(view: View){
        //Делаем аватарку видимой
        bindingClass.imageAvatar.visibility = View.VISIBLE
    }
}