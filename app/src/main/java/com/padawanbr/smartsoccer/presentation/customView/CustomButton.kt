package com.padawanbr.smartsoccer.presentation.customView

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.padawanbr.smartsoccer.R

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val imageView: ImageView
    private val textView: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_button, this, true)
        imageView = view.findViewById(R.id.image)
        textView = view.findViewById(R.id.text)

        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.CustomButton)

            imageView.setImageDrawable(attributes.getDrawable(R.styleable.CustomButton_image))
            textView.text = attributes.getString(R.styleable.CustomButton_text)

            setImageLayoutParams(attributes)
            setTextLayoutParams(attributes)

            setBackground(attributes)

            attributes.recycle()
        }
    }

    private fun setImageLayoutParams(attributes: TypedArray) {
        (imageView.layoutParams as? MarginLayoutParams)?.apply {
            setMargins(
                attributes.getDimensionPixelSize(R.styleable.CustomButton_imageMarginLeft, 0),
                attributes.getDimensionPixelSize(R.styleable.CustomButton_imageMarginTop, 0),
                attributes.getDimensionPixelSize(R.styleable.CustomButton_imageMarginRight, 0),
                attributes.getDimensionPixelSize(R.styleable.CustomButton_imageMarginBottom, 0)
            )
            imageView.layoutParams = this
        }
    }

    private fun setTextLayoutParams(attributes: TypedArray) {
        (textView.layoutParams as? MarginLayoutParams)?.apply {
            setMargins(
                attributes.getDimensionPixelSize(R.styleable.CustomButton_textMarginLeft, 0),
                attributes.getDimensionPixelSize(R.styleable.CustomButton_textMarginTop, 0),
                attributes.getDimensionPixelSize(R.styleable.CustomButton_textMarginRight, 0),
                attributes.getDimensionPixelSize(R.styleable.CustomButton_textMarginBottom, 0)
            )
            textView.layoutParams = this
        }
    }

    private fun setBackground(attributes: TypedArray) {
        val border = GradientDrawable().apply {
            setStroke(attributes.getDimensionPixelSize(R.styleable.CustomButton_borderWidth, 0),
                attributes.getColor(R.styleable.CustomButton_borderColor, Color.BLACK))
            cornerRadius = attributes.getDimension(R.styleable.CustomButton_cornerRadius, 0f)
        }

        val background = GradientDrawable().apply {
            setColor(attributes.getColor(R.styleable.CustomButton_backgroundColor, Color.TRANSPARENT))
            cornerRadius = attributes.getDimension(R.styleable.CustomButton_cornerRadius, 0f)
        }

        val ripple = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val mask = GradientDrawable().apply {
                cornerRadius = attributes.getDimension(R.styleable.CustomButton_cornerRadius, 0f)
                setColor(Color.WHITE)
            }
            RippleDrawable(ColorStateList.valueOf(Color.LTGRAY), LayerDrawable(arrayOf(background, border)), mask)
        } else {
            LayerDrawable(arrayOf(background, border))
        }
        this.background = ripple
    }

}
