package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.SenderDetails
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class SenderDetailsMapper @Inject constructor(
    private val personalInfoMapper: SenderPersonalInfoMapper,
    private val addressMapper: SenderAddressMapper,
    private val locationMapper: SenderLocationMapper
) {
    fun fromResultRow(row: ResultRow): SenderDetails {
        return SenderDetails(
            personalInfo = personalInfoMapper.fromResultRow(row),
            address = addressMapper.fromResultRow(row),
            location = locationMapper.fromResultRow(row)
        )
    }
}