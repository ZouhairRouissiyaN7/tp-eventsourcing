package rouissiya.zouhair.eventsourcing.command.dto;

public record CreditAccountRequestDTO(String accountId, double amount, String currency) {
}