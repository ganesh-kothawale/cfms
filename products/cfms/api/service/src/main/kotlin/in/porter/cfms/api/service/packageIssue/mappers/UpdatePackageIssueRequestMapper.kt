package `in`.porter.cfms.api.service.packageIssue.mappers

import `in`.porter.cfms.api.models.exceptions.CfmsException
import `in`.porter.cfms.api.models.packageIssue.Action
import `in`.porter.cfms.api.models.packageIssue.UpdatePackageIssueRequest
import `in`.porter.cfms.domain.packageIssue.entities.UpdatePackageIssue
import javax.inject.Inject

class UpdatePackageIssueRequestMapper @Inject constructor() {

    // This function maps the UpdatePickupTaskRequest to the PickupTask domain entity
    fun toDomain(request: UpdatePackageIssueRequest): UpdatePackageIssue {
        validateActionEnum(request.action)
        return UpdatePackageIssue(
            taskId = request.taskId,
            action = request.action
        )
    }

    private fun validateActionEnum(action: String) {
        try {
            Action.valueOf(action) // Check if action matches any enum value
        } catch (e: IllegalArgumentException) {
            throw CfmsException("Invalid action value: $action. Expected one of ${Action.entries.joinToString(", ")}.")
        }
    }
}