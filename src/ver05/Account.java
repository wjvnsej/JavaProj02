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
	
	//퍼즐게임
	public void puz_game() {
		while (true) {
			Puzle puzle = new Puzle(7);
			puzle.start();

			Scanner scan = new Scanner(System.in);
			if (Objects.deepEquals(puzle.puz, puzle.PUZ_ORIGIN)) {
				System.out.println("정답입니다!!");
			}
			puzle.showpuz();
			System.out.println("재시작하시겠습니까?(y 누르면 재시작, 나머지는 종료.)");
			String reStart = scan.nextLine();
			if(reStart.equals("y")) {
				puzle.start();
			}
			else {
				System.out.println("게임을 종료합니다.");
				return;
			}
		}
	}
}
//
interface puzVal {
	//퍼즐의 배열 인터페이스화
	String ONE = "1", TWO = "2", THREE = "3", FOUR = "4", FIVE = "5",
			SIX = "6", SEVEN = "7", EIGHT = "8", X = "X"; 
	
	//정답을 확인하기 위한 기본 퍼즐배열 인터페이스화
	String [][] PUZ_ORIGIN = {
			{ONE, TWO, THREE},
			{FOUR, FIVE, SIX},
			{SEVEN, EIGHT, X}
	};
}

class Puzle implements puzVal {
	//X의 기본값
	int i = 2, j = 2;
	//X와 위치를 변경 할 퍼즐이 담길 변수
	String Change;
	//실제 게임에 사용 될 퍼즐
	String [][] puz = {
			{ONE, TWO, THREE},
			{FOUR, FIVE, SIX},
			{SEVEN, EIGHT, X}
	};
	
	//생성자로 퍼즐 셔플
	public Puzle(int num) {
		//셔플 될 횟수
		int shu_num = 0;
		while (shu_num < num) {
			//1~4까지의 랜덤 정수 생성
			int rnd = (int)(Math.random() * 4)+1;
			switch (rnd) {
			//up
			case 1:
				//퍼즐이 위로 움직이지 못할 경우 down() 호출
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
				//퍼즐이 아래로 움직이지 못할 경우 up() 호출
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
				//퍼즐이 왼쪽으로 움직이지 못할 경우 right() 호출
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
				//퍼즐이 오른쪽으로 움직이지 못할 경우 left() 호출
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
		//셔플 후 게임 시작.
		System.out.println("**퍼즐게임을 시작합니다.**");
	}
	
	//게임에 사용되는 퍼즐을 보여주기 위한 함수
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
	
	//퍼즐게임 진행을 위한 함수
	public void start() {
		Scanner scan = new Scanner(System.in);
		
		/*Objects.deepEquals()함수를 사용하여 
		셔플된 퍼즐과 기본 퍼즐의 데이터가 맞는지 확인		
		 */
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
				System.out.println("게임을 종료합니다.");
				return;
			}
		}
	}
	
	//퍼즐을 위로 이동시키기 위한 함수
	public void up() {
		//j의 값과는 상관없이 i의 값이 2이면 위로 움직일 수 없기 때문에 메세지 출력
		if(puz[2][j] == X){
			System.out.println("위로 움직일 수 없습니다.");
			return;
		}
		Change = puz[++i][j];
		puz[--i][j] = Change;
		puz[++i][j] = X;
		return;
	}
	
	//퍼즐을 아래로 이동시키기 위한 함수
	public void down() {
		//j의 값과는 상관없이 i의 값이 0이면 아래로 움직일 수 없기 때문에 메세지 출력
		if(puz[0][j] == X){
			System.out.println("아래로 움직일 수 없습니다.");
			return;
		}
		Change = puz[--i][j];
		puz[++i][j] = Change;
		puz[--i][j] = X;
		return;
	}
	
	//퍼즐을 왼쪽으로 이동시키기 위한 함수
	public void left() {
		//i의 값과는 상관없이 j의 값이 2이면 왼쪽로 움직일 수 없기 때문에 메세지 출력
		if(puz[i][2] == X){
			System.out.println("왼쪽으로 움직일 수 없습니다.");
			return;
		}
		Change = puz[i][++j];
		puz[i][--j] = Change;
		puz[i][++j] = X;
		return;
	}
	
	//퍼즐을 오른쪽로 이동시키기 위한 함수
	public void right() {
		//i의 값과는 상관없이 j의 값이 0이면 오른쪽로 움직일 수 없기 때문에 메세지 출력
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

