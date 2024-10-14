package `in`.porter.cfms.data.factories

import `in`.porter.cfms.domain.franchise.entities.Franchise
import `in`.porter.cfms.domain.franchise.FranchiseStatus
import java.math.BigDecimal
import java.time.Instant

class FranchiseFactory {

    fun create(
        franchiseId: String = "default_id",
        pocName: String = "Default POC Name",
        primaryNumber: String = "1234567890",
        email: String = "default@example.com",
        address: String = "123 Default St",
        latitude: BigDecimal = BigDecimal("0.0"),
        longitude: BigDecimal = BigDecimal("0.0"),
        city: String = "Default City",
        state: String = "Default State",
        pincode: String = "123456",
        porterHubName: String = "Default Hub",
        franchiseGst: String = "GST123456",
        franchisePan: String = "PAN123456",
        franchiseCanceledCheque: String = "Cheque123456",
        status: FranchiseStatus = FranchiseStatus.Active,
        teamId: Int? = null,
        daysOfOperation: String = "Mon-Fri",
        startTime: Instant = Instant.parse("2024-01-01T09:00:00Z"),
        endTime: Instant = Instant.parse("2024-01-01T17:00:00Z"),
        cutOffTime: Instant = Instant.parse("2024-01-01T08:00:00Z"),
        kamUser: String = "Default KAM User",
        hlpEnabled: Boolean = true,
        radiusCoverage: BigDecimal = BigDecimal("5.0"),
        showCrNumber: Boolean = true,
        createdAt: Instant = Instant.now(),
        updatedAt: Instant = Instant.now()
    ): Franchise {
        return Franchise(
            franchiseId,
            pocName,
            primaryNumber,
            email,
            address,
            latitude,
            longitude,
            city,
            state,
            pincode,
            porterHubName,
            franchiseGst,
            franchisePan,
            franchiseCanceledCheque,
            status,
            teamId,
            daysOfOperation,
            startTime,
            endTime,
            cutOffTime,
            kamUser,
            hlpEnabled,
            radiusCoverage,
            showCrNumber,
            createdAt,
            updatedAt
        )
    }
}
