import `in`.porter.cfms.domain.franchise.factories.RecordFranchiseDetailsRequestFactory
import `in`.porter.cfms.domain.franchise.repos.FranchiseRepo
import `in`.porter.cfms.domain.franchise.usecases.internal.CreateFranchise
import `in`.porter.cfms.domain.utils.builders.FranchiseBuilder
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateFranchiseTest {

    private lateinit var franchiseRepo: FranchiseRepo
    private lateinit var createFranchise: CreateFranchise

    private val requestBuilder = RecordFranchiseDetailsRequestFactory()

    @BeforeEach
    fun setUp() {
        franchiseRepo = mockk()
        createFranchise = CreateFranchise(franchiseRepo)
    }

    @Test
    fun `should create franchise with correct data`() = runTest {
        // Arrange: Build the request using RecordFranchiseDetailsRequestFactory
        val request = requestBuilder.build()

        // Create a mock Franchise instance
        val franchise = mockk<FranchiseBuilder> {
            every { buildFromRequest(request) } returns mockk()
        }

        // Mock repository behavior
        coEvery { franchiseRepo.create(any()) } just Runs

        // Act: Call the createFranchise use case
        createFranchise.invoke(request)

        // Assert: Verify that the repository was called correctly
        coVerify(exactly = 1) { franchiseRepo.create(any()) }
    }
}
