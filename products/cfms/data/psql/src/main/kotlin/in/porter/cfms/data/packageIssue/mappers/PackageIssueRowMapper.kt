package `in`.porter.cfms.data.packageIssue.mappers

import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.recon.ReconTable
import `in`.porter.cfms.data.packageIssue.records.PackageIssueRecord
import org.jetbrains.exposed.sql.ResultRow
import org.slf4j.LoggerFactory
import javax.inject.Inject

class PackageIssueRowMapper @Inject constructor() {

    private val logger = LoggerFactory.getLogger(PackageIssueRowMapper::class.java)

    fun toRecord(resultRow: ResultRow): PackageIssueRecord {
        logger.info("Mapping result row to PackageIssueRecord")
        return PackageIssueRecord(
            reconId = resultRow[ReconTable.reconId],
            orderId = resultRow[ReconTable.orderId],
            taskId = resultRow[ReconTable.taskId],
            teamId = resultRow[ReconTable.teamId],
            status = resultRow[ReconTable.reconStatus],
            returnRequested = resultRow[ReconTable.returnRequested],
            returnImageUrl = resultRow[ReconTable.returnImageUrl]
                ?.split(",")
                ?.mapNotNull { it.trim().takeIf { trimmed -> trimmed.isNotEmpty() } },
            action = resultRow[ReconTable.action],
            senderMobile = resultRow[OrdersTable.senderMobile],
            senderName = resultRow[OrdersTable.senderName],
            franchiseId = resultRow[OrdersTable.franchiseId],
            awbNumber = resultRow[OrdersTable.awbNumber],
            createdAt = resultRow[ReconTable.createdAt],
            updatedAt = resultRow[ReconTable.updatedAt]
        )
    }
}
