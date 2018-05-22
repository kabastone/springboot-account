package fr.softview.springboot.model.dto;

public class TransfertDto {
	private String accountA;
	private String accountB;
	private int amount;
	public String getAccountA() {
		return accountA;
	}
	public void setAccountA(String accountA) {
		this.accountA = accountA;
	}
	public String getAccountB() {
		return accountB;
	}
	public void setAccountB(String accountB) {
		this.accountB = accountB;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
