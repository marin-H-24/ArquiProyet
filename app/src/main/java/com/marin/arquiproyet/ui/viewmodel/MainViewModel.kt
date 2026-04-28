package com.marin.arquiproyet.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.marin.arquiproyet.data.local.DataStoreManager
import com.marin.arquiproyet.data.models.Project
import kotlinx.coroutines.launch

class MainViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    val projects = mutableStateListOf<Project>()
    val globalIdeas = mutableStateListOf<String>()

    init {
        viewModelScope.launch {
            dataStoreManager.getProjects().collect { savedProjects ->
                projects.clear()
                projects.addAll(savedProjects)
            }
        }
        viewModelScope.launch {
            dataStoreManager.getGlobalIdeas().collect { savedIdeas ->
                globalIdeas.clear()
                globalIdeas.addAll(savedIdeas)
            }
        }
    }

    private fun saveProjectsLocally() {
        viewModelScope.launch {
            dataStoreManager.saveProjects(projects)
        }
    }

    private fun saveIdeasLocally() {
        viewModelScope.launch {
            dataStoreManager.saveGlobalIdeas(globalIdeas)
        }
    }

    fun addProject(name: String, category: String, date: String) {
        projects.add(Project(name = name, category = category, date = date))
        saveProjectsLocally()
    }

    fun getProjectByName(name: String): Project? {
        return projects.find { it.name == name }
    }

    private fun updateProjectInList(projectName: String, updateBlock: (Project) -> Project) {
        val index = projects.indexOfFirst { it.name == projectName }
        if (index != -1) {
            projects[index] = updateBlock(projects[index])
            saveProjectsLocally()
        }
    }

    fun updateProjectIcon(projectName: String, uriString: String) {
        updateProjectInList(projectName) { it.copy(iconUriString = uriString) }
    }

    fun addApartado(projectName: String, apartadoName: String) {
        updateProjectInList(projectName) { project ->
            val newMap = project.apartados.toMutableMap()
            newMap[apartadoName] = ""
            project.copy(apartados = newMap)
        }
    }

    fun updateApartadoContent(projectName: String, apartadoName: String, content: String) {
        updateProjectInList(projectName) { project ->
            val newMap = project.apartados.toMutableMap()
            newMap[apartadoName] = content
            project.copy(apartados = newMap)
        }
    }

    fun addNotaToProject(projectName: String, nota: String) {
        updateProjectInList(projectName) { project ->
            val newList = project.notas.toMutableList()
            newList.add(nota)
            project.copy(notas = newList)
        }
    }

    fun addIdeaFuturaToProject(projectName: String, idea: String) {
        updateProjectInList(projectName) { project ->
            val newList = project.ideasFuturas.toMutableList()
            newList.add(idea)
            project.copy(ideasFuturas = newList)
        }
    }

    fun addGlobalIdea(idea: String) {
        globalIdeas.add(idea)
        saveIdeasLocally()
    }
}

class MainViewModelFactory(private val dataStoreManager: DataStoreManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dataStoreManager) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}