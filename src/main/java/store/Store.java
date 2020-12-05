package store;

import models.Account;
import models.Hall;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.Properties;

public interface Store {

    void save(Account account);

    Collection<Hall> findAllHalls();

    Hall findHallById();

}
