package ui.dendi.contacts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import ui.dendi.contacts.data.*
import ui.dendi.contacts.domain.ContactsRepository
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