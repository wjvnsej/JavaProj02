package ver03;

public class MenuSelectException extends Exception {
	
	public MenuSelectException() {
		super("1~5사이의 숫자만 입력해주세요.");
	}
}
