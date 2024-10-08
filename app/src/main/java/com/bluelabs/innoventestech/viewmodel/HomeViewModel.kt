package com.bluelabs.innoventestech.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())



    fun validatePan(panEntry: String) :Boolean {
        val pattern: Pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")
        val matcher: Matcher = pattern.matcher(panEntry)
        return  matcher.matches()

    }

    fun validateDate(dayOfMonth: String,monthOfYear:String,year:String) : Boolean {
      return try {
                formatter.parse("$dayOfMonth-$monthOfYear-$year")
                true
            } catch (_: Exception) {
               false
            }


    }


}