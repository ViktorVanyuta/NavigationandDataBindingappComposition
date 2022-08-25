package com.sakhalinec.navigationanddatabindingappcomposition.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sakhalinec.navigationanddatabindingappcomposition.R
import com.sakhalinec.navigationanddatabindingappcomposition.databinding.FragmentGameBinding
import com.sakhalinec.navigationanddatabindingappcomposition.domain.entity.GameLevel
import com.sakhalinec.navigationanddatabindingappcomposition.domain.entity.GameResult
import com.sakhalinec.navigationanddatabindingappcomposition.ui.viewModelFactory.GameViewModelFactory


import java.lang.RuntimeException

class GameFragment : Fragment() {

    // получение аргументов из фрагмента ChooseLevelFragment которые передаются
    // в функции launchGameFragment
    // второй способ получения аргументов через ленивую инициализацию передав в navArgs объект GameFragmentArgs
    private val args by navArgs<GameFragmentArgs>()
    private val viewModelFactory by lazy {
        // первый способ получения аргументов эта строка аналог кода выше
        // будет создан объект GameFragmentArgs вызвав метод fromBundle и передав requireArguments
        // можно получить аргументы из ChooseLevelFragment
        // val args = GameFragmentArgs.fromBundle(requireArguments())
        GameViewModelFactory(gameLevel = args.gameLevel, application = requireActivity().application)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    /* использую ViewBinding для того чтобы не вызывать постоянно findViewById, создается класс
    binding который автоматически генерируется и хранит ссылки на все id элементов view.  */
    // для избежания проблем, _binding делается нулабельным на тот случай если обратиться к данной
    // переменной до метода onCreateView или после onDestroyView то поймаю исключение
    private var _binding: FragmentGameBinding? = null

    // чтобы не создавать постоянные проверки на null, создается не нулабельная переменная binding
    // у которой переопределяется геттер
    private val binding: FragmentGameBinding
        // геттер возвращает _binding если оно не равно null, а если оно равно null то кидает исключение
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // у сгенерированного класса вызывается метод inflate с параметрами и в onCreateView
        // возвращается root из объекта binding
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // для того чтобы можно было реагировать на изменения в объектах liveData и вовремя
        // отписываться от них при перевороте экрана или смены конфига то, необходимо в переменную
        // binding установить viewModel, а так же viewLifecycleOwner. После этих установок все
        // изменения будут автоматически прилетать в объект binding и будут установлены правильные значения
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()

    }

    // подписка на вьюшки
    private fun observeViewModel() {

        // результат игры
        viewModel.gameResult.observe(viewLifecycleOwner){
            launchGameFinishedFragment(it)
        }

    }

    // функция перехода на фрагмент GameFinishedFragment и предаем в него объект GameResult
    private fun launchGameFinishedFragment(gameResult: GameResult) {
        // при запуске фрагмента в файле навигации в fragment_game_finished были переданы аргументы
        // в разделе arguments то-есть был передан parcelable class GameFragment от куда и прилетают
        // аргументы GameResult для запускаемого фрагмента GameFinishedFragment, таким образом
        // автоматически были сгенерированы объекты action
        findNavController().navigate(
            // GameFragmentDirections тут хранятся все направления куда можно перейти
            // из фрагмента GameFragment, в экшене указывается обязательный параметр gameResult
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // присваиваю null объекту _binding для того чтоб нельзя было к нему обратиться после
        // этого метода по цепочке жизненного цикла
        _binding = null
    }

}