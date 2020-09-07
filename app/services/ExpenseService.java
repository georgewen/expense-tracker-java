package services;

import dao.Dao;
import dao.ExpenseDao;
import models.Expense;

import com.google.inject.Inject;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.NoSuchElementException;

public class ExpenseService {
    private final Dao<Expense> expenseDao;

    @Inject
    public ExpenseService(ExpenseDao expenseDao){
        this.expenseDao = expenseDao;
    }
    public List<Expense> getAll() {
        return (List<Expense>) expenseDao.getAll();
    }
    public Expense get(String id) {
        return expenseDao.get(id).orElseThrow(NoSuchElementException::new);
    }

    public void save(Expense expense) {
        expenseDao.save(expense);
    }

    public void update(String id, Expense expense) throws  NoSuchElementException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        expenseDao.update(id,expense);
    }

    public void delete(String id) { expenseDao.delete(id);}

}
