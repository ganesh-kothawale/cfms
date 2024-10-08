//package `in`.porter.cfms.domain.courierpartners.usecases
//
//import `in`.porter.cfms.data.courierPartners.CourierPartnerQueries
//import `in`.porter.cfms.data.courierPartners.mappers.CourierPartnerRecordMapper
//import `in`.porter.cfms.data.courierPartners.repos.PsqlCourierPartnerRepo
//import io.mockk.*
//import kotlinx.coroutines.runBlocking
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestInstance
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class PsqlCourierPartnerRepoTest {
//  private val queries = mockk<CourierPartnerQueries>()
//  private val mapper = mockk<CourierPartnerRecordMapper>()
//  private val repo = PsqlCourierPartnerRepo(queries, mapper)
//
//  @Test
//  fun `test getCpCount returns correct value`() = runBlocking {
//    coEvery { repo.getCpCount() } returns 1
//    val result = repo.getCpCount()
//    assertEquals(result,1)
//  }
//}