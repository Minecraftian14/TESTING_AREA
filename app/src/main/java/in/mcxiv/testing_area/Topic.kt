package `in`.mcxiv.testing_area

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(@StringRes val strId: Int, val smtg: Int, @DrawableRes val imgId: Int)
