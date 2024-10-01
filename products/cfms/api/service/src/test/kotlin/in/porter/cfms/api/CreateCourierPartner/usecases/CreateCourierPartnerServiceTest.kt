package `in`.porter.cfms.api.CreateCourierPartner.usecases

import courierpartner.mappers.CreateCourierPartnerRequestMapper
import courierpartner.usecases.CreateCourierPartnerService
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerResponse
import `in`.porter.cfms.domain.courierPartner.RecordCourierPartner
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import `in`.porter.cfms.domain.courierPartner.usecases.internal.CreateCourierPartner
import `in`.porter.cfms.domain.exceptions.CfmsException
import io.mockk.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals;


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
    createCourierPartnerService = CreateCourierPartnerService(mapper, createCourierPartner)
  }

  companion object {
    val cfmsException = CfmsException("error")
    val responseMessage = "Courier partner added successfully with id: "
  }

  @Test
  fun `CreateCourierPartner service should be invoked successfully`() = runBlocking {

    val createCourierPartnerApiRequest = mockk<CreateCourierPartnerApiRequest>(relaxed = true)
    val domainRequest = mockk<CreateCourierPartnerRequest>()
    val domainResponse = mockk<Int>(relaxed = true)

    every { mapper.toDomain(createCourierPartnerApiRequest) } returns domainRequest
    coEvery { createCourierPartner.invoke(domainRequest) } returns domainResponse

    val result = createCourierPartnerService.invoke(createCourierPartnerApiRequest)

    assertEquals(result.message, CreateCourierPartnerResponse(responseMessage +domainResponse).message)

  }

  @Test
  fun `should throw CfmsException when CfmsException is thrown`() = runBlocking {

    val createCourierPartnerApiRequest = mockk<CreateCourierPartnerApiRequest>(relaxed = true)
    val domainRequest = mockk<CreateCourierPartnerRequest>()

    every { mapper.toDomain(createCourierPartnerApiRequest) } returns domainRequest
    coEvery { createCourierPartner.invoke(domainRequest) } throws cfmsException

    val exception = assertThrows<CfmsException> {
      runBlocking {
        createCourierPartnerService.invoke(createCourierPartnerApiRequest)
      }
    }

    assertEquals(exception.message, cfmsException.message)

  }
}

