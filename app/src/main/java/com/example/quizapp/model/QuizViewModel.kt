// QuizViewModel.kt
package com.example.quizapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.Question
import com.example.quizapp.data.QuizRepository
import com.example.quizapp.data.SQLiteQuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Change to AndroidViewModel to access application context
class QuizViewModel(application: Application) : AndroidViewModel(application) {
    // Use SQLite repository with application context
    private val repository: QuizRepository = SQLiteQuizRepository(application)

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> = _currentQuestion

    private val _totalQuestions = MutableStateFlow(0)
    val totalQuestions: StateFlow<Int> = _totalQuestions

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Keep track of user answers
    private val userAnswers = mutableListOf<Int>()

    init {
        loadQuestions()
    }

    fun loadQuestions() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val quizQuestions = repository.getQuestions()
                _questions.value = quizQuestions
                _totalQuestions.value = quizQuestions.size

                if (quizQuestions.isNotEmpty()) {
                    _currentQuestion.value = quizQuestions[0]
                }
            } catch (e: Exception) {
                // Handle error
                println("Error loading questions: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun moveToNextQuestion() {
        val nextIndex = _currentQuestionIndex.value + 1
        if (nextIndex < _questions.value.size) {
            _currentQuestionIndex.value = nextIndex
            _currentQuestion.value = _questions.value[nextIndex]
        }
    }

    fun selectAnswer(optionIndex: Int) {
        userAnswers.add(optionIndex)

        val currentQuestion = _questions.value[_currentQuestionIndex.value]
        if (optionIndex == currentQuestion.correctAnswerIndex) {
            _score.value = _score.value + 1
        }
    }

    fun resetQuiz() {
        _currentQuestionIndex.value = 0
        _score.value = 0
        userAnswers.clear()

        if (_questions.value.isNotEmpty()) {
            _currentQuestion.value = _questions.value[0]
        }
    }
}