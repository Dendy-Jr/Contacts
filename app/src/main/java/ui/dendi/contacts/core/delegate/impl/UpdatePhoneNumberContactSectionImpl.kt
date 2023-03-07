package ui.dendi.contacts.core.delegate.impl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ui.dendi.contacts.core.delegate.UpdatePhoneNumberContact
import ui.dendi.contacts.domain.model.PhoneNumber
import javax.inject.Inject
import javax.inject.Singleton

class UpdatePhoneNumberContactSectionImpl @Inject constructor() : UpdatePhoneNumberContact {

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
@InstallIn(SingletonComponent::class)
interface UpdatePhoneNumberContactSectionModule {

    @Singleton
    @Binds
    fun binds(impl: UpdatePhoneNumberContactSectionImpl): UpdatePhoneNumberContact
}