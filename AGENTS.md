# AGENTS.md — Maraba Android Otomasyon Uygulaması

> Bu dosya Claude Code'un projeyi anlaması ve tutarlı geliştirme yapması için tek kaynak belgesidir.
> Her agent her görev öncesinde bu dosyayı baştan okur.

---

## 0. Proje Özeti ve Vizyon

**Maraba**, Android 10+ (API 29+) cihazlarda çalışan, **tamamen cihaz içi** bir otomasyon uygulamasıdır.
MacroDroid ve Tasker'a benzer şekilde; kullanıcı "Tetikleyici → Koşul → Aksiyon" kuralları oluşturur,
uygulama bu kuralları arka planda sürekli izleyerek otomatik olarak yürütür.

**PC bağlantısı yoktur. ADB yoktur. Python yoktur.**
Her şey Android cihazın kendi içinde çalışır.

### Temel Konsept: Makro = Trigger(lar) + Koşul(lar) + Aksiyon(lar)

```
Örnek:
  Trigger : Her gün 08:00
  Koşul   : Wi-Fi "Ev_Ağı"na bağlı
  Aksiyon : Spotify'ı aç + Ses seviyesini 60'a ayarla

Örnek:
  Trigger : "Akbank" uygulamasından bildirim geldi
  Koşul   : Bildirim metni "para transferi" içeriyor
  Aksiyon : SMS gönder + HTTP POST ile webhook'u tetikle

Örnek:
  Trigger : Konum "İş Yeri"ne girdi
  Koşul   : Saat 09:00–18:00 arası
  Aksiyon : Sessiz moda geç + İş takvimini aç
```

---

## 1. Mimari Karar Kaydı (ADR)

### ADR-001: Tamamen Native Android Uygulaması

- **Dil:** Kotlin (% 100) — Java interop ile eski kütüphaneler kullanılabilir
- **Min SDK:** API 29 (Android 10)
- **Target SDK:** API 35 (Android 15)
- **Build sistemi:** Gradle (Kotlin DSL)
- **DI:** Hilt (Dagger 2 üzeri)
- **Async:** Kotlin Coroutines + Flow
- **DB:** Room (SQLite üzeri, tip güvenli)
- **UI:** Jetpack Compose (Material 3)
- **Navigation:** Compose Navigation
- **Test:** JUnit5 + MockK + Compose UI Test

PC tarafı yok. Python yok. ADB yok. gRPC yok. Protobuf yok.

---

### ADR-002: Trigger Mimarisi

Her trigger tipi kendi `TriggerReceiver` implementasyonuna sahiptir:

```
TriggerEngine (coordinator)
    ├── TimeTriggerScheduler       → WorkManager / AlarmManager
    ├── AppTriggerReceiver         → AccessibilityService (app open/close)
    ├── NotificationTriggerListener→ NotificationListenerService
    ├── LocationTriggerManager     → Geofencing API (Google Play) / FusedLocation
    ├── BroadcastTriggerReceiver   → System broadcast'ler (boot, airplane, battery...)
    ├── ScreenTriggerObserver      → screen on/off, unlock
    ├── ConnectivityTriggerObserver→ Wi-Fi bağlantı/kesinti, Bluetooth
    ├── SensorTriggerObserver      → Shake, flip, proximity, accelerometer
    ├── CallTriggerReceiver        → Gelen/giden arama
    ├── SMSTriggerReceiver         → Gelen SMS içerik eşleşme
    ├── VariableTriggerObserver    → Kullanıcı değişkeni değişimi
    └── WebhookTriggerServer       → Gelen HTTP webhook (local server)
```

**Rootsuz çalışır.** Root varsa ek trigger'lar aktif olur (sistem düzeyinde event'ler).

---

### ADR-003: Aksiyon Mimarisi

Her aksiyon `Action` sealed class'ından türer, `ActionExecutor` tarafından çalıştırılır:

```
ActionExecutor
    ├── UI Aksiyonları (Accessibility Service)
    │   ├── TapAction              → koordinat veya element
    │   ├── SwipeAction            → yön, hız, mesafe
    │   ├── TypeTextAction         → metin girişi
    │   ├── ScrollAction           → yukarı/aşağı/sol/sağ
    │   ├── LongPressAction
    │   └── KeyEventAction         → back, home, volume, power...
    │
    ├── Uygulama Aksiyonları
    │   ├── LaunchAppAction        → package name ile aç
    │   ├── KillAppAction          → uygulamayı kapat
    │   ├── OpenSettingsAction     → belirli ayar ekranı
    │   └── OpenUrlAction          → browser'da URL aç
    │
    ├── Sistem Aksiyonları
    │   ├── SetVolumeAction        → ring, media, alarm, notification
    │   ├── SetBrightnessAction
    │   ├── SetRingerModeAction    → sessiz, titreşim, normal
    │   ├── WifiAction             → aç/kapat (API 29: Settings yönlendirme)
    │   ├── BluetoothAction        → aç/kapat
    │   ├── FlashlightAction       → fener aç/kapat
    │   ├── TakeScreenshotAction   → MediaProjection API
    │   └── ToggleAirplaneAction   → root gerektirir
    │
    ├── Bildirim Aksiyonları
    │   ├── ShowNotificationAction → local bildirim göster
    │   ├── DismissNotificationAction → bildirimi kapat
    │   └── ReplyNotificationAction   → inline reply
    │
    ├── İletişim Aksiyonları
    │   ├── SendSMSAction          → SMS gönder
    │   ├── MakeCallAction         → arama başlat
    │   └── SendEmailAction        → intent ile
    │
    ├── Veri Aksiyonları
    │   ├── HttpRequestAction      → GET/POST/PUT/DELETE + headers + body
    │   ├── ParseJsonAction        → response'dan değişken ata
    │   ├── SetVariableAction      → kullanıcı değişkeni ata
    │   └── WriteFileAction        → dosyaya yaz (app-private)
    │
    ├── Medya Aksiyonları
    │   ├── PlaySoundAction        → local ses dosyası
    │   ├── TextToSpeechAction     → TTS
    │   └── MediaControlAction     → play/pause/next/prev
    │
    ├── Akış Kontrol Aksiyonları
    │   ├── IfElseAction           → koşullu dal
    │   ├── WaitAction             → belirtilen süre bekle
    │   ├── RepeatAction           → döngü
    │   ├── StopMacroAction        → makroyu durdur
    │   └── RunMacroAction         → başka makroyu çalıştır
    │
    └── Root Aksiyonları (opsiyonel, root gerektirir)
        ├── ShellCommandAction     → su -c ile komut
        ├── SimulateInputAction    → /dev/input inject
        └── SystemSettingsAction   → yazma korumalı ayarlar
```

---

### ADR-004: Koşul (Condition) Mimarisi

Trigger ateşlendikten sonra koşullar değerlendirilir. Tüm koşullar true olursa aksiyonlar çalışır.

```
ConditionEvaluator
    ├── TimeCondition          → saat/gün aralığı
    ├── AppStateCondition      → belirli uygulama foreground/background mı?
    ├── WifiCondition          → belirli SSID'ye bağlı mı?
    ├── BluetoothCondition     → cihaz bağlı mı?
    ├── BatteryCondition       → şarj seviyesi > / < X
    ├── ChargingCondition      → şarjda mı?
    ├── LocationCondition      → belirli bir bölgede mi?
    ├── VariableCondition      → değişken = / > / < / içeriyor
    ├── NotificationCondition  → bekleyen bildirim var mı?
    ├── ScreenCondition        → ekran açık mı?
    └── RingerCondition        → sesli/sessiz/titreşim modu
```

---

### ADR-005: Veri Modeli

```kotlin
// Temel veri modeli
data class Macro(
    val id: String,           // UUID
    val name: String,
    val description: String,
    val isEnabled: Boolean,
    val triggers: List<Trigger>,
    val conditions: List<Condition>,
    val actions: List<Action>,
    val createdAt: Long,
    val lastExecutedAt: Long?,
    val executionCount: Int,
    val priority: Int         // çakışma durumunda öncelik
)

// Değişken sistemi (Tasker'daki %VARIABLE gibi)
data class MarabaVariable(
    val name: String,         // %BATTERY, %TIME, kullanıcı tanımlı
    val value: String,
    val type: VariableType,   // STRING, INT, FLOAT, BOOL, LIST
    val isBuiltIn: Boolean    // sistem değişkeni mi?
)
```

**Sistem değişkenleri (built-in, her aksiyonda kullanılabilir):**
```
%TIME          → 14:35
%DATE          → 2024-01-15
%BATTERY       → 87
%WIFI_SSID     → Ev_Ağı
%LOCATION      → 39.9208,32.8541
%LAST_APP      → com.whatsapp
%SCREEN        → on / off
%CHARGING      → true / false
%VOLUME_MEDIA  → 60
```

---

### ADR-006: Servis Mimarisi

```
MarabaApplication
    │
    ├── MarabaForegroundService     ← sürekli çalışan ana servis (Notification ile)
    │       ├── TriggerEngine       ← tüm trigger'ları koordine eder
    │       ├── MacroExecutor       ← kural motor, aksiyonları sırayla çalıştırır
    │       ├── VariableManager     ← değişken state'i tutar
    │       └── ExecutionLogger     ← her çalışmayı loglar
    │
    ├── MarabaAccessibilityService  ← UI otomasyonu (ekrana dokunma vs.)
    │       ├── UIActionHandler     ← TapAction, SwipeAction vs. yürütür
    │       └── AppStateTracker     ← foreground uygulama takibi
    │
    └── MarabaNotificationListener  ← NotificationListenerService
            └── NotificationFilter  ← trigger eşleştirme
```

**Kritik:** Accessibility Service ve Notification Listener ayrı process'te çalışmaz, ancak
`MarabaForegroundService` ile `Binder IPC` üzerinden iletişim kurar.

---

### ADR-007: UI Mimarisi (Jetpack Compose)

```
MainActivity (single activity)
    │
    └── NavHost
        ├── HomeScreen             ← makro listesi, aktif/pasif toggle
        ├── MacroDetailScreen      ← makro görüntüle/düzenle
        ├── MacroEditorScreen      ← yeni makro oluştur
        │   ├── TriggerPickerScreen
        │   ├── ConditionPickerScreen
        │   └── ActionPickerScreen
        ├── VariablesScreen        ← değişken yönetimi
        ├── LogScreen              ← çalışma geçmişi
        ├── SettingsScreen         ← uygulama ayarları
        └── PermissionsScreen      ← gerekli izin rehberi
```

**UX prensibi:** Kullanıcı teknik değil. Her ekran basit, açıklayıcı, adım adım.
Trigger/Condition/Action seçimi görsel kartlarla yapılır, form doldurma minimum tutulur.

---

### ADR-008: İzin Stratejisi

```
Zorunlu (uygulama başlarken):
  FOREGROUND_SERVICE
  FOREGROUND_SERVICE_SPECIAL_USE
  RECEIVE_BOOT_COMPLETED
  POST_NOTIFICATIONS (API 33+)

Kullanıma göre istenen:
  ACCESS_FINE_LOCATION           → konum trigger'ları
  ACCESS_BACKGROUND_LOCATION    → arka planda konum
  RECEIVE_SMS                   → SMS trigger
  SEND_SMS                      → SMS aksiyonu
  READ_CALL_LOG / CALL_PHONE    → arama trigger/aksiyon
  CAMERA                        → fener aksiyonu
  WRITE_EXTERNAL_STORAGE        → dosya yazma (API 28 altı)

Özel izinler (kullanıcı Settings'ten açmalı):
  Accessibility Service         → UI otomasyonu için şart
  Notification Access           → bildirim trigger/aksiyon için şart
  Display over other apps       → overlay aksiyonları için

Root varsa ek yetenekler (izin istenmez, otomatik tespit):
  WRITE_SECURE_SETTINGS         → bazı sistem ayarları
  INPUT_INJECT                  → doğrudan input inject
```

**İzin yönetimi:** `PermissionsManager` her özellik öncesi izni kontrol eder.
İzin yoksa kullanıcıya açıklayıcı diyalog gösterilir, zorla erişim denenmez.

---

## 2. Proje Dizin Yapısı

```
maraba/
├── AGENTS.md
├── CLAUDE.md
├── README.md
├── build.gradle.kts             ← root build (proje seviyesi)
├── settings.gradle.kts
├── gradle/
│   └── libs.versions.toml       ← version catalog
│
└── app/
    ├── build.gradle.kts         ← app modülü build
    ├── proguard-rules.pro
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml
        │   └── kotlin/com/maraba/app/
        │       │
        │       ├── MaRabaApplication.kt        ← Hilt @HiltAndroidApp
        │       │
        │       ├── data/                        ── VERİ KATMANI
        │       │   ├── db/
        │       │   │   ├── MarabaDatabase.kt    ← Room database
        │       │   │   ├── dao/
        │       │   │   │   ├── MacroDao.kt
        │       │   │   │   ├── VariableDao.kt
        │       │   │   │   └── ExecutionLogDao.kt
        │       │   │   └── entity/
        │       │   │       ├── MacroEntity.kt
        │       │   │       ├── VariableEntity.kt
        │       │   │       └── ExecutionLogEntity.kt
        │       │   ├── repository/
        │       │   │   ├── MacroRepository.kt
        │       │   │   ├── VariableRepository.kt
        │       │   │   └── LogRepository.kt
        │       │   └── model/                   ← domain modeller
        │       │       ├── Macro.kt
        │       │       ├── Trigger.kt           ← sealed class
        │       │       ├── Condition.kt         ← sealed class
        │       │       ├── Action.kt            ← sealed class
        │       │       └── MarabaVariable.kt
        │       │
        │       ├── domain/                      ── DOMAIN KATMANI
        │       │   ├── usecase/
        │       │   │   ├── macro/
        │       │   │   │   ├── CreateMacroUseCase.kt
        │       │   │   │   ├── DeleteMacroUseCase.kt
        │       │   │   │   ├── ToggleMacroUseCase.kt
        │       │   │   │   └── GetMacrosUseCase.kt
        │       │   │   ├── execution/
        │       │   │   │   ├── ExecuteMacroUseCase.kt
        │       │   │   │   └── EvaluateConditionsUseCase.kt
        │       │   │   └── variable/
        │       │   │       ├── GetVariableUseCase.kt
        │       │   │       └── SetVariableUseCase.kt
        │       │   └── engine/
        │       │       ├── TriggerEngine.kt     ← tüm trigger'ları koordine eder
        │       │       ├── ConditionEvaluator.kt
        │       │       ├── MacroExecutor.kt     ← action zincirini çalıştırır
        │       │       └── VariableManager.kt   ← built-in + user değişkenleri
        │       │
        │       ├── service/                     ── SERVİS KATMANI
        │       │   ├── MarabaForegroundService.kt   ← ana servis
        │       │   ├── MarabaAccessibilityService.kt
        │       │   ├── MarabaNotificationListener.kt
        │       │   └── WebhookReceiverService.kt    ← gelen HTTP (opsiyonel)
        │       │
        │       ├── trigger/                     ── TETİKLEYİCİLER
        │       │   ├── base/
        │       │   │   ├── TriggerHandler.kt    ← interface
        │       │   │   └── TriggerEvent.kt      ← sealed class
        │       │   ├── TimeTriggerScheduler.kt
        │       │   ├── AppTriggerTracker.kt
        │       │   ├── NotificationTriggerListener.kt
        │       │   ├── LocationTriggerManager.kt
        │       │   ├── BroadcastTriggerReceiver.kt
        │       │   ├── ScreenTriggerObserver.kt
        │       │   ├── ConnectivityTriggerObserver.kt
        │       │   ├── SensorTriggerObserver.kt
        │       │   ├── CallTriggerReceiver.kt
        │       │   ├── SMSTriggerReceiver.kt
        │       │   └── VariableTriggerObserver.kt
        │       │
        │       ├── action/                      ── AKSİYONLAR
        │       │   ├── base/
        │       │   │   ├── ActionHandler.kt     ← interface
        │       │   │   └── ActionResult.kt      ← sealed class
        │       │   ├── ui/
        │       │   │   ├── TapActionHandler.kt
        │       │   │   ├── SwipeActionHandler.kt
        │       │   │   ├── TypeTextActionHandler.kt
        │       │   │   └── KeyEventActionHandler.kt
        │       │   ├── app/
        │       │   │   ├── LaunchAppActionHandler.kt
        │       │   │   └── KillAppActionHandler.kt
        │       │   ├── system/
        │       │   │   ├── VolumeActionHandler.kt
        │       │   │   ├── BrightnessActionHandler.kt
        │       │   │   ├── WifiActionHandler.kt
        │       │   │   ├── BluetoothActionHandler.kt
        │       │   │   └── FlashlightActionHandler.kt
        │       │   ├── notification/
        │       │   │   └── NotificationActionHandler.kt
        │       │   ├── communication/
        │       │   │   ├── SMSActionHandler.kt
        │       │   │   └── CallActionHandler.kt
        │       │   ├── data/
        │       │   │   ├── HttpRequestActionHandler.kt
        │       │   │   └── VariableActionHandler.kt
        │       │   ├── flow/
        │       │   │   ├── IfElseActionHandler.kt
        │       │   │   ├── WaitActionHandler.kt
        │       │   │   └── RepeatActionHandler.kt
        │       │   └── root/
        │       │       └── ShellCommandActionHandler.kt ← root gerektirir
        │       │
        │       ├── condition/                   ── KOŞULLAR
        │       │   ├── base/
        │       │   │   └── ConditionHandler.kt  ← interface: evaluate(): Boolean
        │       │   ├── TimeConditionHandler.kt
        │       │   ├── AppStateConditionHandler.kt
        │       │   ├── WifiConditionHandler.kt
        │       │   ├── BatteryConditionHandler.kt
        │       │   ├── LocationConditionHandler.kt
        │       │   └── VariableConditionHandler.kt
        │       │
        │       ├── ui/                          ── COMPOSE UI
        │       │   ├── MainActivity.kt
        │       │   ├── theme/
        │       │   │   ├── Theme.kt
        │       │   │   ├── Color.kt
        │       │   │   └── Type.kt
        │       │   ├── screen/
        │       │   │   ├── home/
        │       │   │   │   ├── HomeScreen.kt
        │       │   │   │   └── HomeViewModel.kt
        │       │   │   ├── editor/
        │       │   │   │   ├── MacroEditorScreen.kt
        │       │   │   │   ├── MacroEditorViewModel.kt
        │       │   │   │   ├── TriggerPickerScreen.kt
        │       │   │   │   ├── ConditionPickerScreen.kt
        │       │   │   │   └── ActionPickerScreen.kt
        │       │   │   ├── log/
        │       │   │   │   ├── LogScreen.kt
        │       │   │   │   └── LogViewModel.kt
        │       │   │   ├── variables/
        │       │   │   │   └── VariablesScreen.kt
        │       │   │   ├── settings/
        │       │   │   │   └── SettingsScreen.kt
        │       │   │   └── permissions/
        │       │   │       └── PermissionsScreen.kt
        │       │   └── component/               ← yeniden kullanılabilir bileşenler
        │       │       ├── MacroCard.kt
        │       │       ├── TriggerCard.kt
        │       │       ├── ActionCard.kt
        │       │       └── PermissionBanner.kt
        │       │
        │       ├── di/                          ── DEPENDENCY INJECTION (Hilt)
        │       │   ├── AppModule.kt
        │       │   ├── DatabaseModule.kt
        │       │   ├── EngineModule.kt
        │       │   └── ServiceModule.kt
        │       │
        │       └── util/
        │           ├── PermissionsManager.kt
        │           ├── RootChecker.kt
        │           ├── VariableInterpolator.kt  ← %VARIABLE metinlere ekle
        │           ├── JsonUtils.kt
        │           └── Extensions.kt
        │
        ├── test/                                ← unit testler
        │   └── kotlin/com/maraba/app/
        │       ├── engine/
        │       │   ├── TriggerEngineTest.kt
        │       │   ├── ConditionEvaluatorTest.kt
        │       │   └── MacroExecutorTest.kt
        │       ├── action/
        │       │   └── HttpRequestActionTest.kt
        │       └── util/
        │           └── VariableInterpolatorTest.kt
        │
        └── androidTest/                         ← instrumentation testler
            └── kotlin/com/maraba/app/
                ├── ui/
                │   ├── HomeScreenTest.kt
                │   └── MacroEditorTest.kt
                └── service/
                    └── AccessibilityServiceTest.kt
```

---

## 3. Agent Rolleri ve Kuralları

---

### AGENT: android-architect

**Tetikleyici:** Yeni modül, yeni trigger/action/condition tipi, ADR değişikliği

**Sorumluluklar:**
- Sealed class hiyerarşilerini (Trigger, Condition, Action) tutarlı tut
- Yeni trigger/action eklenince ilgili sealed class branch + Handler + DI binding üçlüsü ekle
- Servisler arası iletişim her zaman `Binder IPC` veya `SharedFlow` üzerinden
- Hiçbir servis başka bir servise doğrudan referans tutmaz

---

### AGENT: engine-developer

**Tetikleyici:** `trigger/`, `action/`, `condition/`, `domain/engine/` dizinleri

**Kurallar:**

1. **TriggerEngine:**
   - Her trigger handler `Flow<TriggerEvent>` emit eder
   - TriggerEngine tüm flow'ları `merge()` ile birleştirir
   - Trigger ateşlenmesi → ConditionEvaluator → MacroExecutor zinciri coroutine scope içinde
   - Bir trigger'ın hatası diğerlerini durdurmamalı (`catch` + log + devam)

2. **MacroExecutor:**
   - Aksiyonlar sırayla yürütülür (paralel değil, akış kontrolü için önemli)
   - Her aksiyon `ActionResult.Success` veya `ActionResult.Failure` döner
   - `Failure` durumunda: log yaz, kullanıcıya bildirim (ayarlanabilir), devam et veya dur (makro ayarına göre)
   - Maksimum çalışma süresi: 5 dakika (configurable), aşılırsa timeout

3. **VariableManager:**
   - Built-in değişkenler `StateFlow` ile anlık güncellenir
   - `%VARIABLE` interpolation her action param'ında çalışır
   - Değişken adları büyük harf + alt çizgi, `%` ile başlar

4. **Action Handler kuralları:**
   - Her handler `suspend fun execute(action: XxxAction, context: ActionContext): ActionResult`
   - Accessibility gerektiren handler'lar: servis bağlı değilse `ActionResult.Failure(reason=NO_ACCESSIBILITY)` döner, crash olmaz
   - Root gerektiren handler'lar: `RootChecker.isRooted()` false ise `ActionResult.Failure(reason=ROOT_REQUIRED)` döner

5. **Trigger Handler kuralları:**
   - Her handler `CoroutineScope` alır, `onDestroy`'da iptal edilir
   - İzin yoksa `Flow` emit etmez, sessizce durur (logla)
   - Aynı trigger'ın 1 saniye içinde iki kez tetiklenmesi engellenir (debounce)

---

### AGENT: ui-developer

**Tetikleyici:** `ui/` dizini, tüm Compose dosyaları

**Kurallar:**

1. **ViewModel'den UI'ya veri akışı:**
   - ViewModel: `StateFlow<UiState>` (tek state nesnesi)
   - UI: `collectAsStateWithLifecycle()` ile toplar
   - Side effect'ler: `Channel<UiEvent>` → `LaunchedEffect` ile consume

2. **Compose kuralları:**
   - Her ekran `@Composable` + ayrı `ViewModel` (Hilt ile inject)
   - Reusable component'ler `component/` altında, ViewModel bağımlılığı olmaz
   - `modifier` parametresi her public composable'da `Modifier = Modifier` default ile
   - Preview: her composable için en az 1 `@Preview`

3. **UX kriterleri:**
   - Trigger/Condition/Action seçimi: arama destekli liste, kategorilere ayrılmış
   - Her makro kart: isim, trigger özeti, son çalışma, toggle switch
   - İzin gerektiğinde: açıklayıcı diyalog, Settings'e yönlendirme butonu
   - Boş durumlar (makro yok, log yok): illustration + açıklama + aksiyon butonu

4. **Tema:**
   - Material 3, dynamic color (Android 12+ cihazlarda)
   - Dark mode zorunlu
   - Font: sistem fontu (Roboto), custom font yok

---

### AGENT: service-developer

**Tetikleyici:** `service/` dizini

**Kurallar:**

1. **MarabaForegroundService:**
   - `startForeground()` ilk 10 saniye içinde çağrılmalı (ANR riski)
   - Persistent notification: makro sayısı, son çalışan makro, pause butonu
   - `onTaskRemoved()` override: kullanıcı task'tan kapatırsa servisi yeniden başlat
   - Battery optimization whitelist yönlendirmesi ilk kurulumda yapılır

2. **MarabaAccessibilityService:**
   - `onAccessibilityEvent()` hızlı döner; ağır iş `launch(Dispatchers.Default)` ile async
   - `AccessibilityNodeInfo.recycle()` her kullanımdan sonra
   - Servis kapatıldığında `TriggerEngine`'e bildir → UI aksiyon'ları devre dışı kalır

3. **MarabaNotificationListener:**
   - Sadece pakete göre filtrele, tüm bildirimleri işleme alma (performans)
   - PII içeren bildirim metni loglara yazılmaz, sadece trigger eşleşmesi kontrol edilir

---

### AGENT: data-developer

**Tetikleyici:** `data/` dizini, Room entity'leri, repository'ler

**Kurallar:**

1. **Room:**
   - Migration zorunlu: her schema değişikliği → `Migration(from, to)` sınıfı
   - `@TypeConverter`: Trigger/Condition/Action sealed class'lar JSON olarak serialize
   - DAO'lar `Flow<List<T>>` döner (reactive), tek seferlik işlem `suspend fun`

2. **Repository pattern:**
   - Repository, data source detaylarını gizler
   - ViewModel/UseCase hiçbir zaman DAO'ya doğrudan erişmez
   - Repository `interface` + `Impl` pattern (test kolaylığı)

3. **JSON serialization:**
   - Kotlinx Serialization kullanılır (Gson/Moshi değil)
   - Sealed class'lar `@SerialName` + custom serializer ile

---

### AGENT: test-engineer

**Tetikleyici:** `test/`, `androidTest/` dizinleri

**Test yapısı:**

```
Unit (cihaz gerekmez, hızlı):
  - ConditionEvaluator logic
  - VariableInterpolator
  - MacroExecutor aksiyon zinciri (mock ActionHandler)
  - Repository (in-memory Room)

Instrumentation (emülatör/cihaz):
  - Compose UI testleri
  - Accessibility Service entegrasyon
  - Room migration testleri

Manuel test checklist (CI'da otomatize edilemeyenler):
  - Accessibility Service izin akışı
  - Notification Listener izin akışı
  - Konum izin akışı
  - Pil optimizasyonu whitelist yönlendirmesi
```

**Kural:** Her yeni trigger veya action tipi için en az 1 unit test yazılmadan implementation tamamlanmış sayılmaz.

---

## 4. Bağımlılıklar (libs.versions.toml)

```toml
[versions]
kotlin = "2.0.0"
agp = "8.5.0"
compose-bom = "2024.06.00"
hilt = "2.51.1"
room = "2.6.1"
coroutines = "1.8.1"
lifecycle = "2.8.3"
navigation = "2.7.7"
kotlinx-serialization = "1.7.1"
okhttp = "4.12.0"
play-services-location = "21.3.0"
work-runtime = "2.9.0"
mockk = "1.13.11"
junit5 = "5.10.3"
leakcanary = "2.14"

[libraries]
# Compose
compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "compose-bom" }
compose-ui = { group = "androidx.compose.ui", name = "ui" }
compose-material3 = { group = "androidx.compose.material3", name = "material3" }
compose-navigation = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigation" }

# Hilt
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-android-compiler", version.ref = "hilt" }
hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version = "1.2.0" }

# Room
room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }

# Coroutines
coroutines-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "coroutines" }

# Lifecycle
lifecycle-service = { group = "androidx.lifecycle", name = "lifecycle-service", version.ref = "lifecycle" }
lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycle" }

# Serialization
kotlinx-serialization-json = { group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-json", version.ref = "kotlinx-serialization" }

# Network
okhttp = { group = "com.squareup.okhttp3", name = "okhttp", version.ref = "okhttp" }

# Location
play-services-location = { group = "com.google.android.gms", name = "play-services-location", version.ref = "play-services-location" }

# WorkManager
work-runtime-ktx = { group = "androidx.work", name = "work-runtime-ktx", version.ref = "work-runtime" }

# Test
mockk = { group = "io.mockk", name = "mockk", version.ref = "mockk" }
junit5-api = { group = "org.junit.jupiter", name = "junit-jupiter-api", version.ref = "junit5" }

# Debug
leakcanary = { group = "com.squareup.leakcanary", name = "leakcanary-android", version.ref = "leakcanary" }
```

---

## 5. AndroidManifest.xml — Kritik Bölümler

```xml
<!-- Servisler -->
<service
    android:name=".service.MarabaForegroundService"
    android:foregroundServiceType="specialUse"
    android:exported="false"/>

<service
    android:name=".service.MarabaAccessibilityService"
    android:exported="true"
    android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
    <intent-filter>
        <action android:name="android.accessibilityservice.AccessibilityService"/>
    </intent-filter>
    <meta-data
        android:name="android.accessibilityservice"
        android:resource="@xml/accessibility_service_config"/>
</service>

<service
    android:name=".service.MarabaNotificationListener"
    android:exported="true"
    android:permission="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE">
    <intent-filter>
        <action android:name="android.service.notification.NotificationListenerService"/>
    </intent-filter>
</service>

<!-- Boot receiver -->
<receiver
    android:name=".trigger.BroadcastTriggerReceiver"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED"/>
        <action android:name="android.intent.action.LOCKED_BOOT_COMPLETED"/>
    </intent-filter>
</receiver>
```

---

## 6. Hata Yönetimi Standardı

```kotlin
// Aksiyon sonuç modeli
sealed class ActionResult {
    object Success : ActionResult()
    data class Failure(
        val reason: FailureReason,
        val message: String,
        val isRetryable: Boolean = false
    ) : ActionResult()
}

enum class FailureReason {
    NO_ACCESSIBILITY,      // Accessibility Service bağlı değil
    NO_PERMISSION,         // İzin yok
    ROOT_REQUIRED,         // Root gerekiyor
    ELEMENT_NOT_FOUND,     // UI element bulunamadı
    TIMEOUT,               // Zaman aşımı
    NETWORK_ERROR,         // HTTP isteği başarısız
    INVALID_PARAMETER,     // Geçersiz parametre
    UNKNOWN                // Bilinmeyen hata
}
```

---

## 7. Loglama Standardı

```kotlin
// ExecutionLog: her makro çalışması için bir kayıt
data class ExecutionLog(
    val id: Long = 0,
    val macroId: String,
    val macroName: String,
    val triggeredAt: Long,
    val completedAt: Long?,
    val status: ExecutionStatus,  // SUCCESS, PARTIAL, FAILED, SKIPPED(condition)
    val actionResults: List<ActionLogEntry>,
    val errorMessage: String?
)

// Hassas veri kuralı: SMS içeriği, bildirim metni, HTTP response body
// loglara TAM olarak yazılmaz — sadece eşleşme sonucu (matched: true/false)
```

---

## 8. Performans Bütçesi

| Metrik | Hedef | Kritik Eşik |
|--------|-------|------------|
| Trigger → Aksiyon başlama gecikmesi | < 300ms | > 1s |
| UI aksiyonu (tap/swipe) | < 500ms | > 2s |
| Accessibility event işleme | < 50ms | > 200ms (ANR riski) |
| Uygulama başlatma (cold start) | < 2s | > 4s |
| Bellek kullanımı (servis) | < 60MB | > 120MB |
| Pil tüketimi | Minimal (WorkManager + Doze uyumlu) | |

---

## 9. Yol Haritası (Roadmap)

### v0.1 — Temel Altyapı
- [ ] Proje iskelet + Hilt + Room + Compose Navigation
- [ ] Makro veri modeli + Room entity + DAO
- [ ] MarabaForegroundService (boş, sadece çalışır durumda)
- [ ] HomeScreen (makro listesi, boş durum)
- [ ] Basit makro oluşturma (sadece zaman trigger + bildirim aksiyonu)

### v0.2 — Trigger Motoru
- [ ] TriggerEngine + Flow mimarisi
- [ ] TimeTriggerScheduler (WorkManager)
- [ ] AppTriggerTracker (Accessibility)
- [ ] NotificationTriggerListener
- [ ] ConditionEvaluator (zaman, pil, Wi-Fi)

### v0.3 — Aksiyon Motoru
- [ ] MacroExecutor (sıralı çalışma, error handling)
- [ ] UI aksiyonları (tap, swipe, type)
- [ ] Sistem aksiyonları (ses, parlaklık, Wi-Fi)
- [ ] HTTP Request aksiyonu
- [ ] VariableManager + interpolation

### v0.4 — Gelişmiş Özellikler
- [ ] Konum trigger (Geofencing)
- [ ] SMS/Arama trigger ve aksiyon
- [ ] Akış kontrol aksiyonları (if/else, döngü, bekle)
- [ ] Log ekranı
- [ ] İzin yönetimi ekranı

### v0.5 — Kullanıcı Deneyimi
- [ ] Makro şablonları (hazır örnekler)
- [ ] Makro import/export (JSON)
- [ ] Değişkenler ekranı
- [ ] Bildirim üzerinden hızlı makro çalıştırma

### v1.0 — Stable Release
- [ ] Root özellikler (shell komutu, system settings)
- [ ] Tasker eklentisi uyumluluğu (plugin API)
- [ ] Widget (ana ekran)
- [ ] Tam test kapsamı (%80 unit)
- [ ] Play Store hazırlığı

---

## 10. Katkı Kuralları (Claude Code için)

1. Her görev öncesi ilgili `AGENT` bölümünü oku
2. Yeni trigger/action/condition tipi eklerken: **sealed class branch + Handler sınıfı + DI binding + birim test** — dördü birlikte
3. Sealed class'lara `when` expression'lar her zaman exhaustive olmalı (else yasak)
4. `Thread.sleep()` yasak → `delay()` kullan
5. Context leak'i önle: `WeakReference` veya `ApplicationContext`
6. Her yeni ekran için `ViewModel` + `UiState` data class zorunlu
7. String literal'lar UI'da yok: `strings.xml` kullan
8. Room migration atlanmaz: schema değişikliği → migration sınıfı
9. `Log.d/e()` üretim kodunda yok: `Timber` kullan
10. Commit formatı: `feat(trigger): zaman trigger WorkManager entegrasyonu`

---

*Proje: Maraba Android Otomasyon Uygulaması*
*Hedef: MacroDroid / Tasker alternatifi, tamamen açık kaynak*
*Son güncelleme: proje başlangıcı — v0.1 öncesi*
