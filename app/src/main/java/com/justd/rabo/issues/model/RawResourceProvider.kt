package com.justd.rabo.issues.model

import android.content.Context
import android.support.annotation.RawRes

class RawResourceProvider(private val context: Context) {

    fun get(@RawRes resId: Int): String {
        val inputStream = context.resources.openRawResource(resId)
        return inputStream.bufferedReader().use { it.readText() }
    }

}