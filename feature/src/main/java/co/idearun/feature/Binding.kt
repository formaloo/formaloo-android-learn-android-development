package co.idearun.feature

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.Html
import android.text.Layout
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import co.idearun.common.Constants
import co.idearun.common.GlideImageGetter
import co.idearun.common.MyTagHandler
import co.idearun.data.model.form.ChoiceItem
import co.idearun.data.model.form.Fields
import co.idearun.data.model.form.Form
import co.idearun.feature.drawer.SortedLessonListAdapter
import co.idearun.feature.lesson.adapter.LessonFieldsAdapter
import co.idearun.feature.lesson.adapter.holder.DropDownItemsAdapter
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber
import java.lang.reflect.Field
import java.net.URI
import java.net.URISyntaxException
import java.util.*


object Binding : KoinComponent {

    @BindingAdapter("app:imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?) {
        val source = url ?: ContextCompat.getDrawable(
            view.context,
            co.idearun.feature.R.drawable.ic_flashcard
        )
        Glide.with(view.context).load(source).into(view)
    }

    @BindingAdapter("app:formBack")
    @JvmStatic
    fun loadFormBack(view: ImageView, form: Form?) {
        val backgroundImage = form?.background_image
        val backgroundColor = form?.background_color

        if (backgroundImage?.isNotEmpty() == true) {
            Glide.with(view.context).load(getUrlWithoutParameters(backgroundImage)).into(view)

        } else if (backgroundColor?.isNotEmpty() == true) {
            getHexColor(backgroundColor)?.let { txtColor ->
                view.setColorFilter(Color.parseColor(txtColor))
            }
        }

    }

    @Throws(URISyntaxException::class)
    private fun getUrlWithoutParameters(url: String): String {
        val uri = URI(url)
        return URI(
            uri.scheme,
            uri.authority,
            uri.path,
            null,  // Ignore the query part of the input url
            uri.fragment
        ).toString()
    }

    @BindingAdapter("app:htmlTxt")
    @JvmStatic
    fun setHtmlTxt(txv: TextView, txt: String?) {

        txv.setOnTouchListener { v, event ->
            var ret = false
            val text = (v as TextView).text
            val stext = Spannable.Factory.getInstance().newSpannable(text)
            val widget = v as TextView
            val action = event.action
            if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN
            ) {
                var x = event.x.toInt()
                var y = event.y.toInt()
                x -= widget.totalPaddingLeft
                y -= widget.totalPaddingTop
                x += widget.scrollX
                y += widget.scrollY
                val layout: Layout = widget.layout
                val line: Int = layout.getLineForVertical(y)
                val off: Int = layout.getOffsetForHorizontal(line, x.toFloat())
                val link = stext.getSpans(
                    off, off,
                    ClickableSpan::class.java
                )
                if (link.isNotEmpty()) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget)
                    }
                    ret = true
                }
            }
            ret
        }

        txt?.let {
            txv.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(
                    txt, Html.FROM_HTML_MODE_LEGACY, GlideImageGetter(txv),
                    MyTagHandler()
                )
            } else {
                Html.fromHtml(txt, GlideImageGetter(txv), MyTagHandler())
            }
        }

    }

    @BindingAdapter("app:htmlSimpeTxt")
    @JvmStatic
    fun setSimpleHtmlTxt(txv: TextView, txt: String?) {
        txt?.let {
            txv.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(txt, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(txt)
            }
        }

    }

    @BindingAdapter("app:movementMethod")
    @JvmStatic
    fun setMovementMethod(txv: TextView, status: Boolean?) {
        txv.movementMethod = LinkMovementMethod.getInstance()

    }

    @JvmStatic
    @BindingAdapter("field_desc")
    fun field_desc(view: TextView, field: Fields) {
        val context = view.context

        var description = ""
        field.description?.let {
            description = it
        }
        val type = if (field.sub_type != null) {
            field.sub_type
        } else {
            field.type
        }

        val descPlus = when (type) {
            Constants.SHORT_TEXT -> {
                if (field.max_length != null) {
                    context.getString(co.idearun.feature.R.string.max_char_lenght) + " : " + field.max_length

                } else {
                    ""
                }
            }

            else -> {

                ""
            }
        }

        view.text = if (descPlus.isNotEmpty()) {
            "$description ( $descPlus )"

        } else {
            description
        }
    }


    @JvmStatic
    @BindingAdapter("text_color")
    fun setTextColor(view: TextView, color: String?) {
        val txtColor = getHexColor(color) ?: convertRgbToHex("55", "55", "55")
        view.setTextColor(Color.parseColor(txtColor))

    }

    @JvmStatic
    @BindingAdapter("nps_color", "nps_data")
    fun nps_color(view: AppCompatButton, form: Form, status: Boolean?) {

        status?.let {
            val txtColor = getHexColor(form.text_color) ?: convertRgbToHex("55", "55", "55")
            val fieldColor = getHexColor(form.field_color) ?: convertRgbToHex("242", "242", "242")
            val background_color =
                getHexColor(form.background_color) ?: convertRgbToHex("242", "242", "242")

            if (it) {
                view.background = ColorDrawable(Color.parseColor(txtColor))
                view.setTextColor(Color.parseColor(fieldColor))

            } else {
                view.background = ColorDrawable(Color.parseColor(background_color))
                view.setTextColor(Color.parseColor(txtColor))

            }
        }

    }

    @JvmStatic
    @BindingAdapter("text_color")
    fun setTextColor(view: AppCompatButton, color: String?) {
        val txtColor = getHexColor(color) ?: convertRgbToHex("55", "55", "55")
        view.setTextColor(Color.parseColor(txtColor))

    }

    @JvmStatic
    @BindingAdapter("edt_background")
    fun fieldBackground(view: TextInputEditText, color: String?) {
        val backColor = getHexColor(color) ?: convertRgbToHex("242", "242", "242")
        view.setBackgroundColor(Color.parseColor(backColor))
    }

    @JvmStatic
    @BindingAdapter("app:imageTintList")
    fun imageTintList(view: ImageButton, color: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.imageTintList = getColorStateList(color)
        }
    }

    @JvmStatic
    @BindingAdapter("app:progressTint")
    fun progressTint(view: LinearProgressIndicator, color: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.progressTintList = getColorStateList(color)
        }
    }

    @JvmStatic
    @BindingAdapter("app:progressTint")
    fun progressTint(view: AppCompatRatingBar, color: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.progressTintList = getColorStateList(color)
        }
    }

    @JvmStatic
    @BindingAdapter("app:backgroundTintList")
    fun backgroundTintList(view: View, color: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.backgroundTintList = getColorStateList(color)
        }
    }

    @JvmStatic
    @BindingAdapter("textCursorDrawable")
    fun textCursorDrawable(view: TextInputEditText, color: String?) {
        val txtColor = getHexColor(color) ?: convertRgbToHex("242", "242", "242")
        setCursorColor(view, Color.parseColor(txtColor))
    }

    @JvmStatic
    @BindingAdapter("field_background")
    fun fieldBackground(view: View, form: Form?) {
        val shapedrawable = GradientDrawable()

        val fieldHex = convertRgbToHex("242", "242", "242")
        val fieldColor = form?.field_color

        val borderHex = convertRgbToHex("255", "255", "255")
        val borderColor = form?.border_color

        val fColor = if (fieldColor != null && fieldColor.isNotEmpty()) {
            getHexColor(fieldColor) ?: fieldHex
        } else {
            fieldHex
        }

        shapedrawable.setColor(Color.parseColor(fColor))


        val borColor = if (borderColor != null && borderColor.isNotEmpty()) {
            getHexColor(borderColor) ?: borderHex
        } else {
            borderHex
        }
        shapedrawable.setStroke(4, Color.parseColor(borColor))



        shapedrawable.cornerRadius = 3f

        view.background = shapedrawable


    }

    @JvmStatic
    @BindingAdapter("selectedFieldBackground")
    fun selectedFieldBackground(view: View, form: Form?) {
        val shapedrawable = GradientDrawable()

        val textColor = form?.text_color
        val txtHX = convertRgbToHex("242", "242", "242")
        val borderHX = convertRgbToHex("255", "255", "255")
        val fieldColor: String?
        val borderColor: String?

        if (textColor != null) {
            val s = getHexColor(textColor)
            fieldColor = s
            borderColor = s
        } else {
            fieldColor = txtHX
            borderColor = borderHX
        }

        shapedrawable.setColor(Color.parseColor(fieldColor))


        shapedrawable.setStroke(4, Color.parseColor(borderColor))

        shapedrawable.cornerRadius = 3f


        view.background = shapedrawable


    }

    @JvmStatic
    @BindingAdapter("text_color")
    fun setSelectedTextColor(view: TextView, form: Form?) {
        val txtColor = getHexColor(form?.background_color) ?: convertRgbToHex("55", "55", "55")
        view.setTextColor(Color.parseColor(txtColor))

    }

    @JvmStatic
    @BindingAdapter("TextInputLayout_style")
    fun TextInputLayoutStyle(view: TextInputLayout, form: Form?) {

        val borderColor = getHexColor(form?.border_color) ?: convertRgbToHex("255", "255", "255")
        val borderColorList = ColorStateList(
            arrayOf(
                intArrayOf(-R.attr.state_enabled),
                intArrayOf(R.attr.state_enabled)
            ), intArrayOf(
                Color.parseColor(borderColor) //disabled
                , Color.parseColor(borderColor) //enabled
            )
        )


        val fieldColor = getHexColor(form?.field_color) ?: convertRgbToHex("242", "242", "242")
        val fieldColorList = ColorStateList(
            arrayOf(
                intArrayOf(-R.attr.state_enabled),
                intArrayOf(R.attr.state_enabled)
            ), intArrayOf(
                Color.parseColor(fieldColor) //disabled
                , Color.parseColor(fieldColor) //enabled
            )
        )


        view.setBoxStrokeColorStateList(borderColorList)
        view.boxBackgroundColor = Color.parseColor(fieldColor)
    }

    @JvmStatic
    @BindingAdapter("app:text_color")
    fun setTextColor(view: TextInputEditText, color: String?) {
        val txtColor = getHexColor(color) ?: convertRgbToHex("55", "55", "55")
        view.setTextColor(Color.parseColor(txtColor))

    }

    fun getHexColor(color_: String?): String? {

        var hexString: String? = null
        var color = color_
        try {
            if (color != null) {
                color = color.replace("{\"", "")
                color = color.replace("\"", "")

                val a = color.substring(color.indexOf("a:") + 2, color.indexOf("}"))
                val r = color.substring(color.indexOf("r:") + 2, color.indexOf(",g"))
                val g = color.substring(color.indexOf("g:") + 2, color.indexOf(",b"))
                val b = color.substring(color.indexOf("b:") + 2, color.indexOf(",a"))

                hexString = convertRgbToHex(r, g, b)

            }
        } catch (e: Exception) {
        }

        return hexString
    }

    fun convertRgbToHex(r: String, g: String, b: String): String {
        return "#${
            Integer.toHexString(
                Color.rgb(
                    Integer.parseInt(r),
                    Integer.parseInt(g),
                    Integer.parseInt(b)
                )
            )
        }"
    }

    fun setCursorColor(view: TextInputEditText, color: Int) {
        try {
            // Get the cursor resource id
            var field: Field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            field.setAccessible(true)
            val drawableResId: Int = field.getInt(view)

            // Get the editor
            field = TextView::class.java.getDeclaredField("mEditor")
            field.isAccessible = true
            val editor: Any = field.get(view)

            // Get the drawable and set a color filter
            val drawable: Drawable? = AppCompatResources.getDrawable(view.context, drawableResId)
            drawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            val drawables = arrayOf(drawable, drawable)

            // Set the drawables
            field = editor.javaClass.getDeclaredField("mCursorDrawable")
            field.setAccessible(true)
            field.set(editor, drawables)
        } catch (ignored: java.lang.Exception) {
        }
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(
        spinner: AppCompatSpinner,
        items: ArrayList<ChoiceItem>
    ) {
        Timber.d("items ${items.size}")
        if (spinner.adapter is DropDownItemsAdapter)
            with(spinner.adapter as DropDownItemsAdapter) {
                listItemsTxt = items
                (spinner.adapter as DropDownItemsAdapter).notifyDataSetChanged()

            }

    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun setFormItems(recyclerView: RecyclerView, resource: ArrayList<HashMap<Int, Form>>?) {
        if (recyclerView.adapter is SortedLessonListAdapter)
            with(recyclerView.adapter as SortedLessonListAdapter) {
                resource?.let {
                    collection = it
                }
            }
    }

    @BindingAdapter("app:items")
    @JvmStatic
    fun setFieldItems(recyclerView: RecyclerView, resource: Form?) {
        if (recyclerView.adapter is LessonFieldsAdapter)
            with(recyclerView.adapter as LessonFieldsAdapter) {
                resource?.fields_list?.let {
                    it.add(
                        0,
                        Fields(
                            "form_title_logo",
                            resource.title,
                            resource.description,
                            resource.logo,
                            "meta",
                            "section"
                        )
                    )
                    collection = it
                }
            }
    }

    @ColorInt
    fun darkenColor(@ColorInt color: Int): Int {
        return Color.HSVToColor(FloatArray(3).apply {
            Color.colorToHSV(color, this)
            this[2] *= 0.8f
        })
    }


    fun getHexHashtagColorFromRgbStr(color: String?): String? {
        return try {
            when {
                color != null -> {
                    val rgbToInt = getIntColorFromRgbStr(color)
                    when {
                        rgbToInt != null -> {
                            return convertIntColorToHashtagHex(rgbToInt)
                        }

                        else -> {
                            null
                        }
                    }
                }

                else -> {
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("Binding.TAG", "getHexColor: $e")
            null
        }

    }

    fun getIntColorFromRgbStr(color_: String?): Int? {
        return try {
            var color = color_
            if (color != null) {
                color = color.replace("{\"", "")
                color = color.replace("\"", "")

                val a = color.substring(color.indexOf("a:") + 2, color.indexOf("}"))
                val r = color.substring(color.indexOf("r:") + 2, color.indexOf(",g"))
                val g = color.substring(color.indexOf("g:") + 2, color.indexOf(",b"))
                val b = color.substring(color.indexOf("b:") + 2, color.indexOf(",a"))

                return convertRgbToInt(r, g, b)


            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("Binding.TAG", "getHexColor: $e")
            null
        }

    }

    fun convertIntColorToHex(color: Int): String {
        return Integer.toHexString(color)
    }

    fun convertIntColorToHashtagHex(color: Int): String {
        return "#${convertIntColorToHex(color)}"
    }

    fun convertRgbToInt(r: String, g: String, b: String): Int {
        return Color.rgb(Integer.parseInt(r), Integer.parseInt(g), Integer.parseInt(b))
    }

    fun getColorStateList(color: String?): ColorStateList? {
        getHexHashtagColorFromRgbStr(color)?.let {
            return ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_enabled),
                    intArrayOf(android.R.attr.state_enabled)
                ), intArrayOf(
                    Color.parseColor(it) //disabled
                    , Color.parseColor(it) //enabled
                )
            )

        }
        return null
    }

}