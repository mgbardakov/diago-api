package ai.diago.diago_api.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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
@Table(name = "node")
class Node(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String = "",

    @Enumerated(EnumType.STRING)
    var kind: NodeKind,

    @JdbcTypeCode(SqlTypes.JSON)
    var spec: JsonNode = JsonNodeFactory.instance.objectNode(),

    @Enumerated(EnumType.STRING)
    var status: NodeStatus = NodeStatus.PENDING,

    @CreationTimestamp
    var createdAt: OffsetDateTime? = null,

    @UpdateTimestamp
    var updatedAt: OffsetDateTime? = null,
) {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    lateinit var system: System

}
