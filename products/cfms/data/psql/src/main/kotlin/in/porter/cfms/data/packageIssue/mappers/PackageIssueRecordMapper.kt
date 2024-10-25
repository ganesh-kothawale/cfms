package `in`.porter.cfms.data.packageIssue.mappers

import `in`.porter.cfms.data.packageIssue.records.PackageIssueRecord
import `in`.porter.cfms.domain.packageIssue.entities.PackageIssue
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PackageIssueRecordMapper @Inject constructor() {
    private val logger = LoggerFactory.getLogger(PackageIssueRecordMapper::class.java)

    fun toDomain(record: PackageIssueRecord): PackageIssue {
        logger.info("Mapping PackageIssueRecord to PackageIssue domain entity: $record")
        return PackageIssue(
            reconId = record.reconId,
            orderId = record.orderId,
            taskId = record.taskId,
            teamId = record.teamId,
            status = record.status,
            returnRequested = record.returnRequested,
            returnImageUrl = record.returnImageUrl,
            action = record.action,
            senderMobile = record.senderMobile,
            senderName = record.senderName,
            franchiseId = record.franchiseId,
            awbNumber = record.awbNumber,
            createdAt = record.createdAt,
            updatedAt = record.updatedAt
        )
    }

    fun toRecord(domain: PackageIssue): PackageIssueRecord {
        logger.info("Mapping PackageIssue domain entity to PackageIssueRecord: $domain")
        return PackageIssueRecord(
            reconId = domain.reconId,
            orderId = domain.orderId,
            taskId = domain.taskId,
            teamId = domain.teamId,
            status = domain.status,
            returnRequested = domain.returnRequested,
            returnImageUrl = domain.returnImageUrl,
            action = domain.action,
            senderMobile = domain.senderMobile,
            senderName = domain.senderName,
            franchiseId = domain.franchiseId,
            awbNumber = domain.awbNumber,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }

}
