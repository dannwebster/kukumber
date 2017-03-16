import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by dwebster on 10/21/16.
 */
class PolynomialsTest {

    class Term(val coef: Int, val exponent: Int) {
        val separator = if (coef < 0) " - " else " + "
        val isNonZero = coef != 0
        val abs = Math.abs(coef)

        override fun toString() =
                if (coef == 0)
                    ""
                else if (exponent == 0)
                    "${abs}"
                else if (exponent == 1)
                    "${abs}x"
                else
                    "${abs}x^${exponent}"

    }


    class P(coefs: Array<Int>) {
        val exponents = (0..coefs.size-1).reversed()
        val prefix = if (coefs.getOrElse(0, {0}) < 0) "-" else ""
        var first = true
        val s = coefs
                    .zip(exponents)
                    .map { Term(it.first, it.second) }
                    .filter { it.isNonZero }
                    .joinToString(prefix = prefix, separator = "") {
                        var v = (if (!first) it.separator else "") + it.toString()
                        first = false
                        v
                    }
        override fun toString() = s
    }

    @Test fun oneCoefficientShouldPrintTheCoeficient() {
        assertEquals("4", P(arrayOf(4)).toString())
    }

    @Test fun twoCoefficientShouldPrintFirstOrder() {
        assertEquals("2x + 4", P(arrayOf(2, 4)).toString())
    }

    @Test fun zeroCoefficientsShouldBeSkipped() {
        assertEquals("5x^4 + 2x^2 + 4", P(arrayOf(5, 0, 2, 0, 4)).toString())
    }

    @Test fun leadingNegativeCoeficientShouldLeadWithAMinusSign() {
        assertEquals("-2x + 4", P(arrayOf(-2, 4)).toString())
    }

    @Test fun nonFirstCoefficientsShouldBeSubtracted() {
        assertEquals("2x - 4", P(arrayOf(2, -4)).toString())
    }
}