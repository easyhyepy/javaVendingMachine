package vendingMachine;
import java.util.Scanner;

public class VendingMachine {

	static void displayVM() {
		System.out.println( "---------------------------------------------\n| SpecialCoffee | PlainCoffee | BlackCoffee |\n|      2000     |    1000     |    1500     |\n---------------------------------------------\n\n\n�ݾװ� ���� �Է��ϼ���:");
	}
	
	public static void main(String[] args) {
		String selection="";
		int cash=0;
		
		//�Է¹ޱ�
		displayVM();
		Scanner sc = new Scanner(System.in);
		cash = sc.nextInt();
		selection = sc.next();
		
		//User�� ���� ������ ����.
		UserPanel U = new UserPanel();
		U.accept(cash, selection);
		System.out.println("U����Դϴ�"); U.getCash();  U.getSelection(); //Ȯ�� �Ϸ�
		
	}
	
}

class UserPanel {
	int cash;
	String selection;
	int change;  //�ܵ�
	String displayPharase = "ȭ������Դϴ�";	//ȭ�� ���
	
	void accept(int cash, String selection) {
		this.cash = cash;
		this.selection = selection;
		Controller c = new Controller();
		c.getUserInput(this.cash, this.selection);
		
		System.out.println("c����Դϴ�"); 
		c.getCash();
		c.getSelection();
		
	}
	
	void setDisplayPharase(String displayPharase) { this.displayPharase = displayPharase; }
	//void receiveCoffee(String selection)
	//void receiveChange(String change);

	void getCash() { System.out.println(this.cash); }
	void getSelection() { System.out.println(this.selection); }
	void getDisplayPharase() { System.out.println(this.displayPharase); }
	
	
	
	
		
}

class Controller {

	Controller() {
	}
	
	int cash;
	String selection;
	String recipe;
	

	void getUserInput(int cash, String seletion) {
		this.cash = cash;
		this.selection = seletion;
	}
	
	//void getRecipe(String selection);

	void getCash() { System.out.println(this.cash); }
	void getSelection() { System.out.println(this.selection); }
	
	
}