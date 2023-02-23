package ui.dendi.contacts.core.extension

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StringExtensionTest {

    @Test
    fun `test string contains number returns true`() {
        val input = "abc123def"
        assertThat(input.containsNumber()).isTrue()
    }

    @Test
    fun `test string contains number returns false`() {
        val input = "abcdef"
        assertThat(input.containsNumber()).isFalse()
    }

    @Test
    fun `test string contains special char returns true`() {
        val input = "abc@def"
        assertThat(input.containsSpecialChar()).isTrue()
    }

    @Test
    fun `test string contains special char returns false`() {
        val input = "abcdef"
        assertThat(input.containsSpecialChar()).isFalse()
    }
}