package `in`.porter.cfms.api.CreateCourierPartner.mappers

import com.sun.org.slf4j.internal.LoggerFactory
import com.sun.org.slf4j.internal.LoggerFactory.*
import courierpartner.mappers.CreateCourierPartnerRequestMapper
import courierpartner.mappers.CreateCourierPartnerRequestMapper.Companion.MANIFEST_LINK
import `in`.porter.cfms.api.CreateCourierPartner.usecases.CreateCourierPartnerServiceTest.Companion.logger
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import io.mockk.clearAllMocks
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import junit.framework.TestCase.assertEquals
import org.apache.logging.log4j.kotlin.Logging

import java.time.LocalDate
import java.util.logging.Logger

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateCourierPartnerRequestMapperTest {
  private lateinit var mapper: CreateCourierPartnerRequestMapper

  @BeforeAll
  fun doSetup() {
    mapper = CreateCourierPartnerRequestMapper()
  }
  companion object : Logging


  @BeforeEach
  fun beforeEach() {
    clearAllMocks()
  }

  @Test
  fun `toDomain should return Domain request object`() = runBlocking {
    val createCourierPartnerApiRequest = CreateCourierPartnerApiRequest(
      cpId = 1,
      franchiseId =  2,
      manifestImageLink = "link"
    )

    val response = mapper.toDomain(createCourierPartnerApiRequest)
    logger.info("mesg $response")

    assertEquals(CreateCourierPartnerRequest(
      courierPartnerId = 1,
      franchiseId =   2,
      manifestImageLink =  "link"
    ), response)
  }
}