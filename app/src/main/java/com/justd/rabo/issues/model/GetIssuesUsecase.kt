package com.justd.rabo.issues.model

import com.justd.rabo.R
import com.justd.rabo.app.model.Date
import com.justd.rabo.app.model.Issue
import java.io.IOException

typealias Matrix = Array<Array<Any>>

private const val FIELD_FIRST_NAME = "First name"
private const val FIELD_SURNAME = "Sur name"
private const val FIELD_ISSUE_COUNT = "Issue count"
private const val FIELD_DATE_OF_BIRTH = "Date of birth"

class GetIssuesUsecase(
    val rawProvider: RawResourceProvider
) {

    fun execute(): List<Issue> {
        val fileContent = rawProvider.get(R.raw.issues)
        try {
            val resultMatrix: Matrix = readCsvMatrix(fileContent)
            return parseIssues(resultMatrix)
        } catch (exception: ClassCastException) {
            throw IOException("failed to parse csv")
        }
    }

    private fun readCsvMatrix(fileContent: String): Matrix {
        val matrix = mutableListOf<Array<Any>>()
        fileContent
            .split("\n")
            .forEach { line ->
                val entries = line.replace("\"", "").split(",")
                matrix.add(entries.toTypedArray())
            }
        return matrix.toTypedArray()
    }

    private fun parseIssues(matrix: Matrix): List<Issue> {
        val issues = mutableListOf<Issue>()
        var nameIndex = -1
        var surnameIndex = -1
        var countIndex = -1
        var dateIndex = -1
        matrix.forEachIndexed { index, array ->
            if (index == 0) { //titles array
                nameIndex = array.indexOf(FIELD_FIRST_NAME)
                surnameIndex = array.indexOf(FIELD_SURNAME)
                countIndex = array.indexOf(FIELD_ISSUE_COUNT)
                dateIndex = array.indexOf(FIELD_DATE_OF_BIRTH)
            } else {
                issues.add(
                    Issue(
                        array[nameIndex] as String,
                        array[surnameIndex] as String,
                        (array[countIndex] as String).toInt(),
                        array[dateIndex] as Date
                    )
                )
            }
        }
        return issues
    }
}