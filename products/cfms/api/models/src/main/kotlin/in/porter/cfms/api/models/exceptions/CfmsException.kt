package `in`.porter.cfms.api.models.exceptions

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties("stack_trace", "cause", "suppressed", "localized_message")
open class CfmsException(override val message: String) : Exception(message)
