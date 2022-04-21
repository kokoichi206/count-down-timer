package jp.mydns.kokoichi0206.countdowntimer.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Util関数の単体テスト。
 */
class FunctionUtilsTest {

    @Test
    fun `milliSecondsFromLocalDataTime for unix start time returns correctly`() {
        // Arrange
        // doesn't work  9:00
        // works        09:00
        val str = "1970-01-01 09:00:00"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(str, formatter)

        // Act
        val result = milliSecondsFromLocalDataTime(dateTime)

        // Assert
        assertEquals(0, result)
    }

    @Test
    fun `milliSecondsFromLocalDataTime for four-year returns correctly`() {
        // Arrange
        // including a leap year
        val str = "1974-01-01 09:00:00"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(str, formatter)

        // Act
        val result = milliSecondsFromLocalDataTime(dateTime)

        // Assert
        assertEquals((4 * 365 + 1) * 24 * 60 * 60 * 1000L, result)
    }

    @Test
    fun `milliSecondsBetween2DateTime for the same datetime returns correctly`() {
        // Arrange
        val str = "1999-01-11 15:32:33"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime1 = LocalDateTime.parse(str, formatter)
        val dateTime2 = LocalDateTime.parse(str, formatter)

        // Act
        val result = milliSecondsBetween2DateTime(dateTime1, dateTime2)

        // Assert
        assertEquals(0, result)
    }

    @Test
    fun `milliSecondsBetween2DateTime returns correctly`() {
        // Arrange
        val str1 = "1999-01-11 15:32:33"
        // 3 days ago
        val str2 = "1999-01-13 15:32:33"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime1 = LocalDateTime.parse(str1, formatter)
        val dateTime2 = LocalDateTime.parse(str2, formatter)

        // Act
        val result = milliSecondsBetween2DateTime(dateTime2, dateTime1)

        // Assert
        assertEquals(2 * 24 * 60 * 60 * 1000, result)
    }

    @Test
    fun `formattedTimeFromMilliSeconds for minus value returns default value`() {
        // Arrange
        val diffTime = -125

        // Act
        val result = formattedTimeFromMilliSeconds(diffTime)

        // Assert
        assertEquals("00:00:00:000", result)
    }

    @Test
    fun `formattedTimeFromMilliSeconds returns correctly`() {
        // Arrange
        val testData = arrayOf(
            arrayOf(326, "00:00:00:326"),
            arrayOf(40 * 1000, "00:00:40:000"),
            arrayOf(360 * 1000, "00:06:00:000"),
            arrayOf(60 * 60 * 1000, "01:00:00:000"),
            arrayOf(27 * 60 * 60 * 1000, "1:03:00:00:000"),
            arrayOf(11 * 24 * 60 * 60 * 1000, "11:00:00:00:000"),
        )

        // Act, Assert
        testData.forEach {
            val result = formattedTimeFromMilliSeconds(it[0] as Int)
            assertEquals(it[1], result)
        }
    }
}
