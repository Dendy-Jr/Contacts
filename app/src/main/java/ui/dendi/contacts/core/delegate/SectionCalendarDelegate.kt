package ui.dendi.contacts.core.delegate

import ui.dendi.contacts.domain.model.Calendar

interface SectionCalendarDelegate {

    var calendar: Calendar
    fun updateCalendarLink(link: String)
    fun updateCalendarLabel(label: String)
    fun updateCalendarType(type: String)
}