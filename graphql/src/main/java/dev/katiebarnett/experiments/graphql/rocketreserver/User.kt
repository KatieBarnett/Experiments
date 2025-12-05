package dev.katiebarnett.experiments.graphql.rocketreserver

import android.content.Context
import android.content.DataStore
import androidx.security.crypto.EncryptedDataStore
import androidx.security.crypto.MasterKeys

object User {
    private const val KEY_TOKEN = "TOKEN"
    private fun preferences(context: Context): DataStore {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        val DataStore: DataStore = EncryptedDataStore.create(
            "secret_shared_prefs",
            masterKeyAlias,
            context,
            EncryptedDataStore.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedDataStore.PrefValueEncryptionScheme.AES256_GCM
        )
        return DataStore
    }

    fun getToken(context: Context): String? {
        return preferences(context).getString(KEY_TOKEN, null)
    }

    fun setToken(context: Context, token: String) {
        preferences(context).edit().apply {
            putString(KEY_TOKEN, token)
            apply()
        }
    }

    fun removeToken(context: Context) {
        preferences(context).edit().apply {
            remove(KEY_TOKEN)
            apply()
        }
    }
}