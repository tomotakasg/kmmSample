package com.example.kmmsample.androidApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.kmmsample.shared.Greeting
import com.example.kmmsample.shared.TopAction
import com.example.kmmsample.shared.TopAction.*
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.logging.Logger

/**
 * FYI:https://asmz.hatenablog.jp/entry/kotlin-multiplatform-mobile-getting-started
 */
class MainActivity : AppCompatActivity() {

    private lateinit var response: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.hello_world)
        tv.text = greet()
        
        val button1: Button = findViewById(R.id.button1)
        response = findViewById(R.id.response_text)
        button1.apply {
            text = "greet"
            setOnClickListener {
                response.text =  greet()
            }
        }

        val button2: Button = findViewById(R.id.button2)
        button2.apply {
            text = "request"
            setOnClickListener {
                click()
            }
        }
    }

    private fun greet(): String {
        //見た目上エラーになっているが動く、さすがアルファ版辛い
        return Greeting().greeting()
    }

    private fun click(){
        GlobalScope.launch(Dispatchers.Main) {
            val result = TopAction().mainButton()
            response.text =  result[0].body
        }
    }
}
