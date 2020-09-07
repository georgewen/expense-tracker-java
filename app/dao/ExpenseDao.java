package dao;

import dev.morphia.query.*;
import models.Expense;
import org.bson.types.ObjectId;
import services.MongoService;

import javax.swing.text.html.Option;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;

public class ExpenseDao implements Dao<Expense> {

    @Override
    public Collection<Expense> getAll() {
        final Query<Expense> query = MongoService.datastore().createQuery(Expense.class);
        return query.asList();
    }

    @Override
    public Optional<Expense> get(String id) {
        return Optional.ofNullable(MongoService.datastore().createQuery(Expense.class).field("_id").equal(new ObjectId(id)).first());
    }

    @Override
    public void save(Expense expense) {
        MongoService.datastore().save(expense);
    }

    @Override
    public void update(String id, Expense expense) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Query<Expense> modifyQuery = MongoService.datastore().createQuery(Expense.class)
                .field("_id").equal(new ObjectId(id));
        UpdateOperations<Expense> updateOperations = MongoService.datastore().createUpdateOperations(Expense.class);
        for(Field f : Expense.class.getDeclaredFields()) {
            String fieldName = f.getName();
            if(fieldName.equals("id")) continue;
            Method m = Expense.class.getMethod("get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1));
            //check if value is null
            if(m.invoke(expense) == null) continue;

            updateOperations.set(fieldName,m.invoke(expense));
        }
        MongoService.datastore().update(modifyQuery, updateOperations);
    }

    @Override
    public void delete(String id) {
        Query<Expense> deleteQuery = MongoService.datastore().createQuery(Expense.class)
                .field("_id").equal(new ObjectId(id));
        MongoService.datastore().delete(deleteQuery);
    }
}
