package rouissiya.zouhair.eventsourcing.commonapi.events;

import rouissiya.zouhair.eventsourcing.commonapi.enums.AccountStatus;

public record AccountCreatedEvent(String accountId, double initialBalance,
        AccountStatus status, String currency) {
}