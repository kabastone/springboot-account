package fr.softview.springboot.service;

import fr.softview.springboot.converter.OperationConverter;
import fr.softview.springboot.exception.AccountException;
import fr.softview.springboot.exception.OperationException;
import fr.softview.springboot.model.business.Operation;
import fr.softview.springboot.model.business.OperationType;
import fr.softview.springboot.model.dto.OperationsDto;
import fr.softview.springboot.model.entity.AccountEntity;
import fr.softview.springboot.model.entity.OperationEntity;
import fr.softview.springboot.repository.AccountRepository;
import fr.softview.springboot.repository.OperationRepository;
import fr.softview.springboot.util.Constants;
import fr.softview.springboot.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sambakamara on 19/11/2017.
 */

@Service
public class AccountServiceImpl implements  AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    OperationConverter operationConverter;


    public Operation deposit(String accountNumber, int amount) {
        checkAmount (amount);

        AccountEntity account = getAccount (accountNumber);

        int balance =  account.getBalance();
        balance = balance + amount;

        OperationEntity operation = new OperationEntity();
        operation.setAccount(accountNumber);
        operation.setAmount(amount);
        operation.setType(OperationType.DEPOSIT.toString());
        operation.setDate(DateUtil.getCurrentDate());
        operation.setBalance(balance);

        operationRepository.save(operation);

        //update account balance
        account.setBalance(balance);
        accountRepository.save(account);

        return  operationConverter.convert(operation);
    }

    public Operation withdraw(String accountNumber, int amount) {
        checkAmount (amount);

        AccountEntity account = getAccount (accountNumber);

        int balance = account.getBalance();
        balance = balance - amount;
        if(balance < 0){
            throw new OperationException(Constants.ERROR_INVALID_OPERATION);
        }

        OperationEntity operation = new OperationEntity();
        operation.setBalance(balance);
        operation.setAccount(accountNumber);
        operation.setAmount(amount);
        operation.setType(OperationType.WITHDRAW.toString());
        operation.setDate(DateUtil.getCurrentDate());

        operationRepository.save(operation);

        //update account balance
        account.setBalance(balance);
        accountRepository.save(account);

        return  operationConverter.convert(operation);
    }

    public OperationsDto history(String accountNumber) {
        List<OperationEntity>  operations = operationRepository.findByAccount(accountNumber);
        return operationConverter.convertList(operations);
    }
    
    public OperationsDto transfert(String accountNumberA, String accountNumberB, int amount) {
    		List<Operation> operations = new ArrayList<>();
    		OperationsDto operationsDto = new OperationsDto();
  
    		operations.add(withdraw(accountNumberA, amount));
    		operations.add(deposit(accountNumberB, amount));
    			
    		operationsDto.setOperations(operations);
    		
    		return operationsDto;	
    }

    private AccountEntity getAccount(String accountNumber){

        AccountEntity account = accountRepository.findByNumber(accountNumber);

        if(account == null) {
            throw new AccountException(Constants.ERROR_INVALID_ACCOUNT);
        }

        return account;
    }

    private void checkAmount (int amount) {
        if(amount < 0){
            throw new OperationException(Constants.ERROR_INVALID_OPERATION);
        }
    }
}
