package ui.dendi.contacts.presentation.delegate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ui.dendi.contacts.core.delegate.SectionPostalAddressDelegate
import ui.dendi.contacts.domain.model.PostalAddress
import javax.inject.Inject

class SectionPostalAddressImpl @Inject constructor() : SectionPostalAddressDelegate {

    override var postalAddress: PostalAddress by mutableStateOf(PostalAddress())

    override fun updateStreet(street: String) {
        postalAddress = postalAddress.copy(street = street)
    }

    override fun updateCity(city: String) {
        postalAddress = postalAddress.copy(city = city)
    }

    override fun updateRegion(region: String) {
        postalAddress = postalAddress.copy(region = region)
    }

    override fun updateNeighborhood(neighborhood: String) {
        postalAddress = postalAddress.copy(neighborhood = neighborhood)
    }

    override fun updatePostCode(postCode: String) {
        postalAddress = postalAddress.copy(postCode = postCode)
    }

    override fun updateCountry(country: String) {
        postalAddress = postalAddress.copy(country = country)
    }

    override fun updatePostalAddressLabel(label: String) {
        postalAddress = postalAddress.copy(label = label)
    }

    override fun updatePostalAddressType(type: String) {
        postalAddress = postalAddress.copy(type = type)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface SectionPostalAddressModule {

    @ViewModelScoped
    @Binds
    fun binds(impl: SectionPostalAddressImpl): SectionPostalAddressDelegate
}