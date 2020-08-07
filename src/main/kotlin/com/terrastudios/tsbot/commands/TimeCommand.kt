package com.terrastudios.tsbot.commands

import com.terrastudios.tsbot.core.commands.annotations.DiscordCommand
import com.terrastudios.tsbot.core.events.CommandEvent
import com.terrastudios.tsbot.core.util.MessageType
import com.terrastudios.tsbot.core.util.ResourceUtils
import org.json.simple.JSONObject
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.zone.ZoneRulesException
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class TimeCommand {

    private val data = HashMap<String, ZoneId>()

    init {

        val parsedObject = ResourceUtils.getJSONObject("/timezones.json")

        for (element in parsedObject) {
            data[element.key as String] = ZoneId.of(element.value as String)
        }

        TimeZone.setDefault(null)

        //start autosave timer
        val executor = Executors.newScheduledThreadPool(1)
        executor.scheduleAtFixedRate({
            val jsonObject = JSONObject()
            for (entry in data) {
                jsonObject[entry.key] = entry.value.id
            }

            val writer = FileWriter(ResourceUtils.getFilePath("/timezones.json"))
            writer.write(jsonObject.toJSONString())
            writer.close()

        }, 10, 10, TimeUnit.SECONDS) //autosave every 30 minutes

    }

    @DiscordCommand(
        commandName = "addtime",
        description = "Adds a user timezone (supports abbreviations and worldapi).",
        usage = "addtime <name> <timezone>",
        minArgs = 2,
        maxArgs = 2
    )
    fun addTime(command: CommandEvent) {
        try {
            val timezone = ZoneId.of(command.args[1])

            println("setting ${command.args[0]}")
            data[command.args[0].toLowerCase()] = timezone
            command.reply(
                MessageType.SUCCESS,
                "Success",
                "Added timezone ${timezone.id} to ${command.args[0]}."

            )
        } catch (e: Exception) {
            command.reply(
                MessageType.ERROR,
                "Invalid TimeZone",
                "Please specify a correct timezone."

            )
        }


    }

    @DiscordCommand(
        commandName = "time",
        description = "Displays the time of a user in military time.",
        usage = "time <name>",
        minArgs = 1,
        maxArgs = 1
    )
    fun time(command: CommandEvent) {
        if (data.containsKey(command.args[0].toLowerCase())) {
            try {
                val cal = ZonedDateTime.now(data[command.args[0].toLowerCase()])
                val format = DateTimeFormatter.ofPattern("HH:mm")
                command.reply(
                    MessageType.INFO, "Time for ${command.args[0]}",
                    cal.format(format).toString()

                )
            } catch (e : Exception) {
                e.printStackTrace()
            }
        } else {
            command.reply(
                MessageType.ERROR,
                "Invalid User",
                "That user doesn't have a timezone assigned!"

            )
        }
    }

}