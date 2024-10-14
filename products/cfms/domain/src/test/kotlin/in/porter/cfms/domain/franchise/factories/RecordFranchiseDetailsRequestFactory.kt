package `in`.porter.cfms.domain.franchise.factories
import `in`.porter.cfms.domain.usecases.entities.RecordFranchiseDetailsRequest
import `in`.porter.cfms.domain.usecases.entities.RecordFranchisePOCRequest
import `in`.porter.cfms.domain.franchise.FranchiseStatus
import `in`.porter.cfms.domain.usecases.entities.RecordFranchiseAddressRequest
import java.math.BigDecimal
import java.time.Instant
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
            cutOffTime = Instant.now(),
            startTime = Instant.now(),
            endTime = Instant.now(),
            porterHubName = "Hub 1",
            franchiseGst = "GST123",
            franchisePan = "PAN123",
            franchiseCanceledCheque = "Cheque",
            kamUser = "1",
            showCrNumber = true
        )
    }
}