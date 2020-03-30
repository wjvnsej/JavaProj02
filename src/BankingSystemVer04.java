import ver04.AccountManager;

/*
4단계[컬렉션&IO] : BankingSystemVer04.java
인스턴스배열 기반의 프로그램을 HashSet<E> 컬렉션으로 변경해보자.
“계좌번호가 동일한 경우 중복된 계좌로 처리한다.”
즉 새로운 정보로 갱신할 지 여부를 물어본후 선택에 따라 처리한다.

컬렉션 기반으로 변경후 인스턴스를 저장하기 위해 입출력을 적용하자.
ObjectInputStream, ObjectOutputStream 클래스를 기반으로 제작한다.
파일의 저장은 프로그램을 종료하는 시점에 이루어져야 하고, 
프로그램 시작 직후 전체정보를 조회하면 기존에 입력된 정보들이 출력되어야 한다.
*/
public class BankingSystemVer04 {

	public static void main(String[] args) {
		
		AccountManager manager = new AccountManager();
		manager.start();

	}

}
