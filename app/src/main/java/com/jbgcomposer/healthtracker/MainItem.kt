package com.jbgcomposer.healthtracker

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MainItem(
    val id: Int,
    @DrawableRes val drawableRes: Int,
    @StringRes val textStringId: Int
)