package rouissiya.zouhair.eventsourcing.command.commands;

import rouissiya.zouhair.eventsourcing.commonapi.enums.AccountStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record UpdateAccountStatusCommand(@TargetAggregateIdentifier String id,
        AccountStatus status) {
}