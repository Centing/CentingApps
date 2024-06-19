package com.c241ps220.centingapps.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = user.id
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[PHONE_KEY] = user.phone
            preferences[ADDRESS_KEY] = user.address
            preferences[GENDER_KEY] = user.gender
            preferences[BIRTHDAY_KEY] = user.birthDate
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[ID_KEY] ?: "",
                preferences[NAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[PHONE_KEY] ?: "",
                preferences[ADDRESS_KEY] ?: "",
                preferences[GENDER_KEY] ?: "",
                preferences[BIRTHDAY_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun getName(): String? {
        val preferences = dataStore.data.first()
        return preferences[NAME_KEY]
    }
    suspend fun getEmail(): String? {
        val preferences = dataStore.data.first()
        return preferences[EMAIL_KEY]
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val ID_KEY = stringPreferencesKey("id")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PHONE_KEY = stringPreferencesKey("phone")
        private val ADDRESS_KEY = stringPreferencesKey("address")
        private val GENDER_KEY = stringPreferencesKey("gender")
        private val BIRTHDAY_KEY = stringPreferencesKey("birthday")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(context: Context): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(context.dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
