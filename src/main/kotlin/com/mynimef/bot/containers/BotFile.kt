package com.mynimef.bot.containers

import java.io.File


/**
 * File that can be attached to a message
 */
data class BotFile(

    /**
     * File to attach to message
     */
    val file: File,

    /**
     * Description of a file
     */
    val description: String? = null

)
