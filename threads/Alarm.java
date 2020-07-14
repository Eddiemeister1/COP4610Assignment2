package nachos.threads;

import java.util.PriorityQueue;

import nachos.machine.*;

/**
 * Uses the hardware timer to provide preemption, and to allow threads to sleep
 * until a certain time.
 */
public class Alarm {
    /**
     * Allocate a new Alarm. Set the machine's timer interrupt handler to this
     * alarm's callback.
     *
     * <p><b>Note</b>: Nachos will not function correctly with more than one
     * alarm.
     */
    public Alarm() {
	Machine.timer().setInterruptHandler(new Runnable() {
		public void run() { timerInterrupt(); }
	    });
    }

    /**
     * The timer interrupt handler. This is called by the machine's timer
     * periodically (approximately every 500 clock ticks). Causes the current
     * thread to yield, forcing a context switch if there is another thread
     * that should be run.
     */
    public void timerInterrupt() {
    	
    	long currentTime= Machine.timer().getTime();
    	boolean status= Machine.interrupt().disable();
    	
    	if(timeQueue.isEmpty()== false) {
    	WaitUptime thread;
    	
    	while((thread=timeQueue.peek())!=null && timeQueue.peek().upTime<= currentTime) {
    		
    		WaitUptime waitUptime= timeQueue.poll();
    		KThread athread= waitUptime.thread;
    		
    		if(athread != null) {
    			athread.ready();
    		}
    	}
    	}
    	
    	Machine.interrupt().restore(status);
    	KThread.yield();
    }

    /**
     * Put the current thread to sleep for at least <i>x</i> ticks,
     * waking it up in the timer interrupt handler. The thread must be
     * woken up (placed in the scheduler ready set) during the first timer
     * interrupt where
     *
     * <p><blockquote>
     * (current time) >= (WaitUntil called time)+(x)
     * </blockquote>
     *
     * @param	x	the minimum number of clock ticks to wait.
     *
     * @see	nachos.machine.Timer#getTime()
     */
    public void waitUntil(long x) {
    	// for now, cheat just to get something working (busy waiting is bad)
    	boolean status= Machine.interrupt().disable();
    	long upTime = Machine.timer().getTime() + x;
    	
    	WaitUptime waitUptime= new WaitUptime(KThread.currentThread(), upTime);
    	timeQueue.add(waitUptime);
    	KThread.sleep();
    	
    	Machine.interrupt().restore(status);
        }
        
        
private class WaitUptime implements Comparable<WaitUptime>{
	
    public WaitUptime (KThread thread, long upTime){
    	Lib.assertTrue(Machine.interrupt().disabled());
    	
		this.thread = thread;
		this.upTime = upTime;
	}
    
    public int compareTo(WaitUptime waitUptime) {
		if (this.upTime < waitUptime.upTime){
			return -1;
		}
		else if (this.upTime > waitUptime.upTime){
			return 1;
		}
		else{
			return thread.compareTo(waitUptime.thread);
		}
    }
    
    
    private KThread thread;
    private long upTime;
    }
    
    private PriorityQueue<WaitUptime> timeQueue = 
            		new PriorityQueue<WaitUptime>();
}