package ai.diago.diago_api.controller.dto.validation

import ai.diago.diago_api.controller.dto.CreateNodeRequest
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class UniqueNodeNamesValidator : ConstraintValidator<UniqueNodeNames, List<CreateNodeRequest>> {
    override fun isValid(value: List<CreateNodeRequest>?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }

        val nodeNames = value.map { it.name.trim() }
        return nodeNames.size == nodeNames.toSet().size
    }
}
