public class Player {
    private String name;
    private int rating;
    private int age;

    public Player(String name, int rating, int age) {
        this.name = name;
        this.rating = rating;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Player " + name + " with rating = " + rating + " and age is " + age;
    }
}


