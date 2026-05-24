package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    // Localization State
    private val _language = MutableStateFlow(AppLanguage.ARABIC)
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    // Simulation User State (Enables full testing of Administrative controls instantly)
    private val _currentUser = MutableStateFlow(
        UserEntity(
            email = "hoda1593575@gmail.com",
            name = "Hoda (Owner)",
            role = "ADMIN"
        )
    )
    val currentUser: StateFlow<UserEntity> = _currentUser.asStateFlow()

    // Search queries
    val searchQuery = MutableStateFlow("")

    init {
        // Prepopulate on startup
        viewModelScope.launch {
            repository.checkAndPrepopulate()
        }
    }

    // Streams of data
    val articles: StateFlow<List<ArticleEntity>> = combine(
        repository.allArticles,
        searchQuery
    ) { list, query ->
        if (query.isBlank()) {
            list
        } else {
            val q = query.lowercase()
            list.filter {
                it.titleAr.lowercase().contains(q) ||
                it.titleEn.lowercase().contains(q) ||
                it.contentAr.lowercase().contains(q) ||
                it.contentEn.lowercase().contains(q) ||
                it.categoryAr.lowercase().contains(q) ||
                it.categoryEn.lowercase().contains(q) ||
                it.language.lowercase().contains(q)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val products: StateFlow<List<ProductEntity>> = combine(
        repository.allProducts,
        searchQuery
    ) { list, query ->
        if (query.isBlank()) {
            list
        } else {
            val q = query.lowercase()
            list.filter {
                it.titleAr.lowercase().contains(q) ||
                it.titleEn.lowercase().contains(q) ||
                it.descriptionAr.lowercase().contains(q) ||
                it.descriptionEn.lowercase().contains(q) ||
                it.fileName.lowercase().contains(q)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val supportMessages: StateFlow<List<SupportMessageEntity>> = repository.allSupportMessages
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val usersList: StateFlow<List<UserEntity>> = repository.allUsers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val gatewaysList: StateFlow<List<PaymentGatewayEntity>> = repository.allGateways
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val purchasesList: StateFlow<List<PurchaseEntity>> = repository.allPurchases
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Language Toggle
    fun toggleLanguage() {
        _language.value = if (_language.value == AppLanguage.ARABIC) {
            AppLanguage.ENGLISH
        } else {
            AppLanguage.ARABIC
        }
    }

    // Simulated Role Toggle
    fun toggleSimulatedRole() {
        val current = _currentUser.value
        val nextRole = when (current.role) {
            "ADMIN" -> "DEVELOPER"
            "DEVELOPER" -> "USER"
            else -> "ADMIN"
        }
        val nextName = when (nextRole) {
            "ADMIN" -> "Hoda (Owner)"
            "DEVELOPER" -> "Ahmad Omar"
            else -> "Yasmin Ali (Customer)"
        }
        val nextEmail = when (nextRole) {
            "ADMIN" -> "hoda1593575@gmail.com"
            "DEVELOPER" -> "ahmad.dev@codemaster.com"
            else -> "yasmin_learner@outlook.com"
        }
        viewModelScope.launch {
            val updatedUser = UserEntity(email = nextEmail, name = nextName, role = nextRole)
            _currentUser.value = updatedUser
            repository.insertUser(updatedUser)
        }
    }

    // Article actions (Admin)
    fun addArticle(titleAr: String, titleEn: String, contentAr: String, contentEn: String, categoryAr: String, categoryEn: String, languageName: String) {
        viewModelScope.launch {
            repository.insertArticle(
                ArticleEntity(
                    titleAr = titleAr,
                    titleEn = titleEn,
                    contentAr = contentAr,
                    contentEn = contentEn,
                    categoryAr = categoryAr,
                    categoryEn = categoryEn,
                    language = languageName
                )
            )
        }
    }

    fun removeArticle(article: ArticleEntity) {
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }

    // Product actions (Developer/Admin)
    fun addProduct(titleAr: String, titleEn: String, descAr: String, descEn: String, priceValue: Double, code: String, file: String) {
        viewModelScope.launch {
            repository.insertProduct(
                ProductEntity(
                    titleAr = titleAr,
                    titleEn = titleEn,
                    descriptionAr = descAr,
                    descriptionEn = descEn,
                    price = priceValue,
                    codeContent = code,
                    fileName = file,
                    fileSizeBytes = code.toByteArray().size.toLong(),
                    sellerEmail = _currentUser.value.email,
                    sellerName = _currentUser.value.name
                )
            )
        }
    }

    fun removeProduct(productId: Int) {
        viewModelScope.launch {
            repository.deleteProductById(productId)
        }
    }

    // Support Messaging action
    fun sendSupportMessage(msgContent: String) {
        if (msgContent.isBlank()) return
        viewModelScope.launch {
            repository.insertSupportMessage(
                SupportMessageEntity(
                    userEmail = _currentUser.value.email,
                    userName = _currentUser.value.name,
                    message = msgContent,
                    timestamp = System.currentTimeMillis(),
                    isAdminReply = _currentUser.value.role == "ADMIN"
                )
            )
        }
    }

    // Reply support ticket as Admin (remote)
    fun replySupportTicket(userName: String, userEmail: String, replyMessage: String) {
        if (replyMessage.isBlank()) return
        viewModelScope.launch {
            repository.insertSupportMessage(
                SupportMessageEntity(
                    userEmail = userEmail,
                    userName = _currentUser.value.name, // The admin name
                    message = replyMessage,
                    timestamp = System.currentTimeMillis(),
                    isAdminReply = true
                )
            )
        }
    }

    fun deleteSupportMessage(id: Int) {
        viewModelScope.launch {
            repository.deleteSupportMessageById(id)
        }
    }

    // Payment Gateway configuration (Admin control)
    fun addPaymentGateway(nameAr: String, nameEn: String, descAr: String, descEn: String, typeName: String, active: Boolean) {
        viewModelScope.launch {
            repository.insertGateway(
                PaymentGatewayEntity(
                    nameAr = nameAr,
                    nameEn = nameEn,
                    descriptionAr = descAr,
                    descriptionEn = descEn,
                    isActive = active,
                    type = typeName
                )
            )
        }
    }

    fun updatePaymentGateway(gateway: PaymentGatewayEntity) {
        viewModelScope.launch {
            repository.updateGateway(gateway)
        }
    }

    fun removePaymentGateway(id: Int) {
        viewModelScope.launch {
            repository.deleteGatewayById(id)
        }
    }

    // Buy Code Template & Generate Purchase record
    fun purchaseProduct(product: ProductEntity, buyerEmail: String) {
        viewModelScope.launch {
            repository.insertPurchase(
                PurchaseEntity(
                    productId = product.id,
                    productName = product.titleEn,
                    price = product.price,
                    buyerEmail = buyerEmail,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    // User management action
    fun updateUserRoleAndPermissions(email: String, role: String) {
        viewModelScope.launch {
            repository.updateUserRole(email, role)
        }
    }

    fun deleteUserAccount(email: String) {
        viewModelScope.launch {
            repository.deleteUser(email)
        }
    }
}

// Simple Factory for Simple Constructor Injection
class MainViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
