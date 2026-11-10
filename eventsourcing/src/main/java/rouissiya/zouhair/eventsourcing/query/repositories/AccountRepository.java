package rouissiya.zouhair.eventsourcing.query.repositories;

import rouissiya.zouhair.eventsourcing.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}