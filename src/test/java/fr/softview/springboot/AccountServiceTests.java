package fr.softview.springboot;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import fr.softview.springboot.converter.OperationConverter;
import fr.softview.springboot.exception.OperationException;
import fr.softview.springboot.model.business.Operation;
import fr.softview.springboot.model.dto.OperationsDto;
import fr.softview.springboot.model.entity.AccountEntity;
import fr.softview.springboot.model.entity.OperationEntity;
import fr.softview.springboot.repository.AccountRepository;
import fr.softview.springboot.repository.OperationRepository;
import fr.softview.springboot.service.AccountServiceImpl;
import fr.softview.springboot.util.DateUtil;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTests {
	
	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private OperationRepository operationRepository;
	
	@Mock
	OperationConverter operationConverter;
	
	@InjectMocks
	private AccountServiceImpl accountServiceImpl;
	
	OperationEntity operation; 
	@Before
	public void setup() throws Exception {
		operation = new OperationEntity();
		operation.setAccount("A209");
		operation.setAmount(30);
		operation.setBalance(50);
		operation.setDate("21/05/2018");
		
	}
	
	@Test
	public void testDeposit() {
		operation.setType("DEPOSIT");
		AccountEntity account = new AccountEntity();
		account.setBalance(50);
		account.setNumber("A209");
		Mockito.when(accountRepository.findByNumber("A209")).thenReturn(account);
		Mockito.when(operationRepository.save(operation)).thenReturn(operation);
		Mockito.when(accountRepository.save(account)).thenReturn(account);
		accountServiceImpl.deposit("A209", 30);
		Mockito.verify(operationRepository, Mockito.times(1)).save(Matchers.any(OperationEntity.class));
		Mockito.verify(accountRepository, Mockito.times(1)).save(account);
		Mockito.verify(accountRepository, Mockito.times(1)).findByNumber("A209");
		
	}
	
	@Test
	public void testWithdrawOK() {
		operation.setType("WITHDRAW");
		AccountEntity account = new AccountEntity();
		account.setBalance(50);
		account.setNumber("A209");
		Mockito.when(accountRepository.findByNumber("A209")).thenReturn(account);
		Mockito.when(operationRepository.save(operation)).thenReturn(operation);
		Mockito.when(accountRepository.save(account)).thenReturn(account);
		accountServiceImpl.withdraw("A209", 30);
		Mockito.verify(operationRepository, Mockito.times(1)).save(Matchers.any(OperationEntity.class));
		Mockito.verify(accountRepository, Mockito.times(1)).save(account);
		Mockito.verify(accountRepository, Mockito.times(1)).findByNumber("A209");
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = OperationException.class)
	public void testWithdrawNOK() {
		operation.setAmount(60);
		operation.setType("WITHDRAW");
		AccountEntity account = new AccountEntity();
		account.setBalance(50);
		account.setNumber("A209");
		Mockito.when(accountRepository.findByNumber("A209")).thenReturn(account);
		Mockito.when(accountServiceImpl.withdraw("A209", operation.getAmount()))
			.thenThrow(OperationException.class);
		Mockito.when(operationRepository.save(operation)).thenReturn(operation);
		Mockito.when(accountRepository.save(account)).thenReturn(account);
		accountServiceImpl.withdraw("A209", 60);
		
	}
	
	@Test
	public void testHistory() {
		
		Mockito.when(operationRepository.findByAccount("A209")).thenReturn(new ArrayList<OperationEntity>());
		accountServiceImpl.history("A209");
		Mockito.verify(operationRepository, Mockito.times(1)).findByAccount("A209");
	}
	
	@Test
	public void testTransfert() {
		Operation operationA = new Operation();
		operationA.setAccountNumber("A209");
		operationA.setAmount(10);
		operationA.setBalance(30);
		operationA.setType("WITHDRAW");
		operationA.setDate("21/05/2018");
		Operation operationB = new Operation();
		operationB.setAccountNumber("A208");
		operationB.setAmount(10);
		operationB.setBalance(50);
		operationB.setType("DEPOSIT");
		operationB.setDate("21/05/2018");
		AccountEntity accountA = new AccountEntity();
		AccountEntity accountB = new AccountEntity();
		accountA.setNumber("A209");
		accountA.setBalance(30);
		accountB.setNumber("A208");
		accountB.setBalance(50);
		OperationsDto operationsDto = new OperationsDto();
		List<Operation> operations = new ArrayList<>();
		operations.add(operationA);
		operations.add(operationB);
		operationsDto.setOperations(operations);
		Mockito.when(accountRepository.findByNumber("A209")).thenReturn(accountA);
		Mockito.when(accountRepository.findByNumber("A208")).thenReturn(accountB);
		Mockito.when(accountServiceImpl.withdraw("A209", 10)).thenReturn(operationA);
		Mockito.when(accountServiceImpl.deposit("A208", 10)).thenReturn(operationB);
		accountServiceImpl.transfert("A209", "A208", 10);
		Mockito.verify(operationRepository, Mockito.times(4)).save(Matchers.any(OperationEntity.class));
		
	}
}
