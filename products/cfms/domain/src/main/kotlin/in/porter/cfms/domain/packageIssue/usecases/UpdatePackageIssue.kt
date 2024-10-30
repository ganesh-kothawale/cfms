package `in`.porter.cfms.domain.packageIssue.usecases


import `in`.porter.cfms.domain.exceptions.CfmsException
import `in`.porter.cfms.domain.packageIssue.entities.UpdatePackageIssue
import `in`.porter.cfms.domain.packageIssue.repos.PackageIssueRepo
import org.slf4j.LoggerFactory
import javax.inject.Inject

class UpdatePackageIssue @Inject constructor(
    private val packageIssueRepo: PackageIssueRepo
) {

    private val logger = LoggerFactory.getLogger(UpdatePackageIssue::class.java)

    suspend fun invoke(updateRequest: UpdatePackageIssue) {
        try {
            logger.info("Updating package issue for task ID: ${updateRequest.taskId}")

            val recordFound = packageIssueRepo.findByTaskId(updateRequest.taskId)

            if (recordFound == false) {
                throw NoSuchElementException("Package issue with task ID ${updateRequest.taskId} not found.")
            }

            // Perform update operation
            packageIssueRepo.updateAction(updateRequest)

            logger.info("Package issue updated successfully for task ID: ${updateRequest.taskId}")
        } catch (e: NoSuchElementException) {
            logger.error("Update failed: ${e.message}")
            throw NoSuchElementException(e.message ?: "Package issue not found")
        } catch (e: Exception) {
            logger.error("Unexpected error during package issue update", e)
            throw CfmsException("An error occurred while updating the package issue.")
        }
    }
}
