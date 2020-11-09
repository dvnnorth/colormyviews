package com.dvnnorth.colormyviews

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dvnnorth.colormyviews.databinding.ActivityMainBinding
import java.util.Hashtable
import kotlin.random.Random

private val colorList: List<Int> = listOf(
    Color.DKGRAY, Color.GRAY,
    Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN, Color.LTGRAY,
    Color.MAGENTA, Color.RED, Color.YELLOW
)
private val colorLookup: Hashtable<String, Long> = Hashtable()

private lateinit var binding: ActivityMainBinding
private lateinit var clickableViews: List<TextView>
private lateinit var clickableButtons: List<Button>


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        clickableViews = listOf(
            binding.boxOneText, binding.boxTwoText, binding.boxThreeText,
            binding.boxFourText, binding.boxFiveText)
        clickableButtons = listOf(binding.buttonRed, binding.buttonYellow, binding.buttonGreen)
        colorLookup["RED"] = 0xFFFF0000
        colorLookup["YELLOW"] = 0xFFFFFF00
        colorLookup["GREEN"] = 0xFF00FF00
        setListeners()
    }

    private fun setListeners() {
        for (view in clickableViews) {
            view.setOnClickListener { colorize(it as TextView) }
        }

        for (view in clickableButtons) {
            view.setOnClickListener { colorFromButton(it as Button) }
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

    private fun colorFromButton(buttonView: Button) {
        val newBGcolor: Long? = colorLookup[buttonView.text]
        newBGcolor?.let {
            val newTextColor = getComplementaryColor(newBGcolor.toInt())
            val viewNumber = Random.nextInt(0, clickableViews.size)
            val view = clickableViews[viewNumber]
            view.setBackgroundColor(it.toInt())
            view.setTextColor(newTextColor)
        }
    }
}
