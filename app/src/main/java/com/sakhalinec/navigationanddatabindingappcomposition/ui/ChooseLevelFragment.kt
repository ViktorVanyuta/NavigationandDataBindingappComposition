package com.sakhalinec.navigationanddatabindingappcomposition.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sakhalinec.navigationanddatabindingappcomposition.R
import com.sakhalinec.navigationanddatabindingappcomposition.databinding.FragmentChooseLevelBinding
import com.sakhalinec.navigationanddatabindingappcomposition.domain.entity.GameLevel

import java.lang.RuntimeException

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnLevelTest.setOnClickListener {
                launchGameFragment(level = GameLevel.TEST)
            }
            btnLevelEasy.setOnClickListener {
                launchGameFragment(level = GameLevel.EASY)
            }
            btnLevelNormal.setOnClickListener {
                launchGameFragment(level = GameLevel.NORMAL)
            }
            btnLevelHard.setOnClickListener {
                launchGameFragment(level = GameLevel.HARD)
            }
        }
    }

    // функция запуска игры, которая примает левел игры в параметрах
    private fun launchGameFragment(level: GameLevel) {
        // при запуске фрагмента в файле навигации в fragment_game были переданы аргументы
        // в разделе arguments то-есть был передан enum class от куда и прилетают аргументы GameLevel
        // для запускаемого фрагмента GameFragment, таким образом автоматически были сгенерированы
        // объекты action
        findNavController().navigate(
            // ChooseLevelFragmentDirections тут хранятся все направления куда можно перейти
            // из фрагмента ChooseLevelFragment, в экшене указывается обязательный параметр! если он есть
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}