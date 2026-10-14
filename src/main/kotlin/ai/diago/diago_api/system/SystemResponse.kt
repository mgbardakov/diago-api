package ai.diago.diago_api.system

import ai.diago.diago_api.entity.System
import ai.diago.diago_api.entity.SystemStatus
import java.time.OffsetDateTime

data class SystemResponse(
    val id: Long,
    val name: String,
    val status: SystemStatus,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
) {
    companion object {
        fun from(system: System) = SystemResponse(
            id = checkNotNull(system.id),
            name = system.name,
            status = system.status,
            createdAt = checkNotNull(system.createdAt),
            updatedAt = checkNotNull(system.updatedAt),
        )
    }
}
