package com.example.japritv.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.japritv.model.Category

class CategoryViewModel : ViewModel() {
    // List kategori menggunakan model Category
    private val _categories = mutableStateListOf(
        Category("🔥 Terlaris", true),
        Category("Daftar Peringkat",false),
        Category("Segera Tayang",false),
        Category("Action",false),
        Category("Drama",false)
    )
    val categories: List<Category> = _categories

    // Update the selected category based on the clicked category
    private var _selectedCategoryIndex = mutableStateOf(0)
    val selectedCategoryIndex: Int by _selectedCategoryIndex

    // Update the selected category based on the clicked category
    fun selectCategory(selectedCategory: Category) {
        val index = _categories.indexOfFirst { it.name == selectedCategory.name }
        if (index != -1) {
            // First unselect all categories
            _categories.forEachIndexed { i, category ->
                // Create a new Category object to force recomposition
                _categories[i] = category.copy(isSelected = false)
            }
            // Then select the target category
            _categories[index] = _categories[index].copy(isSelected = true)
            _selectedCategoryIndex.value = index
        }
    }

    // Reset selection if needed
    fun resetSelection() {
        _categories.forEach { category ->
            category.isSelected = false
        }
    }
}