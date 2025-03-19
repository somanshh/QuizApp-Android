// FirebaseQuizRepository.kt
package com.example.quizapp.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlin.Exception

class FirebaseQuizRepository : QuizRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val questionsCollection = firestore.collection("questions")

    override suspend fun getQuestions(): List<Question> {
        return try {
            val snapshot = questionsCollection.get().await()
            val questions = snapshot.documents.mapNotNull { document ->
                val id = document.getLong("id")?.toInt() ?: return@mapNotNull null
                val questionText = document.getString("questionText") ?: return@mapNotNull null
                val options = document.get("options") as? List<String> ?: return@mapNotNull null
                val correctAnswerIndex = document.getLong("correctAnswerIndex")?.toInt() ?: return@mapNotNull null

                Question(id, questionText, options, correctAnswerIndex)
            }

            if (questions.isEmpty()) {
                // If no questions exist, add sample questions
                val sampleQuestions = getSampleQuestions()
                for (question in sampleQuestions) {
                    addQuestion(question)
                }
                sampleQuestions
            } else {
                questions
            }
        } catch (e: Exception) {
            Log.e("FirebaseQuizRepository", "Error getting questions", e)
            // Return sample questions as fallback
            getSampleQuestions()
        }
    }

    override suspend fun addQuestion(question: Question): Long {
        return try {
            val questionData = hashMapOf(
                "id" to question.id,
                "questionText" to question.questionText,
                "options" to question.options,
                "correctAnswerIndex" to question.correctAnswerIndex
            )

            questionsCollection.document(question.id.toString())
                .set(questionData)
                .await()

            question.id.toLong()
        } catch (e: Exception) {
            Log.e("FirebaseQuizRepository", "Error adding question", e)
            -1L
        }
    }

    override suspend fun updateQuestion(question: Question): Int {
        return try {
            val questionData = hashMapOf(
                "id" to question.id,
                "questionText" to question.questionText,
                "options" to question.options,
                "correctAnswerIndex" to question.correctAnswerIndex
            )

            questionsCollection.document(question.id.toString())
                .update(questionData as Map<String, Any>)
                .await()

            1
        } catch (e: Exception) {
            Log.e("FirebaseQuizRepository", "Error updating question", e)
            0
        }
    }

    override suspend fun deleteQuestion(id: Int): Int {
        return try {
            questionsCollection.document(id.toString())
                .delete()
                .await()

            1
        } catch (e: Exception) {
            Log.e("FirebaseQuizRepository", "Error deleting question", e)
            0
        }
    }

    private fun getSampleQuestions(): List<Question> {
        return listOf(
            Question(
                1,
                "What is the capital of India?",
                listOf("London", "Berlin", "Delhi", "Madrid"),
                2
            ),
            Question(
                2,
                "Which planet is known as the Red Planet?",
                listOf("Earth", "Mars", "Jupiter", "Venus"),
                1
            ),
            Question(
                3,
                "What is the largest mammal?",
                listOf("Elephant", "Blue Whale", "Giraffe", "Hippopotamus"),
                1
            ),
            Question(
                4,
                "Who painted the Mona Lisa?",
                listOf("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Michelangelo"),
                2
            ),
            Question(
                5,
                "Which element has the chemical symbol 'O'?",
                listOf("Gold", "Oxygen", "Osmium", "Oganesson"),
                1
            ),
            Question(
                6,
                "In which year did World War II end?",
                listOf("1943", "1944", "1945", "1946"),
                2
            ),
            Question(
                7,
                "Which mountain is the tallest in the world?",
                listOf("K2", "Mount Everest", "Kangchenjunga", "Makalu"),
                1
            )
        )
    }
}