package rouissiya.zouhair.eventsourcing.query.handlers;

import rouissiya.zouhair.eventsourcing.commonapi.enums.OperationType;
import rouissiya.zouhair.eventsourcing.commonapi.events.*;
import rouissiya.zouhair.eventsourcing.query.entities.Account;
import rouissiya.zouhair.eventsourcing.query.entities.AccountOperation;
import rouissiya.zouhair.eventsourcing.query.repositories.AccountOperationRepository;
import rouissiya.zouhair.eventsourcing.query.repositories.AccountRepository;
import rouissiya.zouhair.eventsourcing.query.queries.WatchEventQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class AccountEventHandler {

    private final AccountRepository accountRepository;
    private final AccountOperationRepository operationRepository;
    private final QueryUpdateEmitter queryUpdateEmitter;

    public AccountEventHandler(AccountRepository accountRepository,
            AccountOperationRepository operationRepository,
            QueryUpdateEmitter queryUpdateEmitter) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }

    @EventHandler
    public void on(AccountCreatedEvent event, @Timestamp Instant instant) {
        log.info("QUERY SIDE: AccountCreatedEvent received");
        accountRepository.save(Account.builder()
                .id(event.accountId())
                .balance(event.initialBalance())
                .status(event.status())
                .currency(event.currency())
                .createdAt(instant)
                .build());
    }

    @EventHandler
    public void on(AccountActivatedEvent event) {
        log.info("QUERY SIDE: AccountActivatedEvent received");
        Account account = accountRepository.findById(event.accountId()).orElseThrow();
        account.setStatus(event.status());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountStatusUpdatedEvent event) {
        log.info("QUERY SIDE: AccountStatusUpdatedEvent received");
        Account account = accountRepository.findById(event.accountId()).orElseThrow();
        account.setStatus(event.status());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event, @Timestamp Instant instant) {
        log.info("QUERY SIDE: AccountDebitedEvent received");
        Account account = accountRepository.findById(event.accountId()).orElseThrow();
        AccountOperation operation = operationRepository.save(AccountOperation.builder()
                .amount(event.amount())
                .operationDate(instant)
                .type(OperationType.DEBIT)
                .currency(event.currency())
                .account(account)
                .build());
        account.setBalance(account.getBalance() - operation.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(
                WatchEventQuery.class,
                query -> query.accountId().equals(event.accountId()),
                operation);
    }

    @EventHandler
    public void on(AccountCreditedEvent event, @Timestamp Instant instant) {
        log.info("QUERY SIDE: AccountCreditedEvent received");
        Account account = accountRepository.findById(event.accountId()).orElseThrow();
        AccountOperation operation = operationRepository.save(AccountOperation.builder()
                .amount(event.amount())
                .operationDate(instant)
                .type(OperationType.CREDIT)
                .currency(event.currency())
                .account(account)
                .build());
        account.setBalance(account.getBalance() + operation.getAmount());
        accountRepository.save(account);
        queryUpdateEmitter.emit(
                WatchEventQuery.class,
                query -> query.accountId().equals(event.accountId()),
                operation);
    }
}