package ai.diago.diago_api.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime

@Entity
@Table(name = "system")
class System(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String = "",

    @Enumerated(EnumType.STRING)
    var status: SystemStatus = SystemStatus.PENDING,

    @CreationTimestamp
    var createdAt: OffsetDateTime? = null,

    @UpdateTimestamp
    var updatedAt: OffsetDateTime? = null,
) {
    @OneToMany(mappedBy = "system", cascade = [CascadeType.ALL], orphanRemoval = true)
    var nodes: MutableSet<Node> = linkedSetOf()

    @OneToMany(mappedBy = "system", cascade = [CascadeType.ALL], orphanRemoval = true)
    var relations: MutableSet<Relation> = linkedSetOf()

}
