package ai.diago.diago_api.controller.dto.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SystemGraphValidator::class])
annotation class ValidSystemGraph(
    val message: String = "relations must reference existing nodes, be unique, and match node kinds",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
