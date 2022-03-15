package fr.aberwag.dev.kata.bank.repo;

import fr.aberwag.dev.kata.bank.model.History;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HistoryRepository extends PagingAndSortingRepository<History, Long> {
}
