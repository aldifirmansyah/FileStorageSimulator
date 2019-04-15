# FileStorageSimulator

*simulator ini tidak menerapkan input handling

File Storage

- Terdiri dari 2 komponen, yaitu Folder dan File
- Suatu Folder A dapat berisi banyak Folder lainnya namun tidak terdapat File di dalam Folder A
- Jika sebuah File A dimasukkan ke dalam Folder B, namun Folder B berisikan Folder lain, maka File A akan dimasukkan ke dalam Folder yang berada di dalam Folder B. Hal ini dilakukan terus menerus hingga tidak ada Folder lagi di dalam sebuah Folder.
- Urutan berdasarkan leksikografis NAMA FOLDER


FILE

- Memiliki nama dan tipe
- Memiliki ukuran tersendiri


FOLDER

- Ukuran Folder adalah 1 + ukuran seluruh File dan/atau Folder yang ada di dalamnya
- Hanya berisikan SATU TIPE File saja
- Jika sebuah File bertipe M dimasukkan ke dalam sebuah Folder J yang sudah berisikan File bertipe N, maka File bertipe M akan dimasukkan ke dalam Folder selanjutnya dari urutan leksikografis berdasarkan nama Folder tersebut. Hal ini dilakukan terus-menerus hingga menemukan folder kosong atau berisikan File bertipe sama.
- Jika Folder selanjutnya berisi Folder lainnya, maka File dimasukkan ke dalam Folder yang ada di dalamnya.
- Jka tidak ada Folder selanjutnya, alias Folder terakhir dalam urutan leksikografis, maka Folder yang menjadi Folder selanjutnya adalah Folder pertama dalam urutan leksikografis alias memutar
- Jika di seluruh File Storage tidak ada Folder kosong maupun File yang bertipe sama, maka File tidak jadi dimasukkan.




Input dan Output :
Program akan menerima input untuk command seperti di bawah ini hingga End of
File:
1. Basic Operation

  1. add A B
  
  Memasukkan Folder A ke dalam Folder B
  e.g. add folderA root
  
  Perintah ini tidak memiliki keluaran
  
  
  2. insert A.B C D
  
  Memasukkan File A bertipe B dengan ukuran C ke dalam Folder D
  Jika operasi gagal, tidak mengeluarkan apapun
  e.g. insert santoryuu.exe 5 root
  
  Keluaran:
  “A.B added to X”
  X adalah nama Folder tempat A akhirnya dimasukkan
  
  
  3. remove A
  
  Menghapus File/Folder dengan nama A
  Jika A adalah Folder, maka hapus seluruh isinya juga
  Jika A adalah File, maka hapus semua File yang memiliki nama sama
  e.g. remove santoryuu
  
  Keluaran:
  Jika A adalah Folder
  “Folder A removed”
  Jika A adalah File
  “X File A removed”
  X adalah jumlah File A yang berhasil dihapus
  
  
  4. search A
  
  Mencari semua File/Folder dengan nama A di dalam File Storage
  e.g. search KongGun
    
  Keluaran:
  
```
  > root
    > folderA
      > KongGun.docx
    > folderB
      > KongGun.xlsx
```
  
  Cetak semua path dari menuju File/Folder yang dicari
  Jika yang dicari adalah File, cetak juga tipe Filenya
  
  
  5. print A
  
  Mencetak isi dari Folder dengan nama A
  Setiap Folder/File yang berada 1 level lebih dalam dicetak dengan
  indentasi 2 spasi lebih kanan dari Folder di atasnya.
  e.g. print root
  
  Keluaran:
  
```
  > root 9
    > folderA 8
      > nihilego.exe 3
      > santoryuu.exe 4
```
      
Contoh input 1:

```
add folderA root
add folderB root
add folderD folderA
add folderE folderA
add folderF root
add folderG root
add folderC root
insert ittoryuu.exe 1 folderE
insert nitoryuu.exe 2 folderB
insert santoryuu.exe 2 folderC
print root
```

Contoh output 1:

```
ittoryu.exe added to folderE
nitoryu.exe added to folderB
santoryu.exe added to folderC
> root 13
  > folderA 4  
    > folderD 1    
    > folderE 2    
      > ittoryu.exe 1      
  > folderB 3  
    > nitoryu.exe 2    
  > folderC 3  
    > santoryu.exe 2    
  > folderF 1  
  > folderG 1
```
  
Contoh input 2:
 
 ```
add folderA root
add folderB root
add folderD folderA
add folderE folderA
add folderF root
add folderG root
add folderC root
insert ittoryuu.exe 1 folderE
insert nitoryuu.exe 2 folderB
insert santoryuu.exe 2 folderC
insert haki.pptx 3 folderE
print root
remove folderB
search haki
```

Contoh output 2:

```
ittoryu.exe added to folderE
nitoryu.exe added to folderB
santoryu.exe added to folderC
haki.pptx added to folderD
> root 16
  > folderA 7  
    > folderD 4    
      > haki.pptx 3      
    > folderE 2    
      > ittoryu.exe 1      
  > folderB 3  
    > nitoryu.exe 2    
  > folderC 3  
    > santoryu.exe 2    
  > folderF 1  
  > folderG 1  
Folder folderB removed
> root
  > folderA  
    > folderD    
      > haki.pptx
```
