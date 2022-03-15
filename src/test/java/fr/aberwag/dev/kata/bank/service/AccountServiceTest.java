package fr.aberwag.dev.kata.bank.service;

import fr.aberwag.dev.kata.bank.dto.AccountData;
import fr.aberwag.dev.kata.bank.dto.HistoryData;
import fr.aberwag.dev.kata.bank.model.Account;
import fr.aberwag.dev.kata.bank.model.History;
import fr.aberwag.dev.kata.bank.repo.AccountRepository;
import fr.aberwag.dev.kata.bank.repo.HistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class AccountServiceTest {

    private AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
    private HistoryRepository historyRepository = Mockito.mock(HistoryRepository.class);
    private AccountService accountService = new AccountService(accountRepository, historyRepository);

    @Test
    public void it_should_get_account_info() throws Exception {
        //Given
        Account account = Account.builder()
                .firstName("Ahmed")
                .lastName("ABERWAG")
                .iban("FR1420041010050500013M02606")
                .bankAccountNumber("0500013M026")
                .countryCode("FR")
                .checkDigits(14)
                .bankCode(20041)
                .branchCode(1005)
                .nationalCheckDigit(6)
                .balance(100.0)
                .histories(Collections.singletonList(History.builder()
                                .operationType("DEPOSIT")
                                .amount(10.0)
                        .build()))
                .build();
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        //When
        AccountData accountDB = accountService.getAccountHistories(1L);

        //Then
        Assertions.assertNotNull(accountDB);
        Assertions.assertEquals("Ahmed", accountDB.getFirstName());
        Assertions.assertEquals("ABERWAG", accountDB.getLastName());
        Assertions.assertEquals("0500013M026", accountDB.getBankAccountNumber());
        Assertions.assertEquals("FR", accountDB.getCountryCode());
        Assertions.assertEquals(14, accountDB.getCheckDigits());
        Assertions.assertEquals(20041, accountDB.getBankCode());
        Assertions.assertEquals(1005, accountDB.getBranchCode());
        Assertions.assertEquals(6, accountDB.getNationalCheckDigit());
        Assertions.assertEquals(100.0, accountDB.getBalance());
    }

    @Test
    public void it_should_deposit_amount_on_account() throws Exception {
        //Given
        ArrayList<History> histories = new ArrayList<>();
        histories.add(History.builder()
                .operationType("DEPOSIT")
                .amount(10.0)
                .build());
        Account account = Account.builder()
                .firstName("Ahmed")
                .lastName("ABERWAG")
                .iban("FR1420041010050500013M02606")
                .bankAccountNumber("0500013M026")
                .countryCode("FR")
                .checkDigits(14)
                .bankCode(20041)
                .branchCode(1005)
                .nationalCheckDigit(6)
                .balance(100.0)
                .histories(histories)
                .build();
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        Mockito.when(historyRepository.save(Mockito.any())).thenReturn(History.builder().amount(20.01).operationType("DEPOSIT").createdAt(new Date()).build());

        //When
        AccountData accountDB = accountService.accountDeposit(1L, HistoryData.builder().amount(20.01).operationType("DEPOSIT").build());

        //Then
        Assertions.assertNotNull(accountDB);
        Assertions.assertNotNull(accountDB.getHistories());
        Assertions.assertEquals(2, accountDB.getHistories().size());
        Assertions.assertEquals(20.01, accountDB.getHistories().get(1).getAmount());
        Assertions.assertEquals("DEPOSIT", accountDB.getHistories().get(1).getOperationType());
    }

    @Test
    public void it_should_withdrawal_amount_from_account() throws Exception {
        //Given
        ArrayList<History> histories = new ArrayList<>();
        histories.add(History.builder()
                .operationType("DEPOSIT")
                .amount(10.0)
                .build());
        Account account = Account.builder()
                .firstName("Ahmed")
                .lastName("ABERWAG")
                .iban("FR1420041010050500013M02606")
                .bankAccountNumber("0500013M026")
                .countryCode("FR")
                .checkDigits(14)
                .bankCode(20041)
                .branchCode(1005)
                .nationalCheckDigit(6)
                .balance(100.0)
                .histories(histories)
                .build();
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        Mockito.when(historyRepository.save(Mockito.any())).thenReturn(History.builder().amount(-20.01).operationType("WITHDRAWAL").createdAt(new Date()).build());

        //When
        AccountData accountDB = accountService.accountWithdrawal(1L, HistoryData.builder().amount(-20.01).operationType("WITHDRAWAL").build());

        //Then
        Assertions.assertNotNull(accountDB);
        Assertions.assertNotNull(accountDB.getHistories());
        Assertions.assertEquals(2, accountDB.getHistories().size());
        Assertions.assertEquals(-20.01, accountDB.getHistories().get(1).getAmount());
        Assertions.assertEquals("WITHDRAWAL", accountDB.getHistories().get(1).getOperationType());
    }
}
