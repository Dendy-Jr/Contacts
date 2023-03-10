package ui.dendi.contacts.core.extension

fun String.containsNumber(): Boolean {
    val regex = Regex(".*\\d+.*")
    return regex.matches(this)
}

fun String.containsSpecialChar(): Boolean {
    val regex = Regex(".*[^A-Za-zА-Яа-яЇїІіЄєҐґ'\\d]+.*")
    return regex.matches(this)
}