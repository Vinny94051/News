package ru.kiryanav.news.koin

import org.koin.dsl.module
import ru.kiryanav.news.data.mapper.MapperEverythingRequest

val mapperModule = module {
    single {
        MapperEverythingRequest(get())
    }
}