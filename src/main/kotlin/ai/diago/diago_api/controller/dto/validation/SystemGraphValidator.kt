package ai.diago.diago_api.controller.dto.validation

import ai.diago.diago_api.controller.dto.CreateSystemRequest
import ai.diago.diago_api.entity.RelationKind
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class SystemGraphValidator : ConstraintValidator<ValidSystemGraph, CreateSystemRequest> {
    override fun isValid(value: CreateSystemRequest?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }

        val nodesByName = value.nodes.associateBy { it.name.trim() }
        val relationKeys = value.relations.map { relation ->
            RelationKey(relation.from.trim(), relation.to.trim(), relation.kind)
        }
        val hasDuplicateRelations = relationKeys.size != relationKeys.toSet().size
        val hasValidRelations = value.relations.all { relation ->
            val fromName = relation.from.trim()
            val toName = relation.to.trim()
            val fromNode = nodesByName[fromName]
            val toNode = nodesByName[toName]

            fromNode != null &&
                toNode != null &&
                fromName != toName &&
                relation.kind in fromNode.kind.outgoingRelationKinds &&
                relation.kind in toNode.kind.incomingRelationKinds
        }
        val isValid = !hasDuplicateRelations && hasValidRelations

        if (!isValid) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate(context.defaultConstraintMessageTemplate)
                .addPropertyNode("relations")
                .addConstraintViolation()
        }

        return isValid
    }

    private data class RelationKey(
        val from: String,
        val to: String,
        val kind: RelationKind,
    )
}
