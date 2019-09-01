class XAdder implements Runnable {

	Thread runner;
	int x = 0;
	int y = 0;
	Object sync;
	
	public XAdder() {
		
	}
	
	public XAdder(int x, int y, Object sync) {
		this.x = x;
		this.y = y;
		this.sync = sync;
	}
	
	public void run() {
		System.out.println("xAdder -- x: " + x + ", y: " + y);
		while(true){
			synchronized(sync){
				try {
					x++;
					System.out.println("xAdder -- x: " + x + ", y: " + y);
					sync.notify();
					sync.wait();
					y = y + 5;
					if(y > 100){
						break;
					}
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			}
		}
	}
}