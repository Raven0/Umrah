## **AIWA Apps** (Alhijaz Indowisata Agency Apps)

AIWA Apps adalah applikasi Android yang dikembangkan oleh Team [Birutekno.inc](https://birutekno.com) untuk memudahkan interaksi Agen Alhijaz dengan Calon Jamaah. AIWA Apps dapat di download melalui [GooglePlay](https://play.google.com/store/apps/details?id=com.birutekno.aiwa). Berikut adalah dokumentasi teknis dari **AIWA Apps**:

- [Project Overview](#Project-Overview)
- [Struktur Project](#Struktur-Project)
- [Flow Apps](#Flow-Apps)
- [Struktur Modul](#Struktur-Modul)
- [Release GooglePlay](#Release-GooglePlay)

## **Project Overview**

Project ini memiliki basis kodingan yang sama dengan project iSchool. maka dari itu untuk alur kodingan akan memiliki karakter yang sama dengan project iSchool, pada bagian Struktur Project yang akan dijelaskan pada bagian berikutnya **diamkan** directories yang bernama **ui dan view**.</br>

Project ini menggunakan Retrofit sebagai main Network infrastructure nya, dan Response yang didapat di didefinisikan satu per satu (belum menggunakan base DAO).

## **Struktur Project**

├── root                # Tempat semua Activity</br>
|   ├── adapter         # Tempat semua Adapter</br>
|   ├── fragment        # Tempat semua Fragment</br>
|       ├── child       # Tempat fragment Banner</br>
|   ├── helper          # Tempat Helper (Network API, Push Notif Service, Response)</br>
|   ├── model           # Tempat semua Model untuk Response</br>
|   ├── ui              # base ui dari project iSchool</br>
|   └── view            # base view dari project iSchool</br>
|</br>

## **Flow Apps**

Flow Utama dari **AIWA Apps** dapat digambarkan melalui list berurutan di bawah ini.

1. Register
2. Login & Lupa Password
3. Menu Dasboard
4. Menu Jamaah
5. Menu Kalkulasi
6. Menu Jadwal
7. Menu Itinerary
8. Menu Hotel
9. Menu Galeri
10. Menu Pesan
11. Menu Profil
12. Menu Komisi
13. Menu Agen
14. Menu Catatan

## **Struktur Modul**

Struktur Modul Menu pada Project ini didefinisikan pada directories **root**. Menu Kalkulasi didefinisikan sebagai KalkulasiActivity, Menu Jadwal didefinisikan sebagai JadwalActivity, dan begitu seterusnya. </br>

Jika di dalam Activity tersebut terdapat ListView atau RecyclerView, maka file yang digunakan dalam Modul tersebut bisa dibuka melalui directories **adapter**, begitu juga pada fragment bisa dibuka melalui directories **fragment**. </br>

Jika ingin melakukan perubahan sebagai mana seperti bahasa java pada umumnya, file yang berkaitan bisa anda buka di atas file java tersebut yang di panggil melalui fungsi **import**. Begitu juga pada view, jika ingin membuat perubahan pada view, maka cari file yang mendefinisikan resourceLayout.

## **Release GooglePlay**

App Release di simpan [di sini](https://github.com/Raven0/Umrah/tree/master/app/release)
JKS di simpan pada root folder [key.jks](https://github.com/Raven0/Umrah/blob/master/key.jks) </br>
**password** : aiwa2018.
