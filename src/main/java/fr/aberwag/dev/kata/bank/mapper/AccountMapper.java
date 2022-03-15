package fr.aberwag.dev.kata.bank.mapper;

import fr.aberwag.dev.kata.bank.dto.AccountData;
import fr.aberwag.dev.kata.bank.dto.HistoryData;
import fr.aberwag.dev.kata.bank.model.Account;
import fr.aberwag.dev.kata.bank.model.History;

import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    public static AccountData mapAccount(Account account) {
        return AccountData.builder()
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .bankAccountNumber(account.getBankAccountNumber())
                .countryCode(account.getCountryCode())
                .checkDigits(account.getCheckDigits())
                .bankCode(account.getBankCode())
                .branchCode(account.getBranchCode())
                .nationalCheckDigit(account.getNationalCheckDigit())
                .balance(account.getBalance())
                .histories(mapHistories(account.getHistories()))
                .build();
    }

    private static List<HistoryData> mapHistories(List<History> histories) {
        if (histories == null || histories.isEmpty()) {
            return null;
        }
        return histories.stream()
                .map(AccountMapper::mapHistory).collect(Collectors.toList());
    }

    private static HistoryData mapHistory(History history) {
        return HistoryData.builder()
                .amount(history.getAmount())
                .operationType(history.getOperationType())
                .createdAt(history.getCreatedAt())
                .build();
    }
}
