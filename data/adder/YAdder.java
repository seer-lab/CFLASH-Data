class YAdder implements Runnable {

	Thread runner;
	int x = 0;
	int y = 0;
	Object sync;
	
	public YAdder() {
	}
	
	public YAdder(int x, int y, Object sync) {
		this.x = x;
		this.y = y;
		this.sync = sync;
	}
	
	public void run() {
		while(true){
			synchronized(sync){
				try {
					sync.wait();
					x++;
					y = y + 5;
					System.out.println("yAdder -- x: " + x + ", y: " + y);
					sync.notify();
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