package nachos.vm;
/*
 * The code in this program is not Mine! It significantly adheres to the code that was made by Ke Xu. The code for the completion of
 * Programming Assignment 3 is in this link: https://github.com/kanrourou/nachos/blob/master/vm/PidAndVpn.java
 */
public class PidAndVpn {
	
	private int pID;
	private int vpn;
	
	public PidAndVpn(int pID,int vpn){
		this.pID=pID;
		this.vpn=vpn;
	}
	
	@Override
	public boolean equals(Object that){
		if(that==null)return false;
		return this.pID==((PidAndVpn)that).pID&&this.vpn==((PidAndVpn)that).vpn;
	}
	
	@Override
	public int hashCode(){
		return (new Integer(pID).toString()+new Integer(vpn).toString()).hashCode();
	}

}