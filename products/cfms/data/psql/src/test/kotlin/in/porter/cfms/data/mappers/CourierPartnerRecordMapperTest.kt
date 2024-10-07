package `in`.porter.cfms.data.mappers

import `in`.porter.cfms.data.courierPartners.CpConnectionTable
import `in`.porter.cfms.data.courierPartners.mappers.CourierPartnerRecordMapper
import `in`.porter.cfms.data.courierPartners.records.CourierPartnerTableData
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.ResultRow
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.Instant
import javax.management.Query

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourierPartnerRecordMapperTest {
  private lateinit var mapper: CourierPartnerRecordMapper

  @BeforeAll
  fun doSetup() {
    mapper = CourierPartnerRecordMapper()
  }

  @BeforeEach
  fun beforeEach() {
    clearAllMocks()
  }

  companion object {
    var cp_id = 1
    var franchise_id = 1
    var link = "http://example.com/manifest"
    var name = "ABC"
  }


  @Test
  fun `test toRecord maps correctly`() {
    // Arrange
    val request = CreateCourierPartnerRequest(
      courierPartnerId = cp_id,
      franchiseId = franchise_id,
      manifestImageLink = link
    )

    // Mock the current time for Instant.now()
    mockkStatic(Instant::class)
    val now = Instant.parse("2023-10-01T00:00:00Z")
    every { Instant.now() } returns now

    // Act
    val result = mapper.toRecord(request)

    // Assert
    assertEquals(1, result.courierPartnerId)
    assertEquals(1, result.franchiseId)
    assertEquals(link, result.manifestImageUrl)
    assertEquals(now, result.createdAt)

    verify { Instant.now() }
  }


  @Test
  fun `test fromResultRow converts ResultRow to CourierPartnerTableData`() {
    // Given
    val resultRow = mockk<ResultRow>()
    val now = Instant.now()

    every { resultRow[CpConnectionTable.id].value } returns 1
    every { resultRow[CpConnectionTable.createdAt] } returns now
    every { resultRow[CpConnectionTable.cpId] } returns 1
    every { resultRow[CpConnectionTable.franchiseId] } returns 1
    every { resultRow[CpConnectionTable.manifestImageUrl] } returns link

    // When
    val result = mapper.fromResultRow(resultRow)

    // Then
    assertEquals(1, result.id)
    assertEquals(now, result.createdAt)
    assertEquals(1, result.courierPartnerId)
    assertEquals(1, result.franchiseId)
    assertEquals(link, result.manifestImageUrl)
  }

  @Test
  fun `test toDomain converts CourierPartnerTableData to CourierPartnerDomain`() {
    // Given
    val courierPartnerTableData = CourierPartnerTableData(
      id = 1,
      createdAt = Instant.now(),
      courierPartnerId = 1,
      franchiseId = 1,
      manifestImageUrl = link,
      courierPartnerName = name
    )

    // When
    val result = mapper.toDomain(courierPartnerTableData)

    // Then
    assertEquals(1, result.id)
    assertEquals(courierPartnerTableData.createdAt, result.createdAt)
    assertEquals(1, result.cpId)
    assertEquals(1, result.franchiseId)
    assertEquals(link, result.manifestImageUrl)
    assertEquals(name, result.courierPartnerName)
  }

}
