package `in`.porter.cfms.api.models.courierpartner


  data class FetchCpRecordsApiResponse(
    val data: List<DataItem>,        // List of data items
    val pagination: Pagination
  )

  data class DataItem(
    val id: Int,
    val createdAt: String,          // ISO 8601 Date format as String
    val cpId: Int,                  // Courier partner ID
    val franchiseId: Int,           // Franchise ID
    val manifestImageUrl: String,  // URL to the manifest image
    val courierPartnerName: String // Name of the courier partner
  )

  data class Pagination(
    val currentPage: Int,           // Current page number
    val pageSize: Int,              // Page size
    val totalRecords: Int,          // Total number of records
    val totalPages: Int             // Total number of pages
  )

