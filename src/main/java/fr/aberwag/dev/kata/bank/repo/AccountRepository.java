package fr.aberwag.dev.kata.bank.repo;

import fr.aberwag.dev.kata.bank.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
}
