public class Task5Person {
    private String name;
    private String position;
    private String email;
    private String phone;
    private float salary;
    private int age;

    public Task5Person(String name, String position, String email, String phone, float salary, int age) {
        this.name = name;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public float getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("name=%s, position=%s, email=%s, phone=%s, salary=%s, age=%s", name, position, email, phone, salary, age);
    }
}
