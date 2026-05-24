package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titleAr: String,
    val titleEn: String,
    val contentAr: String,
    val contentEn: String,
    val categoryAr: String,
    val categoryEn: String,
    val language: String = "Kotlin", // e.g., "Python", "Java", "Kotlin", "Flutter", "C++"
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titleAr: String,
    val titleEn: String,
    val descriptionAr: String,
    val descriptionEn: String,
    val price: Double,
    val codeContent: String, // Code snippet / script itself
    val fileName: String, // e.g., "ComposeLoginScreen.kt"
    val fileSizeBytes: Long,
    val rating: Float = 4.8f,
    val sellerEmail: String = "seller@codemaster.com",
    val sellerName: String = "ProDev Marketplace"
)

@Entity(tableName = "support_messages")
data class SupportMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String,
    val userName: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isAdminReply: Boolean = false
)

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val name: String,
    val role: String, // "ADMIN" or "DEVELOPER" or "USER"
    val joiningDate: String = "2026-05-24"
)

@Entity(tableName = "payment_gateways")
data class PaymentGatewayEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nameAr: String,
    val nameEn: String,
    val descriptionAr: String,
    val descriptionEn: String,
    val isActive: Boolean = true,
    val type: String = "CREDIT_CARD" // e.g. "CREDIT_CARD", "PAYPAL", "WALLET"
)

@Entity(tableName = "purchases")
data class PurchaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val productName: String,
    val price: Double,
    val buyerEmail: String,
    val timestamp: Long = System.currentTimeMillis()
)
