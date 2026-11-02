package com.shelfvalue.beta

import org.junit.Assert.*
import org.junit.Test

class ComparisonEngineTest {
    private fun o(p:String,q:String,u:ComparisonEngine.Unit,n:String="1")=ComparisonEngine.Offer(p,q,u,n)
    private fun rejects(block: () -> kotlin.Unit) { try { block(); fail("Expected IllegalArgumentException") } catch (_: IllegalArgumentException) {} }
    @Test fun sameUnitAndSavings() {
        val r=ComparisonEngine.compare(o("120","500",ComparisonEngine.Unit.G),o("150","500",ComparisonEngine.Unit.G))
        assertEquals("A",r.winner);assertEquals("240.00",r.unitPriceA);assertEquals("60.00",r.savingsPerUnit);assertEquals("20.0",r.savingsPercent)
    }
    @Test fun multipacksAndMixedWeight() {
        val r=ComparisonEngine.compare(o("320","500",ComparisonEngine.Unit.G,"2"),o("500","1",ComparisonEngine.Unit.KG))
        assertEquals("A",r.winner);assertEquals("36.0",r.savingsPercent)
    }
    @Test fun compatibleVolumeTie() { assertEquals("TIE",ComparisonEngine.compare(o("180","750",ComparisonEngine.Unit.ML),o("240","1",ComparisonEngine.Unit.L)).winner) }
    @Test fun preciseDecimalTie() { assertEquals("TIE",ComparisonEngine.compare(o("0.30","3",ComparisonEngine.Unit.ITEM),o("0.10","1",ComparisonEngine.Unit.ITEM)).winner) }
    @Test fun freeItem() { assertEquals("100.0",ComparisonEngine.compare(o("0","1",ComparisonEngine.Unit.ITEM),o("2","1",ComparisonEngine.Unit.ITEM)).savingsPercent) }
    @Test fun largeNumbers() { assertEquals("B",ComparisonEngine.compare(o("999999999999","999999",ComparisonEngine.Unit.ITEM),o("999999999998","999999",ComparisonEngine.Unit.ITEM)).winner) }
    @Test fun exactNearTie() { assertEquals("A",ComparisonEngine.compare(o("1","3",ComparisonEngine.Unit.ITEM),o("0.333333333334","1",ComparisonEngine.Unit.ITEM)).winner) }
    @Test fun urduDigitsAndDecimal() { assertEquals("TIE",ComparisonEngine.compare(o("۱۲۰","۵۰۰",ComparisonEngine.Unit.G),o("120","0٫5",ComparisonEngine.Unit.KG)).winner) }
    @Test fun rejectsZeroAndNegative() { rejects { o("1","0",ComparisonEngine.Unit.G) }; rejects { o("1","-1",ComparisonEngine.Unit.G) };rejects { o("-1","1",ComparisonEngine.Unit.G) } }
    @Test fun rejectsInvalidValuesAndPacks() { rejects { o("abc","1",ComparisonEngine.Unit.G) }; rejects { o("1","1",ComparisonEngine.Unit.G,"0") }; rejects { o("1","1",ComparisonEngine.Unit.G,"1.5") } }
    @Test fun rejectsIncompatible() { rejects { ComparisonEngine.compare(o("1","1",ComparisonEngine.Unit.G),o("1","1",ComparisonEngine.Unit.L)) } }
    @Test fun outputRounding() { val r=ComparisonEngine.compare(o("1","3",ComparisonEngine.Unit.ITEM),o("1","4",ComparisonEngine.Unit.ITEM)); assertEquals("B",r.winner);assertEquals("0.08",r.savingsPerUnit);assertEquals("25.0",r.savingsPercent) }
}
