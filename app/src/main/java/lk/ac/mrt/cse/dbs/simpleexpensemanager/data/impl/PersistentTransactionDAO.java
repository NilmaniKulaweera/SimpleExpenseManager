package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;



public class PersistentTransactionDAO implements TransactionDAO {
    private SQLiteDatabase database;
    public PersistentTransactionDAO(SQLiteDatabase database){
        this.database=database;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {

        ContentValues values= new ContentValues();
        values.put("date",date.getTime());
        values.put("account_no",accountNo);
        values.put("expense_type",(expenseType == ExpenseType.EXPENSE) ? 0 : 1);
        values.put("transaction_amount",amount);
        database.insert("TransactionsTable",null,values);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        Cursor cursor = database.rawQuery("SELECT * FROM TransactionsTable",null);

        ArrayList<Transaction> resultSet = new ArrayList<Transaction>();
        if(cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction(new Date(cursor.getLong(cursor.getColumnIndex("date"))),
                        cursor.getString(cursor.getColumnIndex("account_no")),

                        (cursor.getInt(cursor.getColumnIndex("expense_type")) == 0) ? ExpenseType.EXPENSE : ExpenseType.INCOME,
                        cursor.getDouble(cursor.getColumnIndex("transaction_amount")));
                resultSet.add(transaction);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return resultSet;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        Cursor cursor = database.rawQuery("SELECT * FROM TransactionsTable LIMIT " + limit,null);

        ArrayList<Transaction> resultSet = new ArrayList<Transaction>();
        if(cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction(new Date(cursor.getLong(cursor.getColumnIndex("date"))),
                        cursor.getString(cursor.getColumnIndex("account_no")),
                        (cursor.getInt(cursor.getColumnIndex("expense_type")) == 0) ? ExpenseType.EXPENSE : ExpenseType.INCOME,
                        cursor.getDouble(cursor.getColumnIndex("transaction_amount")));
                resultSet.add(transaction);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return resultSet;
    }
}