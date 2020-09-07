package models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("expenses")
public class Expense {
    @Id
    private String id;
    private String date;
    private String category;
    private int amount;
    private String description;

    public Expense() {
    }

    public Expense(String id, String date, String category, int amount, String description) {
        //super();
        this.id = id;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    //public String generateId() { return UUID.randomUUID().toString().replaceAll("-", "");	}

}
