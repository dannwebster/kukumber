package org.kukumber

import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class BowlingGameTest : Kukumber({
    var game = BowlingGame()

    //Background("Startup")
    //.Given("A New Game") { game = BowlingGame() }

    Scenario("Perfect Game")
            .When("I roll a perfect game") { game + (12 rollsOf 10) }
            .Then("I will score '300'") { assertEquals(300, game.score()) }

    Scenario("Tests")
            .When("I roll <roll> <count> times") { roll: Int, count: Int -> game + (count rollsOf roll) }
            .And("I roll 0 <fill> times") { fill: Int -> game + (fill rollsOf 0) }
            .Then("Then I will score <score>") { score: Int -> assertEquals(score, game.score()) }
            .Table {
                """
                | roll  | count  | fill | score |
                |-------|--------|------|-------|
                | 0     |   20   |   0  |   0   |
                | 1     |   20   |   0  |   20  |
                | 10    |   12   |   0  |   300 |
                """
            }
})
