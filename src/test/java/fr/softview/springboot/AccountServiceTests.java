package fr.softview.springboot;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import fr.softview.springboot.converter.OperationConverter;
import fr.softview.springboot.exception.OperationException;
import fr.softview.springboot.model.business.Operation;
import fr.softview.springboot.model.dto.OperationsDto;
import fr.softview.springboot.model.entity.OperationEntity;
import fr.softview.springboot.repository.AccountRepository;
import fr.softview.springboot.repository.OperationRepository;
import fr.softview.springboot.service.AccountService;

@RunWith(SpringRunner.class)
public class AccountServiceTests {
	
	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private OperationRepository operationRepository;
	
	@Mock
	OperationConverter operationConverter;
	
	@Mock
	private AccountService accountServiceImpl;
	
	Operation operation; 
	OperationsDto operationsDto;
	
	@Before
	public void setup() throws Exception {
		operation = new Operation();
		operation.setId(1);
		operation.setAccountNumber("A209");
		operation.setAmount(30);
		operation.setBalance(50);
		operation.setDate("21/05/2018");
		
	}
	
	@Test
	public void testDeposit_ok() {
		operation.setType("DEPOSIT");
		Mockito.when(accountServiceImpl.deposit("A209",30)).thenReturn(operation);
		Operation op = accountServiceImpl.deposit("A209", 30);
		assertEquals(op, operation);
		
//		Mockito.when(operationRepository.save(operation)).thenReturn(operation);
//		Mockito.when(accountRepository.save(account)).thenReturn(account);
//		accountServiceImpl.deposit("A209", 30);
//		Mockito.verify(accountRepository, Mockito.times(1)).findByNumber("A209");
//		Mockito.verify(operationRepository, Mockito.times(1)).save(Matchers.any(OperationEntity.class));
//		Mockito.verify(accountRepository, Mockito.times(1)).save(account);
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = OperationException.class)
	public void testDeposit_wrong_account() {
		Mockito.when(accountServiceImpl.deposit("WRONG",30)).thenThrow(OperationException.class);
		accountServiceImpl.deposit("WRONG", 30);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = OperationException.class)
	public void testDeposit_wrong_amount() {
		Mockito.when(accountServiceImpl.deposit("A209", 0)).thenThrow(OperationException.class);
		accountServiceImpl.deposit("A209", 0);
	}
	
	@Test
	public void testWithdraw_ok() {
		operation.setType("WITHDRAW");
		Mockito.when(accountServiceImpl.withdraw("A209", 30)).thenReturn(operation);
		
		assertEquals(operation, accountServiceImpl.withdraw("A209", 30));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = OperationException.class)
	public void testWithdraw_wrong_amount() {
		operation.setType("WITHDRAW");
		Mockito.when(accountServiceImpl.withdraw("A209", 60))
			.thenThrow(OperationException.class);
		accountServiceImpl.withdraw("A209", 60);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = OperationException.class)
	public void testWithdraw_wrong_account() {
		operation.setType("WITHDRAW");
		Mockito.when(accountServiceImpl.withdraw("WRONG", 30))
			.thenThrow(OperationException.class);
		accountServiceImpl.withdraw("WRONG", 30);
		
	}
	
	@Test
	public void testHistory() {
		List<Operation> operations = new ArrayList<>();
        operations.add(operation);
        operationsDto = new OperationsDto();
        operationsDto.setOperations(operations);
		Mockito.when(accountServiceImpl.history("A209")).thenReturn(operationsDto);
		assertEquals(operationsDto, accountServiceImpl.history("A209"));
		
	}
	
	@Test
	public void testTransfert_OK() {
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
		OperationsDto operationsDto = new OperationsDto();
		List<Operation> operations = new ArrayList<>();
		operations.add(operationA);
		operations.add(operationB);
		operationsDto.setOperations(operations);
		Mockito.when(accountServiceImpl.withdraw("A209", 10)).thenReturn(operationA);
		Mockito.when(accountServiceImpl.deposit("A208", 10)).thenReturn(operationB);
		Mockito.when(accountServiceImpl.transfert("A209", "A208", 10)).thenReturn(operationsDto);
		assertEquals(operationsDto, accountServiceImpl.transfert("A209", "A208", 10));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = OperationException.class)
	public void testTransfert_wrong_amount() {
		Operation operationA = new Operation();
		operationA.setAccountNumber("A209");
		operationA.setAmount(40);
		operationA.setBalance(30);
		operationA.setType("WITHDRAW");
		operationA.setDate("21/05/2018");
		Operation operationB = new Operation();
		operationB.setAccountNumber("A208");
		operationB.setAmount(10);
		operationB.setBalance(50);
		operationB.setType("DEPOSIT");
		operationB.setDate("21/05/2018");
		OperationsDto operationsDto = new OperationsDto();
		List<Operation> operations = new ArrayList<>();
		operations.add(operationA);
		operations.add(operationB);
		operationsDto.setOperations(operations);
		Mockito.when(accountServiceImpl.withdraw("A209", 40)).thenReturn(operationA);
		Mockito.when(accountServiceImpl.deposit("A208", 10)).thenReturn(operationB);
		Mockito.when(accountServiceImpl.transfert("A209", "A208", 40)).thenThrow(OperationException.class);
		accountServiceImpl.transfert("A209", "A208", 40);
		
	}
}
