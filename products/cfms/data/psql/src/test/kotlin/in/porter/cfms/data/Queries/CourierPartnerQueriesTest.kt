package `in`.porter.cfms.data.Queries

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import `in`.porter.cfms.data.courierPartners.CourierPartnerQueries
import `in`.porter.cfms.data.courierPartners.CourierPartnersTable
import `in`.porter.cfms.data.courierPartners.CpConnectionTable
import `in`.porter.cfms.data.courierPartners.mappers.CourierPartnerRecordMapper
import `in`.porter.cfms.data.courierPartners.records.CourierPartnerData
import `in`.porter.cfms.data.franchise.records.FranchiseRecordData
import io.mockk.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.apache.logging.log4j.kotlin.logger
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.PostgreSQLContainer
import java.math.BigDecimal
import java.time.Clock
import java.time.Instant
import javax.management.Query.match


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourierPartnerQueriesTest {
  private lateinit var postgresContainer: PostgreSQLContainer<Nothing>
  private lateinit var db: Database
  private lateinit var courierPartnerQueries: CourierPartnerQueries
  private lateinit var mapper: CourierPartnerRecordMapper
  private lateinit var testDispatcher: TestDispatcher

  @BeforeAll
  fun setup() {
    postgresContainer = PostgreSQLContainer<Nothing>("postgres:14.13").apply {
      withDatabaseName("test")
      withUsername("test")
      withPassword("test")
      start()
    }

    db = Database.connect(
      url = postgresContainer.jdbcUrl,
      driver = "org.postgresql.Driver",
      user = postgresContainer.username,
      password = postgresContainer.password
    )
    mapper = mockk<CourierPartnerRecordMapper>()
    testDispatcher = StandardTestDispatcher()
    Dispatchers.setMain(testDispatcher)

    MockKAnnotations.init(this, relaxed = true)

    courierPartnerQueries = mockk(relaxed = true)

    transaction(db) {
      SchemaUtils.create(CpConnectionTable)
    }

    courierPartnerQueries = CourierPartnerQueries(db, Dispatchers.Unconfined, mapper)
  }
  @BeforeEach
  fun resetDispatcher() {
    Dispatchers.setMain(testDispatcher)
  }

  @Test
  fun shouldRecord() = runTest {
    val cpData = CourierPartnerData(
      createdAt = Instant.now(),
      courierPartnerId = 1,
      franchiseId = 2,
      manifestImageUrl = ""
    )

    val id = courierPartnerQueries.record(cpData)
    assertNotNull(id)
  }

//  @Test
//  fun `getCPCount should return 0`() = runBlocking {
//    val result = courierPartnerQueries.getCpCount()
//    assertEquals(0,result)
//  }

}