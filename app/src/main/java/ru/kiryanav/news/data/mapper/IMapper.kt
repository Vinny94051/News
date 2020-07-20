package ru.kiryanav.news.data.mapper

/**
 * Main interface for implementation of "Mapper" classes
 */
interface IMapper<I, O> {

    /**
     * Map data-level model into domain model
     */
    fun mapFromEntity(input: I): O
}