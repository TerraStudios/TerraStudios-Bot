package com.terrastudios.tsbot.core.events.listener

import com.terrastudios.tsbot.core.data.ClassStorage
import net.dv8tion.jda.api.events.GenericEvent

class AwaitEvent<T : GenericEvent>(
    val condition: (T) -> Boolean,
    val action: (T) -> Unit
) {


    lateinit var tempClazz: ClassStorage<T>

    fun test(event: T): Boolean {
        if (condition(event)) {
            action(event)
            return true
        }

        return false
    }
}
