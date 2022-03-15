package fr.aberwag.dev.kata.bank.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountData {

    private String firstName;
    private String lastName;
    private String bankAccountNumber;
    private String countryCode;
    private Integer checkDigits;
    private Integer bankCode;
    private Integer branchCode;
    private Integer nationalCheckDigit;
    private Double balance;
    private List<HistoryData> histories;

}
