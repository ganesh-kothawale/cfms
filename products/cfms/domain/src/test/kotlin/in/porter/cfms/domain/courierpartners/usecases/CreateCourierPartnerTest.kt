package `in`.porter.cfms.domain.courierpartners.usecases

import `in`.porter.cfms.domain.courierPartner.RecordCourierPartner
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import `in`.porter.cfms.domain.courierPartner.repos.CourierPartnerRepo
import `in`.porter.cfms.domain.courierPartner.usecases.internal.CreateCourierPartner
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.assertThrows

@TestInstance(Lifecycle.PER_CLASS)
class CreateCourierPartnerTest {
  private val recordCourierPartner: RecordCourierPartner = mockk()
  private val createCourierPartner = CreateCourierPartner(recordCourierPartner)

  @Test
  fun `invoke should return result when recordCourierPartner succeeds`() = runBlocking {
    // Arrange: Set up the mock to return a specific value
    val request = CreateCourierPartnerRequest(courierPartnerId = 1,
      franchiseId = 1,
      manifestImageLink = "")
    val expectedResult = 1
    coEvery { recordCourierPartner.invoke(request) } returns expectedResult

    val result = createCourierPartner.invoke(request)

    assertEquals(expectedResult, result)

    coVerify { recordCourierPartner.invoke(request) }
  }
  @Test
  fun `invoke should throw IllegalArgumentException when recordCourierPartner throws`() = runBlocking {
    // Arrange: Set up the mock to throw an IllegalArgumentException
    val request = CreateCourierPartnerRequest(courierPartnerId = 1,
      franchiseId = 1,
      manifestImageLink = "")
    coEvery { recordCourierPartner.invoke(request) } throws IllegalArgumentException()

    //Verify that the exception is thrown
    assertThrows<IllegalArgumentException> {
      runBlocking {
        createCourierPartner.invoke(request)
      }
    }

    // Verify that the method was called
    coVerify { recordCourierPartner.invoke(request) }
  }

}