package `in`.porter.cfms.api.CreateCourierPartner.usecases

import courierpartner.mappers.CreateCourierPartnerRequestMapper
import courierpartner.usecases.CreateCourierPartnerService
import `in`.porter.cfms.api.CreateCourierPartner.factories.CreateCourierPartnerTestFactory
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.domain.courierPartner.RecordCourierPartner
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import `in`.porter.cfms.domain.courierPartner.repos.CourierPartnerRepo
import `in`.porter.cfms.domain.courierPartner.usecases.internal.CreateCourierPartner
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.Logging
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Duration
import java.time.Instant
import java.time.LocalDate


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateCourierPartnerServiceTest {
  private lateinit var createCourierPartnerService: CreateCourierPartnerService
  private lateinit var mapper: CreateCourierPartnerRequestMapper
  private lateinit var createCourierPartner: CreateCourierPartner
  private lateinit var recordCourierPartner: RecordCourierPartner

  @BeforeEach
  fun setup() {
    mapper = mockk()
    recordCourierPartner = mockk()
    createCourierPartner = mockk()
      //CreateCourierPartner(recordCourierPartner)
    createCourierPartnerService = CreateCourierPartnerService(mapper, createCourierPartner)
  }

  companion object : Logging

  @Test
  fun `jjj`() = runBlocking {

//    val createCourierPartnerApiRequest = mockk<CreateCourierPartnerApiRequest>()
//    val domainRequest = mockk<CreateCourierPartnerRequest>()
//    val domainResponse = mockk<Int>()
//    val apiResponse = mockk<Int>()
//
//    every { mapper.toDomain(createCourierPartnerApiRequest) } returns domainRequest
//    coEvery { createCourierPartner.invoke(domainRequest) } returns domainResponse
//    //every { responseMapper.fromDomain(domainResponse) } returns apiResponse
//    logger.info(domainResponse)
//
//    val result = createCourierPartnerService.invoke(createCourierPartnerApiRequest)
//    logger.info(result.message)
//
//    assertEquals(result,domainResponse)

  }







}

