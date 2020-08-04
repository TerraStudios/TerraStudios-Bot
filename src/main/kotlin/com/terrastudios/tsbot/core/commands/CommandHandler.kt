package com.terrastudios.tsbot.core.commands

import com.terrastudios.tsbot.TSBot
import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.extensions.reply
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import java.lang.reflect.Method

import java.util.*

class CommandHandler : ListenerAdapter() {
    private val commandMap = HashMap<String, Method>()

    init {
        println("Starting annotation scanning..")
        val reflections = Reflections(ConfigurationBuilder()
            .setUrls(ClasspathHelper.forJavaClassPath())
            .setScanners(MethodAnnotationsScanner()))

        val methods = reflections.getMethodsAnnotatedWith(DiscordCommand::class.java)
        println("Done! Found ${methods.size} DiscordCommand annotations.")

        methods.forEach {

            val annotation = it.getAnnotation(DiscordCommand::class.java)

            commandMap[annotation.commandName] = it
        }
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return

        if (event.message.contentRaw.startsWith(TSBot.config.prefix)) {

            val command = event.message.contentRaw.replace("+", "").split(" ")[0]

            if (commandMap.containsKey(command)) {
                val perm = commandMap[command]!!.getAnnotation(DiscordCommand::class.java).permission

                if (perm == Permission.UNKNOWN || event.member!!.hasPermission(perm)) {
                    val instance = commandMap[command]!!.declaringClass.newInstance()
                    commandMap[command]!!.invoke(instance, event)
                } else {
                    event.reply("You don't have permission to execute that command!")
                }
            }
        }
    }


}