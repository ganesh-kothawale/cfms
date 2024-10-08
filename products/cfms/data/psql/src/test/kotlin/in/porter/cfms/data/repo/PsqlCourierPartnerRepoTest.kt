package `in`.porter.cfms.data.repo

import `in`.porter.cfms.data.courierPartners.CourierPartnerQueries
import `in`.porter.cfms.data.courierPartners.mappers.CourierPartnerRecordMapper
import `in`.porter.cfms.data.courierPartners.records.CourierPartnerData
import `in`.porter.cfms.data.courierPartners.records.CourierPartnerTableData
import `in`.porter.cfms.data.courierPartners.repos.PsqlCourierPartnerRepo
import `in`.porter.cfms.domain.courierPartner.entities.CourierPartnerDomain
import `in`.porter.cfms.domain.courierPartner.entities.CourierPartnerRecord
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import `in`.porter.cfms.domain.courierPartner.entities.FetchCpRecordsRequest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant

class PsqlCourierPartnerRepoTest {

  // Mock dependencies
  private lateinit var queries: CourierPartnerQueries
  private lateinit var mapper: CourierPartnerRecordMapper

  // Class under test
  private lateinit var repo: PsqlCourierPartnerRepo

  @BeforeEach
  fun setUp() {
    // Initialize mocks
    queries = mockk()
    mapper = mockk()

    // Inject the mocks into the class
    repo = PsqlCourierPartnerRepo(queries, mapper)
  }

  @Test
  fun `create should call mapper and queries correctly`() = runBlocking {
    // Arrange: Prepare the request and mocked responses
    val request = CreateCourierPartnerRequest(
      courierPartnerId = 1,
      franchiseId = 1,
      manifestImageLink = ""
    )
    val record = CourierPartnerData(courierPartnerId = 1,
      franchiseId = 1,
      manifestImageUrl = "",
      createdAt = Instant.now()
      )

    val expectedResult = 1

    coEvery { mapper.toRecord(request) } returns record
    coEvery { queries.record(record) } returns expectedResult

    val result = repo.create(request)

    assertEquals(expectedResult, result)

    coVerify { mapper.toRecord(request) }
    coVerify { queries.record(record) }
  }

  @Test
  fun `fetchCpRecords should return mapped domain objects`() = runBlocking {
    // Arrange: Prepare the request and mocked responses
    val request = FetchCpRecordsRequest(page = 1, pageSize = 10, franchiseId = 123)
    val records = listOf(
      CourierPartnerTableData(
        id = 1,
        courierPartnerName = "Courier A",
        courierPartnerId = 1,
        franchiseId = 123,
        manifestImageUrl = "",
        createdAt = Instant.now()
      ),
      CourierPartnerTableData(
        id = 2,
        courierPartnerName = "Courier B",
        courierPartnerId = 2,
        franchiseId = 1234,
        manifestImageUrl = "",
        createdAt = Instant.now()
      )
    )

    // Mocked CourierPartnerDomain objects that the mapper would convert to
    val domainObjects = listOf(
      CourierPartnerDomain(
        id = 1,
        courierPartnerName = "Courier A",
        franchiseId = 123,
        cpId = 1,
        createdAt = Instant.now(),
        manifestImageUrl = ""
      ),
      CourierPartnerDomain(
        id = 2 ,
        courierPartnerName = "Courier B",
        franchiseId = 1234,
        cpId = 2,
        createdAt = Instant.now(),
        manifestImageUrl = ""
      ),
    )

    // Mock the queries and mapper behavior
    coEvery { queries.fetchCp(request.pageSize, 0, request.franchiseId) } returns records
    coEvery { mapper.toDomain(records[0]) } returns domainObjects[0]
    coEvery { mapper.toDomain(records[1]) } returns domainObjects[1]

    coEvery { queries.fetchCp(request.pageSize, 0, request.franchiseId) } returns records
    coEvery { mapper.toDomain(records[0]) } returns domainObjects.first()
    coEvery { mapper.toDomain(records[1]) } returns domainObjects.get(1)


    // Act: Call the method under test
    val result = repo.fetchCpRecords(request)

    // Assert: Verify the result is correct
    assertEquals(domainObjects, result)

    // Verify that queries and mapper were called
    coVerify { queries.fetchCp(request.pageSize, 0, request.franchiseId) }
    coVerify(exactly = records.size) { mapper.toDomain(any()) }
  }
@Test
fun `getCpCount should return count from queries`() = runBlocking {
  // Arrange: Mock the count return value
  val expectedCount = 10
  coEvery { queries.getCpCount() } returns expectedCount

  // Act: Call the method under test
  val result = repo.getCpCount()

  // Assert: Verify the count is returned correctly
  assertEquals(expectedCount, result)

  // Verify that queries.getCpCount() was called
  coVerify { queries.getCpCount() }
}

  @Test
  fun `getCpName should return name from queries`() = runBlocking {
    // Arrange: Mock the name response
    val cpId = 1
    val expectedName = "Courier Partner Name"
    coEvery { queries.getName(cpId) } returns expectedName

    // Act: Call the method under test
    val result = repo.getCpName(cpId)

    // Assert: Verify the correct name is returned
    assertEquals(expectedName, result)

    // Verify that queries.getName() was called
    coVerify { queries.getName(cpId) }
  }

  @Test
  fun `getCpName should return null if no name found`() = runBlocking {
    // Arrange: Mock a null response
    val cpId = 2
    coEvery { queries.getName(cpId) } returns null

    // Act: Call the method under test
    val result = repo.getCpName(cpId)

    // Assert: Verify that null is returned
    assertNull(result)

    coVerify { queries.getName(cpId) }
  }
}