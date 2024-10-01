package `in`.porter.cfms.api.CreateCourierPartner.mappers


import courierpartner.mappers.CreateCourierPartnerRequestMapper
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import io.mockk.clearAllMocks
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import junit.framework.TestCase.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateCourierPartnerRequestMapperTest {
  private lateinit var mapper: CreateCourierPartnerRequestMapper

  @BeforeAll
  fun doSetup() {
    mapper = CreateCourierPartnerRequestMapper()
  }

  @BeforeEach
  fun beforeEach() {
    clearAllMocks()
  }

  companion object{
    val link = "link"
  }

  @Test
  fun `mapper toDomain method should return Domain request object`() = runBlocking {
    val createCourierPartnerApiRequest = CreateCourierPartnerApiRequest(
      cpId = 1,
      franchiseId =  2,
      manifestImageLink = link
    )

    val response = mapper.toDomain(createCourierPartnerApiRequest)
    assertEquals(CreateCourierPartnerRequest(
      courierPartnerId = 1,
      franchiseId =   2,
      manifestImageLink =  link
    ), response)
  }
}