package rouissiya.zouhair.eventsourcing.query.handlers;

import rouissiya.zouhair.eventsourcing.query.dto.AccountStatementResponseDTO;
import rouissiya.zouhair.eventsourcing.query.entities.Account;
import rouissiya.zouhair.eventsourcing.query.entities.AccountOperation;
import rouissiya.zouhair.eventsourcing.query.queries.GetAccountStatementQuery;
import rouissiya.zouhair.eventsourcing.query.queries.GetAllAccountsQuery;
import rouissiya.zouhair.eventsourcing.query.queries.WatchEventQuery;
import rouissiya.zouhair.eventsourcing.query.repositories.AccountOperationRepository;
import rouissiya.zouhair.eventsourcing.query.repositories.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountQueryHandler {

    private final AccountRepository accountRepository;
    private final AccountOperationRepository operationRepository;

    public AccountQueryHandler(AccountRepository accountRepository,
            AccountOperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query) {
        return accountRepository.findAll();
    }

    @QueryHandler
    public AccountStatementResponseDTO on(GetAccountStatementQuery query) {
        Account account = accountRepository.findById(query.accountId()).orElseThrow();
        List<AccountOperation> operations = operationRepository.findByAccountId(query.accountId());
        return new AccountStatementResponseDTO(account, operations);
    }

    @QueryHandler
    public AccountOperation on(WatchEventQuery query) {
        return new AccountOperation();
    }
}