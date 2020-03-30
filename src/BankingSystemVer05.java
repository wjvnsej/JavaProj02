import ver05.Account;

/*
9단계[JDBC] : BankingSystemVer09.java
1단계[Class생성] : BankingSystemVer01.java 를 
복사하여 BankingSystemVer05.java를 생성한다.
또한  ver01 패키지를 복사하여 ver05 패키지를 생성.한다
위와 동일하게 JDBC를 이용하여 오라클과 연동하는 프로그램을 제작해본다.

테이블 생성 
계좌번호, 이름, 잔액 을 	저장할수 있는 테이블을 생성한다.
테이블명 : banking_tb
primary key와 같은 제약조건도 걸어준다.
시퀀스 생성
시퀀스명 : seq_banking	
계좌개설 : insert문으로 구현한다.
PreparedStatement 클래스 이용
입금/출금 : 이미 생성된 계좌에서 금액이 변동되는 것이므로 update문으로 구현한다.
PreparedStatement 클래스 이용
전체계좌정보출력 : select문과 like절을 이용하여 구현한다.
Statement  클래스 이용
*/
public class BankingSystemVer05 {

	public static void main(String[] args) {
		
		Account account = new Account();
		account.start();

	}

}
