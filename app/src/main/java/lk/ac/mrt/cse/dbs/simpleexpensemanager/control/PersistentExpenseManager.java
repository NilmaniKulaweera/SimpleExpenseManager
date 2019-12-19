package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.DatabaseTable;
//import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.DatabaseHelper;

import static android.database.sqlite.SQLiteDatabase.CREATE_IF_NECESSARY;



public class PersistentExpenseManager extends ExpenseManager {
    private Context context;
    public PersistentExpenseManager(Context context){
        this.context=context;
        try {
            setup();
        } catch (ExpenseManagerException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setup() throws ExpenseManagerException {

        SQLiteDatabase db=context.openOrCreateDatabase("170320V", context.MODE_PRIVATE,  null);

        db.execSQL("CREATE TABLE IF NOT EXISTS Account(account_no VARCHAR PRIMARY KEY,bank_name VARCHAR,account_holder_name VARCHAR,balance REAL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS TransactionsTable(date Date,account_no VARCHAR,expense_type VARCHAR,transaction_amount REAL,FOREIGN KEY(account_no)References Account(Account_no));");
        ;

        PersistentAccountDAO persistentAccountDAO=new PersistentAccountDAO(db);
        setAccountsDAO(persistentAccountDAO);

        PersistentTransactionDAO persistentTransactionDAO=new PersistentTransactionDAO(db);
        setTransactionsDAO(persistentTransactionDAO);


    }


}
