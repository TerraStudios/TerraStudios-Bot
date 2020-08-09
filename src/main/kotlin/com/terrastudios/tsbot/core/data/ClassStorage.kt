package com.terrastudios.tsbot.core.data

import com.terrastudios.tsbot.core.events.listener.AwaitEvent
import net.dv8tion.jda.api.events.GenericEvent

data class ClassStorage<T>(val clazz : Class<AwaitEvent<T>>) where T : GenericEvent