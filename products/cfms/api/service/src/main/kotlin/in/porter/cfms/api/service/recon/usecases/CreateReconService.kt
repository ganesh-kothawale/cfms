package `in`.porter.cfms.api.service.recon.usecases

import `in`.porter.cfms.api.models.recon.CreateReconRequest
import `in`.porter.cfms.api.models.recon.CreateReconResponse
import `in`.porter.cfms.api.service.recon.mappers.CreateReconRequestMapper
import `in`.porter.cfms.api.service.recon.mappers.CreateReconResponseMapper
import `in`.porter.cfms.domain.recon.usecases.CreateRecon
import `in`.porter.cfms.api.service.utils.CommonUtils
import javax.inject.Inject
import org.slf4j.LoggerFactory

class CreateReconService @Inject constructor(
    private val createRecon: CreateRecon,
    private val createReconRequestMapper: CreateReconRequestMapper,
    private val createReconResponseMapper: CreateReconResponseMapper
) {

    private val logger = LoggerFactory.getLogger(CreateReconService::class.java)

    suspend fun createRecon(request: CreateReconRequest): CreateReconResponse {
        logger.info("Received request to create recon: {}", request)

        // Map the request to the domain entity
        val domainRecon = createReconRequestMapper.toDomain(request)

        // Generate a new recon ID
        val generatedReconId = CommonUtils.generateRandomAlphaNumeric(10)
        logger.info("Generated recon ID: {}", generatedReconId)

        // Create a new instance of domainRecon with the generated recon ID
        val reconWithId = domainRecon.copy(reconId = generatedReconId)

        // Call the domain layer to create the recon
        val reconId = createRecon.create(reconWithId)

        logger.info("Recon created successfully with ID: {}", reconId)

        // Return the response
        return createReconResponseMapper.toResponse(reconId, "Recon created successfully")
    }
}
