package com.ecodation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.ecodation.controller.RegisterController;
import com.ecodation.dto.RegisterDto;
import com.ecodation.utils.NowDateUtil;

import lombok.extern.java.Log;
@Log
public class RegisterDao implements IDaoImplements<RegisterDto> {
	
	ResultSet resultSet;
	private RegisterDto registerDto;
	
	@Override
	public void create(RegisterDto registerDto) {
		try (Connection connection =getInterfaceConnection()) {  
			connection.setAutoCommit(false); //transaction
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement("insert into register(register_name,register_surname,register_email,register_password) values(?,?,?,?)");
			preparedStatement.setString(1, registerDto.getRegisterName());
			preparedStatement.setString(2, registerDto.getRegisterSurname());
			preparedStatement.setString(3, registerDto.getRegisterEmail());
			preparedStatement.setString(4, registerDto.getRegisterPassword());
			preparedStatement.executeUpdate();
			int rowEffected= preparedStatement.executeUpdate();
			if(rowEffected>0) {
				log.info(RegisterDto.class + "Ekleme Basarili");
				connection.commit(); // transaction
			} 
			else 
			{
				log.warning(RegisterDto.class + "Ekleme Basarisiz" +NowDateUtil.DateUtil());
				
			connection.rollback(); // transaction
			connection.setAutoCommit(true);// transaction
			}
			
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
			log.warning(RegisterDto.class + "Ekleme Baþarýsýz" +NowDateUtil.DateUtil());
		}}

	@Override
	public void update(RegisterDto registerDto) {
		try(Connection connection=getInterfaceConnection()) {
			connection.setAutoCommit(false); //transaction
			PreparedStatement preparedStatement = connection.prepareStatement("update  register set register_name=?,register_surname=?,register_email=?,register_password=?,account_balance=?, where register_id=?  values(?,?,?,?,?,?,?)");
			preparedStatement.setString(1, registerDto.getRegisterName());
			preparedStatement.setString(2, registerDto.getRegisterSurname());
			preparedStatement.setString(3, registerDto.getRegisterEmail());
			preparedStatement.setString(4, registerDto.getRegisterPassword());
			preparedStatement.setInt(6, registerDto.getAccountBalance());
			preparedStatement.setInt(7, registerDto.getRegisterId());
			int rowEffected= preparedStatement.executeUpdate();
			if(rowEffected>0) {
				log.info(RegisterDto.class + "Guncelleme Basarili");
				connection.commit(); // transaction
			} 
			else 
			{
				log.warning(RegisterDto.class + "Guncelleme Basarisiz" +NowDateUtil.DateUtil());	
			connection.rollback(); // transaction
			connection.setAutoCommit(true);// transaction
			}
		} 
		    catch (SQLException e) {
			log.warning("Güncelleme sýrasýnda hata meydana geldi:::  " + RegisterDto.class);
			e.printStackTrace();
		}	
	}


	@Override
	public void delete(long id) {
			try(Connection connection=getInterfaceConnection()) {
			connection.setAutoCommit(false); //transaction
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement("delete from  register where register_id=?");
            preparedStatement.setLong(1, registerDto.getRegisterId());
			int rowEffected= preparedStatement.executeUpdate();
			if(rowEffected>0) {
				log.warning(RegisterDto.class + "Silme Basarili");
				connection.commit(); // transaction
			} 
			else 
			{
				log.warning(RegisterDto.class + "Silme Basarisiz" +NowDateUtil.DateUtil());	
			connection.rollback(); // transaction
			connection.setAutoCommit(true);// transaction
			}	
		} 
		catch (SQLException e) {
			log.warning(RegisterDto.class + "Silme Basarisiz" +NowDateUtil.DateUtil());
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<RegisterDto> list() {
		ArrayList<RegisterDto> list = new ArrayList<RegisterDto>();
		RegisterDto registerDto;
		try(Connection connection=getInterfaceConnection()) {
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement("select * from register");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				registerDto = new RegisterDto();
				registerDto.setRegisterId(resultSet.getInt("register_id"));
				registerDto.setRegisterName(resultSet.getString("register_name"));
				registerDto.setRegisterSurname(resultSet.getString("register_surname"));
				registerDto.setRegisterEmail(resultSet.getString("register_email"));
				registerDto.setRegisterPassword(resultSet.getString("register_password"));
				registerDto.setAccountBalance(resultSet.getInt("account_balance"));
				registerDto.setDate(resultSet.getDate("operation_date"));
				registerDto.setRegisterRoles(resultSet.getString("roles"));
				registerDto.setAktiflik(resultSet.getString("aktiflik"));
				list.add(registerDto);
			}
		} catch (SQLException e) {
			log.warning("Listeleme sýrasýnda hata oluþtu.");
			e.printStackTrace();
		}
		return list;
	}
	
	//Admin bu metot sayesinde userpanel tablosundan musterinin id'sini kullanarak, yaptigi islem bilgilerini cekebilecek.
	public void musteriHesapGunlugu() {
		System.out.println("\nHesap gunlugunu goruntulemek istediginiz kullanicinin id numarasini rakamla belirtiniz:");
		Scanner sc=new Scanner(System.in);
		int id=sc.nextInt();
		sc.close();
		ArrayList<RegisterDto> list = new ArrayList<RegisterDto>();
		RegisterDto registerDto;
		try(Connection connection=getInterfaceConnection()){
			connection.setAutoCommit(false); //transaction
			PreparedStatement pr = connection.prepareStatement("select * from userpanel where user_id = ?");    
			pr.setInt(1, id);    
			ResultSet resultSet = pr.executeQuery();
			if (resultSet.next()) {
				registerDto = new RegisterDto();
				registerDto.setUserId(resultSet.getInt("user_id"));
				registerDto.setOperationType(resultSet.getString("operation_type"));
				registerDto.setKime(resultSet.getString("kime"));
				registerDto.setMiktar(resultSet.getString("miktar"));
				list.add(registerDto);
				System.out.println("Musteri islem gunlugu listeleniyor:");
				for(RegisterDto dto : list) {
					System.out.println(dto);
					System.out.println("\n......");}
				connection.commit(); // transaction
			}
			else {
				connection.rollback(); // transaction
				connection.setAutoCommit(true);// transaction
			}
			musKayit(id);
			} 
	        catch (SQLException e) {
		    log.warning("Listeleme sirasinda hata olustu.");
			e.printStackTrace();	
		}
	}
	
	//Adminpanel tablosuna, hesaplarýný inceledigi musterinin id'si de ekleniyor.
	public void musKayit(int id) {
		try (Connection connection =getInterfaceConnection()) {
			connection.setAutoCommit(false); //transaction
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement("insert into adminpanel (id_user, op_type) values(?,?)");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, "Musteri tarafýndan yapilan islemler goruntulendi.");
			preparedStatement.executeUpdate();
			int rowEffected= preparedStatement.executeUpdate();
			if(rowEffected>0) {
				connection.commit(); // transaction
			} 
			else {
			connection.rollback(); // transaction
			connection.setAutoCommit(true);// transaction
			}	
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
//musteri email ile aratiliyor ve hesabi donduruluyor.
	public void dondur() {
				try(Connection connection=getInterfaceConnection()) {
					connection.setAutoCommit(false); //transaction
					String sql = "select * from register where register_email=?";
					PreparedStatement pr=connection.prepareStatement(sql);
					Scanner sc=new Scanner(System.in);
					System.out.println("\nPasifleþtirmek istediðiniz hesaba ait eposta adresini giriniz:");
					String eposta=sc.nextLine();
					sc.close();
					pr.setString(1, eposta);
					ResultSet rs=pr.executeQuery();
					while(rs.next()) {
					String aktiflik= rs.getString("aktiflik"); //database'den aktiflik bilgisini aliyoruz.
					if (aktiflik=="Aktif") {
						String query = "update register set aktiflik = ? where register_email = ?";
						PreparedStatement pre = connection.prepareStatement(query);
                        pre.setString(1, "Pasif");
                        pre.setString(2, eposta);
						pre.executeUpdate();
						System.out.println("Kullanicinin hesabi basariyla donduruldu.");
						int id=findId(eposta); //eposta ile id'yi buluyoruz.
						dKayit(id);           //yapilan islemi db'ye kaydediyoruz.
						connection.commit(); // transaction
						}
					    else {
						System.out.println("\nKullanici zaten pasif durumda.\nCikis yapmak icin 1'e, islemi tekrar denemek icin 2'ye basiniz:");
                    	int key=sc.nextInt();
                    	connection.rollback(); // transaction
						connection.setAutoCommit(true);// transaction
                    	switch (key) {
						case 1:
							System.exit(0);
							break;
                        case 2:
							dondur();
							break;
						default:
							System.out.println("Belirtilen aralýkta bir sayý giriniz.");
							break;
						}}}}
				    catch (Exception e) {
					e.printStackTrace();
					System.out.println("Baglanti hatasi");
				}}
	
//Musterinin hesabini dondurma islemleri de adminpanel tablosuna kaydediliyor.
	public void dKayit(int id) {
				try (Connection connection =getInterfaceConnection()) {
					connection.setAutoCommit(false); //transaction
					java.sql.PreparedStatement preparedStatement = connection.prepareStatement("insert into adminpanel (id_user, op_type) values(?,?)");
					preparedStatement.setInt(1, id);
					preparedStatement.setString(2, "Hesap donduruldu");
					preparedStatement.executeUpdate();
					int rowEffected= preparedStatement.executeUpdate();
					if(rowEffected>0) {
						connection.commit(); // transaction
					} 
					else {
					connection.rollback(); // transaction
					connection.setAutoCommit(true);// transaction
					}	
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
	}
//Admin musterinin hesabini aktiflestirebilir:
	public void aktifle() {
				try(Connection connection=getInterfaceConnection()) {
					connection.setAutoCommit(false); //transaction
					String sql = "select * from register where register_email=?";
					PreparedStatement pr=connection.prepareStatement(sql);
					Scanner sc=new Scanner(System.in);
					System.out.println("\nAktiflestirmek istediginiz hesaba ait eposta adresini giriniz:");
					String eposta=sc.nextLine();
					sc.close();
					pr.setString(1, eposta);
					ResultSet rs=pr.executeQuery();
					if(rs.next()) {
					    String query = "update register set aktiflik = ? where register_email = ?";
						PreparedStatement pre = connection.prepareStatement(query);
                        pre.setString(1, "Aktif");
                        pre.setString(2, eposta);
						pre.executeUpdate();
						System.out.println("Kullanicinin hesabi basariyla aktiflestirildi.");
						int id=findId(eposta);
						aKayit(id);
						connection.commit(); // transaction
						} 
					else { 
						connection.rollback(); // transaction
						connection.setAutoCommit(true);// transaction
                    	}}
				    catch (Exception e) {
					e.printStackTrace();
					System.out.println("Baglanti hatasi");
				    }}

	//Adminin yaptigi aktiflestirme islemi adminpanel'e kaydedilir:
	public void aKayit(int id) {
		try (Connection connection =getInterfaceConnection()) {
			connection.setAutoCommit(false); //transaction
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement("insert into adminpanel (id_user, op_type) values(?,?)");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, "Hesap aktive edildi.");
			preparedStatement.executeUpdate();
			int rowEffected= preparedStatement.executeUpdate();
			if(rowEffected>0) {
				connection.commit(); // transaction
			} 
			else {
			connection.rollback(); // transaction
			connection.setAutoCommit(true);// transaction
			}	
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//mail adresini bildigimiz bir kullanicinin Id'sini bulalim:
	public int findId(String eposta) {
		try(Connection connection=getInterfaceConnection()) {
			connection.setAutoCommit(false); //transaction
			String sql = "select * from register where register_email=?";
			PreparedStatement pr=connection.prepareStatement(sql);
			pr.setString(1, eposta);
			ResultSet rs=pr.executeQuery();
			if (rs.next()) {
				registerDto = new RegisterDto();
				int id= rs.getInt("register_id"); 
				connection.commit(); // transaction
				return id;	
			}
			connection.rollback(); // transaction
			connection.setAutoCommit(true);// transaction
		} 
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Baglanti hatasi");
		}return 0;
			}
	
	//kaydolmaya calisan kisinin girdigi email database'de mevcut mu:
	public void mailTekrari(String email) {
		try(Connection connection=getInterfaceConnection()) {
			connection.setAutoCommit(false); //transaction
			String sql = "select * from register where register_email=?";
			PreparedStatement pr=connection.prepareStatement(sql);
			pr.setString(1, email);
			ResultSet rs=pr.executeQuery();
			while(rs.next()) {
				System.out.println("\nLutfen sistemde kayitli olmayan bir mail adresi giriniz:");
				signUp();
				connection.commit(); // transaction
				}
			    connection.rollback(); // transaction
				connection.setAutoCommit(true);// transaction
				}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Baðlantý hatasý");
		}
	}

	//Sisteme ilk defa kayit olunacak:
	public void signUp() {
				RegisterController rg=new RegisterController();
				RegisterDao dao=new RegisterDao();
				Scanner sc = new Scanner(System.in);
				System.out.println("Adiniz:");
				String name=sc.nextLine();
				System.out.println("Soyadiniz:");
				String surname=sc.nextLine();
				System.out.println("Mail adresinizi giriniz:");
				String email=sc.nextLine();
				mailTekrari(email);
			    System.out.println("Minimum 8 haneli sifrenizi giriniz:");
				String password=sc.nextLine();
				sc.close();
				int count=0;
				for(int i = 0; i < password.length(); i++) {    
		            if(password.charAt(i) != ' ')    
		                count++;    
		        }
				if (count<8) {
					System.out.println("\nSifrenizde en az 8 karakter olmali.\nYeni bir sifre giriniz:");
					password=sc.nextLine();
				} 
				try(Connection connection=getInterfaceConnection()) {
					connection.setAutoCommit(false); //transaction
					java.sql.PreparedStatement preparedStatement = connection.prepareStatement("Insert into register (register_id, register_name, register_surname, register_email, register_password, account_balance, roles,aktiflik) values (?, ?, ?, ?, ?, 0, User,Aktif)");
					int id=dao.idSayisi();
					id+=id;
					preparedStatement.setInt(1, id);
					preparedStatement.setString(2, name);
					preparedStatement.setString(3, surname);
					preparedStatement.setString(4, email);
					preparedStatement.setString(5, password);
					preparedStatement.executeUpdate();
					int rowEffected= preparedStatement.executeUpdate();
					if(rowEffected>0) {
						log.info("\nKaydolma isleminiz basarýyla tamamlandi. Hesabiniza giris yapabilirsiniz.");
						rg.login();
						connection.commit(); // transaction
					}
					log.warning("\nEksik bilgi girdiniz.\n Ana menuye yonlendiriliyorsunuz.");
					rg.loginOrSignUp();
					connection.rollback(); // transaction
					connection.setAutoCommit(true);// transaction
					} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
	//Database'e kac kisi kayitli?
	public int idSayisi() {
				try(Connection connection=getInterfaceConnection()) {
					connection.setAutoCommit(false); //transaction
					Statement stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery("select * from register");
					 if(rs.last()) {
					 rs.last();
					 int id=rs.getRow();
					 connection.commit(); // transaction
					 return id;}
					 else {
						 connection.rollback(); // transaction
						 connection.setAutoCommit(true);// transaction
					 }
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
	}
	
			
	//Bize verilen mail ve sifre ile kullanicinin varligini sorguluyoruz.
	public boolean searchUser(String RegisterEmail,String RegisterPassword) {
		try(Connection connection=getInterfaceConnection()) {
			connection.setAutoCommit(false); //transaction
			String sql = "select * from register where register_email=? and register_password=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, RegisterEmail);
			preparedStatement.setString(2, RegisterPassword);
			ResultSet resultSet=preparedStatement.executeQuery();
			if (resultSet.next()) {
				String name=resultSet.getString("register_name");
			    String surname=resultSet.getString("register_surname");
				System.out.println("\nHos geldiniz"+name +surname);
				connection.commit(); // transaction
				return true;
			}
			else {
				 System.out.println("\nEmail veya sifreniz hatalý.");
				 connection.rollback(); // transaction
			     connection.setAutoCommit(true);// transaction
				return false;
			 }
			
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Baglantý hatasi!");
			
		}
		return false;
		
	}
	
	//Kullanicinin mail ve sifresi kullanilarak db'den id'sini aliyoruz.
	public int idSearch(String RegisterEmail,String RegisterPassword) {
		try(Connection connection=getInterfaceConnection()) {
			connection.setAutoCommit(false); //transaction
			String sql = "select * from register where register_email=? and register_password=?";
			PreparedStatement pr=connection.prepareStatement(sql);
			pr.setString(1, RegisterEmail);
			pr.setString(2, RegisterPassword);
			ResultSet rs=pr.executeQuery();
			while (rs.next()) {
				int id= rs.getInt("register_id"); 
				connection.commit(); // transaction
				return id;
			}
			connection.rollback(); // transaction
		    connection.setAutoCommit(true);// transaction
		} 
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Baðlantý hatasý");
		}
		return 0;
	}
	
	//Para yollamak istenilen kisinin hesap bakiyesini aliyoruz.
	public int aliciBul(String RegisterEmail){
		try(Connection connection=getInterfaceConnection()) {
			connection.setAutoCommit(false); //transaction
			String sql = "select * from register where register_email=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, RegisterEmail);
			ResultSet rs=preparedStatement.executeQuery();
			if (rs.next()) {
				int acc= rs.getInt("account_balance"); 
				connection.commit(); // transaction
				return acc;}
			else {
				System.out.println("Boyle bir kullanici yok.");
				connection.rollback(); // transaction
			    connection.setAutoCommit(true);// transaction
			} }
			catch (Exception e) {
			e.printStackTrace();
			System.out.println("Baglanti hatasi");
		}
		return 0;	
	}
	
	//Havale ile para yollanan kullanicinin hesap durumu guncelleniyor.
	public void aliciupdate(int acc, String mail){
		try(Connection con=getInterfaceConnection()) {
			con.setAutoCommit(false); //transaction
			PreparedStatement pr = con.prepareStatement("select * from register where register_email=?");
			pr.setString(1, mail);
			ResultSet rs = pr.executeQuery();
			while (rs.next()) {
			PreparedStatement pre = con.prepareStatement("update register set account_balance=? where register_email=?  values(?,?)");	
			pre.setInt(1, acc);
			pre.setString(2, mail);
			pre.executeUpdate();
			int rowEffected= pre.executeUpdate();
			if(rowEffected>0) {
				log.info("Havale islemi basariyla tamamlandi.");
				con.commit(); // transaction
			} 
			else 
			{
				log.warning("Havale islemi gerceklestirilemedi.");	
			con.rollback(); // transaction
			con.setAutoCommit(true);// transaction
			}}}
		 catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Baglanti hatasi");
		}	
	
}
	
	//mail gonderme islemleri db'deki userpanel tablosuna kaydediliyor.
	public void mailKayit(int id, String kime) {
		try (Connection connection =getInterfaceConnection()) {
			connection.setAutoCommit(false); //transaction
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement("insert into userpanel(user_id,kime,operation_type) values(?,?,?)");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, kime);
			preparedStatement.setString(3, "mail gonderildi");
			preparedStatement.executeUpdate();
			int rowEffected= preparedStatement.executeUpdate();
			if(rowEffected>0) { //Islem kaydi basarili ise:
				connection.commit(); } // transaction} 
			else {
				//Islem kaydi basarisiz
			connection.rollback(); // transaction
			connection.setAutoCommit(true);// transaction
			}	
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}}
	
   //gerceklestirilen havale islemlerinin kaydi userpanel tablosuna kaydediliyor. 
public void havaleKayit(int id, String kime,int miktar) {
		
		try (Connection connection =getInterfaceConnection()) {
			connection.setAutoCommit(false); //transaction
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement("insert into userpanel(user_id,kime,miktar,operation_type) values(?,?,?,?)");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, kime);
			preparedStatement.setInt(3, miktar);
			preparedStatement.setString(4, "Belirtilen adrese havale yapildi.");
			preparedStatement.executeUpdate();
			int rowEffected= preparedStatement.executeUpdate();
			if(rowEffected>0) {
				connection.commit(); // transaction
			} 
			else 
			{connection.rollback(); // transaction
			connection.setAutoCommit(true);// transaction 
			}} 
		catch (SQLException e) {
			e.printStackTrace();
		}}

    //para cekme islemlerinin kayitlari
public void cekKayit(int id, int miktar) {
	
	try (Connection connection =getInterfaceConnection()) {
		connection.setAutoCommit(false); //transaction
		java.sql.PreparedStatement preparedStatement = connection.prepareStatement("insert into userpanel(user_id,miktar,operation_type) values(?,?,?)");
		preparedStatement.setInt(1, id);
		preparedStatement.setInt(2, miktar);
		preparedStatement.setString(3, "Para cekildi.");
		preparedStatement.executeUpdate();
		int rowEffected= preparedStatement.executeUpdate();
		if(rowEffected>0) {
			//islem tamamlandi
			connection.commit(); // transaction
		} 
		else {
		connection.rollback(); // transaction
		connection.setAutoCommit(true);// transaction
		}	
	} 
	catch (SQLException e) {
		e.printStackTrace();
	}}

    //para yatirma isleminin kaydedilmesi:
public void yatirKayit(int id, int miktar) {
	try (Connection connection =getInterfaceConnection()) {
		connection.setAutoCommit(false); //transaction
		java.sql.PreparedStatement preparedStatement = connection.prepareStatement("insert into userpanel(user_id,miktar,operation_type) values(?,?,?)");
		preparedStatement.setInt(1, id);
		preparedStatement.setInt(2, miktar);
		preparedStatement.setString(3, "Para yatirildi.");
		preparedStatement.executeUpdate();
		int rowEffected= preparedStatement.executeUpdate();
		if(rowEffected>0) {
			connection.commit(); // transaction
		} 
		else {
		connection.rollback(); // transaction
		connection.setAutoCommit(true);// transaction
		}	
	} 
	catch (SQLException e) {
		e.printStackTrace();
	}}

	public Connection getInterfaceConnection() {
		return getInterfaceConnection();
	}
//id'si bilinen kullanicinin hesap bakiyesini aratiyoruz:
	public int bakiyeBul(int id) {
		try(Connection connection=getInterfaceConnection()) {
			connection.setAutoCommit(false); //transaction
			String sql = "select * from register where register_id=?";
			PreparedStatement pr=connection.prepareStatement(sql);
			pr.setInt(1, id);
			ResultSet rs=pr.executeQuery();
			if (rs.next()) {
				int bakiye=rs.getInt("account_balance");
			    connection.commit(); // transaction
				return bakiye;
			}
			else {
				connection.rollback(); // transaction
			     connection.setAutoCommit(true);// transaction
				return 0;
			 }
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Baglantý hatasi!");
		}
		return 0;
	}
	
//para cek,yatir veya havale islemi yapan kullanicinin hesap durumunu guncelliyoruz.
	public void bakiyeGuncelle(int id,int acc1) {
		try(Connection con=getInterfaceConnection()) {
			con.setAutoCommit(false); //transaction
			PreparedStatement pr = con.prepareStatement("select * from register where register_id=?");
			pr.setInt(1, id);
			ResultSet rs = pr.executeQuery();
			con.commit(); // transaction
			while (rs.next()) {
			PreparedStatement pre = con.prepareStatement("update register set account_balance=? where register_id=?  values(?,?)");	
			pre.setInt(1, acc1);
			pre.setInt(2, id);
			pre.executeUpdate();
			int rowEffected= pre.executeUpdate();
			if(rowEffected>0) {
				con.commit(); // transaction
			} 
			else 
			{
		    con.rollback(); // transaction
			con.setAutoCommit(true);// transaction
			}}}
		 catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Baglanti hatasi");
		}
	}

	public String aktif(String email) {
		try(Connection con=getInterfaceConnection()) {
			con.setAutoCommit(false); //transaction
			PreparedStatement pr = con.prepareStatement("select * from register where register_email=?");
			pr.setString(1, email);
			ResultSet rs = pr.executeQuery();
			con.commit(); // transaction
			while (rs.next()) {
			String aktiflik= rs.getString("aktiflik");
			con.commit(); // transaction
			return aktiflik;
			}
			con.rollback(); // transaction
			con.setAutoCommit(true);// transaction
			}
		 catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	}
