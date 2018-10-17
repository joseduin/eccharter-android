package flight.report.ec.charter.modelo;

public class Aircraft {

    private String tail;

    public Aircraft(String tail) {
        this.tail = tail;
    }

    public Aircraft() { }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

}
