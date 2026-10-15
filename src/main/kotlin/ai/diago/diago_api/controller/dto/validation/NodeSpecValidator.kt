package ai.diago.diago_api.controller.dto.validation

import ai.diago.diago_api.controller.dto.CreateNodeRequest
import ai.diago.diago_api.entity.NodeKind
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class NodeSpecValidator : ConstraintValidator<ValidNodeSpec, CreateNodeRequest> {
    override fun isValid(value: CreateNodeRequest?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }

        val isValid = when (value.kind) {
            NodeKind.GENERATED_API -> value.spec?.openApi != null
            NodeKind.GENERATED_UI -> value.spec != null && value.spec.openApi == null
            else -> value.spec == null
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate(context.defaultConstraintMessageTemplate)
                .addPropertyNode("spec")
                .addConstraintViolation()
        }

        return isValid
    }
}
