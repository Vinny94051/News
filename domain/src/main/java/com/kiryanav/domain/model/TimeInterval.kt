package com.kiryanav.domain.model


enum class TimeInterval(val timeInMnts: Long) {
    MINUTES_15(15),
    MINUTES_30(30),
    HOURS_1(60),
    HOURS_2(120),
    HOURS_4(240),
    HOURS_8(480),
    ONE_DAY(1440);

    companion object {
        fun defineInterval(interval: Long): TimeInterval? =
            when (interval) {
                15L -> MINUTES_15
                30L -> MINUTES_30
                60L -> HOURS_1
                120L -> HOURS_2
                240L -> HOURS_4
                480L -> HOURS_8
                1440L -> ONE_DAY
                else -> null
            }
    }
}