package rouissiya.zouhair.eventsourcing.query.dto;

import rouissiya.zouhair.eventsourcing.query.entities.Account;
import rouissiya.zouhair.eventsourcing.query.entities.AccountOperation;

import java.util.List;

public record AccountStatementResponseDTO(Account account, List<AccountOperation> operations) {
}