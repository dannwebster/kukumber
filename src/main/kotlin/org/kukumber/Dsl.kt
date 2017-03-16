package org.kukumber

import java.util.*



class Gherkin(setup: () -> Unit) {
    init {
        setup()
    }
}
class Scenario(description: String) {

    companion object {
        val stepDefs = HashMap<String, Function<Unit>>()
    }

    val testSteps = ArrayList<Function<Unit>>()

    fun test() {
        testSteps.forEach { f -> f.run {  }}
    }
    fun TestStep(description: String, step: Function<Unit>): Scenario {
        stepDefs[description] = step
        testSteps += step
        return this
    }

    fun TestStep(description: String): Scenario {
        val step = stepDefs.getOrElse(description) { throw IllegalArgumentException("'$description' does not exist")}
        testSteps += step
        return this
    }

    fun Given(description: String): Scenario { return TestStep(description) }
    fun When(description: String) : Scenario { return TestStep(description) }
    fun Then(description: String) : Scenario { return TestStep(description) }
    fun And(description: String) : Scenario { return TestStep(description) }

    fun Given(description: String, step: Function<Unit>) : Scenario { return TestStep(description, step)}
    fun When(description: String, step: Function<Unit>) : Scenario { return TestStep(description, step)}
    fun Then(description: String, step: Function<Unit>) : Scenario { return TestStep(description, step)}
    fun And(description: String, step: Function<Unit>) : Scenario { return TestStep(description, step)}

    fun Background(description: String, step: Function<Unit>) {}

    fun Table(table: () -> String): Scenario {
        val lines = table().split("\n")
        val headerLine = lines[0]
        val headers = headerLine.split("|")
        lines.take(2)
        lines
            .map { it.split("|") }
            .map { TableRow(headers, it) }
            .toCollection(ArrayList<TableRow>())
        return this
    }

}


data class TableRow(val headers: List<String>, val values: List<String>) {
    val cells: Map<String, String> = headers.zip(values).toMap()
    fun get(header: String) = cells[header]
}