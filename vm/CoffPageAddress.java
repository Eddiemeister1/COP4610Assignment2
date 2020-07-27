package nachos.vm;
/*
 * The code in this program is not Mine! It significantly adheres to the code that was made by Ke Xu. The code for the completion of
 * Programming Assignment 3 is in this link: https://github.com/kanrourou/nachos/blob/master/vm/CoffPageAddress.java
 */
public class CoffPageAddress {
	
	private int sectionNumber;
	private int pageOffset;
	
	public CoffPageAddress(int sectionNumber,int pageOffset){
		this.sectionNumber=sectionNumber;
		this.pageOffset=pageOffset;
	}
	
	public int getSectionNumber(){
		return sectionNumber;
	}
	
	public int getPageOffset(){
		return pageOffset;
	}
	
	public boolean equals(CoffPageAddress that){
		if(that==null)return false;
		return this.sectionNumber==that.sectionNumber&&this.pageOffset==that.pageOffset;
	}
	
	@Override
	public int hashCode(){
		return (new Integer(sectionNumber).toString()+new Integer(pageOffset).toString()).hashCode();
	}
	
	

}