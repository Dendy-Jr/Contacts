package ui.dendi.contacts.presentation.delegate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ui.dendi.contacts.core.delegate.SectionPhoneNumberDelegate
import ui.dendi.contacts.domain.model.PhoneNumber
import javax.inject.Inject

class SectionPhoneNumberImpl @Inject constructor() : SectionPhoneNumberDelegate {

    override var phoneNumber: PhoneNumber by mutableStateOf(PhoneNumber())

    override fun updatePhoneNumber(number: String) {
        phoneNumber = phoneNumber.copy(number = number)
    }

    override fun updatePhoneNumberLabel(label: String) {
        phoneNumber = phoneNumber.copy(label = label)
    }

    override fun updatePhoneNumberType(type: String) {
        phoneNumber = phoneNumber.copy(type = type)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface SectionPhoneNumberModule {

    @ViewModelScoped
    @Binds
    fun binds(impl: SectionPhoneNumberImpl): SectionPhoneNumberDelegate
}