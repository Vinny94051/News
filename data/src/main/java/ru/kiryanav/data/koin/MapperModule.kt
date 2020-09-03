package ru.kiryanav.data.koin

import org.koin.dsl.module
import ru.kiryanav.data.mapper.MapperNewsRequest

val mapperModule = module {
    single {
        MapperNewsRequest()
    }
}