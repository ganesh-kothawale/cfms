package `in`.porter.cfms.domain.franchise.usecases

import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.franchise.entities.Franchise
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.cfms.domain.franchise.factories.RecordFranchiseDetailsRequestFactory
import `in`.porter.cfms.domain.franchise.usecases.internal.CreateFranchise
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
class CreateFranchiseTest {

    private lateinit var createFranchise: CreateFranchise
    private lateinit var franchiseRepo: FranchiseRepo
    private lateinit var requestFactory: RecordFranchiseDetailsRequestFactory

    @BeforeEach
    fun setup() {
        franchiseRepo = mockk()
        createFranchise = CreateFranchise(franchiseRepo)
        requestFactory = RecordFranchiseDetailsRequestFactory() // Initialize the factory
    }

    @Test
    fun `should create franchise successfully`() = runBlocking {
        // Arrange
        val request = requestFactory.build() // Use the factory to build the request

        coEvery { franchiseRepo.create(any<Franchise>()) } returns Unit

        // Act
        createFranchise.invoke(request)

        // Assert
        coVerify(exactly = 1) { franchiseRepo.create(any<Franchise>()) }
    }

    @Test
    fun `should throw exception when poc name is missing`() = runBlocking {
        // Arrange
        val request = requestFactory.build().copy(poc = requestFactory.build().poc.copy(name = "")) // Missing POC name

        // Act & Assert
        val exception = assertThrows<CfmsException> {
            createFranchise.invoke(request)
        }

        assertEquals("POC name cannot be empty.", exception.message)
        coVerify(exactly = 0) { franchiseRepo.create(any<Franchise>()) }
    }

    @Test
    fun `should throw exception when email is invalid`() = runBlocking {
        // Arrange
        val request = requestFactory.build().copy(poc = requestFactory.build().poc.copy(email = "invalid-email")) // Invalid email format

        // Act & Assert
        val exception = assertThrows<CfmsException> {
            createFranchise.invoke(request)
        }

        assertEquals("Invalid email format.", exception.message)
        coVerify(exactly = 0) { franchiseRepo.create(any<Franchise>()) }
    }

    @Test
    fun `should throw exception when address is invalid`() = runBlocking {
        // Arrange
        val request = requestFactory.build().copy(address = requestFactory.build().address.copy(address = "")) // Invalid (empty) address

        // Act & Assert
        val exception = assertThrows<CfmsException> {
            createFranchise.invoke(request)
        }

        assertEquals("Address cannot be empty.", exception.message)
        coVerify(exactly = 0) { franchiseRepo.create(any<Franchise>()) }
    }

    @Test
    fun `should throw exception when storing franchise fails`() = runBlocking {
        // Arrange
        val request = requestFactory.build()

        coEvery { franchiseRepo.create(any<Franchise>()) } throws Exception("DB error")

        // Act & Assert
        val exception = assertThrows<CfmsException> {
            createFranchise.invoke(request)
        }

        assertEquals("Failed to create franchise: DB error", exception.message)
        coVerify(exactly = 1) { franchiseRepo.create(any<Franchise>()) }
    }
}
