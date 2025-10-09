package com.hdy.compose_examples.ui.reply

import com.hdy.compose_examples.data.model.Email
import com.hdy.compose_examples.data.model.MailboxType
import com.hdy.compose_examples.data.local.staticdata.LocalEmailsDataProvider

data class ReplyUiState(
    val mailboxes: Map<MailboxType, List<Email>> = emptyMap(),
    val currentMailbox: MailboxType = MailboxType.Inbox,
    val currentSelectedEmail: Email = LocalEmailsDataProvider.defaultEmail,
    val isShowingHomepage: Boolean = true
) {
    // 根据收件箱、已发送、草稿、回收站获取对应的邮件列表
    val currentMailboxEmails: List<Email> by lazy { mailboxes[currentMailbox]!! }
}
