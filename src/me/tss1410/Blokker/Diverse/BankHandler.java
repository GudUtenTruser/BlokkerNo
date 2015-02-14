package me.tss1410.Blokker.Diverse;

import me.tss1410.Blokker.Players.Spiller;

public class BankHandler {
	
	public void addMoney(Spiller s, int i){
		int before = s.getBalance();
		int after = before + i;
		setMoney(s, after);
	}
	
	public void removeMoney(Spiller s, int i){
		int before = s.getBalance();
		int after = before - i;
		setMoney(s, after);
	}
	
	public void setMoney(Spiller s, int i){
		s.setBalance(i);
	}
	
	public void payMoney(Spiller from, Spiller to, int i){
		//SPILLER 1
		int before = from.getBalance();
		int after = before - i;
		setMoney(from, after);
		
		
		//SPILLER 2
		int before2 = to.getBalance();
		int after2 = before2 + i;
		setMoney(to, after2);
	}

}
