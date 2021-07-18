# Bankamatik-Projesi
Ecodation Java SE Staj Projesi
Sisteme kayıt ya da giriş yaparak girdiğiniz bu bankamatik projesinde müşteri; para çekme, yatırma, havale ve mail gönderme işlemlerini gerçekleştirir. Adminin bunların yanında kendisine özel iki işlem seçeneği daha vardır: müşterinin yaptığı işlemlerin kaydını inceleme ve müşterinin hesabını aktifleştirip pasifleştirebilme.
Database'de üç adet tablo oluşturdum ve bunları birbiriyle 1-n ilişki biçimiyle bağladım. 
1(register) - n(userpanel ve adminpanel)
Register tablosunda, sisteme kayıtlı tüm kullanıcıların genel bilgileri bulunmakta: ad, soyad, id, mail, şifre, aktiflik durumu, hesap bakiyesi.
Userpanel tablosunda, sadece müşterilere ait hesap işlemleri kayıtlı. Müşteri ne zaman bankada bir işlem yapsa, idsiyle birlikte userpanel tablosunda yeni bir kayıt açılır.
Havale yaptıysa, para çekti veya yatırdıysa bunun miktarı kaydedilir. Havale veya mail gönderdiyse kime gönderdiği kaydedilir.
Adminpanel tablosunda ise, adminin üzerinde işlem yaptığı müşterinin id numarası ve ne tür bir işlem yaptığı kaydedilir.
Admin, dilediği zaman userpanel tablosundan müşterinin yaptığı tüm işlem kayıtlarına, idsinin bulunduğu satırların listesini çağırarak ulaşabilir.
