package `in`.porter.cfms.data.auditlogs

import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.CurrentTimestamp
import org.jetbrains.exposed.sql.or

object AuditLogsTable : Table("audit_logs") {
    val id = integer("id").autoIncrement()
    val auditLogId = varchar("audit_log_id", 10).uniqueIndex()
    val entityId = varchar("entity_id", 255)
    val entityType = varchar("entity_type", 50).check { it eq "Order" or (it eq "Recon") or (it eq "Task") or (it eq "Franchise") or (it eq "Holiday") }
    val status = varchar("status", 50).check { it eq "Created" or (it eq "Updated") or (it eq "Deleted") or (it eq "Pending") or (it eq "Shipped") or (it eq "Delivered") or (it eq "Cancelled") or (it eq "Return Requested") or (it eq "Completed") or (it eq "In Progress")or (it eq "Failed") }
    val message = varchar("message", 255).nullable()
    val updatedBy = integer("updated_by")
    val changeTimestamp = timestampWithoutTZAsInstant("change_timestamp").defaultExpression(CurrentTimestamp())
    val createdAt = timestampWithoutTZAsInstant("created_at").defaultExpression(CurrentTimestamp())
    val updatedAt = timestampWithoutTZAsInstant("updated_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id, name = "PK_AuditLogs_ID")
    init {
        index(true, auditLogId)
    }
}
