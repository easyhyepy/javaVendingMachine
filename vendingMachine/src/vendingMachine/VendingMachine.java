package vendingMachine;

import java.util.Scanner;

public class VendingMachine {

	public static void main(String[] args) {
		String selection="";
		int cash=0;
		
		//�Է¹ޱ�
		System.out.print("�ݾװ� ���� �Է��ϼ���:");
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
	int change=0;  //�ܵ�
	String displayPharase = "ȭ������Դϴ�";	//ȭ�� ���
		
}

class Controller {

	Controller() {
		System.out.println("��Ʈ�ѷ� �⺻������");
	}
	Controller(int cash) {
		this.cash = cash;
		System.out.println(cash);
	}

	//void getUserInput(int cash, String seletion);
	//void getRecipe(String seletion);
	int naemam() { return 123; }


	int cash = 0;
	String selection = "����";
	String recipe = "������";
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
	PrinterServer printServer = new PrinterServer(); // �����ͼ����� ����Ŵ
	void print (int file) { // computer���� �� Print (file)�� ����
		printServer.print (file); // PrinterServer���� Print (file)�� �����.
	}
}

class PrinterServer {
	Printer printer = new Printer (); // �����͸� ����Ŵ
	Queue queue = new Queue (); // ť�� ����Ŵ
	void print (int file) { // computer�� ����� print (File)�� ����
		if ( printer.isBusy() == 1) // ���� �����Ͱ� ��� �Ұ����ϸ�
			queue.store(file); // Queue����I store (File)�� �����
		else // ����Ʈ�� ��� �����ϸ�
			printer.print(file); // Printer���� print (file)�� �����.
		}
}

class Printer {
	void print(int file) {
		System.out.println("Printer.print ���");
	}
	int isBusy() { return 1;}
}

class Queue{
	void store(int file) {
		System.out.println("Queue.store ���");
	}
}
*/