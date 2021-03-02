package il.ac.shenkar.Model;

import java.util.Objects;

public class Category {

    private String name;
    private int id;

    /**
     *
     * This constructor builds a Category class from a user input.
     * @params name - should be the name of the category
     *
     */

    public Category(String name) {
        setName(name);
        setId(-1);
    }

    /**
     *
     * This constructor builds a Category class from a database load of values.
     * @params name - should be the name of the category.
     *
     * @params id - the id of the category that is read from the data base.
     */
    public Category(String name, int id){
        setName(name);
        setId(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id && Objects.equals(name, category.name);
    }


    @Override
    public String toString() {
        return name;
    }
}