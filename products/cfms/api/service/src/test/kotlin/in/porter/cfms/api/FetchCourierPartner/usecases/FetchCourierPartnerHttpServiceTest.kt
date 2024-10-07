package `in`.porter.cfms.api.FetchCourierPartner.usecases

import courierpartner.mappers.CreateCourierPartnerRequestMapper
import courierpartner.mappers.FetchCpRecordsMapper
import courierpartner.usecases.CreateCourierPartnerService
import courierpartner.usecases.FetchCpRecordsService
import `in`.porter.cfms.api.CreateCourierPartner.usecases.CreateCourierPartnerServiceTest.Companion.cfmsException
import `in`.porter.cfms.api.models.courierpartner.FetchCpRecordsApiRequest
import `in`.porter.cfms.api.models.courierpartner.FetchCpRecordsApiResponse
import `in`.porter.cfms.domain.courierPartner.RecordCourierPartner
import `in`.porter.cfms.domain.courierPartner.entities.CreateCourierPartnerRequest
import `in`.porter.cfms.domain.courierPartner.entities.FetchCpRecordsRequest
import `in`.porter.cfms.domain.courierPartner.entities.FetchCpRecordsResponse
import `in`.porter.cfms.domain.courierPartner.repos.CourierPartnerRepo
import `in`.porter.cfms.domain.courierPartner.usecases.internal.CreateCourierPartner
import `in`.porter.cfms.domain.courierPartner.usecases.internal.FetchCpRecords
import `in`.porter.cfms.domain.exceptions.CfmsException
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.Logging
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows


    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class FetchCpRecordsServiceTest {
        private lateinit var fetchCpRecordsService: FetchCpRecordsService
        private lateinit var mapper: FetchCpRecordsMapper
        private lateinit var fetchCpRecords: FetchCpRecords
        private lateinit var courierPartnerRepo: CourierPartnerRepo

        @BeforeEach
        fun setup() {
            mapper = mockk()
            courierPartnerRepo = mockk()
            fetchCpRecords = mockk()
            fetchCpRecordsService = FetchCpRecordsService(mapper, fetchCpRecords)
        }

        companion object : Logging


        @Test
        fun `fetchCpRecords service should be invoked successfully`() = runBlocking {

            val fetchCpRecordsApiRequest = mockk<FetchCpRecordsApiRequest>(relaxed = true)
            val domainRequest = mockk<FetchCpRecordsRequest>(relaxed = true)
            val domainResponse = mockk<FetchCpRecordsResponse>(relaxed = true)
            val apiResponse = mockk<FetchCpRecordsApiResponse>(relaxed = true)

            every { mapper.toDomain(fetchCpRecordsApiRequest) } returns domainRequest
            coEvery {fetchCpRecords.invoke(domainRequest) } returns domainResponse
            coEvery { mapper.fromDomain(domainResponse) } returns apiResponse

            val result = fetchCpRecordsService.invoke(fetchCpRecordsApiRequest)

            assertEquals(result, apiResponse)

        }

        @Test
        fun `should throw CfmsException when CfmsException is thrown`() = runBlocking {

            val fetchCourierPartnerApiRequest = mockk<FetchCpRecordsApiRequest>(relaxed = true)
            val domainRequest = mockk<FetchCpRecordsRequest>()

            every { mapper.toDomain(fetchCourierPartnerApiRequest) } returns domainRequest
            coEvery { fetchCpRecords.invoke(domainRequest) } throws cfmsException

            val exception = assertThrows<CfmsException> {
                runBlocking {
                  fetchCpRecordsService.invoke(fetchCourierPartnerApiRequest)
                }
            }

            assertEquals(exception.message, cfmsException.message)

        }
    }
