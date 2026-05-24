package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MainDao {
    // Articles
    @Query("SELECT * FROM articles ORDER BY timestamp DESC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    @Update
    suspend fun updateArticle(article: ArticleEntity)

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)

    // Products
    @Query("SELECT * FROM products ORDER BY id DESC")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteProductById(id: Int)

    // Support Messages
    @Query("SELECT * FROM support_messages ORDER BY timestamp ASC")
    fun getAllSupportMessages(): Flow<List<SupportMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupportMessage(message: SupportMessageEntity)

    @Query("DELETE FROM support_messages WHERE id = :id")
    suspend fun deleteSupportMessageById(id: Int)

    @Query("DELETE FROM support_messages")
    suspend fun clearAllSupportMessages()

    // Users
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE users SET role = :newRole WHERE email = :email")
    suspend fun updateUserRole(email: String, newRole: String)

    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUser(email: String)

    // Payment Gateways
    @Query("SELECT * FROM payment_gateways")
    fun getAllGateways(): Flow<List<PaymentGatewayEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGateway(gateway: PaymentGatewayEntity)

    @Update
    suspend fun updateGateway(gateway: PaymentGatewayEntity)

    @Query("DELETE FROM payment_gateways WHERE id = :id")
    suspend fun deleteGatewayById(id: Int)

    // Purchases
    @Query("SELECT * FROM purchases ORDER BY timestamp DESC")
    fun getAllPurchases(): Flow<List<PurchaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPurchase(purchase: PurchaseEntity)
}
