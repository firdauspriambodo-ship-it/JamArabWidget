Jam Arab Widget - versi modifikasi

Perubahan:
- Desain minimal modern dengan latar gelap semi-transparan.
- Sudut membulat dan border tipis agar terlihat rapi di home screen.
- Angka Arab (٠١٢٣٤٥٦٧٨٩) tetap digunakan.
- Format 24 jam HH:mm.
- Refresh diselaraskan mendekati pergantian menit.
- Refresh ulang saat boot, perubahan waktu, dan perubahan zona waktu.
- Tidak membutuhkan root dan tidak memakai library eksternal.

Project: Android Gradle Plugin 8.2.2 / compileSdk 35 / targetSdk 35.

--- Catatan Gradle Wrapper ---
File gradlew, gradlew.bat, dan gradle/wrapper/gradle-wrapper.properties sudah
disertakan (target Gradle 8.2). Satu file biner yang belum bisa disertakan
di sini adalah gradle/wrapper/gradle-wrapper.jar, karena butuh koneksi
internet untuk mengunduhnya dari server Gradle resmi.

Cara mendapatkannya otomatis (pilih salah satu):
1. Buka project ini di Android Studio -> saat sync pertama kali, Studio akan
   otomatis melengkapi gradle-wrapper.jar yang hilang.
2. Atau, jika sudah punya Gradle terinstall di komputer, jalankan di folder
   project ini:
       gradle wrapper --gradle-version 8.2
   Ini akan membuat ulang gradle-wrapper.jar yang sesuai.

--- Build otomatis via GitHub Actions (tanpa laptop) ---
File .github/workflows/build.yml sudah disertakan. Cara pakai:
1. Buat repository baru di GitHub (lewat browser HP juga bisa).
2. Upload SELURUH isi folder JamArabWidget ini ke repo tersebut
   (pertahankan struktur folder apa adanya, termasuk folder .github).
3. Buka tab "Actions" di repo -> workflow "Build APK" akan otomatis
   berjalan setiap ada push ke branch main/master (atau jalankan manual
   lewat tombol "Run workflow").
4. Setelah selesai (warna hijau/centang), klik run yang selesai tsb ->
   scroll ke bagian "Artifacts" -> unduh "JamArabWidget-debug-apk".
5. Extract file zip artifact tsb, di dalamnya ada app-debug.apk siap install.
