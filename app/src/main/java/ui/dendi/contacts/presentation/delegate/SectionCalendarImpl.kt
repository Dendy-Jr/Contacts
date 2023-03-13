package ui.dendi.contacts.presentation.delegate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ui.dendi.contacts.core.delegate.SectionCalendarDelegate
import ui.dendi.contacts.domain.model.Calendar
import javax.inject.Inject

class SectionCalendarImpl @Inject constructor() : SectionCalendarDelegate {

    override var calendar: Calendar by mutableStateOf(Calendar())

    override fun updateCalendarLink(link: String) {
        calendar = calendar.copy(link = link)
    }

    override fun updateCalendarLabel(label: String) {
        calendar = calendar.copy(label = label)
    }

    override fun updateCalendarType(type: String) {
        calendar = calendar.copy(type = type)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface SectionCalendarModule {

    @ViewModelScoped
    @Binds
    fun binds(impl: SectionCalendarImpl): SectionCalendarDelegate
}