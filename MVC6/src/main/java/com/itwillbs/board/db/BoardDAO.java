package com.itwillbs.board.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * 	DB-itwill_board 테이블에 처리되는 모든 동작 처리하는 객체
 */
public class BoardDAO {
	
	// 공통사용 변수 선언
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";
	
	
	// DB 연결 메서드-------------------
	private Connection getConnect() throws Exception {
		// DB 연결정보 - META-INF/context.xml 파일 
		Context initCTX = new InitialContext(); // javax.naming
		DataSource ds = (DataSource) initCTX.lookup("java:comp/env/jdbc/MVC"); // javax.sql
		
		// 1. 드라이버 로드 + 2. DB 연결
		con = ds.getConnection();
		System.out.println(" DAO : DB 연결 성공 (CP) ");
		System.out.println(" DAO : "+con);
		return con;
	}
	// DB 연결 메서드-------------------

	
	// DB 자원해제 메서드-------------------
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
	}
	// DB 자원해제 메서드-------------------
	
	
	// 게시판 글쓰기 - insertBoard(dto)
	public void insertBoard(BoardDTO dto) {
		int bno = 0;
		try {
			// 1+2. DB 연결(CP)
			con = getConnect();
			// 3. SQL 구문 & pstmt 객체
			//    bno계산
			sql= "select max(bno) from itwill_board";
			pstmt = con.prepareStatement(sql);
			// 4. SQL 구문 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리
			if(rs.next()) {
				// bno = rs.getInt("max(bno)") + 1;
				bno = rs.getInt(1) + 1; // sql null 값이 존재하여 0을 반환 + 1
			} 
//			else {
//				bno = 1;
//				System.out.println(" else ");
//			}
			System.out.println(" DAO : bno : "+bno);
			
			// 글쓰기 시작
			sql = "insert into itwill_board (bno,name,pass,subject,content,readcount,re_ref,re_lev,re_seq,date,ip,file) "
					+ "values(?,?,?,?,?,?,?,?,?,now(),?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getPass());
			pstmt.setString(4, dto.getSubject());
			pstmt.setString(5, dto.getContent());
			pstmt.setInt(6, 0); // 모든 글 조회수는 0 초기화
			pstmt.setInt(7, bno); // re_ref는 그룹번호 == bno
			pstmt.setInt(8, 0); // re_lev 0 초기화
			pstmt.setInt(9, 0); // re_seq 0 초기화
			pstmt.setString(10, dto.getIp());
			pstmt.setString(11, dto.getFile());
			
			// SQL 구문 실행
			pstmt.executeUpdate();
			
			System.out.println(" DAO : 글쓰기 완료 ");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
	}
	// 게시판 글쓰기 - insertBoard(dto)
	
	
	// 게시판 저장된 전체 글 개수 조회 - getBoardCount()
	public int getBoardCount() {
		int count = 0;
		
		try {
			// 1+2. DB연결(CP)
			con = getConnect();
			// 3. SQL 구문(select) & pstmt 객체
			sql = "select count(bno) from itwill_board";
			pstmt = con.prepareStatement(sql);
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리
			if(rs.next()) {
				count = rs.getInt(1);
				// count = rs.getInt("count(bno)");
			}
			
			System.out.println(" DAO : 전체 글 개수 : "+count+"개");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return count;
	}
	// 게시판 저장된 전체 글 개수 조회 - getBoardCount()
	
	
	// 게시판 저장된 전체 글 개수 조회 - getBoardCount(search)
	public int getBoardCount(String search) {
		int count = 0;
		
		try {
			// 1+2. DB연결(CP)
			con = getConnect();
			// 3. SQL 구문(select) & pstmt 객체
			sql = "select count(bno) from itwill_board where subject like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%"); // SQL 구문에 ?와 함께 사용못하여 따로 적음
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리
			if(rs.next()) {
				count = rs.getInt(1);
				// count = rs.getInt("count(bno)");
			}
			
			System.out.println(" DAO : 전체 글 개수 : "+count+"개");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return count;
	}
	// 게시판 저장된 전체 글 개수 조회 - getBoardCount(search)
	
	
	// 게시판 전체 리스트 조회 - getBoardList()
	public List<BoardDTO> getBoardList() { // java.util
		
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		try {
			// 1+2. DB연결
			con = getConnect();
			// 3. SQL 구문 & pstmt 객체
			// sql = "select * from itwill_board";
			// sql = "select * from itwill_board order by bno desc limit 0,10";
			sql = "select * from itwill_board order by bno desc limit 10,10";
			pstmt = con.prepareStatement(sql);
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리 rs -> DTO -> List
			while(rs.next()) {
				
				// 1) rs -> DTO
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setDate(rs.getDate("date"));
				dto.setIp(rs.getString("ip"));
				dto.setFile(rs.getString("file"));
				
				// 2) DTO -> List
				boardList.add(dto);
				
			}// while
			
			System.out.println(" DAO : 게시판 전체 글 조회 성공 ");
			System.out.println(" DAO : "+boardList.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return boardList;
	}
	// 게시판 전체 리스트 조회 - getBoardList()
	
	
	// 게시판 전체 리스트 조회 - getBoardList(startRow, pageSize)
	public List<BoardDTO> getBoardList(int startRow, int pageSize) { // java.util
		
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		try {
			// 1+2. DB연결
			con = getConnect();
			// 3. SQL 구문 & pstmt 객체
			// sql = "select * from itwill_board";
			// sql = "select * from itwill_board order by bno desc limit 0,10";
			sql = "select * from itwill_board order by re_ref desc,re_seq asc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow-1); // 시작행 -1
			pstmt.setInt(2, pageSize); // 페이지 사이즈
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리 rs -> DTO -> List
			while(rs.next()) {
				
				// 1) rs -> DTO
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setDate(rs.getDate("date"));
				dto.setIp(rs.getString("ip"));
				dto.setFile(rs.getString("file"));
				
				// 2) DTO -> List
				boardList.add(dto);
				
			}// while
			
			System.out.println(" DAO : 게시판 전체 글 조회 성공 ");
			System.out.println(" DAO : "+boardList.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return boardList;
	}
	// 게시판 전체 리스트 조회 - getBoardList(startRow, pageSize)
	
	
	// 게시판 전체 리스트 조회 - getBoardList(startRow, pageSize, search)
	public List<BoardDTO> getBoardList(int startRow, int pageSize, String search) { // java.util
		
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		try {
			// 1+2. DB연결
			con = getConnect();
			// 3. SQL 구문 & pstmt 객체
			// sql = "select * from itwill_board";
			// sql = "select * from itwill_board order by bno desc limit 0,10";
			// sql = "select * from itwill_board order by re_ref desc,re_seq asc limit ?,?";
			sql = "select * from itwill_board where subject like ? order by re_ref desc,re_seq asc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow-1); // 시작행 -1
			pstmt.setInt(3, pageSize); // 페이지 사이즈
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리 rs -> DTO -> List
			while(rs.next()) {
				
				// 1) rs -> DTO
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setDate(rs.getDate("date"));
				dto.setIp(rs.getString("ip"));
				dto.setFile(rs.getString("file"));
				
				// 2) DTO -> List
				boardList.add(dto);
				
			}// while
			
			System.out.println(" DAO : 게시판 전체 글 조회 성공 ");
			System.out.println(" DAO : "+boardList.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return boardList;
	}
	// 게시판 전체 리스트 조회 - getBoardList(startRow, pageSize, search)

		
	// 조회수 1증가 - updateReadcount(bno)
	public void updatereadcount(int bno) {
		try {
			// 1+2. DB연결(CP)
			con = getConnect();
			// 3. SQL 구문(update), pstmt 삭제
			sql = "update itwill_board set readcount = readcount + 1 where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			// 4. SQL 실행
			pstmt.executeUpdate();
			System.out.println(" DAO : 글 조회수 1증가 완료 ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
	}
	// 조회수 1증가 - updateReadcount(bno)
	
	
	// 특정 글정보 조회 - getBoard(bno)
	public BoardDTO getBoard(int bno) { // java.util
		
		BoardDTO dto = null;
		
		try {
			// 1+2. DB연결
			con = getConnect();
			// 3. SQL 구문 & pstmt 객체
			sql = "select * from itwill_board where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리 rs -> DTO
			if(rs.next()) {
				
				// rs -> DTO
				dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setDate(rs.getDate("date"));
				dto.setIp(rs.getString("ip"));
				dto.setFile(rs.getString("file"));
				
			}// if
			
			System.out.println(" DAO : "+bno+"번 글정보 저장완료 ");
			System.out.println(" DAO : "+dto);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return dto;
	}
	// 특정 글정보 조회 - getBoard(bno)
	
	// 이전글 제목 조회 - prevSubject(bno)
	public String prevSubject(int bno) {
		String result = "";
		// String result = null;
		try {
			// 1+2. DB연결 (CP)
			con = getConnect();
			// 3. SQL 구문(select) & pstmt
			sql = "select subject from itwill_board where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno-1);
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리
			if(rs.next()) {
				// result = rs.getString("subject");
				result = rs.getString(1); // 성능면에서 좋음
			}
			System.out.println(" DAO : 이전글 제목 조회 성공 ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return result;
	}
	// 이전글 제목 조회 - prevSubject()
	
	
	// 다음글 제목 조회 - nextSubject()
	public String nextSubject(int bno) {
		String result = "";
		// String result = null;
		try {
			// 1+2. DB연결 (CP)
			con = getConnect();
			// 3. SQL 구문(select) & pstmt
			sql = "select subject from itwill_board where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno+1);
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리
			if(rs.next()) {
				// result = rs.getString("subject");
				result = rs.getString(1); // 성능면에서 좋음
			}
			System.out.println(" DAO : 다음글 제목 조회 성공 ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return result;
	}
	// 다음글 제목 조회 - nextSubject()
	
	
	// 게시판 글 정보 수정 - updateBoard(dto)
	public int updateBoard(BoardDTO dto) {
		int result = -1; // -1,0,1
		
		try {
			// 1+2. DB연결
			con = getConnect();
			// 3. SQL 구문(select : 게시판 글 있는지 체크) & pstmt 객체
			sql = "select pass from itwill_board where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBno());
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터처리
			if(rs.next()) {
				// 글 정보 있음 (bno 해당하는 비밀번호 있음)
				if(dto.getPass().equals(rs.getString("pass"))) {
					// 글정보 있음(bno O), 비밀번호 O => 정보 수정
					// 3. SQL 구문(update) & pstmt 객체
					sql = "update itwill_board set name=?,subject=?,content=? where bno=?";
					pstmt = con.prepareStatement(sql);
					
					// ???
					pstmt.setString(1, dto.getName());
					pstmt.setString(2, dto.getSubject());
					pstmt.setString(3, dto.getContent());
					pstmt.setInt(4, dto.getBno());
					
					// 4. SQL 실행
					// pstmt.executeUpdate();
					result = pstmt.executeUpdate(); // bno(PK) 하나라서 변하는 행의 수 '1' 반환
					// result = 1;
				} else {
					// 글정보는 있으나 (bno O), 비밀번호 X
					result = 0;
				}
			} else {
				// 글정보 없음 (bno 해당하는 비밀번호 없음)
				result = -1;
			}
			System.out.println(" DAO : 글 수정 완료 ("+result+") ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return result;
	}
	// 게시판 글 정보 수정 - updateBoard(dto)

	
	// 게시판 글 삭제 - deleteBoard(dto)
	public int deleteBoard(BoardDTO dto) {
		int result = -1; // -1,0,1
		
		try {
			// 1+2. DB연결
			con = getConnect();
			// 3. SQL 구문(select : 게시판 글 있는지 체크) & pstmt 객체
			sql = "select pass from itwill_board where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBno());
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터처리
			if(rs.next()) {
				// 글 정보 있음 (bno 해당하는 비밀번호 있음)
				if(dto.getPass().equals(rs.getString("pass"))) {
					// 글정보 있음(bno O), 비밀번호 O => 글 삭제
					// 3. SQL 구문(update) & pstmt 객체
					sql = "delete from itwill_board where bno=?";
					pstmt = con.prepareStatement(sql);
					
					// ???
					pstmt.setInt(1, dto.getBno());
					
					// 4. SQL 실행
					// pstmt.executeUpdate();
					result = pstmt.executeUpdate(); // bno(PK) 하나라서 변하는 행의 수 '1' 반환
					// result = 1;
				} else {
					// 글정보는 있으나 (bno O), 비밀번호 X
					result = 0;
				}
			} else {
				// 글정보 없음 (bno 해당하는 비밀번호 없음)
				result = -1;
			}
			System.out.println(" DAO : 글 삭제 완료 ("+result+") ");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		return result;
	}
	// 게시판 글 삭제 - deleteBoard(dto)
	
	
	// 답글쓰기 메서드 - reInsertBoard(dto)
	public void reInsertBoard(BoardDTO dto) {
		int bno = 0; // 답글 번호
		try {
			// 1+2. DB연결(CP)
			con=getConnect();
			
			// ***** 답글 번호 계산 ***** //
			// 3. SQL 구문 & pstmt 객체
			sql = "select max(bno) from itwill_board";
			pstmt = con.prepareStatement(sql);
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리
			if(rs.next()) {
				// bno = rs.getInt("max(bno)")+1;
				bno = rs.getInt(1)+1;
			}
			System.out.println(" DAO : 답글번호 "+bno);
			// ***** 답글 번호 계산 ***** //
			
			
			// ***** 답글 seq 변경 ***** //
			// 답글 순서를 재배치
			// 3. SQL 구문 & pstmt 객체
			// 부모글과 ref 값이 같으면서, re_seq값이 더 큰 값만 1씩 증가
			sql = "update itwill_board set re_seq = re_seq + 1 where re_ref = ? and re_seq > ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getRe_ref()); // 같은 그룹번호
			pstmt.setInt(2, dto.getRe_seq()); // 부모의 그룹순서
			// 4. SQL 실행
			int check = pstmt.executeUpdate();
			
			if(check>0) {
				System.out.println(" DAO : 답글 순서 재배치 완료 "+check);
			}
			// ***** 답글 seq 변경 ***** //
			
			
			// 답글 내용 저장 //
			// 3. SQL 구문 & pstmt 객체
			sql = "insert into itwill_board (bno,name,pass,subject,content,readcount,re_ref,re_lev,re_seq,date,ip,file) "
					+ "values(?,?,?,?,?,?,?,?,?,now(),?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getPass());
			pstmt.setString(4, dto.getSubject());
			pstmt.setString(5, dto.getContent());
			
			pstmt.setInt(6, 0); // 모든 글 조회수는 0 초기화
			pstmt.setInt(7, dto.getRe_ref()); // re_ref는 그룹번호 == 부모글의 re_ref
			pstmt.setInt(8, dto.getRe_lev()+1); // re_lev == 부모글의 re_lev + 1
			pstmt.setInt(9, dto.getRe_seq()+1); // re_seq == 부모글의 re_seq + 1
			
			pstmt.setString(10, dto.getIp());
			pstmt.setString(11, dto.getFile());
			
			// SQL 구문 실행
			pstmt.executeUpdate();
			
			System.out.println(" DAO : 답글쓰기 완료 ");
			
			
			// 답글 내용 저장 //
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		
	}
	// 답글쓰기 메서드 - reInsertBoard(dto)
	
	
	
	
	
	
}// class BoardDAO
