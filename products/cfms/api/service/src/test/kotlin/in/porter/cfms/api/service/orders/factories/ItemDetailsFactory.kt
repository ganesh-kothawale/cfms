package `in`.porter.cfms.api.service.orders.factories

import `in`.porter.cfms.api.models.orders.ItemDetails as ApiItemDetails
import `in`.porter.cfms.domain.orders.entities.ItemDetails as DomainItemDetails
import `in`.porter.cfms.domain.orders.entities.Dimensions as DomainDimensions
import `in`.porter.cfms.api.models.orders.Dimensions as ApiDimensions

object ItemDetailsFactory {

    fun buildApiDimensions(): ApiDimensions {
        return ApiDimensions(
            length = 10.0,
            breadth = 5.0,
            height = 2.0
        )
    }

    fun buildDomainDimensions(): DomainDimensions {
        return DomainDimensions(
            length = 10.0,
            breadth = 5.0,
            height = 2.0
        )
    }

    fun buildApiItemDetails(): ApiItemDetails {
        return ApiItemDetails(
            materialType = "Plastic",
            materialWeight = 500,
            dimensions = buildApiDimensions()
        )
    }

    fun buildDomainItemDetails(): DomainItemDetails {
        return DomainItemDetails(
            materialType = "Plastic",
            materialWeight = 500,
            dimensions = buildDomainDimensions()
        )
    }
}