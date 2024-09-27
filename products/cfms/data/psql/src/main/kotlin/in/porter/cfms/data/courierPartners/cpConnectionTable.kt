package `in`.porter.cfms.data.courierPartners

import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object CpConnectionTable : IntIdTable("cp_connection_record") {
  val createdAt = timestampWithoutTZAsInstant("created_at")
  val cpId = integer("cp_id")
  val franchiseId = integer("franchise_id")
  val manifestImageUrl = varchar("manifest_image_url", 255).nullable()
}