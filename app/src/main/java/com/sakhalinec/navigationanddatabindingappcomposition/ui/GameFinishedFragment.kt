package com.sakhalinec.navigationanddatabindingappcomposition.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sakhalinec.navigationanddatabindingappcomposition.R
import com.sakhalinec.navigationanddatabindingappcomposition.databinding.FragmentGameFinishedBinding
import com.sakhalinec.navigationanddatabindingappcomposition.domain.entity.GameResult

import java.lang.RuntimeException

class GameFinishedFragment : Fragment() {

    // получение аргументов из фрагмента GameFragment которые передаются в launchGameFinishedFragment
    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        bindViews()

    }

    // слушатель клика на кнопку назад
    private fun setupClickListeners() {
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    // биндим все необходимые значения во вьюшки
    private fun bindViews() {
        binding.gameResult = args.gameResult
    }

    // функция возврата к экрану выбора уровня
    private fun retryGame() {
        // за стек навигации отвечает навКонтролер в файле навигации были установлены два атрибута
        // для необходимой навигации между экранами app:popUpTo="@id/gameFragment" app:popUpToInclusive="true"
        // эти атрибуты можно переопределить в разделе Pop Behavior вкладки дизаин или написать их ручками:))
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}