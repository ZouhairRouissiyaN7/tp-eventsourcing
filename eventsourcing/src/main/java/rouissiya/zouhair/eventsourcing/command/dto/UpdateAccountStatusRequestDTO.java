package rouissiya.zouhair.eventsourcing.command.dto;

import rouissiya.zouhair.eventsourcing.commonapi.enums.AccountStatus;

public record UpdateAccountStatusRequestDTO(String accountId, AccountStatus status) {
}