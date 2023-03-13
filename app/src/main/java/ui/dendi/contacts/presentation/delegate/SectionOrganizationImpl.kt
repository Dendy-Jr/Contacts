package ui.dendi.contacts.presentation.delegate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ui.dendi.contacts.core.delegate.SectionOrganizationDelegate
import ui.dendi.contacts.domain.model.Organization
import javax.inject.Inject

class SectionOrganizationImpl @Inject constructor() : SectionOrganizationDelegate {

    override var organization: Organization by mutableStateOf(Organization())

    override fun updateOrganizationName(name: String) {
        organization = organization.copy(name = name)
    }

    override fun updateOrganizationLabel(label: String) {
        organization = organization.copy(label = label)
    }

    override fun updateJobTitle(jobTitle: String) {
        organization = organization.copy(jobTitle = jobTitle)
    }

    override fun updateJobDescription(jobDescription: String) {
        organization = organization.copy(jobDescription = jobDescription)
    }

    override fun updateDepartment(department: String) {
        organization = organization.copy(department = department)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface SectionOrganizationModule {

    @ViewModelScoped
    @Binds
    fun binds(impl: SectionOrganizationImpl): SectionOrganizationDelegate
}