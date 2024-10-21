package `in`.porter.cfms.data.cpConnections

import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.date

object CpConnectionTable : IntIdTable("cp_connection_record") {
    val cpId = integer("cp_id")
    val franchiseId = varchar("franchise_id", 255)
    val manifestImageUrl = varchar("manifest_image_url", 1024).nullable()
    val createdAt = timestampWithoutTZAsInstant("created_at")
    val updatedAt = timestampWithoutTZAsInstant("updated_at")
}
