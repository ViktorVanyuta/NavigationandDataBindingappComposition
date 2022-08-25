package com.sakhalinec.navigationanddatabindingappcomposition.domain.repository

import com.sakhalinec.navigationanddatabindingappcomposition.domain.entity.GameLevel
import com.sakhalinec.navigationanddatabindingappcomposition.domain.entity.GameQuestion
import com.sakhalinec.navigationanddatabindingappcomposition.domain.entity.GameSettings


interface GameRepository {

    fun generateQuestion(
        // максимальное значение которое будет сгенерированно в поле сумма
        maxSumValue: Int,
        // сколько нужно генерировать вариантов ответов
        countOfOptions: Int
    ): GameQuestion

    // примает уровень игры и возвращает настройки под выбранный уровень
    fun getGameSettings(gameLevel: GameLevel): GameSettings

}