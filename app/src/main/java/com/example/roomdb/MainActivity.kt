package com.example.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {

    val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "User.db"
        ) .fallbackToDestructiveMigration()     //.allowMainThreadQueries()  // Will allow to run on Main Thread
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val button1 = findViewById<Button>(R.id.button1)
        val textview1 = findViewById<TextView>(R.id.textView)
        val textview2 = findViewById<TextView>(R.id.textView2)
        val textview3 = findViewById<TextView>(R.id.textView3)
        val textview4 = findViewById<TextView>(R.id.textView4)

        button.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                db.userDao().insert(User("Ayush Sharma", "8610721153", "Bangalore", 24, 1))
            }
        }
//        button1.setOnClickListener {
//        GlobalScope.launch(Dispatchers.Main) {
//            //Even RunBlocking could have been used.
//            val list = GlobalScope.async { db.userDao().getAllUser()}
//            if(list.await().isNotEmpty()){
//                with(list.await()[0]){
//                    textview1.text = name
//                    textview2.text = age.toString()
//                    textview3.text = address
//                    textview4.text = number
//                }
//            }
//        }
//        }

        //Live Data
        db.userDao().getAllUser().observe(this, androidx.lifecycle.Observer { list ->
            if (list.isNotEmpty()) {
                with(list[0]) {
                    textview1.text = name
                    textview2.text = age.toString()
                    textview3.text = address
                    textview4.text = number
                }
            }
        })
    }
}