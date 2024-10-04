package `in`.porter.cfms.api.FetchCourierPartner.mappers

import courierpartner.mappers.CreateCourierPartnerRequestMapper
import courierpartner.mappers.FetchCpRecordsMapper
import `in`.porter.cfms.api.FetchCourierPartner.factories.FetchCourierPartnersTestFactory
import `in`.porter.cfms.api.models.courierpartner.CreateCourierPartnerApiRequest
import `in`.porter.cfms.api.models.courierpartner.FetchCpRecordsApiRequest
import `in`.porter.cfms.domain.courierPartner.entities.*
import io.mockk.clearAllMocks
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.Instant

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FetchCourierPartnerRequestMapperTest {
    private lateinit var mapper: FetchCpRecordsMapper

    @BeforeAll
    fun doSetup() {
        mapper = FetchCpRecordsMapper()
    }

    @BeforeEach
    fun beforeEach() {
        clearAllMocks()
    }

    @Test
    fun `test toDomain should map FetchCpRecordsApiRequest to FetchCpRecordsRequest`() = runBlocking {
        val fetchCourierPartnersApiRequest = FetchCpRecordsApiRequest(
            page = 1,
            page_size = 2,
            franchise_id = 3
        )
        val response = mapper.toDomain(fetchCourierPartnersApiRequest)
        assertEquals(
            FetchCpRecordsRequest(
                page = 1,
                pageSize = 2,
                franchiseId = 3
            ), response
        )
    }

    @Test
    fun `test fromDomain should map FetchCpRecordsResponse to FetchCpRecordsApiResponse`() {
        // Arrange: Create sample data for domain response
        val courierPartners = listOf(
            CourierPartnerDomain(
                id = 1,
                createdAt = Instant.now(),
                cpId = 1,
                franchiseId = 1001,
                manifestImageUrl = "http://link.to.image/1",
                courierPartnerName = "Courier AAA"
            ),
            CourierPartnerDomain(
                id = 2,
                createdAt = Instant.now(),
                cpId = 2,
                franchiseId = 1002,
                manifestImageUrl = "http://link.to.image/2",
                courierPartnerName = "Courier BBB"
            )
        )

        val pagination = Pagination(
            currentPage = 1,
            pageSize = 10,
            totalRecords = 100,
            totalPages = 10
        )

        val domainResponse = FetchCpRecordsResponse(
            data = courierPartners,
            pagination = pagination
        )

        // Act: Call the fromDomain function
        val result = mapper.fromDomain(domainResponse)

        // Assert: Check if the mapping is correct
        assertEquals(2, result.data.size)

        // Check first courier
        val firstCourier = result.data[0]
        assertEquals(1, firstCourier.id)
        assertEquals(1, firstCourier.cpId)
        assertEquals(1001, firstCourier.franchiseId)
        assertEquals("http://link.to.image/1", firstCourier.manifestImageUrl)
        assertEquals("Courier AAA", firstCourier.courierPartnerName)

        // Check second courier
        val secondCourier = result.data[1]
        assertEquals(2, secondCourier.id)
        assertEquals(2, secondCourier.cpId)
        assertEquals(1002, secondCourier.franchiseId)
        assertEquals("http://link.to.image/2", secondCourier.manifestImageUrl)
        assertEquals("Courier BBB", secondCourier.courierPartnerName)

        assertEquals(1, result.pagination.currentPage)
        assertEquals(10, result.pagination.pageSize)
        assertEquals(100, result.pagination.totalRecords)
        assertEquals(10, result.pagination.totalPages)
    }
}
