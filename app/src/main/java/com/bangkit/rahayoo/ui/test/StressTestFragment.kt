package com.bangkit.rahayoo.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.rahayoo.R
import com.bangkit.rahayoo.data.model.StressTestQuestions
import com.bangkit.rahayoo.databinding.FragmentStressTestBinding
import com.bangkit.rahayoo.util.toEmoteValue
import com.bangkit.rahayoo.util.toProgressValue
import com.bangkit.rahayoo.util.toScaleValue
import com.robinhood.ticker.TickerUtils

class StressTestFragment : Fragment(), View.OnClickListener {

    companion object {
        private const val TAG = "StressTestFragment"
    }

    private var _binding: FragmentStressTestBinding? = null
    private val binding get() = _binding!!

    private var questionCounter = 0

    private val questions = StressTestQuestions.getAllQuestion()
    private var currentQuestion = questions[questionCounter]

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStressTestBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.slider.addOnChangeListener { _, value, _ ->
            binding.cpiSlider.progress = value.toProgressValue()
            binding.tvLabelAnswer.text = value.toScaleValue().toString()
            binding.tvAnswerEmote.text = value.toEmoteValue(currentQuestion.questionType)
        }

        binding.btnStressTest.setOnClickListener(this)
        binding.btnQuestionNavigate.setOnClickListener(this)

        binding.tvLabelAnswer.setCharacterLists(TickerUtils.provideNumberList())
        binding.tvStressLevelHeadline.setCharacterLists(TickerUtils.provideAlphabeticalList())
    }

    private fun setupQuestion() {
        binding.tvStressLevelIntroTitle.visibility = View.GONE
        binding.tvStressLevelIntroSubtitle.visibility = View.GONE
        binding.btnStressTest.visibility = View.GONE
        binding.cvQuestionContainer.visibility = View.VISIBLE
        binding.btnQuestionNavigate.visibility = View.VISIBLE
        binding.tvQuestionHeader.visibility = View.VISIBLE

        binding.lpiQuestion.progress = 10
        binding.tvStressLevelHeadline.text = getString(R.string.question_counter_headline, questionCounter + 1)
        binding.tvQuestion.text = currentQuestion.question
    }

    private fun nextQuestion() {
        currentQuestion = questions[++questionCounter]

        resetSlider()

        binding.lpiQuestion.incrementProgressBy(10)
        binding.tvStressLevelHeadline.text = getString(R.string.question_counter_headline, questionCounter + 1)
        binding.tvQuestion.text = currentQuestion.question
    }

    private fun resetSlider() {
        binding.slider.value = 0F
        binding.cpiSlider.progress = 0
        binding.tvAnswerEmote.text = 0F.toEmoteValue(currentQuestion.questionType)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_stress_test -> {
                setupQuestion()
            }
            R.id.btn_question_navigate -> {
                nextQuestion()
            }
        }
    }
}