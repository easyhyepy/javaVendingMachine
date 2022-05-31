package vendingMachine;

import java.util.Scanner;

public class VendingMachine {

	public static void main(String[] args) {
		String selection="";
		int cash=0;
		
		//입력받기
		System.out.print("금액과 선택 입력하세요:");
		Scanner sc = new Scanner(System.in);
		cash = sc.nextInt();
		selection = sc.next();
		
		UserPanel U = new UserPanel();
		U.accept(cash, selection);
		U.getCash();
		U.getSelection();
		U.getDisplayPharase();
		
		Controller c = new Controller();
		System.out.println(c.naemam());
	}
}

class UserPanel {
	  
	void accept(int cash, String seletion) {
		this.cash = cash;
		this.seletion = seletion;
	}
	
	void setDisplayPharase(String displayPharase) { this.displayPharase = displayPharase; }
	//void receiveCoffee(String seletion)
	//void receiveChange(String change);

	void getCash() { System.out.println(this.cash); }
	void getSelection() { System.out.println(seletion); }
	void getDisplayPharase() { System.out.println(displayPharase); }
	
	int cash =0;
	String seletion = "";
	int change=0;  //잔돈
	String displayPharase = "화면출력입니다";	//화면 출력
		
}

class Controller {

	Controller() {
		System.out.println("컨트롤러 기본생성자");
	}
	Controller(int cash) {
		this.cash = cash;
		System.out.println(cash);
	}

	//void getUserInput(int cash, String seletion);
	//void getRecipe(String seletion);
	int naemam() { return 123; }


	int cash = 0;
	String selection = "선택";
	String recipe = "레시피";
}



/*
 public class vendingMachine {

	public static void main(String[] args) {
		Computer computer = new Computer();
		int file=1;
		computer.print(file);
	}
}

class Computer {
	PrinterServer printServer = new PrinterServer(); // 프린터서버를 가리킴
	void print (int file) { // computer에게 온 Print (file)에 대응
		printServer.print (file); // PrinterServer에게 Print (file)을 명령함.
	}
}

class PrinterServer {
	Printer printer = new Printer (); // 프린터를 가리킴
	Queue queue = new Queue (); // 큐를 가리킴
	void print (int file) { // computer가 명령한 print (File)에 대응
		if ( printer.isBusy() == 1) // 만약 프린터가 사용 불가능하면
			queue.store(file); // Queue에거I store (File)을 명령합
		else // 프린트가 사용 가능하면
			printer.print(file); // Printer에게 print (file)을 명령합.
		}
}

class Printer {
	void print(int file) {
		System.out.println("Printer.print 출력");
	}
	int isBusy() { return 1;}
}

class Queue{
	void store(int file) {
		System.out.println("Queue.store 출력");
	}
}
*/