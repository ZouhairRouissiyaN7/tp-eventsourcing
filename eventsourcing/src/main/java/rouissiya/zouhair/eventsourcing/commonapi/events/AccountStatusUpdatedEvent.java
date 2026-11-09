package rouissiya.zouhair.eventsourcing.commonapi.events;

import rouissiya.zouhair.eventsourcing.commonapi.enums.AccountStatus;

public record AccountStatusUpdatedEvent(String accountId, AccountStatus status) {
}