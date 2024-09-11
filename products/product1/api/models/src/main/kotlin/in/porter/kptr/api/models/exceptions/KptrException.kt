package `in`.porter.kptr.api.models.exceptions

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties("stack_trace", "cause", "suppressed", "localized_message")
open class KptrException(override val message: String) : Exception(message)
