package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

import android.provider.BaseColumns;

public class DatabaseTable {
    public static final String database_name = "170320V";

    public static class AccountsTable implements BaseColumns{
        public static final String account_no = "accountNo";
        public static final String bank_name = "bankName";
        public static final String account_holder_name = "accountHolderName";
        public static final String balance = "balance";
    }

    public static class TransactionsTable implements BaseColumns{
        public static final String date = "date";
        public static final String account_no = "accountNo";
        public static final String expense_type = "expenseType";
        public static final String transaction_amount = "amount";
    }
}
