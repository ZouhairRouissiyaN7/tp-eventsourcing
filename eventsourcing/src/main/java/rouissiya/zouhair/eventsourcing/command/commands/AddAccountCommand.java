package rouissiya.zouhair.eventsourcing.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record AddAccountCommand(@TargetAggregateIdentifier String id,
        double initialBalance, String currency) {
}