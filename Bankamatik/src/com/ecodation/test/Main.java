package com.ecodation.test;
import com.ecodation.controller.RegisterController;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

public class Main {
		public static void main(String[] args) {
			RegisterController cont=new RegisterController();
			cont.loginOrSignUp();}
		}
