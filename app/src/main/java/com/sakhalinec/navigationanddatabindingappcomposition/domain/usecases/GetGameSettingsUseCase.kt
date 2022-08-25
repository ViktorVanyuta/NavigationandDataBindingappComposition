package com.sakhalinec.navigationanddatabindingappcomposition.domain.usecases

import com.sakhalinec.navigationanddatabindingappcomposition.domain.entity.GameLevel
import com.sakhalinec.navigationanddatabindingappcomposition.domain.entity.GameSettings
import com.sakhalinec.navigationanddatabindingappcomposition.domain.repository.GameRepository


class GetGameSettingsUseCase(
    // передаем репозиторий в качестве параметра конструктору
    private val repository: GameRepository
) {

    // принимает уровень игры и возвращает настройки игры
    operator fun invoke(gameLevel: GameLevel): GameSettings {
        return repository.getGameSettings(gameLevel = gameLevel)
    }

}