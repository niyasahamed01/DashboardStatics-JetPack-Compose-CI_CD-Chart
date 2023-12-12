package com.example.staticsapp.remote

import com.example.staticsapp.model.InvoiceApiModel
import com.example.staticsapp.model.InvoiceStatus
import com.example.staticsapp.model.JobApiModel
import com.example.staticsapp.model.JobStatus
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone
import kotlin.random.Random

object SampleData {

    private val isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    fun generateRandomJobList(size: Int): List<JobApiModel> {
        val random = Random
        return (1..size).map {
            JobApiModel(
                jobNumber = it,
                title = generateRandomJobTitle(),
                startTime = LocalDateTime.now().plusDays(random.nextLong(1, 30))
                    .format(isoFormatter),
                endTime = LocalDateTime.now().plusDays(random.nextLong(31, 60))
                    .format(isoFormatter),
                status = when (random.nextInt(5)) {
                    0 -> JobStatus.YetToStart
                    1 -> JobStatus.InProgress
                    2 -> JobStatus.Canceled
                    3 -> JobStatus.Completed
                    else -> JobStatus.Incomplete
                }
            )
        }
    }

    fun generateRandomInvoiceList(size: Int): List<InvoiceApiModel> {
        val random = Random
        return (1..size).map {
            InvoiceApiModel(
                invoiceNumber = random.nextInt(1, Int.MAX_VALUE),
                customerName = generateRandomCustomerName(),
                total = random.nextInt(1, 10) * 1000,
                status = when (random.nextInt(4)) {
                    0 -> InvoiceStatus.Draft
                    1 -> InvoiceStatus.Pending
                    2 -> InvoiceStatus.Paid
                    else -> InvoiceStatus.BadDebt
                }
            )
        }
    }

    fun convertGmtToLocal(startTime: String): String {

        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Assuming the input is in UTC

            val outputFormat = SimpleDateFormat("dd/MMM/yyyy hh:mm a", Locale.getDefault())
            outputFormat.timeZone = TimeZone.getDefault() // Use the device's default time zone
            val date = inputFormat.parse(startTime)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            startTime
        }
    }
}

private fun generateRandomJobTitle(): String {
    val adjectives = listOf("Amazing", "Fantastic", "Awesome", "Incredible", "Superb")
    val nouns = listOf("Job", "Task", "Project", "Assignment", "Work")

    val randomAdjective = adjectives.random()
    val randomNoun = nouns.random()

    return "$randomAdjective $randomNoun"
}

fun generateRandomCustomerName(): String {
    val firstNames = listOf("John", "Jane", "Alice", "Bob", "Eva")
    val lastNames = listOf("Doe", "Smith", "Johnson", "Brown", "Lee")

    val randomFirstName = firstNames.random()
    val randomLastName = lastNames.random()

    return "$randomFirstName $randomLastName"
}



