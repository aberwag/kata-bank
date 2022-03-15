package fr.aberwag.dev.kata.bank.controller;

import fr.aberwag.dev.kata.bank.dto.AccountData;
import fr.aberwag.dev.kata.bank.dto.HistoryData;
import fr.aberwag.dev.kata.bank.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}/history")
    public AccountData getAccountInfo(@PathVariable Long accountId) throws Exception {
        return accountService.getAccountHistories(accountId);
    }

    @PostMapping("/{accountId}/deposit")
    public AccountData accountDeposit(@PathVariable Long accountId, @RequestBody HistoryData deposit) throws Exception {
        return accountService.accountDeposit(accountId, deposit);
    }

    @PostMapping("/{accountId}/withdrawal")
    public AccountData accountWithdrawal(@PathVariable Long accountId, @RequestBody HistoryData withdrawal) throws Exception {
        return accountService.accountWithdrawal(accountId, withdrawal);
    }
}
