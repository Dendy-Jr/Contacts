package ui.dendi.contacts.core.delegate.impl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ui.dendi.contacts.core.delegate.UpdateOrganizationContact
import ui.dendi.contacts.domain.model.Organization
import javax.inject.Inject
import javax.inject.Singleton

class UpdateOrganizationContactSectionImpl @Inject constructor() :
    UpdateOrganizationContact {

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
@InstallIn(SingletonComponent::class)
interface UpdateOrganizationContactSectionModule {

    @Singleton
    @Binds
    fun binds(impl: UpdateOrganizationContactSectionImpl): UpdateOrganizationContact
}