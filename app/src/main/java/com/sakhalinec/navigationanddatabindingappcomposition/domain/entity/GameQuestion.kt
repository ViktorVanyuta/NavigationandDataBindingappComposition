package com.sakhalinec.navigationanddatabindingappcomposition.domain.entity

data class GameQuestion (
    // значение суммы
    val sum: Int,
    // видимое число
    val visibleNumber: Int,
    // варианты ответов
    val options: List<Int>
){

    // значение правильного ответа
    val rightAnswer: Int
        get() = sum - visibleNumber

}
