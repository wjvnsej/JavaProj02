package ver04;

/*
HighCreditAccount : 신용신뢰계좌 > 신용도가 높은 고객에게 개설이 
허용되며 높은 이율의 계좌이다.
NormalAccount 와 마찬가지로 생성자를 통해서 이율정보(이자비율의정보)를 
초기화 할수있도록 정의한다.
고객의 신용등급을 A, B, C로 나누고 계좌개설시 이 정보를 등록한다.
A,B,C 등급별로 각각 기본이율에 7%, 4%, 2%의 이율을 추가로 제공한다.

이자계산에 대해서는 다음의 규칙을 적용한다.
이자계산은 입금시에만 잔고를 대상으로 계산한다. 출금할때는 이자계산을 하지 않는다.
이자계산방식 : 잔고:5000원, 기본이자:2%, 신용등급이자:4%, 입금액:2000원이라 가정하면….
일반계좌 : 잔고 + (잔고 * 기본이자) + 입금액 
Ex) 5000 + (5000 * 0.02) + 2000 = 7,100원
신용계좌 : 잔고 + (잔고 * 기본이자) + (잔고 * 추가이자) + 입금액
Ex) 5000 + (5000 * 0.02) + (5000 * 0.04) + 2000 = 7,300원
초기 계좌개설에서는 이자를 계산하지 않는다.
계좌개설 이후 입금을 할때만 이자가 발생한다.
이자의 계산과정에서 발생하는 소수점은 무시해도 된다.(무조건버림처리)
 */
public class HighCreditAccount extends Account {
	int rate; String grade; 
	
	public HighCreditAccount
		(String accountNum, String name, int balance, int rate, String grade) {
		
		super(accountNum, name, balance);
		this.rate = rate;
		this.grade = grade;
	}
	
	@Override
	public void rateCalcul(int inputMoney) {
		int grade = 0;
		
		if(this.grade.equals("A")) {
			grade = CustomSpecialRate.A;
		}
		else if(this.grade.equals("B")) {
			grade = CustomSpecialRate.B;
		}
		else if(this.grade.equals("C")) {
			grade = CustomSpecialRate.C;
		}
		balance = balance + (balance * rate / 100) 
				+ (balance * grade / 100) + inputMoney;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + rate;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Account account = (Account)obj;
		if(account.accountNum.equals(this.accountNum)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return super.toString()
				+ "\n기본이자 : " + rate + "%"
				+ "\n신용등급 : " + grade;
	}
}
