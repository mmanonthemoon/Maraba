# CLAUDE.md — Maraba

Maraba, Android cihazlarda çalışan **MacroDroid / Tasker benzeri** bir otomasyon uygulamasıdır.
PC bağlantısı yoktur. Her şey Android'in kendi içinde çalışır.

**Her görev öncesinde AGENTS.md'yi oku.** Tek kaynak belgedir.

## Hızlı Referans

- **Dil:** Kotlin (% 100 native Android)
- **Paket:** `com.maraba.app`
- **Min SDK:** 29 (Android 10), Target: 35
- **UI:** Jetpack Compose + Material 3
- **DI:** Hilt
- **DB:** Room
- **Async:** Coroutines + Flow
- **Serialization:** Kotlinx Serialization

## Temel Konsept

```
Makro = Trigger(lar) + Koşul(lar) + Aksiyon(lar)
```

Kullanıcı kural oluşturur → uygulama arka planda izler → kural eşleşince otomatik çalışır.

## Mevcut Milestone

AGENTS.md Bölüm 9 — Roadmap'teki ilk tamamlanmamış görevden devam et.

## Kritik Kurallar

- `Thread.sleep()` yasak → `delay()` kullan
- `else` branch sealed class `when` expression'larında yasak
- Her yeni trigger/action/condition: sealed class + Handler + DI binding + test (hepsi birlikte)
- Context leak: `WeakReference` veya `ApplicationContext`
- Log: `Timber` (üretim kodunda `Log.d/e()` yok)
- UI string: `strings.xml` (kod içinde string literal yok)
