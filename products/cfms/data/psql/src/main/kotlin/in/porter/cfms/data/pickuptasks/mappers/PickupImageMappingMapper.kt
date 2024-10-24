package `in`.porter.cfms.data.pickuptasks.mappers

import `in`.porter.cfms.data.pickuptasks.records.PickupImageMappingRecord
import `in`.porter.cfms.domain.pickuptasks.entities.PickupImageMapping

class PickupImageMappingMapper {
    fun toDomain(record: PickupImageMappingRecord): PickupImageMapping {
        return PickupImageMapping(
            taskId = record.taskId,
            pickupDetailsId = record.pickupDetailsId,
            orderImage = record.orderImage,
            createdAt = record.createdAt,
            updatedAt = record.updatedAt
        )
    }

    fun toRecord(domain: PickupImageMapping): PickupImageMappingRecord {
        return PickupImageMappingRecord(
            taskId = domain.taskId,
            pickupDetailsId = domain.pickupDetailsId,
            orderImage = domain.orderImage,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}
