package `in`.porter.cfms.domain.pickuptasks.repos

import `in`.porter.cfms.domain.pickuptasks.entities.PickupDetails

interface PickupDetailsRepo {
    suspend fun create(pickupDetails: PickupDetails): String
}
