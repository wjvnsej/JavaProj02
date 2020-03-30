package ver05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class Account {
	
	PreparedStatement psmt;
	Connection con;
	Statement stmt;
	ResultSet rs;
	
	//데이터베이스 연결
	public Account() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection
					("jdbc:oracle:thin://@localhost:1521:orcl", 
							"kosmo","1234"
					);
			System.out.println("오라클 DB 연결성공");

			
		}
		catch (ClassNotFoundException e) {
			System.out.println("오라클 드라이버 로딩 실패");
			e.printStackTrace();
		}
		catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println("알수 없는 예외 발생");
		}
	}
	
	public void close() {
		try {
			if(psmt != null) psmt.close();
			if(stmt != null) stmt.close();
			if(con != null) con.close();
			if(rs != null) rs.close();
			System.out.println("오라클 DB 연결종료");
         
		}
		catch(SQLException e) {
			System.out.println("자원 반납 시 오류가 발생하였습니다.");
		}
	}////end of close
	
	
	//메뉴출력
	public void showMenu() {
		System.out.println("-----Menu------");
		System.out.println("1. 계좌개설");
		System.out.println("2. 입금");
		System.out.println("3. 출금");
		System.out.println("4. 계좌검색");
		System.out.println("5. 전체계좌정보출력");
		System.out.println("6. 3by3 퍼즐게임");
		System.out.println("7. 프로그램종료");
		System.out.print("선택 : ");
	}
	
	public void start() {
		
		while(true) {

			showMenu();

			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();
			scan.nextLine();
			
			switch (choice) {
			case MenuChoice.MAKE: 
				makeAccount();
				break;
			case MenuChoice.DEPOSIT:
				depositMoney();
				break;
			case MenuChoice.WITHDRAW:
				withdrawMoney();
				break;
			case MenuChoice.ACCSEARCH:
				accSearch();
				break;
			case MenuChoice.INQUIRE:
				showAccInfo();
				break;
			case MenuChoice.GAME:
				puz_game();
				break;
			case MenuChoice.EXIT:
				System.out.println("프로그램을 종료합니다.");
				close();
				return;
			}
		}
	}
	
	//계좌개설
	public void makeAccount() {	
		try {
			Scanner scan = new Scanner(System.in);	

			String query = "INSERT INTO banking_tb VALUES " 
					+ "(seq_banking.nextval, ?, ?, ?)";

			psmt = con.prepareStatement(query);

			System.out.println("***신규계좌개설***");
			System.out.println("계좌번호 : " );
			String accountNum = scan.nextLine();
			
			System.out.println("이름 : " );
			String name = scan.nextLine();
			
			System.out.println("잔고 : " );
			int balance = scan.nextInt();

			psmt.setString(1, accountNum);
			psmt.setString(2, name);
			psmt.setInt(3, balance);

			int affected = psmt.executeUpdate();
			System.out.println(affected + "행이 입력되었습니다.");
			System.out.println("계좌 개설 완료!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//입금
	public void depositMoney() {
		
		try {
			int inputMoney;
			Scanner scan = new Scanner(System.in);
			System.out.println("***입   금***");
			System.out.println("계좌번호와 입금할 금액을 입력하세요.");
			System.out.println("계좌번호 : ");
			String s_AccNum = scan.nextLine();
			System.out.println("입금액 : ");
			inputMoney = scan.nextInt();
			
			String query = "UPDATE banking_tb "
					+ "	SET balance = (balance + ?) "
					+ "	WHERE accNum = ? ";
			
			psmt = con.prepareStatement(query);
			psmt.setInt(1, inputMoney);
			psmt.setString(2, s_AccNum);
			System.out.println("입금이 완료되었습니다.\n");
			int affected = psmt.executeUpdate();
			System.out.println(affected + "행이 업데이트 되었습니다.");

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//출금
	public void withdrawMoney() {
		
		try {
			int outputMoney;
			Scanner scan = new Scanner(System.in);
			System.out.println("***출   금***");
			System.out.println("계좌번호와 출금할 금액을 입력하세요.");
			System.out.println("계좌번호 : ");
			String s_AccNum = scan.nextLine();
			System.out.println("출금액 : ");
			outputMoney = scan.nextInt();
			
			String query = "UPDATE banking_tb "
					+ "	SET balance = (balance - ?) "
					+ "	WHERE accNum = ? ";
			
			psmt = con.prepareStatement(query);
			psmt.setInt(1, outputMoney);
			psmt.setString(2, s_AccNum);
			System.out.println("출금이 완료되었습니다.\n");
			int affected = psmt.executeUpdate();
			System.out.println(affected + "행이 업데이트 되었습니다.");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 계좌정보 검색
	public void accSearch() {
		try {

			stmt = con.createStatement();
			Scanner scan = new Scanner(System.in);
			System.out.println("***계좌 검색***");
			System.out.println("검색할 계좌번호 입력 : ");
			String inputAccNum = scan.nextLine();

			String query = "SELECT * FROM banking_tb " 
					+ "WHERE accNum LIKE " + "'" + inputAccNum + "'";

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String accNum = rs.getString(2);
				String accName = rs.getString("accName");
				int balance = rs.getInt("balance");

				System.out.println("-----------------");
				System.out.printf("계좌번호 : %s\n이름 : %s\n잔고 : %d\n"
						, accNum, accName, balance);
				System.out.println("-----------------");
				System.out.println("계좌 검색을 완료했습니다.");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//전체계좌정보출력
	public void showAccInfo() {
	
		try {

			stmt = con.createStatement();
			System.out.println("***계좌정보출력***");

			String query = "SELECT * FROM banking_tb";

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String accNum = rs.getString(2);
				String accName = rs.getString("accName");
				int balance = rs.getInt("balance");
				System.out.println("-----------------");
				System.out.printf("계좌번호 : %s\n이름 : %s\n잔고 : %d\n", 
						accNum, accName, balance);
				System.out.println("-----------------");
			}

			System.out.println("전체계좌정보 출력이 완료되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void puz_game() {
		while (true) {
			Puzle puzle = new Puzle(4100);
			puzle.start();

			Scanner scan = new Scanner(System.in);
			System.out.println("재시작하시겠습니까?(y 누르면 재시작, 나머지는 종료.)");
			String reStart = scan.nextLine();
			if(reStart.equals("y")) {
				puzle.start();
			}
			else {
				System.exit(0);
			}
		}
	}
}
interface puzVal {
	String ONE = "1", TWO = "2", THREE = "3", FOUR = "4", FIVE = "5",
			SIX = "6", SEVEN = "7", EIGHT = "8", X = "X"; 
	
	String [][] PUZ_ORIGIN = {
			{ONE, TWO, THREE},
			{FOUR, FIVE, SIX},
			{SEVEN, EIGHT, X}
	};
}

class Puzle implements puzVal {
	int i = 2, j = 2;
	String Change;
	String [][] puz = {
			{ONE, TWO, THREE},
			{FOUR, FIVE, SIX},
			{SEVEN, EIGHT, X}
	};
	
	public Puzle(int num) {
		int shu_num = 0;
		while (shu_num < num) {
			int rnd = (int)(Math.random() * 4)+1;
			switch (rnd) {
			//up
			case 1:
				if(i == 2) {
					down();
					break;
				}
				Change = puz[++i][j];
				puz[--i][j] = Change;
				puz[++i][j] = X;
				break;
			//down
			case 2:
				if(i == 0) {
					up();
					break;
				}
				Change = puz[--i][j];
				puz[++i][j] = Change;
				puz[--i][j] = X;
				break;
			//left
			case 3:
				if(j == 2) {
					right();
					break;
				}
				Change = puz[i][++j];
				puz[i][--j] = Change;
				puz[i][++j] = X;
				break;
			//right
			case 4:
				if(j == 0) {
					left();
					break;
				}
				Change = puz[i][--j];
				puz[i][++j] = Change;
				puz[i][--j] = X;
				break;
			}
			shu_num ++;
		}
	}
	
	public void showpuz() {
		System.out.println("--------");
		for(int i=0;i<puz.length;i++) {
			for(int j=0;j<puz[i].length;j++) {
				System.out.printf("%-3s",puz[i][j]);
			}
			System.out.println();
		}
		System.out.println("--------");
	}
	
	public void start() {
		Scanner scan = new Scanner(System.in);
		System.out.println("**퍼즐게임을 시작합니다.**");
		while(!(Objects.deepEquals(puz, PUZ_ORIGIN))) {
			showpuz();
			System.out.println
				("[ 이동 ] 'w' : UP, 's' : DOWN, 'a' : LEFT, 'd' : RIGHT");
			System.out.println("[ 종료 ] x : EXIT");
			System.out.println("선택>> ");
			String move = scan.nextLine();
			
			switch (move) {
			case "w":
				up();
				break;
			case "s":
				down();
				break;
			case "a":
				left();
				break;
			case "d":
				right();
				break;
			case "x":
				System.out.println("프로그램을 종료합니다.");
				return;
			}
			
		}
		System.out.println("정답입니다!!");
		showpuz();
		
	}
	
	public void up() {
					
		if(puz[2][j] == X){
			System.out.println("위로 움직일 수 없습니다.");
			return;
		}
		Change = puz[++i][j];
		puz[--i][j] = Change;
		puz[++i][j] = X;
		return;
	}
	
	public void down() {
		if(puz[0][j] == X){
			System.out.println("아래로 움직일 수 없습니다.");
			return;
		}
		Change = puz[--i][j];
		puz[++i][j] = Change;
		puz[--i][j] = X;
		return;
	}
	public void left() {
		if(puz[i][2] == X){
			System.out.println("왼쪽으로 움직일 수 없습니다.");
			return;
		}
		Change = puz[i][++j];
		puz[i][--j] = Change;
		puz[i][++j] = X;
		return;
	}
	public void right() {
		if(puz[i][0] == X){
			System.out.println("오른쪽으로 움직일 수 없습니다.");
			return;
		}
		Change = puz[i][--j];
		puz[i][++j] = Change;
		puz[i][--j] = X;
		return;
	}
	
	
}

