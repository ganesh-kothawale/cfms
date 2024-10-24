package `in`.porter.cfms.domain.franchise.factories
import `in`.porter.cfms.domain.franchise.entities.RecordFranchiseDetailsRequest
import `in`.porter.cfms.domain.franchise.entities.RecordFranchisePOCRequest
import `in`.porter.cfms.domain.franchise.FranchiseStatus
import `in`.porter.cfms.domain.franchise.entities.RecordFranchiseAddressRequest
import java.math.BigDecimal

class RecordFranchiseDetailsRequestFactory {
    fun build(): RecordFranchiseDetailsRequest {
        return RecordFranchiseDetailsRequest(
            franchiseId = "FR123",
            poc = RecordFranchisePOCRequest(
                name = "John Doe",
                primaryNumber = "1234567890",
                email = "john@example.com"
            ),
            address = RecordFranchiseAddressRequest(
                address = "123 Main St",
                city = "City",
                state = "State",
                pincode = "123456",
                latitude = BigDecimal.valueOf(12.34),
                longitude = BigDecimal.valueOf(56.78)
            ),
            customerShipmentLabelRequired = true,
            radiusCoverage = BigDecimal.valueOf(50.0),
            hlpEnabled = true,
            status = FranchiseStatus.Active,
            daysOfOperation = "Monday,Tuesday",
            cutOffTime = "17",
            startTime = "9",
            endTime = "17",
            porterHubName = "Hub 1",
            franchiseGst = "GST123",
            franchisePan = "PAN123",
            franchiseCanceledCheque = "Cheque",
            kamUser = "1",
            showCrNumber = true
        )
    }
}