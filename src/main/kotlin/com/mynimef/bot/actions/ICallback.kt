package com.mynimef.bot.actions

import com.mynimef.bot.IBot


interface ICallback {

    fun callback(
        chatId: String?,
        messageId: Int?,
        message: String?,
        username: String?,
        firstName: String?,
        lastName: String?,
        bot: IBot?
    )

}
