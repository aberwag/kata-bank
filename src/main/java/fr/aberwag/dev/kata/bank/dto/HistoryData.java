package fr.aberwag.dev.kata.bank.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class HistoryData {

    private Double amount;
    private String operationType;
    private Date createdAt;

}
