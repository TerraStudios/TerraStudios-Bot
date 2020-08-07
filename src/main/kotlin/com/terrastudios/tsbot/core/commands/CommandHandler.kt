package com.terrastudios.tsbot.core.commands

import com.terrastudios.tsbot.TSBot
import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.data.CustomCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.events.extensions.reply
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.extensions.EmbedFactory
import net.dv8tion.jda.api.EmbedBuilder
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
    val commandMap = HashMap<String, Method>()

    //Caches objects per class to keep member variables
    private val cachedClasses = HashMap<Class<*>, Any>()

    val customCommands = HashMap<String, CustomCommand>()

    init {
        TSBot.commandHandler = this
        println("Starting annotation scanning..")
        val reflections = Reflections(
            ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(MethodAnnotationsScanner())
        )

        val methods = reflections.getMethodsAnnotatedWith(DiscordCommand::class.java)
        println("Done! Found ${methods.size} DiscordCommand annotations.")

        methods.forEach {

            val annotation = it.getAnnotation(DiscordCommand::class.java)

            commandMap[annotation.commandName] = it

            if (!cachedClasses.containsKey(it.declaringClass)) {
                cachedClasses[it.declaringClass] = it.declaringClass.newInstance()
            }

            annotation.aliases.forEach { alias -> commandMap[alias] = it }
        }
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return

        if (event.message.contentRaw.startsWith(TSBot.config.prefix)) {

            val command = event.message.contentRaw.substring(1).split(" ")[0]
            val args: Array<String> =
                event.message.contentRaw.split(" ").stream().skip(1).toArray { size -> arrayOfNulls<String>(size) }

            if (commandMap.containsKey(command)) {
                val anno = commandMap[command]!!.getAnnotation(DiscordCommand::class.java)
                val perm = anno.permission
                val minArgs = commandMap[command]!!.getAnnotation(DiscordCommand::class.java).minArgs
                val maxArgs = commandMap[command]!!.getAnnotation(DiscordCommand::class.java).maxArgs

                if (perm == Permission.UNKNOWN || event.member!!.hasPermission(perm)) {
                    if (minArgs == -1 || args.size >= minArgs) {
                        if (maxArgs == -1 || args.size <= maxArgs) {

                            val clazz = commandMap[command]!!.declaringClass

                            if (!cachedClasses.containsKey(clazz)) {
                                cachedClasses[clazz] = clazz.newInstance()
                            }

                            val instance = cachedClasses[clazz]
                            commandMap[command]!!.invoke(instance, CommandEvent(event, args))
                        } else {
                            event.reply(EmbedFactory.getEmbed(MessageType.ERROR, "Too many arguments", "Usage: ${TSBot.config.prefix}${anno.usage}"))
                        }
                    } else {
                        event.reply(EmbedFactory.getEmbed(MessageType.ERROR, "Too few arguments", "Usage: ${TSBot.config.prefix}${anno.usage}"))
                    }
                } else {
                    event.reply(EmbedFactory.getEmbed(MessageType.ERROR, "Invalid Permissions", "You don't have permission to execute that command!"))
                }
            } else {
                if (customCommands.containsKey(command)) {
                    try {
                        val customCommand = customCommands[command]!!
                        val embedBuilder = EmbedBuilder()
                            .setTitle(if (customCommand.title == "") " " else customCommand.title)
                            .setColor(customCommand.color.getColor())
                        if (customCommand.text != "") embedBuilder.setDescription(customCommand.text)
                        if (customCommand.imageURL != "") embedBuilder.setImage(customCommand.imageURL)

                        event.reply(embedBuilder.build())
                    } catch (e : Exception) {
                        e.printStackTrace()
                    }
                } else {
                    event.reply(EmbedFactory.getEmbed(MessageType.ERROR, "Unknown command", "Type `${TSBot.config.prefix}commands` for a list of commands."))
                }
            }
        }
    }


}