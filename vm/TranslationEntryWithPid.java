package nachos.vm;

import nachos.machine.TranslationEntry;
/*
 * The code in this program is not Mine! It significantly adheres to the code that was made by Ke Xu. The code for the completion of
 * Programming Assignment 3 is in this link: https://github.com/kanrourou/nachos/blob/master/vm/TranslationEntryWithPid.java
 */
public class TranslationEntryWithPid {
	
	public TranslationEntry translationEntry;
	public int pID;
	
	public TranslationEntryWithPid(int pID,TranslationEntry translationEntry){
		this.translationEntry=translationEntry;
		this.pID=pID;
	}

}