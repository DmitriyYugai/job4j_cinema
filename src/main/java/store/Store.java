package store;

import models.Account;
import models.Hall;

import java.util.Collection;
import java.util.Optional;


public interface Store {

    boolean save(Account account);

    Collection<Hall> findAllHalls();

    Optional<Hall> findHallByRowCol(int rowCol);

}
