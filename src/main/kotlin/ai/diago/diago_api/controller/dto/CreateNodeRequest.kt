package ai.diago.diago_api.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateNodeRequest(
    @field:NotBlank
    @field:Size(max = 128)
    val name: String,

    @field:NotBlank
    @field:Size(max = 64)
    val kind: String,
)
