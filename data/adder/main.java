public class main {
	public static void main(String[] args) {
		Object sync = new Object();
		XAdder xThread = new XAdder(1,5, sync);
		YAdder yThread = new YAdder(1,5, sync);
		Thread thread1 = new Thread(xThread, "thread1");
		Thread thread2 = new Thread(yThread, "thread2");
		thread1.start();
		thread2.start();
	}
}
