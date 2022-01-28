package com.example.twistedclicker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView

class MainActivity : AppCompatActivity(), Get {
    private var colorName : String = Data.colors
    private var scores : String? = null

    override fun forActivity(colorName : String?){
        var text = findViewById<TextView>(R.id.textColor)
        if (colorName == null){
            this.colorName = "white"
            text.text = this.colorName
        }
        else{
            this.colorName = colorName
            Data.colors = colorName
            text.text = this.colorName
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        var text = findViewById<TextView>(R.id.textColor)
        text.text = colorName
    }

    fun onClickStart(view : View) {
       val intent = Intent(this, GameActivity :: class.java)
       intent.putExtra("color", colorName)
       startActivity(intent)
    }

    fun onClickMarket(view: View) {
        val scoresInfo = ScoresInfo(scores.toString())
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, MarketFragment.newInstance(scoresInfo)).commit()
    }
}

