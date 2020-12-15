package Model;

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
        this.name = name;
        this.id = -1;
    }

    /**
     *
     * This constructor builds a Category class from a database load of values.
     * @params name - should be the name of the category.
     *
     * @params id - the id of the category that is read from the data base.
     */
    public Category(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Category={ " +
                "name='" + name + "'" +
                ", id=" + id +
                '}';
    }
}