package com.dvnnorth.colormyviews

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

private val colorList: List<Int> = listOf(
    Color.DKGRAY, Color.GRAY,
    Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN, Color.LTGRAY,
    Color.MAGENTA, Color.RED, Color.YELLOW
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        val clickableViews: List<TextView> =
                listOf(
                    box_one_text, box_two_text, box_three_text,
                    box_four_text, box_five_text
                )

        for (view in clickableViews) {
            view.setOnClickListener { colorize(it as TextView) }
        }
    }

    private fun colorize(view: TextView) {
        val bgColor = colorList[Random.nextInt(0, colorList.size)]
        view.setBackgroundColor(bgColor)
        view.setTextColor(getComplementaryColor(bgColor))
    }

    // From StackOverflow, thanks, I really didn't want to write this myself, lol
    // https://stackoverflow.com/questions/3054873/programmatically-find-complement-of-colors
    private fun getComplementaryColor(color: Int): Int {
        var R = color and 255
        var G = color shr 8 and 255
        var B = color shr 16 and 255
        val A = color shr 24 and 255
        R = 255 - R
        G = 255 - G
        B = 255 - B
        return R + (G shl 8) + (B shl 16) + (A shl 24)
    }
}