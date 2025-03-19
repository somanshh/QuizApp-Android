// SQLiteQuizRepository.kt
package com.example.quizapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SQLiteQuizRepository(context: Context) : QuizRepository {
    private val dbHelper = QuizDatabaseHelper(context)

    override suspend fun getQuestions(): List<Question> = withContext(Dispatchers.IO) {
        val questions = mutableListOf<Question>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            QuizDatabaseHelper.TABLE_QUESTIONS,
            null,
            null,
            null,
            null,
            null,
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndexOrThrow(QuizDatabaseHelper.COLUMN_ID))
                    val questionText = it.getString(it.getColumnIndexOrThrow(QuizDatabaseHelper.COLUMN_QUESTION))
                    val optionsString = it.getString(it.getColumnIndexOrThrow(QuizDatabaseHelper.COLUMN_OPTIONS))
                    val correctAnswerIndex = it.getInt(it.getColumnIndexOrThrow(QuizDatabaseHelper.COLUMN_CORRECT_ANSWER))

                    val options = optionsString.split("|")

                    questions.add(Question(id, questionText, options, correctAnswerIndex))
                } while (it.moveToNext())
            }
        }

        // If no questions in database, add sample questions
        if (questions.isEmpty()) {
            val sampleQuestions = getSampleQuestions()
            sampleQuestions.forEach { question ->
                addQuestion(question)
            }
            return@withContext sampleQuestions
        }

        return@withContext questions
    }

    override suspend fun addQuestion(question: Question): Long = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(QuizDatabaseHelper.COLUMN_QUESTION, question.questionText)
            put(QuizDatabaseHelper.COLUMN_OPTIONS, question.options.joinToString("|"))
            put(QuizDatabaseHelper.COLUMN_CORRECT_ANSWER, question.correctAnswerIndex)
        }

        return@withContext db.insert(QuizDatabaseHelper.TABLE_QUESTIONS, null, values)
    }

    override suspend fun updateQuestion(question: Question): Int = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(QuizDatabaseHelper.COLUMN_QUESTION, question.questionText)
            put(QuizDatabaseHelper.COLUMN_OPTIONS, question.options.joinToString("|"))
            put(QuizDatabaseHelper.COLUMN_CORRECT_ANSWER, question.correctAnswerIndex)
        }

        return@withContext db.update(
            QuizDatabaseHelper.TABLE_QUESTIONS,
            values,
            "${QuizDatabaseHelper.COLUMN_ID} = ?",
            arrayOf(question.id.toString())
        )
    }

    override suspend fun deleteQuestion(id: Int): Int = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        return@withContext db.delete(
            QuizDatabaseHelper.TABLE_QUESTIONS,
            "${QuizDatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    private fun getSampleQuestions(): List<Question> {
        return listOf(
            Question(
                1,
                "What is the capital of France?",
                listOf("London", "Berlin", "Paris", "Madrid"),
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
                "Which country has the fastest running trains?",
                listOf("Australia", "India", "China", "Japan"),
                2
            )
        )
    }
}