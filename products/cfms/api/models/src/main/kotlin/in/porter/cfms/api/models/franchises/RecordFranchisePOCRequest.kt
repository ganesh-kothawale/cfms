package `in`.porter.cfms.api.models.franchises

import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.annotations.Nullable

data class RecordFranchisePOCRequest(
    val name: String,
    @JsonProperty("mobile") val primaryNumber: String,
    val email: String
)