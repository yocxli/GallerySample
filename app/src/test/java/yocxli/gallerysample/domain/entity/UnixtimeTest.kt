package yocxli.gallerysample.domain.entity

import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class UnixtimeTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getValue() {
        val unixtime = Unixtime(0)

        assertThat(unixtime.value).isEqualTo(0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getValue_negativeValue() {
        Unixtime(-1)
    }

    @Test
    fun compareTo() {
        val unixtime1 = Unixtime(100)
        val unixtime2 = Unixtime(101)

        assertThat(unixtime1 > unixtime2).isFalse()
        assertThat(unixtime1 < unixtime2).isTrue()
        assertThat(unixtime1 >= unixtime2).isFalse()
        assertThat(unixtime1 <= unixtime2).isTrue()
    }
}