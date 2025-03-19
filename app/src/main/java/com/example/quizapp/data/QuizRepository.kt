// QuizRepository.kt
package com.example.quizapp.data

interface QuizRepository {
    suspend fun getQuestions(): List<Question>
    suspend fun addQuestion(question: Question): Long
    suspend fun updateQuestion(question: Question): Int
    suspend fun deleteQuestion(id: Int): Int
}