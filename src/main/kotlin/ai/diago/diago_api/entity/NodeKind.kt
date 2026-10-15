package ai.diago.diago_api.entity

enum class NodeKind(
    val outgoingRelationKinds: Set<RelationKind>,
    val incomingRelationKinds: Set<RelationKind>,
) {
    POSTGRES(emptySet(), setOf(RelationKind.JDBC)),
    GENERATED_API(
        outgoingRelationKinds = setOf(RelationKind.JDBC, RelationKind.REST),
        incomingRelationKinds = setOf(RelationKind.REST),
    ),
    GENERATED_UI(
        outgoingRelationKinds = setOf(RelationKind.REST),
        incomingRelationKinds = emptySet(),
    ),
}
