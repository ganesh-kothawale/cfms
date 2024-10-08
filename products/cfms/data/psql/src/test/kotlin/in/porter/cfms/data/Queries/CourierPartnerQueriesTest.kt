//package `in`.porter.cfms.data.Queries
//
//import com.nhaarman.mockitokotlin2.verify
//import `in`.porter.cfms.data.courierPartners.CourierPartnerQueries
//import `in`.porter.cfms.data.courierPartners.CpConnectionTable
//import `in`.porter.cfms.data.courierPartners.mappers.CourierPartnerRecordMapper
//import `in`.porter.cfms.data.courierPartners.records.CourierPartnerData
//import io.mockk.*
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.runBlockingTest
//import org.apache.logging.log4j.kotlin.logger
//import org.jetbrains.exposed.dao.id.EntityID
//import org.jetbrains.exposed.sql.Database
//import org.jetbrains.exposed.sql.SchemaUtils
//import org.jetbrains.exposed.sql.insertAndGetId
//import org.jetbrains.exposed.sql.transactions.transaction
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.BeforeAll
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestInstance
//import org.testcontainers.containers.PostgreSQLContainer
//import java.time.Clock
//import java.time.Instant
//import javax.management.Query.match
//
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class CourierPartnerQueriesTest {
////  private lateinit var db: Database
////  private lateinit var dispatcher: CoroutineDispatcher
////  private lateinit var mapper: CourierPartnerRecordMapper
////
////  // Class under test
////  private lateinit var queries: CourierPartnerQueries
////
////  @BeforeEach
////  fun setUp() {
////    // Initialize mocks
////    db = mockk()
////    dispatcher = mockk()
////    mapper = mockk()
////
////    // Create instance of the class under test
////    queries = CourierPartnerQueries(db, dispatcher, mapper)
////  }
////
////  @Test
////  fun `record should insert courier partner and return generated ID`() = runBlocking {
////    // Arrange: Mock the database insert operation
////    val req = CourierPartnerData(franchiseId = 1, createdAt = Instant.now(), courierPartnerId = 123, manifestImageUrl = "http://image.url")
////    val expectedId = 10
////
////    mockkStatic(CpConnectionTable::class) // Static mocking for Exposed insert method
////    every { CpConnectionTable.insertAndGetId(any()) } returns mockk {
////      every { value } returns expectedId
////    }
////
////    // Act: Call the record method
////    val result = queries.record(req)
////
////    // Assert: Verify the returned ID
////    assertEquals(expectedId, result)
////
////    // Verify that the insert was called with the correct values
//////    verify {
//////      CpConnectionTable.insertAndGetId(match {
//////        it[CpConnectionTable.franchiseId] == req.franchiseId &&
//////                it[CpConnectionTable.createdAt] == req.createdAt &&
//////                it[CpConnectionTable.cpId] == req.courierPartnerId &&
//////                it[CpConnectionTable.manifestImageUrl] == req.manifestImageUrl
//////      })
//////    }
////  }
//
////  @Test
////  fun `fetchCp should return mapped domain objects when franchiseId is provided`() = runBlocking {
////    // Arrange: Mock the database select operation
////    val limit = 10
////    val offset = 0
////    val franchiseId = 123
////    val rows = listOf(mockk<ExposedResultRow>()) // Mock ExposedResultRow for the fetched data
////    val expectedDomainObjects = listOf(CourierPartnerTableData(/* mock properties */))
////
////    mockkStatic(CpConnectionTable::class)
////    every { CpConnectionTable.selectAll().andWhere { CpConnectionTable.franchiseId eq franchiseId }
////      .orderBy(CpConnectionTable.createdAt, SortOrder.DESC).limit(limit, offset) } returns rows
////    every { mapper.mapOrders(rows) } returns expectedDomainObjects
////
////    // Act: Call the fetchCp method
////    val result = queries.fetchCp(limit, offset, franchiseId)
////
////    // Assert: Verify the result
////    assertEquals(expectedDomainObjects, result)
////
////    // Verify that select and mapper were called correctly
////    verify {
////      CpConnectionTable.selectAll().andWhere { CpConnectionTable.franchiseId eq franchiseId }
////        .orderBy(CpConnectionTable.createdAt, SortOrder.DESC).limit(limit, offset)
////      mapper.mapOrders(rows)
////    }
////  }
////
////  @Test
////  fun `fetchCp should return mapped domain objects when franchiseId is null`() = runBlocking {
////    // Arrange: Mock the database select operation
////    val limit = 10
////    val offset = 0
////    val rows = listOf(mockk<ExposedResultRow>()) // Mock ExposedResultRow for the fetched data
////    val expectedDomainObjects = listOf(CourierPartnerTableData(/* mock properties */))
////
////    mockkStatic(CpConnectionTable::class)
////    every { CpConnectionTable.selectAll().orderBy(CpConnectionTable.createdAt, SortOrder.DESC)
////      .limit(limit, offset) } returns rows
////    every { mapper.mapOrders(rows) } returns expectedDomainObjects
////
////    // Act: Call the fetchCp method
////    val result = queries.fetchCp(limit, offset, null)
////
////    // Assert: Verify the result
////    assertEquals(expectedDomainObjects, result)
////
////    // Verify that select and mapper were called correctly
////    verify {
////      CpConnectionTable.selectAll().orderBy(CpConnectionTable.createdAt, SortOrder.DESC)
////        .limit(limit, offset)
////      mapper.mapOrders(rows)
////    }
////  }
////
////  @Test
////  fun `getCpCount should return count from database`() = runBlocking {
////    // Arrange: Mock the count result from the database
////    val expectedCount = 20
////
////    mockkStatic(CpConnectionTable::class)
////    every { CpConnectionTable.selectAll().count() } returns expectedCount
////
////    // Act: Call the getCpCount method
////    val result = queries.getCpCount()
////
////    // Assert: Verify the count result
////    assertEquals(expectedCount, result)
////
////    // Verify the query was executed
////    verify { CpConnectionTable.selectAll().count() }
////  }
//
////  @Test
////  fun `getName should return name from database when cpId is found`() = runBlocking {
////    // Arrange: Mock the database query
////    val cpId = 1
////    val expectedName = "Courier Partner A"
////    val row = mockk<ExposedResultRow>()
////
////    mockkStatic(CourierPartnersTable::class)
////    every { CourierPartnersTable.select { CourierPartnersTable.id eq cpId }.firstOrNull() } returns row
////    every { row[CourierPartnersTable.name] } returns expectedName
////
////    // Act: Call the getName method
////    val result = queries.getName(cpId)
////
////    // Assert: Verify the name is returned
////    assertEquals(expectedName, result)
////
////    // Verify the select query was executed
////    verify { CourierPartnersTable.select { CourierPartnersTable.id eq cpId }.firstOrNull() }
////  }
////
////  @Test
////  fun `getName should return null when no cpId is found`() = runBlocking {
////    // Arrange: Mock the database query returning no result
////    val cpId = 1
////
////    mockkStatic(CourierPartnersTable::class)
////    every { CourierPartnersTable.select { CourierPartnersTable.id eq cpId }.firstOrNull() } returns null
////
////    // Act: Call the getName method
////    val result = queries.getName(cpId)
////
////    // Assert: Verify null is returned
////    assertNull(result)
////
////    // Verify the select query was executed
////    verify { CourierPartnersTable.select { CourierPartnersTable.id eq cpId }.firstOrNull() }
////  }
//
//  private lateinit var postgresContainer: PostgreSQLContainer<Nothing>
//  private lateinit var db: Database
//  private lateinit var courierPartnerQueries: CourierPartnerQueries
//  private lateinit var mapper: CourierPartnerRecordMapper
//
//  @BeforeAll
//  fun setup() {
//    postgresContainer = PostgreSQLContainer<Nothing>("postgis/postgis:15-3.4").apply {
//      withDatabaseName("test")
//      withUsername("test")
//      withPassword("test")
//      start()
//    }
//
//    db = Database.connect(
//      url = postgresContainer.jdbcUrl,
//      driver = "org.postgresql.Driver",
//      user = postgresContainer.username,
//      password = postgresContainer.password
//    )
//    mapper = mockk<CourierPartnerRecordMapper>()
//
//    MockKAnnotations.init(this, relaxed = true)
//
//    courierPartnerQueries = mockk(relaxed = true)
//
//    transaction(db) {
//      SchemaUtils.create(CpConnectionTable)
//    }
//
//    courierPartnerQueries = CourierPartnerQueries(db, Dispatchers.Unconfined, mapper)
//  }
//
//  @Test
//  fun `getCPCount should return 0`(): Unit = runBlocking {
//  coEvery { courierPartnerQueries.getCpCount()} returns 0
//
//    val result = courierPartnerQueries.getCpCount()
//
//    assertEquals(result, 0)
//
//
//
//
//  }
//}