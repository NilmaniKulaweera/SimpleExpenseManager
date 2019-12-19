package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import java.util.ArrayList;
import java.util.List;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO implements AccountDAO{
    private SQLiteDatabase database;

    public PersistentAccountDAO(SQLiteDatabase database){

        this.database=database;

    }
    @Override
    public List<String> getAccountNumbersList() {

        Cursor cursor=database.rawQuery("SELECT account_no FROM Account",null);


        ArrayList<String> resultSet = new ArrayList<String>();
        if(cursor.moveToFirst()) {
            do{
                resultSet.add(cursor.getString(cursor.getColumnIndex("account_no")));
            }while (cursor.moveToNext());
        }
        cursor.close();

        return resultSet;

    }

    @Override
    public List<Account> getAccountsList() {

        Cursor cursor=database.rawQuery("SELECT * FROM Account",null);

        ArrayList<Account> AccountList=new ArrayList<Account>();
        if(cursor.moveToFirst()) {
            do {

                Account account = new Account(cursor.getString(cursor.getColumnIndex("account_no")),
                        cursor.getString(cursor.getColumnIndex("bank_name")),
                        cursor.getString(cursor.getColumnIndex("account_holder_name")),
                        cursor.getDouble(cursor.getColumnIndex("balance")));

                AccountList.add(account);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return AccountList;




    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Cursor cursor=database.rawQuery("SELECT * FROM Account WHERE account_no="+accountNo,null);
        cursor.moveToFirst();
        Account account=null;
        if(cursor.moveToFirst()) {
            account = new Account(cursor.getString(cursor.getColumnIndex("account_no")),
                    cursor.getString(cursor.getColumnIndex("bank_name")),
                    cursor.getString(cursor.getColumnIndex("account_holder_name")),
                    cursor.getDouble(cursor.getColumnIndex("balance")));
        }
        else{

            throw new InvalidAccountException("No such account");
        }
        cursor.close();

        return account;
    }

    @Override
    public void addAccount(Account account) {
        String query="INSERT INTO Account(account_no,bank_name,account_holder_name,balance) VALUES(?,?,?,?)";
        SQLiteStatement stat=database.compileStatement(query);
        stat.bindString(1,account.getAccountNo());
        stat.bindString(2,account.getBankName());
        stat.bindString(3,account.getAccountHolderName());
        stat.bindDouble(4,account.getBalance());
        stat.executeInsert();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        String query="DELETE FROM Account where account_no= ?";
        SQLiteStatement stat=database.compileStatement(query);
        stat.bindString(1,accountNo);
        stat.executeUpdateDelete();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        String query="UPDATE Account SET balance=balance +? WHERE account_no=?";
        SQLiteStatement stat=database.compileStatement(query);
        if(expenseType==ExpenseType.EXPENSE){
            stat.bindDouble(1,-amount);
        }
        else{
            stat.bindDouble(1,+amount);
        }
        stat.bindString(2,accountNo);
        stat.executeUpdateDelete();

    }
}
