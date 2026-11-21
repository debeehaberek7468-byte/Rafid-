package com.example.model

enum class LawSimulatorType {
    COULOMB,
    ELECTRIC_FIELD,
    CURRENT,
    OHM_LAW,
    SERIES_RESISTORS,
    PARALLEL_RESISTORS,
    EMF,
    ELECTRIC_POWER,
    ELECTRIC_ENERGY_COST,
    TRANSFORMER_RATIO,
    TRANSFORMER_EFFICIENCY,
    JOULE_HEAT
}

data class DefinitionItem(
    val id: String,
    val chapterNumber: Int,
    val title: String,
    val content: String,
    val isMinisterial: Boolean = false
)

data class ReasoningItem(
    val id: String,
    val chapterNumber: Int,
    val question: String,
    val answer: String,
    val isMinisterial: Boolean = true
)

data class SummaryCardItem(
    val id: String,
    val chapterNumber: Int,
    val title: String,
    val categoryLabel: String,
    val points: List<String>
)

data class PhysicsLaw(
    val id: String,
    val chapterNumber: Int,
    val name: String,
    val formula: String,
    val variables: String,
    val unitNote: String,
    val simulatorType: LawSimulatorType? = null
)

data class SolvedProblem(
    val id: String,
    val chapterNumber: Int,
    val title: String,
    val problemText: String,
    val givenData: List<String>,
    val formulaUsed: String,
    val steps: List<String>,
    val finalAnswer: String,
    val isMinisterial: Boolean = true
)

data class PhysicsChapter(
    val number: Int,
    val title: String,
    val subtitle: String,
    val badgeText: String,
    val definitions: List<DefinitionItem>,
    val reasonings: List<ReasoningItem>,
    val summaries: List<SummaryCardItem>,
    val laws: List<PhysicsLaw>,
    val solvedProblems: List<SolvedProblem>
)

data class QuizQuestion(
    val id: String,
    val chapterNumber: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val isTrueFalse: Boolean = false
)

data class MinisterialTip(
    val id: String,
    val title: String,
    val category: String,
    val details: String
)

data class SearchResultItem(
    val id: String,
    val chapterNumber: Int,
    val typeLabel: String,
    val title: String,
    val snippet: String,
    val favoriteType: FavoriteType,
    val fullContent: String
)
