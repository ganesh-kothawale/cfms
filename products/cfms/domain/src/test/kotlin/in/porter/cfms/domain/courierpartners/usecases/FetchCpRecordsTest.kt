package `in`.porter.cfms.domain.courierpartners.usecases

import `in`.porter.cfms.domain.courierPartner.RecordCourierPartner
import `in`.porter.cfms.domain.courierPartner.entities.CourierPartnerDomain
import `in`.porter.cfms.domain.courierPartner.entities.FetchCpRecordsResponse
import `in`.porter.cfms.domain.courierPartner.repos.CourierPartnerRepo
import `in`.porter.cfms.domain.courierPartner.usecases.internal.FetchCpRecords
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle

@TestInstance(Lifecycle.PER_CLASS)
class FetchCpRecordsTest {
  private val repo: CourierPartnerRepo = mockk()

  @Test
  fun `should fetch CP Records successfully`() = runTest {
    val request = FetchCpRecordsFactory.buildFetchCpRecordsRequest()
    val response =  mockk<List<CourierPartnerDomain>>(relaxed = true)

    coEvery { repo.fetchCpRecords(request) } returns response

    repo.fetchCpRecords(request)

    coVerify(exactly = 1) { repo.fetchCpRecords(request) }
  }

//  @Test
//  fun `should throw IllegalArgumentException when invalid input provided`() = runTest {
//    // Arrange
//    val request = RecordCourierPartnerFactory.buildRecordCourierPartnerRequest()
//    coEvery { repo.create(request) } throws IllegalArgumentException("Invalid request")
//
//    val exception = assertThrows(Exception::class.java) {
//      runBlocking { recordCourierPartner.invoke(request)}
//    }
//
//    assertEquals("Invalid request", exception.message)
//
//    coVerify(exactly = 1) { repo.create(request) }
//  }
//
//  @Test
//  fun `should throw Exception for internal server error`() = runTest {
//    // Arrange
//    val request = RecordCourierPartnerFactory.buildRecordCourierPartnerRequest()
//    coEvery { repo.create(request) } throws IllegalArgumentException("Internal server error")
//
//    val exception = assertThrows(Exception::class.java) {
//      runBlocking { recordCourierPartner.invoke(request)}
//    }
//
//    assertEquals("Internal server error", exception.message)
//
//    coVerify(exactly = 1) { repo.create(request) }
//  }
}

