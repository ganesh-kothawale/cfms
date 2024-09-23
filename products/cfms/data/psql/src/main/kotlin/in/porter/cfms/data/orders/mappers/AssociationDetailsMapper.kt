package `in`.porter.cfms.data.orders.repos

import `in`.porter.cfms.domain.orders.entities.AssociationDetails
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class AssociationDetailsMapper @Inject constructor() {
    fun fromResultRow(row: ResultRow): AssociationDetails {
        return AssociationDetails(
            franchiseId = row[OrdersTable.franchiseId],
            teamId = row[OrdersTable.teamId]
        )
    }
}