package ui.dendi.contacts.core.delegate.impl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ui.dendi.contacts.core.delegate.UpdateCalendarContact
import ui.dendi.contacts.domain.model.Calendar
import javax.inject.Inject
import javax.inject.Singleton

class UpdateCalendarContactSectionImpl @Inject constructor() : UpdateCalendarContact {

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
@InstallIn(SingletonComponent::class)
interface UpdateCalendarContactSectionModule {

    @Singleton
    @Binds
    fun binds(impl: UpdateCalendarContactSectionImpl): UpdateCalendarContact
}