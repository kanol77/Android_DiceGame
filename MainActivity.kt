package com.example.laboratorium2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var kostka: Array<ImageView?>
    private lateinit var wynik1: TextView
    private lateinit var wynik2: TextView
    private lateinit var button: Button
    private lateinit var button1: Button

    var etapRundy = 0
    var runda = 0

    var rzutG1_1 = 0
    var rzutG1_2 = 0
    var rzutG2_1 = 0
    var rzutG2_2 = 0

    val rundyGracz1 = ArrayList<Int>()
    val rundyGracz2 = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        kostka = arrayOfNulls(6)

        kostka[0] = findViewById(R.id.imageView4)
        kostka[1] = findViewById(R.id.imageView5)
        kostka[2] = findViewById(R.id.imageView6)
        kostka[3] = findViewById(R.id.imageView3)
        kostka[4] = findViewById(R.id.imageView2)
        kostka[5] = findViewById(R.id.imageView1)

        wynik1 = findViewById(R.id.textView7)
        wynik2 = findViewById(R.id.textView8)
        button = findViewById(R.id.button2)
        button1 = findViewById(R.id.button)

        button1.isEnabled = false

        button.text = "GRACZ 1 - Pierwszy rzut"
        wynik1.setBackgroundColor(Color.LTGRAY)
    }

    fun Losuj(view: View) {
        val kostki = Array(6) { (1..6).random() }

        for (i in kostka.indices) {
            przypiszPlikObrazu(kostka[i], kostki[i])
        }

        val sumaRzutu = kostki.sum()

        when (etapRundy) {
            0 -> {
                rzutG1_1 = sumaRzutu
                wynik1.text = "$rzutG1_1"
                button.text = "GRACZ 2 - Pierwszy rzut"
                wynik1.setBackgroundColor(Color.WHITE)
                wynik2.setBackgroundColor(Color.LTGRAY)
            }
            1 -> {
                rzutG2_1 = sumaRzutu
                wynik2.text = "$rzutG2_1"
                button.text = "GRACZ 1 - Drugi rzut"
                wynik1.setBackgroundColor(Color.LTGRAY)
                wynik2.setBackgroundColor(Color.WHITE)
            }
            2 -> {
                rzutG1_2 = sumaRzutu
                val sumaG1 = rzutG1_1 + rzutG1_2
                wynik1.text = "$sumaG1"
                button.text = "GRACZ 2 - Drugi rzut"
                wynik1.setBackgroundColor(Color.WHITE)
                wynik2.setBackgroundColor(Color.LTGRAY)
            }
            3 -> {
                rzutG2_2 = sumaRzutu
                val sumaG2 = rzutG2_1 + rzutG2_2
                wynik2.text = "$sumaG2"

                val sumaG1 = rzutG1_1 + rzutG1_2
                rundyGracz1.add(sumaG1)
                rundyGracz2.add(sumaG2)

                runda++
                if (runda >= 5) {
                    button.text = "KONIEC"
                    button.isEnabled = false
                    button1.isEnabled = true

                    var wygraneGracz1 = 0
                    var wygraneGracz2 = 0
                    for (i in 0 until 5) {
                        if (rundyGracz1[i] > rundyGracz2[i]) wygraneGracz1++
                        else if (rundyGracz2[i] > rundyGracz1[i]) wygraneGracz2++
                    }

                    wynik1.text = "$wygraneGracz1"
                    wynik2.text = "$wygraneGracz2"

                    button.text = when {
                        wygraneGracz1 > wygraneGracz2 -> "WYGRAŁ GRACZ 1"
                        wygraneGracz2 > wygraneGracz1 -> "WYGRAŁ GRACZ 2"
                        else -> "REMIS"
                    }
                } else {
                    button.text = "GRACZ 1 - Pierwszy rzut"
                    wynik1.setBackgroundColor(Color.LTGRAY)
                    wynik2.setBackgroundColor(Color.WHITE)
                }
            }
        }

        etapRundy = (etapRundy + 1) % 4
    }


    fun start(view: View) {
        rundyGracz1.clear()
        rundyGracz2.clear()
        runda = 0
        etapRundy = 0
        rzutG1_1 = 0
        rzutG1_2 = 0
        rzutG2_1 = 0
        rzutG2_2 = 0

        showToast("Nowa kolejka", applicationContext)

        button.text = "GRACZ 1 - Pierwszy rzut"
        wynik1.setBackgroundColor(Color.LTGRAY)
        wynik2.setBackgroundColor(Color.WHITE)

        for (i in 0..5) {
            przypiszPlikObrazu(kostka[i], 7)
        }

        wynik1.text = "0"
        wynik2.text = "0"
        button.isEnabled = true
        button1.isEnabled = false
    }

    fun przypiszPlikObrazu(imageView: ImageView?, randomNumber: Int) {
        val resourceId = when (randomNumber) {
            1 -> R.drawable.diceone
            2 -> R.drawable.dicetwo
            3 -> R.drawable.dicethree
            4 -> R.drawable.dicefour
            5 -> R.drawable.dicefive
            6 -> R.drawable.dicesix
            else -> R.drawable.question
        }
        imageView?.setImageResource(resourceId)
    }

    fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun wyniki(view: View) {
        val intent = Intent(this, WynikiActivity::class.java)
        intent.putIntegerArrayListExtra("wynikiGracz1", rundyGracz1)
        intent.putIntegerArrayListExtra("wynikiGracz2", rundyGracz2)
        startActivity(intent)
    }
}
