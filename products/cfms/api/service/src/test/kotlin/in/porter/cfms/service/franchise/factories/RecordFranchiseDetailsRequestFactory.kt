package `in`.porter.cfms.service.franchise.factories

import `in`.porter.cfms.api.models.franchises.RecordFranchiseAddressRequest
import `in`.porter.cfms.api.models.franchises.RecordFranchiseDetailsRequest
import `in`.porter.cfms.api.models.franchises.RecordFranchisePOCRequest
import `in`.porter.cfms.api.models.FranchiseStatus
import java.math.BigDecimal
import java.time.Instant

class RecordFranchiseDetailsRequestFactory {

    fun createDefaultRequest(): RecordFranchiseDetailsRequest {
        return RecordFranchiseDetailsRequest(
            poc = RecordFranchisePOCRequest(
                name = "John Doe",
                primaryNumber = "1234567890",
                email = "john.doe@example.com"
            ),
            address = RecordFranchiseAddressRequest(
                address = "123 Main St",
                city = "CityName",
                state = "StateName",
                pincode = "123456",
                latitude = BigDecimal.valueOf(12.34),
                longitude = BigDecimal.valueOf(56.78)
            ),
            customerShipmentLabelRequired = true,
            radiusCoverage = BigDecimal.valueOf(50.0),
            hlpEnabled = true,
            status = FranchiseStatus.Active,
            daysOfOperation = "Monday,Tuesday",
            cutOffTime = Instant.now(),
            startTime = Instant.now(),
            endTime = Instant.now(),
            porterHubName = "Hub 1",
            franchiseGst = "GST123",
            franchisePan = "PAN123",
            franchiseCanceledCheque = "CancelledCheque",
            kamUser = "Manager 1",
            showCrNumber = true
        )
    }
}
