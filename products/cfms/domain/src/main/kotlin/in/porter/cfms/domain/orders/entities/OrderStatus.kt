package `in`.porter.cfms.domain.orders.entities

enum class OrderStatus {
    OutForPickup,
    ReachedPorterHub,
    ReturnInitiated,
    PickedUp,
    Connected,
    InTransit,
    PickupPending
}