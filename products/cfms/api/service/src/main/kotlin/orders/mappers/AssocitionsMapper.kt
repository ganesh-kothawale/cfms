package orders.mappers

import `in`.porter.cfms.api.models.orders.AssociationDetails
import `in`.porter.cfms.domain.orders.entities.AssociationDetails as DomainAssociationDetails

class AssociationDetailsMapper {
    fun map(associationDetails: AssociationDetails): DomainAssociationDetails {
        return DomainAssociationDetails(
            franchiseId  = associationDetails.franchiseId,
         teamId= associationDetails.teamId
                    )
    }
}