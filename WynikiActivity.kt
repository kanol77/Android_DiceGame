package com.example.laboratorium2

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WynikiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wyniki)

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish() // Zamyka aktywność i wraca do MainActivity
        }

        val table: TableLayout = findViewById(R.id.tableLayout)
        val wynikiGracz1 = intent.getIntegerArrayListExtra("wynikiGracz1") ?: arrayListOf()
        val wynikiGracz2 = intent.getIntegerArrayListExtra("wynikiGracz2") ?: arrayListOf()

        var suma1 = 0
        var suma2 = 0

        // Funkcja pomocnicza do tworzenia stylizowanego TextView
        fun createStyledTextView(text: String): TextView {
            val tv = TextView(this)
            tv.text = text
            tv.gravity = Gravity.CENTER
            tv.setTypeface(null, Typeface.BOLD)
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            val scale = resources.displayMetrics.density
            val paddingPx = (8 * scale + 0.5f).toInt()
            tv.setPadding(paddingPx, paddingPx, paddingPx, paddingPx)
            tv.setBackgroundResource(R.drawable.table_cell_border) // obramowanie komórki
            return tv
        }

        val liczbaRund = minOf(wynikiGracz1.size, wynikiGracz2.size)

        for (i in 0 until liczbaRund) {
            val row = TableRow(this)

            val t1 = createStyledTextView("${i + 1}")
            val t2 = createStyledTextView("${wynikiGracz1[i]}")
            val t3 = createStyledTextView("${wynikiGracz2[i]}")

            // Kolor tła czerwony dla wygranego pola rundy
            if (wynikiGracz1[i] > wynikiGracz2[i]) {
                t2.setTextColor(Color.RED)
            } else if (wynikiGracz2[i] > wynikiGracz1[i]) {
                t3.setTextColor(Color.RED)
            }

            suma1 += wynikiGracz1[i]
            suma2 += wynikiGracz2[i]

            row.addView(t1)
            row.addView(t2)
            row.addView(t3)
            table.addView(row)
        }

        val sumRow = TableRow(this)
        val label = createStyledTextView("Suma:")
        val sum1View = createStyledTextView("$suma1")
        val sum2View = createStyledTextView("$suma2")

        if(suma1 > suma2){ sum1View.setTextColor(Color.RED)}
        else if (suma1 < suma2){ sum2View.setTextColor(Color.RED)}

        sumRow.addView(label)
        sumRow.addView(sum1View)
        sumRow.addView(sum2View)
        table.addView(sumRow)
    }
}
