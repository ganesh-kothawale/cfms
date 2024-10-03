package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.orders.entities.AssociationDetails
import `in`.porter.cfms.domain.orders.entities.AssociationDetails as DomainAssociationDetails
import org.jetbrains.exposed.sql.ResultRow
import javax.inject.Inject

class AssociationDetailsMapper @Inject constructor() {
    fun fromResultRow(row: ResultRow): AssociationDetails {
        return AssociationDetails(
            franchiseId = row[OrdersTable.franchiseId],
            teamId = row[OrdersTable.teamId]
        )
    }

    fun toDomain(entity: AssociationDetails): DomainAssociationDetails {
        return DomainAssociationDetails(
            franchiseId = entity.franchiseId,
            teamId = entity.teamId
        )
    }
}