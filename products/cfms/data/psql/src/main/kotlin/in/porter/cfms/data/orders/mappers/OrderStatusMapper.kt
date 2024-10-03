package `in`.porter.cfms.data.orders.mappers

import `in`.porter.cfms.domain.orders.entities.OrderStatus
import javax.inject.Inject

class OrderStatusMapper
@Inject constructor()
{
    companion object {
        const val OUT_FOR_PICKUP = "Out For Pickup"
        const val REACHED_PORTER_HUB = "Reached Porter Hub"
        const val READY_FOR_CONNECTION = "Ready For Connection"
        const val HANDED_OVER_TO_COURIER_PARTNER = "Handed over to Courier Partner"
        const val REACHED_TO_COURIER_HUB = "Reached to Courier Hub"
        const val RETURN_INITIATED = "Return Initiated"
        const val COLLECTION_HUB_STATUS = "Collection Hub Status"
        const val PICK_UP_PENDING = "PickupPending"
    }

    fun invoke(cpStatus: String): OrderStatus? = when (cpStatus) {
        OUT_FOR_PICKUP -> OrderStatus.OutForPickup
        REACHED_PORTER_HUB -> OrderStatus.ReachedPorterHub
        RETURN_INITIATED -> OrderStatus.ReturnInitiated
        READY_FOR_CONNECTION -> OrderStatus.PickedUp
        HANDED_OVER_TO_COURIER_PARTNER -> OrderStatus.Connected
        REACHED_TO_COURIER_HUB -> OrderStatus.InTransit
        PICK_UP_PENDING -> OrderStatus.PickupPending
        else -> null
    }
    fun toString(orderStatus: OrderStatus): String? = when (orderStatus) {
        OrderStatus.OutForPickup -> OUT_FOR_PICKUP
        OrderStatus.ReachedPorterHub -> REACHED_PORTER_HUB
        OrderStatus.ReturnInitiated -> RETURN_INITIATED
        OrderStatus.PickedUp -> READY_FOR_CONNECTION
        OrderStatus.Connected -> HANDED_OVER_TO_COURIER_PARTNER
        OrderStatus.InTransit -> REACHED_TO_COURIER_HUB
        OrderStatus.PickupPending -> PICK_UP_PENDING
        else -> null
    }

}