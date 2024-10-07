package `in`.porter.cfms.data.Queries

import com.nhaarman.mockitokotlin2.verify
import `in`.porter.cfms.data.courierPartners.CourierPartnerQueries
import `in`.porter.cfms.data.courierPartners.CpConnectionTable
import `in`.porter.cfms.data.courierPartners.mappers.CourierPartnerRecordMapper
import `in`.porter.cfms.data.courierPartners.records.CourierPartnerData
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.apache.logging.log4j.kotlin.logger
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.PostgreSQLContainer
import java.time.Clock
import java.time.Instant


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourierPartnerQueriesTest {
  private lateinit var postgresContainer: PostgreSQLContainer<Nothing>
  private lateinit var db: Database
  private lateinit var courierPartnerQueries: CourierPartnerQueries
  private lateinit var mapper: CourierPartnerRecordMapper

  @BeforeAll
  fun setup() {
    postgresContainer = PostgreSQLContainer<Nothing>("postgis/postgis:15-3.4").apply {
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

    MockKAnnotations.init(this, relaxed = true)

    courierPartnerQueries = mockk(relaxed = true)

    transaction(db) {
      SchemaUtils.create(CpConnectionTable)
    }

    courierPartnerQueries = CourierPartnerQueries(db, Dispatchers.Unconfined, mapper)
  }

  @Test
  fun `getCPCount should return 0`(): Unit = runBlocking {
//    val count = courierPartnerQueries.getCpCount()
//    logger("here")


  }
}