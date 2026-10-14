package ai.diago.diago_api.controller

import ai.diago.diago_api.controller.dto.CreateSystemRequest
import ai.diago.diago_api.controller.dto.SystemResponse
import ai.diago.diago_api.controller.dto.SystemService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/systems")
class SystemController(
    private val systemService: SystemService,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: CreateSystemRequest): SystemResponse {
        val system = systemService.create(request)
        return SystemResponse.from(system)
    }
}
