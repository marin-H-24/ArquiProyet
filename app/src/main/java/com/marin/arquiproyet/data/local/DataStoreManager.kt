package com.marin.arquiproyet.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.marin.arquiproyet.data.models.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "arquiproyet_prefs")

class DataStoreManager(private val context: Context) {
    private val gson = Gson()
    private val projectsKey = stringPreferencesKey("projects_key")
    private val ideasKey = stringPreferencesKey("ideas_key")

    fun getProjects(): Flow<List<Project>> {
        return context.dataStore.data.map { preferences ->
            val json = preferences[projectsKey] ?: "[]"
            val type = object : TypeToken<List<Project>>() {}.type
            gson.fromJson(json, type)
        }
    }

    suspend fun saveProjects(projects: List<Project>) {
        val json = gson.toJson(projects)
        context.dataStore.edit { preferences ->
            preferences[projectsKey] = json
        }
    }

    fun getGlobalIdeas(): Flow<List<String>> {
        return context.dataStore.data.map { preferences ->
            val json = preferences[ideasKey] ?: "[]"
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type)
        }
    }

    suspend fun saveGlobalIdeas(ideas: List<String>) {
        val json = gson.toJson(ideas)
        context.dataStore.edit { preferences ->
            preferences[ideasKey] = json
        }
    }
}