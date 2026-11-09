// commonapi/events/AccountCreditedEvent.java
package rouissiya.zouhair.eventsourcing.commonapi.events;

public record AccountCreditedEvent(String accountId, double amount, String currency) {
}