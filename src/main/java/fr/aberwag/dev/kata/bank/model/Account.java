package fr.aberwag.dev.kata.bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "check_digits")
    private Integer checkDigits;

    @Column(name = "bank_code")
    private Integer bankCode;

    @Column(name = "branch_code")
    private Integer branchCode;

    @Column(name = "national_check_digit")
    private Integer nationalCheckDigit;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<History> histories;

    public void deposit(Double amount) {
        this.setBalance(this.getBalance() + amount);
    }
}
