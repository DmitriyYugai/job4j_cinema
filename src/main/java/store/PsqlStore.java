package store;

import models.Account;
import models.Hall;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class PsqlStore implements Store{
    private static final Logger LOGGER = LoggerFactory.getLogger(PsqlStore.class.getName());

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db_cinema.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }


    @Override
    public void save(Account account) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps1 =  cn.prepareStatement("INSERT INTO accounts(name, phone, hall) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement ps2 =  cn.prepareStatement("UPDATE halls SET account_id=? WHERE row_column=?")
        ) {
            ps1.setString(1, account.getName());
            ps1.setString(2, account.getPhone());
            ps1.setInt(3, account.getHall());
            ps1.execute();
            try (ResultSet id = ps1.getGeneratedKeys()) {
                if (id.next()) {
                    account.setId(id.getInt(1));
                }
            }
            ps2.setInt(1, account.getId());
            ps2.setInt(2, account.getHall());
            ps2.execute();
        } catch (Exception e) {
            LOGGER.error("Error during saving user", e);
        }
    }

    @Override
    public Collection<Hall> findAllHalls() {
        List<Hall> halls = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM halls ORDER BY row_column ASC")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    halls.add(new Hall(it.getInt("id"),
                            it.getInt("row_column"),
                            it.getInt("account_id"))
                    );
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error during finding posts", e);
        }
        return halls;
    }

    @Override
    public Optional<Hall> findHallByRowCol(int rowCol) {
        try (Connection cn = pool.getConnection();
             PreparedStatement st = cn.prepareStatement(
                     "select * from halls where row_column = ?")) {
            st.setInt(1, rowCol);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Hall(
                            rs.getInt("id"),
                            rs.getInt("row_column"),
                            rs.getInt("account_id")
                    ));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error during finding post by id", e);
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.save(new Account("Dima", "123", 22));
        System.out.println(store.findAllHalls());
        System.out.println(store.findHallByRowCol(22).get());
    }
}
