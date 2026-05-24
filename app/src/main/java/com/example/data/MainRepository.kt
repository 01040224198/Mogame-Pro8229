package com.example.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class MainRepository(private val mainDao: MainDao) {

    val allArticles: Flow<List<ArticleEntity>> = mainDao.getAllArticles()
    val allProducts: Flow<List<ProductEntity>> = mainDao.getAllProducts()
    val allSupportMessages: Flow<List<SupportMessageEntity>> = mainDao.getAllSupportMessages()
    val allUsers: Flow<List<UserEntity>> = mainDao.getAllUsers()
    val allGateways: Flow<List<PaymentGatewayEntity>> = mainDao.getAllGateways()
    val allPurchases: Flow<List<PurchaseEntity>> = mainDao.getAllPurchases()

    // CRUD operations
    suspend fun insertArticle(article: ArticleEntity) = mainDao.insertArticle(article)
    suspend fun updateArticle(article: ArticleEntity) = mainDao.updateArticle(article)
    suspend fun deleteArticle(article: ArticleEntity) = mainDao.deleteArticle(article)

    suspend fun insertProduct(product: ProductEntity) = mainDao.insertProduct(product)
    suspend fun updateProduct(product: ProductEntity) = mainDao.updateProduct(product)
    suspend fun deleteProductById(id: Int) = mainDao.deleteProductById(id)

    suspend fun insertSupportMessage(message: SupportMessageEntity) = mainDao.insertSupportMessage(message)
    suspend fun deleteSupportMessageById(id: Int) = mainDao.deleteSupportMessageById(id)
    suspend fun clearSupportMessages() = mainDao.clearAllSupportMessages()

    suspend fun insertUser(user: UserEntity) = mainDao.insertUser(user)
    suspend fun updateUserRole(email: String, role: String) = mainDao.updateUserRole(email, role)
    suspend fun deleteUser(email: String) = mainDao.deleteUser(email)

    suspend fun insertGateway(gateway: PaymentGatewayEntity) = mainDao.insertGateway(gateway)
    suspend fun updateGateway(gateway: PaymentGatewayEntity) = mainDao.updateGateway(gateway)
    suspend fun deleteGatewayById(id: Int) = mainDao.deleteGatewayById(id)

    suspend fun insertPurchase(purchase: PurchaseEntity) = mainDao.insertPurchase(purchase)

    // Prepopulate DB if empty
    suspend fun checkAndPrepopulate() {
        // Prep Articles
        val articles = allArticles.first()
        if (articles.isEmpty()) {
            mainDao.insertArticle(
                ArticleEntity(
                    titleAr = "مفهوم بايندنج وبرمجة الواجهات في أندرويد",
                    titleEn = "Understanding View Binding & Jetpack Compose",
                    contentAr = "تطورت برمجة واجهات الأندرويد بشكل كبير في السنوات الأخيرة. بعد الانتقال من ملفات XML المعقدة والاستخدام المتكرر لـ findViewById، قدمت جوجل ميزة View Binding لتوفير أمان للأنواع وتسهيل الربط، تلاها التحول الكامل نحو Jetpack Compose القائم على التكوين البرمجي المباشر والمصمم بلغة كوتلن بالكامل.",
                    contentEn = "Android UI programming has evolved significantly. Moving from XML layouts and heavy findViewById calls, Google introduced View Binding for type safety, followed by Jetpack Compose—a modern declarative UI toolkit built completely in Kotlin coding styles.",
                    categoryAr = "تطوير أندرويد",
                    categoryEn = "Android Development",
                    language = "Kotlin"
                )
            )
            mainDao.insertArticle(
                ArticleEntity(
                    titleAr = "خطواتك الأولى في لغة بايثون للذكاء الاصطناعي",
                    titleEn = "First Steps in Python for Artificial Intelligence",
                    contentAr = "لغة Python هي الخيار رقم واحد لعلماء البيانات ومهندسي تعلم الآلة حول العالم. يرجع ذلك لسهولتها الكبيرة وبساطتها الماكروية التي تسمح لغير المبرمجين بفهم الكود، بالإضافة لامتلاكها حزماً برمجية رائدة مثل Pandas و NumPy و TensorFlow اللازمة لبناء النماذج الذكية.",
                    contentEn = "Python is the undisputed language of choice for data scientists and ML engineers globally. Its minimalistic syntax allows fast prototyping, backed by powerful arrays of libraries including Pandas, NumPy, and TensorFlow utilized for building smart neural models.",
                    categoryAr = "ذكاء اصطناعي",
                    categoryEn = "Artificial Intelligence",
                    language = "Python"
                )
            )
            mainDao.insertArticle(
                ArticleEntity(
                    titleAr = "دورة سريعة في فلاتر وتطوير تطبيقات الآيفون والأندرويد",
                    titleEn = "Flutter Quick Start for Android & iOS Apps",
                    contentAr = "فلاتر (Flutter) هي إطار عمل متميز وسريع مدعوم من شركة جوجل يتيح للمبرمجين تطوير تطبيقات أصلية فائقة الجمال وبكفاءة عالية لكل من Android و iOS باستخدام قاعدة أكواد برمجية واحدة مكتوبة بلغة دارت (Dart)، مما يوفر الوقت والتكلفة البرمجية لفرق العمل والشركات الناشئة.",
                    contentEn = "Flutter is a comprehensive UI SDK created by Google for constructing beautiful cross-platform native compilation of apps on iOS & Android using a single Dart codebase. It saves startup costs and team bandwidth immensely.",
                    categoryAr = "متقاطع المنصات",
                    categoryEn = "Cross-Platform",
                    language = "Flutter"
                )
            )
            mainDao.insertArticle(
                ArticleEntity(
                    titleAr = "مبادئ كتابة الأكواد النظيفة (Clean Code)",
                    titleEn = "Clean Coding Principles for Developers",
                    contentAr = "إن كتابة كود برامجي يؤدي الوظيفة هو أمر يفعله أي مبرمج مبتدئ، لكن كتابة كود نظيف يمكن قراءته وفهمه وصيانته بعد أشهر هو الفارق الحقيقي للبروفيسور المحترف. تعتمد المبادئ على تسميات واضحة للمتغيرات، دوال تفعل شيئاً واحداً فقط، وتجنب تكرار الكود (DRY).",
                    contentEn = "Writing code that runs is easy. Writing clean code that reads like well-crafted prose and is readily maintainable is the hallmark of a true professional. Good design focuses on proper variable semantics, single-responsibility functions, and avoiding duplication.",
                    categoryAr = "الممارسات الفضلى",
                    categoryEn = "Best Practices",
                    language = "Java"
                )
            )
        }

        // Prep Products
        val products = allProducts.first()
        if (products.isEmpty()) {
            mainDao.insertProduct(
                ProductEntity(
                    titleAr = "قالب واجهة تسجيل دخول نيون - Compose UI",
                    titleEn = "Neon Gradient Login Template - Jetpack Compose",
                    descriptionAr = "قالب واجهة احترافي كامل بلغة Kotlin لـ Jetpack Compose يتميز بوجود خلفيات تدرج لوني النيون ورسوم متحركة سلسة للتحقق من المدخلات ومربعات نصية مرئية متطورة.",
                    descriptionEn = "A visual masterpiece login interface built in Kotlin with Jetpack Compose. Features futuristic neon gradient animated backgrounds, form text valids, and fully responsive layouts.",
                    price = 12.99,
                    codeContent = """
// Jetpack Compose Neon Login Screen Template
package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NeonLoginScreen() {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364))
    )
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    Box(modifier = Modifier.fillMaxSize().background(gradient)) {
        Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
            Text("Welcome to Future", style = MaterialTheme.typography.headlineLarge, color = Color.Cyan)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { /* Neon Login Active */ }) {
                Text("CONNECT")
            }
        }
    }
}
                    """.trimIndent(),
                    fileName = "NeonLoginScreen.kt",
                    fileSizeBytes = 1104L
                )
            )
            mainDao.insertProduct(
                ProductEntity(
                    titleAr = "سكربت أتمتة تنظيف البيانات وجدولة التقارير - بايثون",
                    titleEn = "Automated Data Cleaning & Scheduling - Python",
                    descriptionAr = "سكربت بايثون قوي لمعالجة وتنظيف ملفات البيانات Excel و CSV الضخمة وتصفية القيم المفقودة وإرسال تقارير الملخص تلقائياً عبر البريد الإلكتروني مع مخططات بيانية تفاعلية.",
                    descriptionEn = "An industrial-grade python automation script that ingests faulty CSV data, cleans null fields, imputes standard distributions, and schedules HTML summaries directly to stakeholder emails.",
                    price = 34.50,
                    codeContent = """
# Automated Data Imputer & PDF Scheduler
import pandas as pd
import numpy as np
import smtplib

def clean_and_report(filepath, recipient_email):
    df = pd.read_csv(filepath)
    df.replace("", np.nan, inplace=True)
    df.fillna(method="ffill", inplace=True)
    
    summary = df.describe().to_html()
    print("Prepping PDF report to:", recipient_email)
    # SMTP Code triggers scheduled notification delivery...
                    """.trimIndent(),
                    fileName = "clean_scheduler.py",
                    fileSizeBytes = 422L
                )
            )
            mainDao.insertProduct(
                ProductEntity(
                    titleAr = "مكون مشغل كليب مخصص - Flutter Custom Playback",
                    titleEn = "Flutter Advanced Soundboard & Playlist Component",
                    descriptionAr = "حزمة ومجموعة واجهات برمجية لبرنامج Flutter مبرمجة كلياً بالـ Dart تتيح إدراج مشغل صوتيات متطور مع موجة اهتزازية بصرية متجاوبة وتأثيرات صوتية غامرة.",
                    descriptionEn = "A Dart package for Flutter supplying custom audio canvas playback, adaptive visual wave-forms matching output channels, speed regulators, and elegant dark theme sliders.",
                    price = 19.00,
                    codeContent = """
// Flutter Advanced Waveform Widget
import 'package:flutter/material.dart';

class CustomAudioPlayerWidget extends StatefulWidget {
  final String audioUrl;
  const CustomAudioPlayerWidget({Key? key, required this.audioUrl}) : super(key: key);
  @override
  _CustomAudioPlayerWidgetState createState() => _CustomAudioPlayerWidgetState();
}

class _CustomAudioPlayerWidgetState extends State<CustomAudioPlayerWidget> {
  double _playbackSpeed = 1.0;
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.black87,
      child: Row(
        children: [
          IconButton(icon: Icon(Icons.play_arrow), onPressed: () {}),
          Text("Waveform Dynamic Canvas: " + widget.audioUrl)
        ]
      )
    );
  }
}
                    """.trimIndent(),
                    fileName = "wave_soundboard.dart",
                    fileSizeBytes = 730L
                )
            )
        }

        // Prep Gateways
        val gateways = allGateways.first()
        if (gateways.isEmpty()) {
            mainDao.insertGateway(
                PaymentGatewayEntity(
                    nameAr = "بطاقة الائتمان (فيزا / ماستركارد)",
                    nameEn = "Credit/Debit Card (Visa/Mastercard)",
                    descriptionAr = "بوابة دفع مشفرة بالكامل تتيح الشراء المباشر والآمن.",
                    descriptionEn = "Fully encrypted secure processing gateway for instant checkout globally.",
                    isActive = true,
                    type = "CREDIT_CARD"
                )
            )
            mainDao.insertGateway(
                PaymentGatewayEntity(
                    nameAr = "بايبال (PayPal)",
                    nameEn = "PayPal Secure Checkout",
                    descriptionAr = "ادفع بأمان عبر حساب بايبال الخاص بك بضغطة واحدة.",
                    descriptionEn = "Fast international web payment with standard PayPal client APIs.",
                    isActive = true,
                    type = "PAYPAL"
                )
            )
            mainDao.insertGateway(
                PaymentGatewayEntity(
                    nameAr = "محفظة فودافون كاش / أورانج / اتصالات",
                    nameEn = "Digital Wallet (Vodafone / Orange / Etisalat Cash)",
                    descriptionAr = "بوابة دفع محلية ممتازة ومطورة لسهولة تحويل الأموال وتلقي السحوبات.",
                    descriptionEn = "Popular localized mobile cash transfer wallet supporting instant transactions in Egypt.",
                    isActive = true,
                    type = "WALLET"
                )
            )
        }

        // Prep Users
        val users = allUsers.first()
        if (users.isEmpty()) {
            mainDao.insertUser(
                UserEntity(
                    email = "hoda1593575@gmail.com",
                    name = "Hoda (Owner)",
                    role = "ADMIN",
                    joiningDate = "2026-05-24"
                )
            )
            mainDao.insertUser(
                UserEntity(
                    email = "ahmad.dev@codemaster.com",
                    name = "Ahmad Omar",
                    role = "DEVELOPER",
                    joiningDate = "2026-04-12"
                )
            )
            mainDao.insertUser(
                UserEntity(
                    email = "yasmin_learner@outlook.com",
                    name = "Yasmin Ali",
                    role = "USER",
                    joiningDate = "2026-05-18"
                )
            )
        }

        // Prep Support Messages
        val messages = allSupportMessages.first()
        if (messages.isEmpty()) {
            mainDao.insertSupportMessage(
                SupportMessageEntity(
                    userEmail = "yasmin_learner@outlook.com",
                    userName = "Yasmin Ali",
                    message = "مرحباً يا فريق الدعم، لقد قمت بشراء قالب صفحة تسجيل دخول النيون ولكنني أجهل كيف أقوم بربطه بزر التثبيت الأساسي في تطبيقي المحلى. هل من نصيحة؟",
                    timestamp = System.currentTimeMillis() - 6000000,
                    isAdminReply = false
                )
            )
            mainDao.insertSupportMessage(
                SupportMessageEntity(
                    userEmail = "yasmin_learner@outlook.com",
                    userName = "Hoda (Owner)",
                    message = "أهلاً بك يا ياسمين! يمكنك القيام بذلك بكل سهولة عن طريق استيراد الملف NeonLoginScreen.kt داخل مشروعك ثم استدعاء الدالة NeonLoginScreen() بداخل كود Scaffold الرئيسي الخاص بك. لا تترددي في إعلامنا بمزيد من التفاصيل.",
                    timestamp = System.currentTimeMillis() - 3000000,
                    isAdminReply = true
                )
            )
        }

        // Prep Purchases
        val purchases = allPurchases.first()
        if (purchases.isEmpty()) {
            mainDao.insertPurchase(
                PurchaseEntity(
                    productId = 1,
                    productName = "قالب واجهة تسجيل دخول نيون - Compose UI",
                    price = 12.99,
                    buyerEmail = "yasmin_learner@outlook.com",
                    timestamp = System.currentTimeMillis() - 6000000
                )
            )
            mainDao.insertPurchase(
                PurchaseEntity(
                    productId = 2,
                    productName = "سكربت أتمتة تنظيف البيانات وجدولة التقارير - بايثون",
                    price = 34.50,
                    buyerEmail = "ahmad.dev@codemaster.com",
                    timestamp = System.currentTimeMillis() - 3000000
                )
            )
        }
    }
}
