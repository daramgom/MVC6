package com.itwillbs.member.db;

import java.sql.Timestamp;

/**
 * MemberDTO : 회원정보를 저장객체
 * DTO(Data Transfer Object)
 * 	XXXXXAction 객체 <-> DB사이에서 정보를 이동시키는 객체
 * 		=> 회원정보 테이블정보를 모두 저장 가능한 객체
 * 테이블의 컬럼명과 객체의 변수명이 같음
 */	
public class MemberDTO {
	private String id;
	private String pw;
	private String name;
	private String email;
	private String gender;
	private int age;
	private Timestamp regdate;
	
	// alt shift s + r
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	
	// alt shift s + s
	@Override
	public String toString() {
		return "MemberDTO [id=" + id + ", pw=" + pw + ", name=" + name + ", email=" + email + ", gender=" + gender
				+ ", age=" + age + ", regdate=" + regdate + "]";
	}
	
	
	
}
