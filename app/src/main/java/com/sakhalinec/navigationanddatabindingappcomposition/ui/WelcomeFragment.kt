package com.sakhalinec.navigationanddatabindingappcomposition.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sakhalinec.navigationanddatabindingappcomposition.R
import com.sakhalinec.navigationanddatabindingappcomposition.databinding.FragmentWelcomeBinding

import java.lang.RuntimeException

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException( "FragmentWelcomeBinding == null" )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonUnderstand.setOnClickListener {
            launchChooseLevelFragment()
        }

    }

    // функция перехода на фрагмент ChooseLevelFragment
    private fun launchChooseLevelFragment() {
        // получаю ссылку на контроллер навигации и в параметрах передаю экшен для перехода на вью
        //findNavController().navigate(R.id. action_welcomeFragment_to_chooseLevelFragment)

        findNavController().navigate(
            // WelcomeFragmentDirections тут хранятся все направления куда можно перейти
            // из фрагмента WelcomeFragment, в экшене указывается обязательный параметр если он есть!!!
            WelcomeFragmentDirections.actionWelcomeFragmentToChooseLevelFragment()
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}