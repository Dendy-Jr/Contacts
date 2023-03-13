package ui.dendi.contacts.presentation.delegate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ui.dendi.contacts.core.delegate.SectionWebsiteDelegate
import ui.dendi.contacts.domain.model.Website
import javax.inject.Inject

class SectionWebsiteImpl @Inject constructor() : SectionWebsiteDelegate {

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
@InstallIn(ViewModelComponent::class)
interface SectionWebsiteModule {

    @ViewModelScoped
    @Binds
    fun binds(impl: SectionWebsiteImpl): SectionWebsiteDelegate
}