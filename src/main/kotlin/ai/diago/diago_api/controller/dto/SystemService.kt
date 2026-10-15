package ai.diago.diago_api.controller.dto

import ai.diago.diago_api.entity.System
import ai.diago.diago_api.entity.SystemRepository
import ai.diago.diago_api.entity.Node
import ai.diago.diago_api.entity.Relation
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tools.jackson.databind.ObjectMapper

@Service
class SystemService(
    private val systemRepository: SystemRepository,
    private val objectMapper: ObjectMapper,
) {
    @Transactional
    fun create(request: CreateSystemRequest): System {
        val system = System(name = request.name.trim())
        val nodesByName = linkedMapOf<String, Node>()

        request.nodes.forEach { requestNode ->
            val name = requestNode.name.trim()

            val node = Node(name = name, kind = requestNode.kind)
            requestNode.spec?.let { node.spec = objectMapper.valueToTree(it) }
            node.system = system
            system.nodes.add(node)
            nodesByName[name] = node
        }

        request.relations.forEach { requestRelation ->
            val fromNode = nodesByName.getValue(requestRelation.from.trim())
            val toNode = nodesByName.getValue(requestRelation.to.trim())

            val relation = Relation(
                kind = requestRelation.kind,
                details = requestRelation.details,
            )
            relation.system = system
            relation.fromNode = fromNode
            relation.toNode = toNode
            system.relations.add(relation)
        }

        return systemRepository.save(system)
    }
}
