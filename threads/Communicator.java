package nachos.threads;

import nachos.machine.*;

/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit
 * messages. Multiple threads can be waiting to <i>speak</i>,
 * and multiple threads can be waiting to <i>listen</i>. But there should never
 * be a time when both a speaker and a listener are waiting, because the two
 * threads can be paired off at this point.
 */
public class Communicator {

	private Lock lock;
	private int message;

	private boolean waitForListen;
	private boolean waitForSpeaker;
	private boolean messReceived;

	private Condition speakerQueue;
	private Condition listenQueue;
	private Condition sendQueue;
	private Condition getQueue;

    /**
     * Allocate a new communicator.
     */
    public Communicator() {
    	lock= new Lock();
    	
    	waitForListen= false;
    	waitForSpeaker= false;
    	messReceived= false;
    	
    	sendQueue= new Condition(lock);
    	
        getQueue= new Condition(lock);
        
    	speakerQueue= new Condition(lock);
    	
        listenQueue= new Condition(lock);
    }

    /**
     * Wait for a thread to listen through this communicator, and then transfer
     * <i>word</i> to the listener.
     *
     * <p>
     * Does not return until this thread is paired up with a listening thread.
     * Exactly one listener should receive <i>word</i>.
     *
     * @param	word	the integer to transfer.
     */
    public void speak(int word) {
    	lock.acquire();
    	
    	while(waitForSpeaker==true) {
    		speakerQueue.sleep();
    	}
    	
    	waitForSpeaker=true;
    	
    	message=word;

    	while(waitForListen==false || messReceived==false) {
    		sendQueue.sleep();
    		getQueue.wake();
    	}
    	
    	messReceived= false;
    	speakerQueue.wake();
    	listenQueue.wake();
    	
    	waitForSpeaker=false;
    	waitForListen=false;
    	
    	lock.release();
    }

    /**
     * Wait for a thread to speak through this communicator, and then return
     * the <i>word</i> that thread passed to <tt>speak()</tt>.
     *
     * @return	the integer transferred.
     */    
    public int listen() {
        lock.acquire();
        
        while(waitForListen==true) {
        	listenQueue.sleep();
        }
        
        waitForListen=true;
        
        while(waitForSpeaker==false) {
        	getQueue.sleep();
        }
        
        sendQueue.wake();
        messReceived=true;
        lock.release();
        return message;
    	//return 0;
        }
}
