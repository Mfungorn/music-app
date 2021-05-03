package com.fungorn.musicapp.data.mapper

interface Mapper<T, E> {
    fun map(t: T): E
}
