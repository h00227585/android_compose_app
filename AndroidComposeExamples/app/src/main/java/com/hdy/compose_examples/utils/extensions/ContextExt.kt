package com.hdy.compose_examples.utils.extensions

import android.content.Context
import android.net.Uri
import androidx.annotation.AnyRes
import com.hdy.compose_examples.utils.UriUtil

fun Context.resourceUri(@AnyRes resId: Int): Uri =
    UriUtil.fromResource(this, resId)