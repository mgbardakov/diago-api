package ai.diago.diago_api.controller.dto

import ai.diago.diago_api.entity.NodeKind
import ai.diago.diago_api.controller.dto.validation.ValidNodeSpec
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@ValidNodeSpec
data class CreateNodeRequest(
    @field:NotBlank
    @field:Size(max = 128)
    val name: String,

    val kind: NodeKind,

    @field:Valid
    val spec: GeneratedNodeSpec? = null,
)
