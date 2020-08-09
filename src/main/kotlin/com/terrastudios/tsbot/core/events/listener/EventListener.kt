package com.terrastudios.tsbot.core.events.listener

import com.terrastudios.tsbot.TSBot
import com.terrastudios.tsbot.core.data.ClassStorage
import com.terrastudios.tsbot.core.events.MessageEvent
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.hooks.SubscribeEvent
import java.util.*
import kotlin.collections.HashSet


class EventListener : ListenerAdapter() {

    val listeners = HashMap<Class<out GenericEvent>, MutableSet<AwaitEvent<GenericEvent>>>()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> listen(noinline condition: (T) -> Boolean, noinline action: (T) -> Unit) where T : GenericEvent {
        try {
            val clazz = T::class.java

            println("class is ${clazz.name}")

            listeners.computeIfAbsent(clazz) { HashSet() }

            val set = listeners[clazz]!!
            val awaitEvent = AwaitEvent(condition, action)

            awaitEvent.tempClazz = ClassStorage(awaitEvent.javaClass)

            set.add(awaitEvent as AwaitEvent<GenericEvent>)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }


    @SubscribeEvent
    override fun onGenericEvent(event: GenericEvent) {

        try {
            var foundEvent = event //set to var for access

            if (event is MessageReceivedEvent) {
                println("it is indeed")
                foundEvent = MessageEvent(this, event)
            }

            if (listeners.containsKey(foundEvent::class.java)) {
                println(1)
                val iterator = listeners[foundEvent::class.java]!!.iterator()
                for (listener in iterator) {
                    val subListener = getT(listener, listener.tempClazz.clazz)

                    if (subListener.test(foundEvent)) {
                        listeners[foundEvent::class.java]!!.remove(listener)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun <T> getT(
        listener: AwaitEvent<GenericEvent>,
        clazz: Class<AwaitEvent<T>>
    ): AwaitEvent<T> where T : GenericEvent {
        return clazz.cast(listener)
    }


}