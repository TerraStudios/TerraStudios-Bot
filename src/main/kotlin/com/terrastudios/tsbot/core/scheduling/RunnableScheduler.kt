package com.terrastudios.tsbot.core.scheduling

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

class RunnableScheduler private constructor(
    private val consumer: Runnable,
    private val delay: Int,
    private val delayUnit: TimeUnit,
    private val period: Int,
    private val periodUnit: TimeUnit
) {

    fun run() {
        val executor = Executors.newScheduledThreadPool(1)

        executor.scheduleAtFixedRate(
            consumer,
            delayUnit.convert(delay.toLong(), TimeUnit.SECONDS),
            periodUnit.convert(period.toLong(), TimeUnit.SECONDS),
            TimeUnit.SECONDS
        )
    }

    companion object {
        class Builder(private val consumer: Runnable) {

            private var delay: Int = 0
            private var delayUnit: TimeUnit = TimeUnit.SECONDS
            private var period: Int = 0
            private var periodUnit: TimeUnit = TimeUnit.SECONDS

            fun every(period: Int, unit: TimeUnit) {
                this.period = period
                this.periodUnit = unit
            }

            fun after(delay: Int, unit: TimeUnit) {
                this.delay = delay
                this.delayUnit = unit
            }

            fun build(): RunnableScheduler {
                return RunnableScheduler(consumer, delay, delayUnit, period, periodUnit)


            }


        }
    }

    fun start() {

    }

}