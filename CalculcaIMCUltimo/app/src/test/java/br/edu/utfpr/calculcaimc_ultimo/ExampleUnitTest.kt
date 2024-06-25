package br.edu.utfpr.calculcaimc_ultimo

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val imc = MainActivity.Calculo()

        assertEquals(24.69, imc.calculaIMC(80.0, 1.80), 0.01)
    }
}