package `in`.porter.cfms.domain.usecases.external

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.exceptions.FranchiseAlreadyExistsException
import `in`.porter.cfms.domain.franchise.entities.Franchise
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.cfms.domain.franchise.usecases.internal.CreateFranchise
import `in`.porter.cfms.domain.usecases.entities.RecordFranchiseDetailsRequest
import `in`.porter.cfms.domain.franchise.factories.RecordFranchiseDetailsRequestFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecordFranchiseDetailsTest {

    private lateinit var recordFranchiseDetails: RecordFranchiseDetails
    private lateinit var franchiseRepo: FranchiseRepo
    private lateinit var createFranchise: CreateFranchise
    private lateinit var requestFactory: RecordFranchiseDetailsRequestFactory

    @BeforeEach
    fun setup() {
        franchiseRepo = mockk()
        createFranchise = mockk()
        recordFranchiseDetails = RecordFranchiseDetails(franchiseRepo, createFranchise)
        requestFactory = RecordFranchiseDetailsRequestFactory() // Initialize the factory
    }

    @Test
    fun `should create franchise successfully`() = runBlocking {
        // Arrange
        val request = requestFactory.build() // Use the factory to build the request

        coEvery { franchiseRepo.getByEmail(request.poc.email) } returns null // No existing franchise
        coEvery { createFranchise.invoke(request) } returns Unit // Successful creation

        // Act
        recordFranchiseDetails.invoke(request)

        // Assert
        coVerify(exactly = 1) { createFranchise.invoke(request) }
    }

    @Test
    fun `should throw FranchiseAlreadyExistsException when franchise already exists`() = runBlocking {
        // Arrange
        val existingFranchiseEmail = "existing@franchise.com"
        val request = requestFactory.build().copy(poc = requestFactory.build().poc.copy(email = existingFranchiseEmail))

        // Return a mock object (not null) to simulate an existing franchise
        val existingFranchise = mockk<Franchise>() // Replace with actual franchise model if needed
        coEvery { franchiseRepo.getByEmail(existingFranchiseEmail) } returns existingFranchise

        // Act & Assert
        val exception = assertThrows<FranchiseAlreadyExistsException> {
            recordFranchiseDetails.invoke(request)
        }

        assertEquals("Franchise with Franchise Id $existingFranchiseEmail already exists", exception.message)
        coVerify(exactly = 0) { createFranchise.invoke(any()) }
    }

    @Test
    fun `should handle exceptions thrown by createFranchise`() = runBlocking {
        // Arrange
        val request = requestFactory.build() // Use the factory to build the request

        coEvery { franchiseRepo.getByEmail(request.poc.email) } returns null // No existing franchise
        coEvery { createFranchise.invoke(request) } throws CfmsException("Creation failed") // Throwing exception

        // Act
        val exception = assertThrows<CfmsException> {
            recordFranchiseDetails.invoke(request)
        }

        // Assert
        assertEquals("Creation failed", exception.message)
        coVerify(exactly = 1) { createFranchise.invoke(request) }
    }
}
