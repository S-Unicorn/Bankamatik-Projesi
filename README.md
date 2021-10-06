# Bankamatik Projesi
  Kullanılan teknolojiler:<p><img src="https://img.icons8.com/color/48/000000/java-coffee-cup-logo--v2.png"/><img src="https://img.icons8.com/color/48/000000/mysql-logo.png"/><img src="https://img.icons8.com/office/40/000000/java-eclipse.png"/></p><p><strong>Amaç:</strong> bir bankamatik projesinin ana hatlarını katmanlı mimariyle tasarlayarak temel metotlarını, sınıf, paket ve veri tabanı düzenini oluşturmak.
  Console’a giren kişiyi iki seçenek bekliyor: 
  <p><strong><ul><li>Kayıt olmak:</strong> Kişi, sisteme kayıtlı değilse bilgilerini girerek kaydolur.</br>
  <strong><li>Giriş yapmak:</strong> Kayıtlıysa mail ve şifresiyle sisteme giriş yaparak istediği bankamatik işlemini gerçekleştirir. </p></ul>
    <p> Bu proje <em>admin</em> ve <em>user</em> olmak üzere iki tip kullanıcıya hizmet vermek üzere tasarlanmıştır. Kullanıcı sisteme giriş yaptıktan sonra, kullanıcı tipine göre yapabilecekleri işlemlerin sıralandığı menüye yönlendirilir. </p>
     <p>Admin, user’ın yapabildiği:</br>
    <ul> <strong><li>Para çekme</br></li></strong>
     <strong><li>Para yatırma</br></li></strong>
     <strong><li>Havale ile para gönderme </br></li></strong>
     <strong><li>Mail gönderme </strong> işlemlerini gerçekleştirebilir ve buna ek olarak: </br></li></ul>
     <ul><strong><li>Seçtiği bir kullanıcının işlem geçmişini görüntüleme</strong></br></li>
    <strong><li>Kullanıcıların üyeliklerini aktif ya da pasif hale getirme</strong> yetkisine sahiptir.</p></li></ul>
     <p>User, yani sıradan kullanıcı ise sadece para çekme, para yatırma, para havale etme ve mail gönderme işlemlerini gerçekleştirebilir.</br> Bütün bunların veri tabanına kayıt edilmesi için MySQL’de: adminpanel, userpanel ve register olmak üzere üç ayrı tablo oluşturulmuştur.  </p>
     <p>Sisteme kayıtlı olan her üyenin bilgileri <em>register</em> tablosunda yer almaktadır. Sisteme ilk kez üye olmak isteyen kullanıcı: ad, soyad, şifre, hesap bakiyesi, id, aktiflik durumu, kullanıcı tipi ve mail adresiyle birlikte veri tabanına kaydedilir. Sistemi sadece bir tane admin olabilecek şekilde kurduğum için ilk kez kaydolan kullanıcı otomatik olarak “user”, kullanıcının hesap bakiyesi default olarak “0” ve aktiflik durumu “aktif” olarak kaydedilir. </p>
     <p>Tüm kullanıcıların para çekme, para yatırma, havale ve mail gönderme işlemleri ve bunların detayları, işlemlerin yapıldığı tarihle birlikte <em>userpanel</em> tablosuna kaydedilir.</p>
    <p> Admine özel işlemler olan hesap geçmişi görüntüleme ve kullanıcı aktifleştirme ya da pasifleştirme işlemi ise <em>adminpanel</em> tablosuna kaydedilir. Adminin yaptığı para çekme, para yatırma, havale ve mail gönderme işlemleri her kullanıcı için ortak işlemler oldukları için veri tabanındaki userpanel tablosuna kaydedilir.</p><a href="https://heyzine.com/flip-book/da5768aa44.html">Projenin detaylı tanıtımını ve açıklamasını yaptığım staj defterimi burdan inceleyebilirsiniz!</a>
