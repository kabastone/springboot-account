package fr.softview.springboot.controller;

import fr.softview.springboot.model.business.Operation;
import fr.softview.springboot.model.dto.DepositDto;
import fr.softview.springboot.model.dto.OperationsDto;
import fr.softview.springboot.model.dto.TransfertDto;
import fr.softview.springboot.model.dto.WithdrawDto;
import fr.softview.springboot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sambakamara on 19/11/2017.
 */

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @RequestMapping(value = "/deposit", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Operation deposit (@RequestBody DepositDto depositDto) {

        return accountService.deposit(depositDto.getAccountNumber(), depositDto.getAmount());
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Operation withdraw (@RequestBody WithdrawDto withdrawDto) {

        return accountService.withdraw(withdrawDto.getAccountNumber(), withdrawDto.getAmount());
    }

    @RequestMapping(value = "/history/{accountNumber}", produces = "application/json")
    @ResponseBody
    public OperationsDto history (@PathVariable (value = "accountNumber") String accountNumber) {
        return accountService.history(accountNumber);
    }
    
    @RequestMapping(value = "/transfert", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public OperationsDto transfert (@RequestBody TransfertDto transfertDto) {

        return accountService.transfert(transfertDto.getAccountA(), transfertDto.getAccountB(), transfertDto.getAmount());
    }

}
