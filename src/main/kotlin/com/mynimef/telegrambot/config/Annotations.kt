package com.mynimef.telegrambot.config


/**
 * Annotation for functions to define the action on the specified command
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BotCommand(vararg val commands: String)

/**
 * Annotation for functions to define the callback from pressing inline buttons
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BotCallback(val callback: String)