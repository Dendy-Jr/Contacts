package ui.dendi.contacts.domain.use_case

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import ui.dendi.contacts.domain.model.ContactInputValidationType

class ValidateContactInputUseCaseTest {

    private val validateContactInputUseCase = ValidateContactInputUseCase()

    @Test
    fun `test firs name and last name fields return validation type empty field`() {
        val result1 = validateContactInputUseCase("", "")
        val result2 = validateContactInputUseCase("   ", "  ")
        assertThat(result1).isEqualTo(ContactInputValidationType.EmptyField)
        assertThat(result2).isEqualTo(ContactInputValidationType.EmptyField)
    }

    @Test
    fun `test firs name and last name fields return validation type contains number`() {
        val result1 = validateContactInputUseCase("John123", "Doe")
        val result2 = validateContactInputUseCase("Jane", "Doe456")
        val result3 = validateContactInputUseCase("123", "456")
        assertThat(result1).isEqualTo(ContactInputValidationType.FieldContainsNumber)
        assertThat(result2).isEqualTo(ContactInputValidationType.FieldContainsNumber)
        assertThat(result3).isEqualTo(ContactInputValidationType.FieldContainsNumber)
    }

    @Test
    fun `test firs name and last name fields return validation type contains special char`() {
        val result1 = validateContactInputUseCase("John@", "Doe")
        val result2 = validateContactInputUseCase("Jane", "Doe#")
        val result3 = validateContactInputUseCase("$", "%")
        assertThat(result1).isEqualTo(ContactInputValidationType.FieldContainsSpecialCharacter)
        assertThat(result2).isEqualTo(ContactInputValidationType.FieldContainsSpecialCharacter)
        assertThat(result3).isEqualTo(ContactInputValidationType.FieldContainsSpecialCharacter)
    }

    @Test
    fun `test firs name and last name fields return validation type valid`() {
        val result = validateContactInputUseCase("John", "Doe")
        assertThat(result).isEqualTo(ContactInputValidationType.Valid)
    }
}