package com.chocomiruku.core.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.formatCreationDate(): String {
    return SimpleDateFormat("MM-dd-yyyy")
        .format(this).toString()
}