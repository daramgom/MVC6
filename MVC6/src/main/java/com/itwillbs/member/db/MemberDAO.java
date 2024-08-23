package com.itwillbs.member.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.protocol.Resultset;

/**
 * MemberDAO : 회원정보(itwill_member 테이블)관한 모든 디비동작을 처리하는 객체 DAO(Date Access
 * Object) 각 동작별 메서드를 생성(DB처리 1개당 하나의 메서드)
 */

public class MemberDAO {

	// 공통변수 선언
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";

	// **********DB 연결 메서드 - getConnect()**********
	private Connection getConnect() throws Exception {
		// DB 연결정보
		final String DRIVER = "com.mysql.cj.jdbc.Driver";
		final String DBURL = "jdbc:mysql://localhost:3306/jspdb";
		final String DBID = "root";
		final String DBPW = "1234";

		// 1. 드라이버 로드
		Class.forName(DRIVER);
		System.out.println(" DAO : 드라이버 로드 성공! ");
		// 2. DB 연결
		con = DriverManager.getConnection(DBURL, DBID, DBPW);
		System.out.println(" DAO : DB 연결 성공! ");
		System.out.println(" DAO : con - " + con);

		return con;
	}// **********DB 연결 메서드 - getConnect()**********

	// **********DB 자원해제 메서드 - closeDB()**********
	public void closeDB() {
		System.out.println(" DAO : 자원해제 코드 - 시작 ");

		try {
			// 코드 사용 역순 해제
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(" DAO : 자원해제 코드 - 끝 ");
	}// **********DB 자원해제 메서드 - closeDB()**********

	public MemberDAO() {
		System.out.println(" DAO : MemberDAO객체 생성 - DB처리 가능 ");
	}

	// 회원가입 메서드
	public void insertMember(MemberDTO dto) throws Exception {
		// DB 연결정보
//		final String DRIVER = "com.mysql.cj.jdbc.Driver";
//		final String DBURL = "jdbc:mysql://localhost:3306/jspdb";
//		final String DBID = "root";
//		final String DBPW = "1234";

		// 1. 드라이버 로드
//		Class.forName(DRIVER);
//		System.out.println(" DAO : 드라이버 로드 성공! ");

		// 2. DB연결
//		Connection con = DriverManager.getConnection(DBURL, DBID, DBPW);
//		System.out.println(" DAO : DB연결 성공! ");
//		System.out.println(" DAO : "+con);
		con = getConnect();

		// 3. SQL구문(insert) & pstmt 객체
		String sql = "insert into itwill_member (id,pw,name,email,gender,age,regdate) values(?,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);

		// ? ? ? 정보 채우기
		pstmt.setString(1, dto.getId());
		pstmt.setString(2, dto.getPw());
		pstmt.setString(3, dto.getName());
		pstmt.setString(4, dto.getEmail());
		pstmt.setString(5, dto.getGender());
		pstmt.setInt(6, dto.getAge());
		pstmt.setTimestamp(7, dto.getRegdate());

		// 4. SQL 실행
		pstmt.executeUpdate();
		System.out.println(" DAO : 회원가입 완료! ");
	}// insertMember

	// 로그인 여부 체크 - loginCheck()
	public int loginCheck(MemberDTO dto) {
		int result = -1; // 아이디 X : -1 / 아이디 O,비밀번호 X : 0 / 아이디 O,비밀번호 O : 1

		try {
			// 1. 드라이버 로드
			// 2. DB 연결
			// => getConnect() 메서드 처리
			con = getConnect();

			// 3. SQL 구문(select) & pstmt 객체
			sql = "select pw from itwill_member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getId());

			// 4. SQL 실행 & rs 저장
			rs = pstmt.executeQuery();

			// 5. 데이터 처리 - 로그인 여부 체크
			if (rs.next()) {
				// 아이디에 해당하는 비밀번호가 있다
				if (dto.getPw().equals(rs.getString("pw"))) {
					// 아이디가 같으면서(존재) 비밀번호 같음
					result = 1;
				} else {
					// 아이디가 같으면서(존재) 비밀번호 다름
					result = 0;
				}
			} else {
				// 아이디에 해당하는 비밀번호가 없다
				// => 회원정보 없음
				result = -1;
			}

			System.out.println(" DAO : SQL 구문 실행완료 ");
			System.out.println(" DAO : 로그인 체크 완료 (" + result + ")");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println(" DAO : 자원해제 코드 - 시작 ");

			try {
				// 코드 사용 역순 해제
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			System.out.println(" DAO : 자원해제 코드 - 끝 ");
		}

		return result;
	}// 로그인 여부 체크 - loginCheck()

	// 회원정보 조회 - getMember(id)
	public MemberDTO getMember(String id) {
		// 회원정보 저장 객체
		MemberDTO dto = null;
		// MemberDTO dto = new MemberDTO();
		try {
			// 1. 드라이버 로드
			// 2. DB 연결
			// => getConnect() 메서드 처리
			con = getConnect();
			// 3. SQL 구문(select) & pstmt 객체
			sql = "select * from itwill_member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// 4. SQL 실행 & rs 저장
			rs = pstmt.executeQuery();
			// 5. 데이터 처리 - 조회한 정보를 MemberDTO에 저장
			if(rs.next()) {
				// 데이터가 있음 rs -> DTO 저장
				dto = new MemberDTO();
				dto.setAge(rs.getInt("age"));
				dto.setEmail(rs.getString("email"));
				dto.setGender(rs.getString("gender"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPw(rs.getString("pw"));
				dto.setRegdate(rs.getTimestamp("regdate"));
			}
			System.out.println(" DAO : 회원정보 저장완료 ");
			System.out.println(" DAO : "+dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	
		return dto;
	}// 회원정보 조회 - getMember(id)
	
	// 회원정보 수정 - updateMember(dto)
	public int updateMember(MemberDTO dto) {
		int result = -1; // -1 : 아이디X , 0 : 아이디O, 비밀번호X , 1 : 정상처리 
		
		try {
			// 1. 드라이버 로드
			// 2. DB 연결
			con = getConnect();
			// 3. SQL 구문(select-본인확인) & pstmt 객체
			sql = "select pw from itwill_member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			if(rs.next()) {
				// 회원정보 있음 (아이디에 해당하는 비밀번호 있음)
				if(dto.getPw().equals(rs.getString("pw"))) {
					// 아이디O, 비밀번호O => 본인
					// 3. SQL 구문(update) & pstmt 객체
					sql = "update itwill_member set name=?,gender=?,age=? where id=?";
					pstmt = con.prepareStatement(sql);
					
					// ???
					pstmt.setString(1, dto.getName());
					pstmt.setString(2, dto.getGender());
					pstmt.setInt(3, dto.getAge());
					pstmt.setString(4, dto.getId());
					
					// 4. SQL 실행
					// pstmt.executeUpdate();
					result = pstmt.executeUpdate(); // 아이디는 하나이기에 변하는 행의 수는 1개
					// result = 1;
				} else {
					// 아이디O, 비밀번호X
					result = 0;
				}
				
			} else {
				// 회원정보 없음 (아이디에 해당하는 비밀번호 없음)
				result = -1;
			}
			System.out.println(" DAO : 회원정보 수정완료("+result+")");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return result;
	}
	
	// 회원정보 수정 - updateMember(dto)
	
	// 회원정보 삭제 - deleteMember(dto)
	public int deleteMember(MemberDTO dto) {
		int result = -1; // -1 : 아이디X , 0 : 아이디O, 비밀번호X , 1 : 정상처리 
		
		try {
			// 1. 드라이버 로드
			// 2. DB 연결
			con = getConnect();
			// 3. SQL 구문(select-본인확인) & pstmt 객체
			sql = "select pw from itwill_member where id=?";
			pstmt = con.prepareStatement(sql);
			// ???
			pstmt.setString(1, dto.getId());
			
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			if(rs.next()) {
				// 회원정보 있음 (아이디에 해당하는 비밀번호 있음)
				if(dto.getPw().equals(rs.getString("pw"))) {
					// 아이디O, 비밀번호O => 본인
					// 회원정보 삭제
					// 3. SQL 구문(update) & pstmt 객체
					sql = "delete from itwill_member where id=?";
					pstmt = con.prepareStatement(sql);
					
					// ???
					pstmt.setString(1, dto.getId());
					
					// 4. SQL 구문 실행
					// pstmt.executeUpdate();
					result = pstmt.executeUpdate(); // 아이디는 하나이기에 변하는 행의 수는 1개
					// result = 1;
				} else {
					// 아이디O, 비밀번호X
					result = 0;
				}
				
			} else {
				// 회원정보 없음 (아이디에 해당하는 비밀번호 없음)
				result = -1;
			}
			System.out.println(" DAO : 회원정보 삭제완료("+result+")");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return result;
	}
	// 회원정보 삭제 - deleteMember(dto)
	
	// 회원목록 리스트 - getMemberList()
	public ArrayList<MemberDTO> getMemberList() {
		
		// 회원 목록정보 저장하는 가변길이 배열 생성
		ArrayList<MemberDTO> memberList = new ArrayList<MemberDTO>();
		
		try {
			// 1. 드라이버 로드
			// 2. DB 연결
			getConnect();
			
			// 3. SQL 구문 작성 & pstmt 객체
			String sql = "select * from itwill_member where id != ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "admin");
			
			// 4. SQL 구문 실행
			rs = pstmt.executeQuery();
			
			// 5. 데이터 처리
			while(rs.next()) {
				// 데이터 있을 때마다 처리 -> while
				// rs -> DTO -> List
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setGender(rs.getString("gender"));
				dto.setAge(rs.getInt("age"));
				dto.setRegdate(rs.getTimestamp("regdate"));
				
				// DTO -> List 한칸
				memberList.add(dto);
				
			}// while
			System.out.println(" DAO : 회원 목록 조회성공 ");
			System.out.println(" DAO : "+memberList.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return memberList;
	}
	
	// 회원목록 리스트 - getMemberList()
	
	
}// class MemberDAO
