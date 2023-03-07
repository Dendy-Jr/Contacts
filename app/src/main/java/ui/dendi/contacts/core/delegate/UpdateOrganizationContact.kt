package ui.dendi.contacts.core.delegate

import ui.dendi.contacts.domain.model.Organization

interface UpdateOrganizationContact {

    var organization: Organization
    fun updateOrganizationName(name: String)
    fun updateOrganizationLabel(label: String)
    fun updateJobTitle(jobTitle: String)
    fun updateJobDescription(jobDescription: String)
    fun updateDepartment(department: String)
}