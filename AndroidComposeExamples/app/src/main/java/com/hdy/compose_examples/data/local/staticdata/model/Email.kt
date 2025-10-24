package com.hdy.compose_examples.data.local.staticdata.model

import androidx.annotation.StringRes

data class Email(
    /** Unique ID of the email **/
    val id: Long,
    /** Sender of the email **/
    val sender: Account,
    /** Recipient(s) of the email **/
    val recipients: List<Account> = emptyList(),
    /** Title of the email **/
    @field:StringRes val subject: Int = -1,
    /** Content of the email **/
    @field:StringRes val body: Int = -1,
    /** Which mailbox it is in **/
    var mailbox: MailboxType = MailboxType.Inbox,
    /**
     * Relative duration in which it was created. (e.g. 20 mins ago)
     * It should be calculated from relative time in the future.
     * For now it's hard coded to a [String] value.
     */
    @field:StringRes var createdAt: Int = -1
)