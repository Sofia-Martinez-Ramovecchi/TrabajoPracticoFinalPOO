package calendario;

public class Salon {
    private String nombre;
    private int capacidad;
    private boolean esAudiovisual;
    private boolean tieneCatering;

    public Salon(String nombre, int capacidad, boolean esAudiovisual, boolean tieneCatering) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.esAudiovisual = esAudiovisual;
        this.tieneCatering = tieneCatering;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean esAudiovisual() {
        return esAudiovisual;
    }

    public void setEsAudiovisual(boolean esAudiovisual) {
        this.esAudiovisual = esAudiovisual;
    }

    public boolean tieneCatering() {
        return tieneCatering;
    }

    public void setTieneCatering(boolean tieneCatering) {
        this.tieneCatering = tieneCatering;
    }

    // Método para agregar un nuevo salón
    public static Salon agregarSalon(String nombre, int capacidad, boolean esAudiovisual, boolean tieneCatering) {
        return new Salon(nombre, capacidad, esAudiovisual, tieneCatering);
    }

    // Método para editar un salón existente
    public void editarSalon(String nombre, int capacidad, boolean esAudiovisual, boolean tieneCatering) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.esAudiovisual = esAudiovisual;
        this.tieneCatering = tieneCatering;
    }

    // Método para mostrar la información del salón
    public String mostrarSalon() {
        return "Nombre: " + nombre + ", Capacidad: " + capacidad + ", Audiovisual: " + esAudiovisual + ", Catering: " + tieneCatering;
    }
    
}