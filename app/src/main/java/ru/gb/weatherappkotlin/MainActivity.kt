package ru.gb.weatherappkotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<AppCompatButton>(R.id.btn1).setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                Log.d("Button1", "Button clicked")
            }
        })

        val dataClass1 = Note("My First Title", "My First Note", R.color.purple_500)
        Log.d("data classes", "$dataClass1")
        val dataClass2 = dataClass1.copy("My Second Title", "My Second Note", R.color.teal_700)
        Log.d("data classes", "$dataClass2")
        val dataClass3 = dataClass1.copy("My Third Title")
        Log.d("data classes", "${dataClass3.title}, ${dataClass3.color}")

        val daysOfWeek = listOf("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday")

        daysOfWeek.forEach {
            Log.d("Cycles", it)
        }
        Log.d("Cycles","------------------")

        for (i in daysOfWeek) {
            Log.d("Cycles", i)
        }
        Log.d("Cycles","------------------")

        var counter = daysOfWeek.size-1
        while (--counter>0){
            Log.d("Cycles", daysOfWeek[counter])
        }
    }
}