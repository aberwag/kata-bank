package fr.aberwag.dev.kata.bank.service;

import fr.aberwag.dev.kata.bank.dto.AccountData;
import fr.aberwag.dev.kata.bank.dto.HistoryData;
import fr.aberwag.dev.kata.bank.mapper.AccountMapper;
import fr.aberwag.dev.kata.bank.model.Account;
import fr.aberwag.dev.kata.bank.model.History;
import fr.aberwag.dev.kata.bank.repo.AccountRepository;
import fr.aberwag.dev.kata.bank.repo.HistoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class AccountService {

    private final static String OPERATION_TYPE_DEPOSIT = "DEPOSIT";
    private final static String OPERATION_TYPE_WITHDRAWAL = "WITHDRAWAL";
    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;

    public AccountData getAccountHistories(Long accountId) throws Exception {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            return AccountMapper.mapAccount(account.get());
        }
        throw new Exception("Account not found");
    }

    @Transactional
    public AccountData accountDeposit(Long accountId, HistoryData deposit) throws Exception {
        checkDeposit(deposit);
        return accountOperation(accountId, deposit);
    }

    @Transactional
    public AccountData accountWithdrawal(Long accountId, HistoryData withdrawal) throws Exception {
        checkWithdrawal(withdrawal);
        return accountOperation(accountId, withdrawal);
    }

    private AccountData accountOperation(Long accountId, HistoryData operation) throws Exception {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            History operationHistory = historyRepository.save(History.builder()
                    .createdAt(new Date())
                    .amount(operation.getAmount())
                    .operationType(operation.getOperationType())
                    .account(account.get())
                    .build());
            account.get().deposit(operation.getAmount());
            account.get().getHistories().add(operationHistory);
            accountRepository.save(account.get());
            return AccountMapper.mapAccount(account.get());
        }
        throw new Exception("Account not found");
    }

    private void checkDeposit(HistoryData deposit) throws Exception {
        check(deposit);
        if (OPERATION_TYPE_DEPOSIT.equals(deposit.getOperationType()) && deposit.getAmount() < 0) {
            throw new Exception("400 Bad Request");
        }
    }

    private void checkWithdrawal(HistoryData withdrawal) throws Exception {
        check(withdrawal);
        if (OPERATION_TYPE_WITHDRAWAL.equals(withdrawal.getOperationType()) && withdrawal.getAmount() > 0) {
            throw new Exception(",400 Bad Request");
        }
    }

    private void check(HistoryData deposit) throws Exception {
        if (deposit == null || deposit.getAmount() == null || deposit.getOperationType() == null) {
            throw new Exception("400 Bad Request");
        }
    }
}
