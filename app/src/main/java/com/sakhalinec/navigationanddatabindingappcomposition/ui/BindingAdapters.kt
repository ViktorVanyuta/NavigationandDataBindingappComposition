package com.sakhalinec.navigationanddatabindingappcomposition.ui

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.sakhalinec.navigationanddatabindingappcomposition.R
import com.sakhalinec.navigationanddatabindingappcomposition.domain.entity.GameResult


// в кавычках аннотации указано имя атрибута который будет использован в макете
@BindingAdapter("requiredAnswers")
// первым параметром передается то к чему применяется атрибут, в данном случае к TextView
// вторым параметром указывается значение которое будет использоваться, то есть Int
fun bindRequiredAnswers(textView: TextView, count: Int) {
    // у переданного TextView устанавливается text, который получаем из строковых ресурсов и
    // форматируем при помощи String.format
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("score")
fun bindScore(textView: TextView, score: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        score
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, percentage: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        percentage
    )
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPercentOfRightAnswers(gameResult = gameResult)
    )
}
// возвращает процент правильных ответов
private fun getPercentOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    // если количество вопросов равно 0 то вернуть 0
    if (countOfQuestions == 0) {
        0
    } else {
        // делим количество правильных ответов на количество вопросов и умножаем на 100
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

@BindingAdapter("resultEmoji")
fun bindResultEmoji(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getSmileResId(winner = winner))
}
// простая проверка, если победили то картинка с веселым смайликом, если нет то грыстный
private fun getSmileResId(winner: Boolean): Int {
    return if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, enough: Boolean) {
    textView.setTextColor(getColorByState(textView.context, enough))
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, enough: Boolean) {
    val color = getColorByState(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

// состояние цвета
private fun getColorByState(context: Context, goodState: Boolean): Int {
    // если правильных ответов достаточное количество то, установим зеленый цвет иначе красный
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

/* ВАЖНО !!! ОШИБКА ВЫЗОВА МЕТОДА
(((cannot find method chooseAnswer(java.lang.Object)
in class com.sakhalinec.navigationanddatabindingappcomposition.ui.GameViewModel)))
данная ошибка потому что в BindingAdapter нельзя использовать лямбда выражения, при компиляции указанный
тип clickListener: (Int) -> превращается в object и компилятор пытается вызвать метод который
принимает в качестве параметра object, а такого метода нету! вместо этого нужно использовать
интерфейсы с одним методом!!!

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: (Int) -> Unit) {
    textView.setOnClickListener {
        clickListener(textView.text.toString().toInt())
    }
}
*/

interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}