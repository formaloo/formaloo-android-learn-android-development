package co.idearun.feature.lesson.adapter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import co.idearun.feature.R

enum class CSATFunny(@StringRes val str: Int, @DrawableRes val drawable: Int) {
    ANGRY(R.string.angry, android.R.drawable.star_big_on),
    SAD(R.string.sad, android.R.drawable.star_big_on),
    NEUTRAL(R.string.neutral, android.R.drawable.star_big_on),
    SMILE(R.string.smile, android.R.drawable.star_big_on),
    LOVE(R.string.love, android.R.drawable.star_big_on)
}

enum class CSATFlat(@StringRes val str: Int, @DrawableRes val drawable: Int) {
    ANGRY(R.string.angry, android.R.drawable.star_big_on),
    SAD(R.string.sad, android.R.drawable.star_big_on),
    NEUTRAL(R.string.neutral, android.R.drawable.star_big_on),
    SMILE(R.string.smile, android.R.drawable.star_big_on),
    LOVE(R.string.love, android.R.drawable.star_big_on)
}

enum class CSATMonster(@StringRes val str: Int, @DrawableRes val drawable: Int) {
    ANGRY(R.string.angry, android.R.drawable.star_big_on),
    SAD(R.string.sad, android.R.drawable.star_big_on),
    NEUTRAL(R.string.neutral, android.R.drawable.star_big_on),
    SMILE(R.string.smile, android.R.drawable.star_big_on),
    LOVE(R.string.love, android.R.drawable.star_big_on)
}

enum class CSATOutline(@StringRes val str: Int, @DrawableRes val drawable: Int) {
    ANGRY(R.string.angry, android.R.drawable.star_big_on),
    SAD(R.string.sad, android.R.drawable.star_big_on),
    NEUTRAL(R.string.neutral, android.R.drawable.star_big_on),
    SMILE(R.string.smile, android.R.drawable.star_big_on),
    LOVE(R.string.love,android.R.drawable.star_big_on)
}

enum class CSATHEART(@StringRes val str: Int, @DrawableRes val drawable: Int) {
    ANGRY(R.string.empty_txt, android.R.drawable.star_big_on),
    SAD(R.string.empty_txt, android.R.drawable.star_big_on),
    NEUTRAL(R.string.empty_txt, android.R.drawable.star_big_on),
    SMILE(R.string.empty_txt, android.R.drawable.star_big_on),
    LOVE(R.string.empty_txt,android.R.drawable.star_big_on)
}

enum class CSATSTAR(@StringRes val str: Int, @DrawableRes val drawable: Int) {
    ANGRY(R.string.empty_txt,android.R.drawable.star_big_on),
    SAD(R.string.empty_txt, android.R.drawable.star_big_on),
    NEUTRAL(R.string.empty_txt, android.R.drawable.star_big_on),
    SMILE(R.string.empty_txt,android.R.drawable.star_big_on),
    LOVE(R.string.empty_txt,android.R.drawable.star_big_on)
}
