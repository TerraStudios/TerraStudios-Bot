package com.terrastudios.tsbot.core.commands.annotations

import net.dv8tion.jda.api.Permission

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DiscordCommand(
    val commandName : String,
    val description : String,
    val usage : String,
    val aliases : Array<String> = [],
    val permission : Permission = Permission.UNKNOWN,
    val minArgs : Int = -1,
    val maxArgs : Int = -1)