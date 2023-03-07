package ui.dendi.contacts.core.delegate.impl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ui.dendi.contacts.core.delegate.UpdateEmailAddressContact
import ui.dendi.contacts.domain.model.EmailAddress
import javax.inject.Inject
import javax.inject.Singleton

class UpdateEmailAddressContactSectionImpl @Inject constructor() :
    UpdateEmailAddressContact {

    override var emailAddress: EmailAddress by mutableStateOf(EmailAddress())

    override fun updateEmailAddress(link: String) {
        emailAddress = emailAddress.copy(link = link)
    }

    override fun updateEmailAddressType(type: String) {
        emailAddress = emailAddress.copy(type = type)
    }

    override fun updateEmailAddressLabel(label: String) {
        emailAddress = emailAddress.copy(label = label)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface UpdateEmailAddressContactSectionModule {

    @Singleton
    @Binds
    fun binds(impl: UpdateEmailAddressContactSectionImpl): UpdateEmailAddressContact
}