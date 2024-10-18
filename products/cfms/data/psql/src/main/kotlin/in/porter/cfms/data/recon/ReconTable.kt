package `in`.porter.cfms.data.recon

import `in`.porter.cfms.data.orders.repos.OrdersTable
import `in`.porter.cfms.data.tasks.TasksTable
import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.or

object ReconTable : Table("recon") {
    val id = integer("id").autoIncrement()
    val reconId = varchar("recon_id", 10).uniqueIndex()
    val orderId = varchar("order_id", 10).references(OrdersTable.orderId)
    val taskId = varchar("task_id", 10).references(TasksTable.taskId)  // Foreign key to TasksTable
    val teamId = varchar("team_id", 10)
    // TODO : Add a foreign key constraint to TeamsTable
    //.references(TeamsTable.teamId)  // Foreign key to TeamsTable
    val reconStatus = varchar("recon_status", 20).nullable().check {
        it eq "Pending" or (it eq "Completed") or (it eq "Return Requested")
    }
    val packagingRequired = bool("packaging_required").nullable()
    val prePackagingImageUrl = varchar("pre_packaging_image_url", 255).nullable()
    val shipmentIsEnvelopeOrDocument = bool("shipment_is_envelope_or_document").nullable()
    val shipmentWeight = decimal("shipment_weight", 10, 2).nullable()
    val weightPhotoUrl = varchar("weight_photo_url", 255).nullable()
    val shipmentDimensionsCmOrInch = varchar("shipment_dimensions_cm_or_inch", 10).nullable()
    val shipmentLength = decimal("shipment_length", 10, 2).nullable()
    val shipmentWidth = decimal("shipment_width", 10, 2).nullable()
    val shipmentHeight = decimal("shipment_height", 10, 2).nullable()
    val dimensionsPhotoUrls = text("dimensions_photo_urls").nullable()
    val returnRequested = bool("return_requested").nullable()
    val returnImageUrl = varchar("return_image_url", 255).nullable()
    val createdAt = timestampWithoutTZAsInstant("created_at")
    val updatedAt = timestampWithoutTZAsInstant("updated_at")

    override val primaryKey = PrimaryKey(id, name = "PK_Recon_ID")

    init {
        index(true, reconId)
    }
}
