package ui.dendi.contacts.presentation.delegate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.*
import ui.dendi.contacts.R
import ui.dendi.contacts.core.delegate.InputValidationDelegate
import ui.dendi.contacts.core.delegate.SectionMainDelegate
import ui.dendi.contacts.core.model.UiText
import ui.dendi.contacts.domain.model.ContactInputValidationType
import ui.dendi.contacts.domain.use_case.ValidateContactInputUseCase
import javax.inject.Inject
import javax.inject.Provider

class InputValidationImpl @Inject constructor(
    private val validateContactInputUseCase: ValidateContactInputUseCase,
    private val editMainContact: Provider<SectionMainDelegate>,
) : InputValidationDelegate {

    private val job: Job = SupervisorJob()
    private val coroutineScope: CoroutineScope = CoroutineScope(job + Dispatchers.Main.immediate)

    override var enableDoneButton: Boolean by mutableStateOf(false)
    override var screenMessage: UiText? by mutableStateOf(null)
    override var showScreenMessage: Boolean by mutableStateOf(false)

    override fun checkInputValidation() {
        coroutineScope.launch {
            val person = editMainContact.get().person
            val validationResult = validateContactInputUseCase(
                firstName = person.firstName,
                lastName = person.lastName
            )
            processInputValidationType(validationResult)
        }
    }

    override fun processInputValidationType(type: ContactInputValidationType) {
        when (type) {
            ContactInputValidationType.EmptyField -> {
                enableDoneButton = false
                showScreenMessage = true
                screenMessage = UiText.StringResource(resId = R.string.name_cannot_be_empty)
            }
            ContactInputValidationType.FieldContainsNumber -> {
                enableDoneButton = false
                showScreenMessage = true
                screenMessage = UiText.StringResource(resId = R.string.name_cannot_contain_numbers)
            }
            ContactInputValidationType.FieldContainsSpecialCharacter -> {
                enableDoneButton = false
                showScreenMessage = true
                screenMessage =
                    UiText.StringResource(resId = R.string.name_cannot_contain_special_characters)
            }
            ContactInputValidationType.Valid -> {
                enableDoneButton = true
                showScreenMessage = false
            }
        }
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface InputValidationModule {

    @ViewModelScoped
    @Binds
    fun binds(impl: InputValidationImpl): InputValidationDelegate
}