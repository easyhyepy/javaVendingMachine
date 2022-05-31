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
	
	
	
	void receiveCoffee(String selection) {
		System.out.print(selection); System.out.println(" Ŀ�ǰ� ���Դ�.");
		//MoneyManager m = new MoneyManager();
		//m.setFinishFalse();
	}
	
	void change(int change) { System.out.print(change); System.out.println("���� �ܵ��� UserPanel�� ��ȯ�Ǿ���.");}
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
				System.out.print("Manufacture üũ: ");mf.getRequestManufacture(this.selection);
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
	
	//boolean notFinish = true;
	//void setFinishFalse() {this.notFinish = false;}
	
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
		
		//�Ƹ� ���⼭ change ȣ���ؾ��ҵ�.
		
		
		//while (notFinish){  //MoneyManager�� notFinish�� setFinishiFalse()�� �ܵ� ��ȯ�� ���߷������� ����. �׷��� �� �ּ��صΰ���. UserPanel�� setFinishFalse()�� ���������� �ּ��ص�
		UserPanel u = new UserPanel();

		if (selection.equals("SpecialCoffee")) { u.change(cash-2000); }
		else if (selection.equals("PlainCoffee")) { u.change(cash-1000); }
		else if (selection.equals("BlackCoffee")) { u.change(cash-1500); }

		//}
		
	}
	void getBalance() { System.out.print("���Ǳ� �ܰ�Ȯ��: "); System.out.println(this.balance); }
		
}

class CupManager {
	int cupCount = 10;
	boolean checkAvailibility() {
		if (cupCount>=1) return true;
		else {return false;}
	}
	int getOrderCup() {
		System.out.println("CupManager�� Manufacture���� ���� �ش�.");
		cupCount--;
		return 1;
	}
}

class IngredientManager {
	int SpecialCoffeeCount = 1;
	int PlainCoffeeCount = 5;
	int BlackCoffeeCount  = 10;
	//String selection;
	
	boolean checkAvailibility(String selection) {
		if (SpecialCoffeeCount>=1) return true;
		else if (PlainCoffeeCount>=1) return true;
		else if (BlackCoffeeCount>=1) return true;
		else {return false;}
	}
	
	int getOrderIngredient(String selection) {
		//this.selection=selection;
		System.out.println("IngredientManager�� Manufacture���� Ingredient�� �ش�.");
		if (selection.equals("SpecialCoffee")) { SpecialCoffeeCount--; }
		else if (selection.equals("PlainCoffee")) { PlainCoffeeCount--; }
		else if (selection.equals("BlackCoffee")) { BlackCoffeeCount--; }
		
		return 1;
	}
	
	int getSpecialCoffeeCount() {return SpecialCoffeeCount;}
	int getPlainCoffeeCount() {return PlainCoffeeCount;}
	int getBlackCoffeeCount() {return BlackCoffeeCount;}
}

class WaterManager {
	int amountOfWater=1000;	//ml����
	
	boolean checkAvailibility(String selection) {
		if (amountOfWater>=150) return true;		//������ ���� �ص� �ɵ�
		else {return false;}
	}
	
	int getOrderWater() {
		amountOfWater-=150;
		return 150;
	}
	int getAmountOfWater() {return amountOfWater;}
}

class Manufacture {
	String selection;
	void getRequestManufacture(String selection) {
		this.selection=selection;
		System.out.print("������û�� �޾Ҵ�. selection ���: "); System.out.println(selection);
		
		CupManager cm = new CupManager();   //�� ��ü�����ϸ� �ȵ� �� ����.
		System.out.print(cm.getOrderCup()); System.out.println("���� ���� �޾Ҵ�.\n");
		
		IngredientManager im = new IngredientManager();
		System.out.print(im.getOrderIngredient(this.selection));	System.out.println("���� ��Ḧ �޾Ҵ�.");
		System.out.print(im.getSpecialCoffeeCount());	System.out.print("�� SpecialCoffee�� ���Ҵ�. / ");
		System.out.print(im.getPlainCoffeeCount());		System.out.print("�� PlainCoffee�� ���Ҵ�. / ");
		System.out.print(im.getBlackCoffeeCount());		System.out.println("�� BlackCoffee�� ���Ҵ�.\n");
		
		WaterManager wm = new WaterManager();
		System.out.print(wm.getOrderWater());			System.out.println("ml�� ���� �޾Ҵ�.");
		System.out.print(wm.getAmountOfWater());		System.out.println("ml�� ���� WaterManger�� �����ִ�.\n");
		
		CoffeeDispenser cd = new CoffeeDispenser();
		cd.getMixed(selection);
		
	}
}

class CoffeeDispenser {
	void getMixed(String selection) { 
		System.out.println("CoffeeDispenser�� �Ű� ���� ���� �޾Ұ�, �̸� ���´�.\n");
		UserPanel u = new UserPanel(); 
		u.receiveCoffee(selection);
		
	}
	
}
