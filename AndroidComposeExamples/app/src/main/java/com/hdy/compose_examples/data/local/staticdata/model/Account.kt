package com.hdy.compose_examples.data.local.staticdata.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Account(
    /** Unique ID of a user **/
    val id: Long,
    /** User's first name **/
    @field:StringRes val firstName: Int,
    /** User's last name **/
    @field:StringRes val lastName: Int,
    /** User's email address **/
    @field:StringRes val email: Int,
    /** User's avatar image resource id **/
    @field:DrawableRes val avatar: Int
)