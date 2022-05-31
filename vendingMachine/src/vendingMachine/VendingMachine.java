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
		//System.out.println("U����Դϴ�"); U.getCash();  U.getSelection(); //Ȯ�� �Ϸ�
		
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
		
		// System.out.println("c����Դϴ�"); 		c.getCash();		c.getSelection();
		
	}
	
	void setDisplayPharase(String displayPharase) { this.displayPharase = displayPharase; }
	//void receiveCoffee(String selection)
	//void receiveChange(String change);

	void getCash() { System.out.println(this.cash); }
	void getSelection() { System.out.println(this.selection); }
	void getDisplayPharase() { System.out.println(this.displayPharase); }
	
}

class Controller {

	int cash;
	String selection;	

	void getUserInput(int cash, String seletion) {
		this.cash = cash;
		this.selection = seletion;
		
		MoneyManager m = new MoneyManager();
		//System.out.println("�ܾ��ִ���, �׸��� ���Աݾ� �̻����� Ȯ��"); 		System.out.println(m.checkAvailibilityOfChangesAndAboveprice(this.cash, this.selection));
		if (m.checkAvailibilityOfChangesAndAboveprice(this.cash, this.selection)) {
			m.updateBalance(cash,selection);
			m.getBalance();
			
			CupManager cm = new CupManager();
			IngredientManager im = new IngredientManager();
			WaterManager wm = new WaterManager();
			//System.out.println("manager��(cm, im, wm)�� üũ"); 			System.out.println(cm.checkAvailibility());			System.out.println(im.checkAvailibility(selection));			System.out.println(wm.checkAvailibility(selection));
			if (cm.checkAvailibility() && im.checkAvailibility(selection) && wm.checkAvailibility(selection)) {
				Manufacture mf = new Manufacture();
				//System.out.println("Manufacture üũ");mf.getRequestManufacture();
			}
			
		}
		else {System.out.println("sys balance ������Ʈ �ȵ�. �� ����ڰ� �Է��� �ݾ� �����ִ°� -> ���� �޽��� �����"); m.getBalance();}
	
		
	}
	
	//void getRecipe(String selection);

	void getCash() { System.out.println(this.cash); }
	void getSelection() { System.out.println(this.selection); }
	
}

class MoneyManager {
	int balance=4000;	//�ܾ�
	boolean availibility;
	
	boolean checkAvailibilityOfChangesAndAboveprice(int cash, String selection) {		//�ܾ׸� Ȯ���ϴ°� �ƴ϶� ���Աݾ�>=��ǰ���׵� Ȯ���ؾ� & ������ else�κ��� ���� ���ڿ��� �߸� �Է��ѰϹ�
		if (selection.equals("SpecialCoffee")) {
			if(cash>=2000) {
				availibility = (balance>=cash-2000)?true:false;
			} else availibility=false;
		}
		else if (selection.equals("PlainCoffee")) {
			if(cash>=1000) {
				availibility = (balance>=cash-1000)?true:false;
			} else availibility=false;
		}
		else if (selection.equals("BlackCoffee")) {
			if(cash>=1000) {
				availibility = (balance>=cash-1500)?true:false;
			} else availibility=false;
		}
		else availibility=false;	//���� �߸��� ���
		
		return availibility;
	}
	
	void updateBalance(int cash, String selection) {
		if (selection.equals("SpecialCoffee")) { balance += 2000; }
		else if (selection.equals("PlainCoffee")) { balance += 1000; }
		else if (selection.equals("BlackCoffee")) {  balance += 1500; }
	}
	void getBalance() { System.out.println("���Ǳ� �ܰ�Ȯ��"); System.out.println(this.balance); }
		
}

class CupManager {
	int cupCount = 10;
	boolean checkAvailibility() {
		if (cupCount>=1) return true;
		else {return false;}
	}
}

class IngredientManager {
	int SpecialCoffee = 1;
	int PlainCoffee = 5;
	int BlackCoffee  = 10;
	
	boolean checkAvailibility(String selection) {
		if (SpecialCoffee>=1) return true;
		else if (PlainCoffee>=1) return true;
		else if (BlackCoffee>=1) return true;
		else {return false;}
	}
}

class WaterManager {
	int amountOfWater=1000;	//ml����
	
	boolean checkAvailibility(String selection) {
		if (amountOfWater>=150) return true;		//������ ���� �ص� �ɵ�
		else {return false;}
	}
}

class Manufacture {
	void getRequestManufacture() {System.out.println("�����ߴ�");}
}