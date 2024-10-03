package `in`.porter.cfms.api.service.orders.mappers

import `in`.porter.cfms.api.models.orders.AssociationDetails
import javax.inject.Inject
import `in`.porter.cfms.domain.orders.entities.AssociationDetails as DomainAssociationDetails

class AssociationDetailsMapper
@Inject constructor() {
    fun map(associationDetails: AssociationDetails): DomainAssociationDetails {
        return DomainAssociationDetails(
            franchiseId = associationDetails.franchiseId,
            teamId = associationDetails.teamId
        )
    }

    fun fromDomain(domainAssociationDetails: DomainAssociationDetails): AssociationDetails {
        return AssociationDetails(
            franchiseId = domainAssociationDetails.franchiseId,
            teamId = domainAssociationDetails.teamId
        )

    }
}
