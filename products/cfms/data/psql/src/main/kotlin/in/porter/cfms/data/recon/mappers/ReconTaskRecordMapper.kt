package `in`.porter.cfms.data.recon.mappers

import `in`.porter.cfms.data.recon.records.ReconTaskRecord
import `in`.porter.cfms.domain.recon.entities.ReconTask
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class ReconTaskRecordMapper @Inject constructor() : Traceable {
    fun toDomain(record: ReconTaskRecord): ReconTask {
        return ReconTask(
            taskId = record.taskId,
            crId = record.crId,
            cpName = record.cpName,
            cpImageUrl = record.cpImageUrl,
            shipmentImageUrl = record.shipmentImageUrl,
            awb = record.awb,
            status = record.status
        )
    }

    fun toRecord(recon: ReconTask): ReconTaskRecord {
        return ReconTaskRecord(
            taskId = recon.taskId,
            crId = recon.crId,
            cpName = recon.cpName,
            cpImageUrl = recon.cpImageUrl,
            shipmentImageUrl = recon.shipmentImageUrl,
            awb = recon.awb,
            status = recon.status
        )
    }
}