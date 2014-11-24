package Decorator;

public class Test {

	public static void main(String[] args) {
		Humburger humburger = new ChickenBurger();
		System.out.println(humburger.getName()+"     价钱:"+humburger.getPrice());
		
		Lettuce lettuce = new Lettuce(humburger);
		System.out.println(lettuce.getName()+"    价钱："+lettuce.getPrice());
		
		Chilli chilli = new Chilli(humburger);
		System.out.println(chilli.getName()+"     价钱:"+chilli.getPrice());
	
		Lettuce lettuce2 = new Lettuce(chilli);
		System.out.println(lettuce2.getName()+"     价钱:"+lettuce2.getPrice());
	}

}
