package il.ac.shenkar.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CostItem {

    private int id;
    private Category category;
    private double amount;
    private Currency currency;
    private String description;
    private LocalDate date;


    /**
     *
     * This constructor builds a CostItem object from input.
     * @params description - A short text describing the cost reason of spending.
     *
     * @params  amount - The total amount of the cost
     *
     * @params currency - The currency that was being used during the purchase time.
     *
     * @params Category - An instantiation  of an object that represents the category that
     *                    the Cost belonged to.
     */
    public CostItem(Category category, double amount, Currency currency,  String description,String date) throws CostManagerException{

        if(amount < 0)
            throw new CostManagerException("Not a valid amount");
        setDate(createDateFromString(date));
        setDescription(description);
        setAmount(amount);
        setCurrency(currency);
        setId(-1);
        setCategory(category);
    }

    /**
     *
     * This constructor builds a CostItem object from a database load.
     * @params description - A short text describing the cost reason of spending.
     *
     * @params  amount - The total amount of the cost
     *
     * @params currency - The currency that was being used during the purchase time.
     *
     * @params Category - An instantiation  of an object that represents the category that
     *                    the Cost belonged to.
     *
     * @params id - A variable to hold the Cost id from DB.
     */

    public CostItem(int id, Category category, double amount, Currency currency,  String description,String date) throws CostManagerException {

        if(amount < 0)
            throw new CostManagerException("Not a valid amount");
        setDate(createDateFromString(date));
        setDescription(description);
        setAmount(amount);
        setCurrency(currency);
        setId(id);
        setCategory(category);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     *
     * All get function to class members.
     */



    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {return date;}

    private LocalDate createDateFromString(String dateTime){

        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Parsing the date
        LocalDate date = LocalDate.parse(dateTime, inputFormat);

        return date;
    }

    /**
     * Over riding class object methods.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CostItem costItem = (CostItem) o;
        return id == costItem.id &&
                Double.compare(costItem.amount, amount) == 0 &&
                category.equals(costItem.category) &&
                currency == costItem.currency &&
                description.equals(costItem.description) &&
                date.equals(costItem.date);
    }


    @Override
    public String toString() {
        return category.toString() + " with amount of " + amount ;
    }
}