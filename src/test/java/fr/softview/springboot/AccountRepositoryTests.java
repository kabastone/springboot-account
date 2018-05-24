package fr.softview.springboot;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import fr.softview.springboot.model.entity.AccountEntity;
import fr.softview.springboot.repository.AccountRepository;

@RunWith(SpringRunner.class)
public class AccountRepositoryTests {
	@Mock
	private AccountRepository accountRepository;
	AccountEntity account;
	
	@Before
	public void setup() {
		account = new AccountEntity();
		account.setNumber("0001");
		account.setBalance(20);
	}
	
	@Test
	public void findByNumberTest() {
		Mockito.when(accountRepository.findByNumber("0001")).thenReturn(account);
		assertEquals(account, accountRepository.findByNumber("0001"));
		
	}
	@Test
	public void saveTest() {
		Mockito.when(accountRepository.save(account)).thenReturn(account);
		assertEquals(account, accountRepository.save(account));
		
	}
	
}
