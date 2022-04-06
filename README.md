# github-user-app
Project ini adalah hasil akhir dari pembelajaran pada <strong>[Kelas Dicoding Belajar Fundamental Aplikasi Android (BFAA)](https://www.dicoding.com/academies/14)</strong>.

Repository ini saya buat hanya untuk refrensi teman-teman agar dapat membantu memahami dan menyelesaikan submission akhir pada kelas tersebut.
Perlu dingat untuk teman-teman sekalian agar tidak hanya melakukan <strong>copy-paste</strong> kodingan yang ada tanpa memahami konteks ataupun isi dari logika pada project ini.

Untuk menggunakan menjalankan repo ini yang harus dilakukan adalah:
1. Masukan API Token github kalian pada file gradle.properties
2. Update dan sesuaikan gradle project.

Fitur atau hal apa saja yang diterapkan pada project ini diantaranya adalah:
- SearchView untuk melakukan pencarian data user
- TabLayout dengan ViewPager2 sebagai untuk menampilkan Followers dan Following User
- Retrofit
- Indikator Loading pada saat data sedang dimuat
- Menampilkan keterangan jika hasil search tidak ditemukan
- ViewModel dan LiveData
- Splash screen yang dapat menyesuaikan dengan tema yang dipilih
- Mengimplementasikan Room untuk menyimpan data ke dalam daftar favorite. alur untuk mengakses database yang saya gunakan : </br>View -> ViewModel -> Repository -> RoomDAO
- Menggunakan DataStore untuk penyimpanan pengaturan tema (DarkMode).
- UI Test Menggunakan Espresso.
