package com.ecodation.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data        //getter setterlarla sayfayi doldurmamak icin guzel bir yontem
@AllArgsConstructor
@NoArgsConstructor

public class RegisterDto implements Serializable{

	private static final long serialVersionUID = -8106785507897528762L;
	private int RegisterId;
	private String RegisterName;
	private String RegisterSurname;
	private String RegisterEmail;
	private String RegisterPassword;
	private int AccountBalance;
	private Date Date;
	private String RegisterRoles;
	private String Aktiflik;
	private int UserId;
	private String OperationType;
	private String Kime;
	private String Miktar;
	
	
}
