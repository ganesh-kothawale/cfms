package `in`.porter.cfms.api.CreateCourierPartner.usecases

import courierpartner.mappers.CreateCourierPartnerRequestMapper
import courierpartner.usecases.CreateCourierPartnerService
import `in`.porter.cfms.api.CreateCourierPartner.usecases.CreateCourierPartnerServiceTest.Companion
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerResponse
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.servers.ktor.external.usecases.courierpartner.CreateCourierPartnerHttpService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateCourierPartnerHttpServiceTest {
  private lateinit var createCourierPartnerService: CreateCourierPartnerService
  private lateinit var createCourierPartnerApiRequest: CreateCourierPartnerApiRequest
  private lateinit var createCourierPartnerHttpService: CreateCourierPartnerHttpService


  @BeforeEach
  fun setup() {
    createCourierPartnerApiRequest = mockk<CreateCourierPartnerApiRequest>(relaxed = false)
    createCourierPartnerService = mockk<CreateCourierPartnerService>(relaxed = true)
    createCourierPartnerHttpService = CreateCourierPartnerHttpService(createCourierPartnerService)

  }

  companion object {
    val cfmsException = CfmsException("error")
  }

}