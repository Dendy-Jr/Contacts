package ui.dendi.contacts.presentation.delegate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ui.dendi.contacts.core.delegate.SectionEmailAddressDelegate
import ui.dendi.contacts.domain.model.EmailAddress
import javax.inject.Inject

class SectionEmailAddressImpl @Inject constructor() : SectionEmailAddressDelegate {

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
@InstallIn(ViewModelComponent::class)
interface SectionEmailAddressModule {

    @ViewModelScoped
    @Binds
    fun binds(impl: SectionEmailAddressImpl): SectionEmailAddressDelegate
}