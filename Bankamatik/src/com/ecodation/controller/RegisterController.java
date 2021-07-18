	package com.ecodation.controller;
	import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ecodation.dao.IDaoImplements;
import com.ecodation.dao.RegisterDao;
import com.ecodation.dto.RegisterDto;


	public class RegisterController implements Serializable, IDaoImplements<RegisterDto>{
	private static final long serialVersionUID = 59853837987807058L;
	private RegisterDto registerDto=new RegisterDto();
	private RegisterDao registerDao=new RegisterDao();
	private ArrayList<RegisterDto> registerList;
	
	//Giris ve kayit seceneklerinin oldugu kisim:
	public void loginOrSignUp() {
		Scanner klavye = new Scanner(System.in);
		System.out.println("Yapmak istediginiz islemi seçciniz\n 1)Giris yap\n 2)Kayiýt ol\n3)Cikis");
		int key=klavye.nextInt();
		klavye.close();          
		switch (key) {
	case 1:
		login();
		break;
		
   case 2:
		registerDao.signUp();
		break;

   case 3:
		System.out.println("Cikis yapiliyor...");
		System.exit(0);
		break;
	default:
		System.out.println("Lutfen belirtilen aralikta deger giriniz.");
		break; 
	}
    }
	
	//Login
	public void login() {
			RegisterDao dao=new RegisterDao();
			RegisterController cont=new RegisterController();
			try(Scanner klavye = new Scanner(System.in); ) {
					System.out.println("\nLutfen emailinizi giriniz:");
					String Email = klavye.nextLine();
					System.out.println("\nSifrenizi giriniz:");
					String Password = klavye.nextLine();
					boolean result = dao.searchUser(Email, Password); 
					String aktiflik=dao.aktif(Email);
					while(result==true && aktiflik=="aktif") { 
					int Id = dao.idSearch(Email, Password);
					if (Id==1) {
						System.out.println("\nAdmin paneline yonlendiriliyorsunuz...");
						adminOperations(Email,Id);
					} else {
	                    cont.userOperations(Email,Id);
					}}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}

    //Admin Ýþlemleri
	public static void adminOperations(String email,int id){
			System.out.println("\nYapmak istediginiz islemin basindaki sayiyi klavyeden giriniz:\n1)Para Yatirma\n 2)Para Cekme\n3)Havale Yapma\n4)Mail Gonderme\n5)Musteri Hesap Kayitlarina Gozat\\n6)Musteri Hesap Durumu Guncelle\n7)Cikis");
			Scanner klavye = new Scanner(System.in);
			int key=klavye.nextInt();
			klavye.close();
			switch (key) {
		case 1:
			paraYatýr(id);
			adminMenu(email,id);
			break;
        case 2:
			paraCek(id);
			adminMenu(email,id);
			break;
        case 3:
	        havaleYap(id);
	        adminMenu(email,id);
	        break;
        case 4:
	        mailGonder(email,id);
	        adminMenu(email,id);
	        break;
        case 5:
	        hesapKontrol();
	        adminMenu(email,id);
	        break;
        case 6:
	        hesapGuncelle();
	        adminMenu(email,id);
	        break;

        case 7:
			System.out.println("Cikis yapiliyor...");
			System.exit(0);
			break;
		default:
			System.out.println("Lutfen belirtilen aralikta deger giriniz.");
			break;
		}
		}
		
	//User Ýþlemleri
	public void userOperations(String Email,int id) {
					System.out.println("\nYapmak istediginiz islemin basýndaki sayiyi klavyeden giriniz:\n1)Para Yatirma\n 2)Para Cekme\3)Havale Yapma\n4)Mail Gonderme\n5)Cikis");
					Scanner klavye = new Scanner(System.in);
					int key=klavye.nextInt();
					klavye.close();
							switch (key) {
					case 1:
						paraYatýr(id);
						userMenu(Email,id);
						break;
			        case 2:
						paraCek(id);
						userMenu(Email, id);
						break;
			        case 3:
				        havaleYap(id);
				        userMenu(Email,id);
				        break;
			        case 4:
				        mailGonder(Email,id);
				        userMenu(Email,id);
				        break;
				        
			        case 5:
						System.out.println("Cikis yapiliyor...");
						System.exit(0);
						break;
					default:
						System.out.println("Lutfen belirtilen aralikta deger giriniz.");
						break;
					}
					
				}
	
	//User hesabýný dondurabilir veya yeniden aktiflestirebilir.
    private static void hesapGuncelle() {
        	RegisterDao dao=new RegisterDao();
        	Scanner sc = new Scanner(System.in);
        System.out.println("\nYapmak istediginiz islemi seciniz:\n1)Musteri hesap dondurma\n2)Dondurulmuþ bir hesabi aktiflestirme\n3)Cikis");	
        int key=sc.nextInt();
        sc.close();
        switch (key) {
		case 1:
			dao.dondur();
			break;
        case 2:
			dao.aktifle();
			break;
        case 3:
        System.out.println("Sistemden cikis yapiyorsunuz...");
        System.exit(0);
        default:
			break;
		}
        }
        
	//Musteri hesap gunlukleri incelenebilir:
    public static void hesapKontrol() {
			Scanner sc = new Scanner(System.in);
			RegisterDao dao=new RegisterDao();
			System.out.println("\n1)Musteri kayitlari icinden incelemek istediginiz id'yi secin.\n2)Sectiginiz id sahibinin hesap gunluklerini inceleyin.\n3)Cikis");
            int key=sc.nextInt();
			sc.close();
			ArrayList<RegisterDto> kayit; //kayitli tum kullanicilar listelenecek, boylece admin iclerinden birini secebilir.
			switch (key) {
			case 1:
				kayit=dao.list();
				System.out.println(kayit);
				break;
            case 2:
				dao.musteriHesapGunlugu(); //girdiði id ile musterinin yaptigi hesap islemlerinin kayitlarina ulasir.
				break;
            case 3:
	            System.out.println("Cikis yapiliyor...");
	            System.exit(0);
	            break;
			default:
				break;
			}
		}
		
	//mail gonderme islemi:
    public static void mailGonder(String email, int id) {
			  Scanner sc = new Scanner(System.in);
			  System.out.println("\nMail gonderilecek adresi giriniz:");
			  sc.close();
			  String to = sc.nextLine();
		      String from = email; 
		      String host = "localhost";  
		  
		     Properties properties = System.getProperties();  
		      properties.setProperty("mail.smtp.host", host);  
		      Session session = Session.getDefaultInstance(properties);  
		  
		      try{  
		         MimeMessage message = new MimeMessage(session);  
		         message.setFrom(new InternetAddress(from));  
		         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
		         System.out.println("\nMailinizin konusunu giriniz:");
		         String subject=sc.nextLine();
		         message.setSubject(subject);
		         System.out.println("\nGondereceginiz maili giriniz:");
		         String text=sc.nextLine();
		         message.setText(text);  
		  
		         Transport.send(message);  
		         System.out.println("Mail basariyla gonderildi.");   
		         RegisterDao dao=new RegisterDao();
		         dao.mailKayit(id, to); //user tablosuna islem kaydi gonderiyoruz.
		         }catch (MessagingException mex) {mex.printStackTrace();}  	
		}

	//havale islemi:
    public static void havaleYap(int id) {
			RegisterDao dao=new RegisterDao();
			Scanner klavye = new Scanner(System.in);
			System.out.println("\nYatirilacak tutari rakamla giriniz:");
			int tutar=klavye.nextInt();
			klavye.close();
			System.out.println("\nAlicinin mail adresini giriniz:");
			String alici=klavye.next();
			klavye.close();
			int bakiye=dao.bakiyeBul(id); //para gondermek isteyen kullanicinin bakiyesini ogrendik.
			if (bakiye>tutar) {
				int acc1=bakiye-tutar;
				dao.bakiyeGuncelle(id,acc1);    //para gonderen kullanicinin bakiye durumu guncellendi.
				int acc2=dao.aliciBul(alici); //alicinin hesabindaki para miktari ogrenildi.
				int accfinal=acc2+tutar;
				dao.aliciupdate(accfinal,alici); //alicinin hesap bakiyesi guncellendi
				dao.havaleKayit(id, alici,tutar);  //userpanel tablosuna islem kaydi gonderiyoruz.
			} else {
				System.out.println("\nUygun bir miktar girmediniz.\nParaniz geri iade ediliyor.");
	            return;
			}
			
			
			
		}

	//Para cekme islemi
    public static void paraCek(int id) {
    	    RegisterDao dao=new RegisterDao();
			Scanner klavye = new Scanner(System.in);
			System.out.println("\nCekilecek tutari rakamla giriniz:");
			int tutar=klavye.nextInt();
			int bakiye=dao.bakiyeBul(id); //halihazirda kullanici hesabinda kac tl var?
			klavye.close();
			if (tutar<bakiye) {
				int acc=bakiye-tutar;
				dao.bakiyeGuncelle(id,acc); //para cektikten sonra kalan miktar guncellendi.
				System.out.println("\nIsleminiz basariyla tamamlandi.\nHesabinizda guncel olarak:" +acc +"tl bulunmaktadir.");
				dao.cekKayit(id, tutar);//user tablosuna islem kaydi gonderiyoruz.
				}
		    else {
            System.out.println("\nUygun bir miktar girmediniz.\nParanýz geri iade ediliyor.");
            return;
			}}
		
    //para yatirma islemi
	public static void paraYatýr(int id) {
			RegisterDao dao=new RegisterDao();
			Scanner klavye = new Scanner(System.in);
			System.out.println("\nYatirilacak tutari giriniz:");
			int tutar=klavye.nextInt();
			klavye.close();
			int bakiye=dao.bakiyeBul(id);
			if (tutar%5==0) {
				int acc=bakiye+tutar;
				dao.bakiyeGuncelle(id,acc);
				System.out.println("\nIsleminiz basariyla tamamlandi.\nHesabinizda guncel olarak:" +acc +"tl bulunmaktadir.");
				dao.yatirKayit(id, tutar); //user tablosuna islem kaydi gonderiyoruz.
			} else { 
            System.out.println("\nUygun bir miktar girmediniz.\nParaniz geri iade ediliyor.");
            return;
			}}
	
	//islemlerden sonra admin menusune geri donebilmek icin	
    public static void adminMenu(String email,int id) {
	Scanner klavye = new Scanner(System.in);
	System.out.println("Menuye donmek icin 1'i, sistemden cikis yapmak icin 2'yi tuslayiniz.");
	int key=klavye.nextInt();
	klavye.close();
	
	switch (key) {
	case 1:
		adminOperations(email,id);
		break;
    case 2:
    	System.out.println("Cikis yapiliyor...");
		System.exit(0);
		break;
	default:
		System.out.println("Lutfen belirtilen aralikta bir deger giriniz.");
		break;}}
    
    //islemlerden sonra user menuye geri donebilmek icin
    public static void userMenu(String email, int id) {
    	RegisterController cont=new RegisterController();
    	Scanner klavye = new Scanner(System.in);
    	System.out.println("Ana menuye donus yapmak icin 1'i, sistemden cikis yapmak icin 2'yi tuslayiniz.");
    	int key=klavye.nextInt();
    	klavye.close();
    	switch (key) {
    	case 1:
    		cont.userOperations(email,id);
    		break;
        case 2:
        	System.out.println("Cýkýs yapiliyor...");
    		System.exit(0);
    		break;
    	default:
    		System.out.println("Lutfen belirtilen aralikta bir deger giriniz.");
    		break;}}

		@Override
		public void create(RegisterDto t) {
			registerDao.create(t);	
		}

		@Override
		public void update(RegisterDto t) {
			registerDao.update(t);	
			
		}

		@Override
		public void delete(long id) {
			registerDao.delete(id);	
			
		}

		@Override
		public ArrayList<RegisterDto> list() {
			registerList = registerDao.list();
			return registerList;
		}

		public RegisterDto getRegisterDto() {
			return registerDto;
		}

		public void setRegisterDto(RegisterDto registerDto) {
			this.registerDto = registerDto;
		}

		public Connection getInterfaceConnection() {
			return getInterfaceConnection();
		}

	}