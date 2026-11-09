package rouissiya.zouhair.eventsourcing.commonapi.events;

import rouissiya.zouhair.eventsourcing.commonapi.enums.AccountStatus;

public record AccountActivatedEvent(String accountId, AccountStatus status) {
}