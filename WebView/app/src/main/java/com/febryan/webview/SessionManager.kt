package com.febryan.webview

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    val keyPreferences = "login"
    val keyName = "name"
    val keyEmail = "email"

    //  init adalah yang pertama kali dijalanin ketika akses kelas SessionManager
    init {
        sharedPreferences = context.getSharedPreferences(keyPreferences,Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
    }

    fun sessionLogin(name: String, email: String){
        editor?.putString(keyName, name)
        editor?.putString(keyEmail, email)
        editor?.apply()
    }

    fun sessionLogout(){
        editor?.remove(keyName)
        editor?.remove(keyEmail)
        editor?.apply()
    }

    val name : String?
        get() = sharedPreferences?.getString(keyName, null)

    val email : String?
        get() = sharedPreferences?.getString(keyEmail, null)

    fun isLogin() : Boolean{
        if(!name.isNullOrEmpty() && !email.isNullOrEmpty()){
            return true
        }
        return false
    }

}