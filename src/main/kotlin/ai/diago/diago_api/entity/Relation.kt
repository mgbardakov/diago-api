package ai.diago.diago_api.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.type.SqlTypes
import tools.jackson.databind.JsonNode
import tools.jackson.databind.node.JsonNodeFactory
import java.time.OffsetDateTime

@Entity
@Table(name = "relation")
class Relation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var kind: String = "",

    @JdbcTypeCode(SqlTypes.JSON)
    var details: JsonNode = JsonNodeFactory.instance.objectNode(),

    @CreationTimestamp
    var createdAt: OffsetDateTime? = null,

    @UpdateTimestamp
    var updatedAt: OffsetDateTime? = null,
) {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    lateinit var system: System

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    lateinit var fromNode: Node

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    lateinit var toNode: Node

}
