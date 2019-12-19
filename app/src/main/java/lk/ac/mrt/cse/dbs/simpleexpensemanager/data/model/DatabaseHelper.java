package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, DatabaseTable.database_name, null, 1);
        //when this constructor is called database will be created
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE AccountsTable (" + DatabaseTable.AccountsTable.account_no + " TEXT(20) PRIMARY KEY, "
                + DatabaseTable.AccountsTable.bank_name + " TEXT(20)," + DatabaseTable.AccountsTable.account_holder_name + " TEXT(20),"
                + DatabaseTable.AccountsTable.balance + " REAL )");
        //execute the query and create the table
        db.execSQL("CREATE TABLE TransactionsTable (" + DatabaseTable.TransactionsTable.date + "DATE," + DatabaseTable.TransactionsTable.account_no + " TEXT(20)," + DatabaseTable.TransactionsTable.expense_type + " TEXT(20)," + DatabaseTable.TransactionsTable.transaction_amount + " REAL, FOREIGN KEY (" + DatabaseTable.TransactionsTable.account_no + ") REFERENCES Account("
                + DatabaseTable.TransactionsTable.account_no + " ))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS AccountsTable");
        db.execSQL("DROP TABLE IF EXISTS TransactionsTable");
        onCreate(db);
    }
}
