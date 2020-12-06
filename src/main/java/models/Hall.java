package models;

public class Hall {
    private int id;
    private int rowColumn;
    private int accountId;

    public Hall() {
    }

    public Hall(int id, int rowColumn, int accountId) {
        this.id = id;
        this.rowColumn = rowColumn;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRowColumn() {
        return rowColumn;
    }

    public void setRowColumn(int rowColumn) {
        this.rowColumn = rowColumn;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
