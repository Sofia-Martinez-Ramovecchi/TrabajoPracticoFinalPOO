package calendario;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class Calendario {
    private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public void guardarEventos(List<Evento> eventos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("eventos.txt"))) {
            for (Evento evento : eventos) {
                String invitadosStr = evento.getPersonasInvitadas().isEmpty() ? ""
                        : String.join(",", evento.getPersonasInvitadas().stream()
                                .map(Persona::getNombre)
                                .toArray(String[]::new));

                writer.write(evento.getId() + ";" // Guardar el ID único
                        + evento.getNombre() + ";"
                        + formatoFecha.format(evento.getFecha()) + ";"
                        + evento.getUbicacion() + ";"
                        + evento.getDescripcion() + ";"
                        + evento.isEsAudioVisual() + ";"
                        + evento.isTieneCatering() + ";"
                        + invitadosStr);
                writer.newLine();
            }
        } catch (IOException e) {
            // Manejar el error en la lógica
            e.printStackTrace();
        }
    }

    public List<Evento> cargarEventos() {
        List<Evento> eventosCargados = new ArrayList<>();
        File archivo = new File("eventos.txt");
        if (!archivo.exists()) {
            return eventosCargados; // Devuelve lista vacía si no hay archivo
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length < 7) {
                    continue; // Salta líneas mal formateadas
                }
                String id = partes[0];
                String nombre = partes[1];
                Date fecha = formatoFecha.parse(partes[2]);
                String ubicacion = partes[3];
                String descripcion = partes[4];
                boolean esAudioVisual = Boolean.parseBoolean(partes[5]);
                boolean tieneCatering = Boolean.parseBoolean(partes[6]);

                Evento evento = new Evento(ubicacion, nombre, descripcion, fecha, esAudioVisual, tieneCatering);
                evento.setId(id); // Establecer el ID único

                if (partes.length > 7 && !partes[7].isEmpty()) {
                    String[] invitados = partes[7].split(",");
                    for (String nombreInvitado : invitados) {
                        if (!nombreInvitado.trim().isEmpty()) {
                            Persona persona = new Persona(nombreInvitado.trim());
                            evento.agregarInvitado(persona);
                        }
                    }
                }

                eventosCargados.add(evento);
            }
        } catch (IOException | ParseException e) {
            // Manejar el error en la lógica
            e.printStackTrace();
        }
        return eventosCargados;
    }

    public void cancelarEvento(List<Evento> eventos, String id) {
        Evento eventoACancelar = null;
        for (Evento evento : eventos) {
            if (evento.getId().equals(id)) {
                eventoACancelar = evento;
                break;
            }
        }

        if (eventoACancelar != null) {
            eventos.remove(eventoACancelar); // Elimina el evento de la lista
            guardarEventos(eventos); // Guarda la lista actualizada
            JOptionPane.showMessageDialog(null, "Evento cancelado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Evento no encontrado.");
        }
    }

}
