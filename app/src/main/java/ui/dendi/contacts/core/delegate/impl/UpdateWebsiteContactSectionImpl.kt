package ui.dendi.contacts.core.delegate.impl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ui.dendi.contacts.core.delegate.UpdateWebsiteContact
import ui.dendi.contacts.domain.model.Website
import javax.inject.Inject
import javax.inject.Singleton

class UpdateWebsiteContactSectionImpl @Inject constructor() : UpdateWebsiteContact {

    override var website: Website by mutableStateOf(Website())

    override fun updateWebsiteLink(link: String) {
        website = website.copy(link = link)
    }

    override fun updateWebsiteLabel(label: String) {
        website = website.copy(label = label)
    }

    override fun updateWebsiteType(type: String) {
        website = website.copy(type = type)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface UpdateWebsiteContactSectionModule {

    @Singleton
    @Binds
    fun binds(impl: UpdateWebsiteContactSectionImpl): UpdateWebsiteContact
}