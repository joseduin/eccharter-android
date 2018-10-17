package flight.report.ec.charter.modelo;

public class Customer {

    private String full_name;

    public Customer(String full_name) {
        this.full_name = full_name;
    }

    public Customer() {}

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
