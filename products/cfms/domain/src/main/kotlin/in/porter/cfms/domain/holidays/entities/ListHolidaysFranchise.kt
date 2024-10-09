package `in`.porter.cfms.domain.holidays.entities

data class ListHolidaysFranchise(
    val franchiseId: String,
    val franchiseName: String,
    val poc: FranchisePoc,        // Nested POC object
    val address: FranchiseAddress // Nested address object
)

data class FranchisePoc(
    val name: String,
    val contact: String
)

data class FranchiseAddress(
    val gpsAddress: String,
    val city: String,
    val state: String
)
