package ui.dendi.contacts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import ui.dendi.contacts.data.*
import ui.dendi.contacts.data.model.*
import ui.dendi.contacts.data.repository.ContactsRepositoryImpl
import ui.dendi.contacts.domain.repository.ContactsRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRealm(): Realm {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                PersonObject::class,
                PhoneNumberObject::class,
                PostalAddressObject::class,
                EmailAddressObject::class,
                OrganizationObject::class,
                WebsiteObject::class,
                CalendarObject::class,
            )
        )
            .compactOnLaunch()
            .build()
        return Realm.open(config)
    }

    @Singleton
    @Provides
    fun provideContactsRepository(realm: Realm): ContactsRepository {
        return ContactsRepositoryImpl(realm = realm)
    }
}