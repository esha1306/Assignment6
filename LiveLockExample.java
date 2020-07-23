import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LiveLockExample {
	private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LiveLockExample livelock = new LiveLockExample();
        new Thread(livelock::operation1, "T1").start();
        new Thread(livelock::operation2, "T2").start();

	}
	public void operation1() {
        while (true) {
            tryLock(lock1, 50);
            System.out.println("lock1 acquired, trying to acquire lock2.");
            try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
            if (tryLock(lock2)) {
            	System.out.println("lock2 acquired.");
            } else {
            	System.out.println("cannot acquire lock2, releasing lock1.");
                lock1.unlock();
                continue;
            }
 
            System.out.println("executing first operation.");
            break;
        }
        lock2.unlock();
        lock1.unlock();
    }
 
    private boolean tryLock(Lock lock22) {
		// TODO Auto-generated method stub
		return false;
	}
	private void tryLock(Lock lock12, int i) {
		// TODO Auto-generated method stub
		
	}
	public void operation2() {
        while (true) {
            tryLock(lock2, 50);
            System.out.println("lock2 acquired, trying to acquire lock1.");
            try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
            if (tryLock(lock1)) {
            	System.out.println("lock1 acquired.");
            } else {
            	System.out.println("cannot acquire lock1, releasing lock2.");
                lock2.unlock();
                continue;
            }
 
            System.out.println("executing second operation.");
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }
 


}
