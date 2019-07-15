package com.justd.rabo.issues

import com.justd.rabo.issues.model.GetIssuesUsecase
import com.justd.rabo.issues.model.RawResourceProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertTrue
import org.junit.Test

class GetIssuesUsecaseTest {

    private val rawProvider = mock<RawResourceProvider>()
    private val testInstance = GetIssuesUsecase(rawProvider)

    @Test
    fun `given proper csv when parsing issues then return list of issues`() {
        whenever(rawProvider.get(any())).thenReturn(
            """
            "First name","Sur name","Issue count","Date of birth"
            "Theo","Jansen",5,"1978-01-02T00:00:00"
            "Fiona","de Vries",7,"1950-11-12T00:00:00"
            "Petra","Boersma",1,"2001-04-20T00:00:00"
        """.trimIndent()
        )

        val issues = testInstance.execute()

        assertTrue(issues.size == 3)
        assertTrue(issues[0].firstame == "Theo")
        assertTrue(issues[1].surname == "de Vries")
        assertTrue(issues[2].issueCount == 1)
        assertTrue(issues[2].dateOfBirth == "2001-04-20T00:00:00")
    }
}