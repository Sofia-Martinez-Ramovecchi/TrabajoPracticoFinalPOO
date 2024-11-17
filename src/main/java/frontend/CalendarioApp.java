package frontend;

import calendario.Calendario;
import calendario.Evento;
import calendario.Persona;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CalendarioApp {

    private JFrame frame;
    private JList<String> eventList;
    private DefaultListModel<String> eventListModel;
    private final List<Evento> eventos;
    private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private final Calendario calendario;

    public CalendarioApp() {
        calendario = new Calendario();
        eventos = calendario.cargarEventos();
        configurarInterfaz();
        cargarEventosEnLista();
    }

    private void configurarInterfaz() {
        frame = new JFrame("Calendario de Eventos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        eventListModel = new DefaultListModel<>();
        eventList = new JList<>(eventListModel);
        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        eventList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    mostrarDetalleEvento(eventList.getSelectedIndex());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(eventList);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();

        JButton botonEditar = new JButton("Editar Evento");
        JButton botonTodosEventos = new JButton("Todos los Eventos");

        botonEditar.addActionListener(e -> {
            int indexEditar = eventList.getSelectedIndex();
            if (indexEditar != -1) {
                Evento eventoSeleccionado = eventos.get(indexEditar);
                agregarEvento(eventoSeleccionado.getId());
            } else {
                JOptionPane.showMessageDialog(frame, "Selecciona un evento para editar.");
            }
        });

        botonTodosEventos.addActionListener(e -> mostrarTodosEventos());

        JButton addButton = new JButton("Agregar Evento");
        addButton.addActionListener(e -> agregarEvento(null));

        JButton cancelButton = new JButton("Cancelar Evento");
        cancelButton.addActionListener(e -> {
            int indexCancelar = eventList.getSelectedIndex();
            if (indexCancelar != -1) {
                Evento eventoSeleccionado = eventos.get(indexCancelar);
                calendario.cancelarEvento(eventos, eventoSeleccionado.getId());
                cargarEventosEnLista();
            } else {
                JOptionPane.showMessageDialog(frame, "Selecciona un evento para cancelar.");
            }
        });

        panelBotones.add(addButton);
        panelBotones.add(cancelButton);
        panelBotones.add(botonEditar);
        panelBotones.add(botonTodosEventos);

        frame.add(panelBotones, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void cargarEventosEnLista() {
        eventListModel.clear();
        eventos.stream()
                .sorted((e1, e2) -> e1.getFecha().compareTo(e2.getFecha()))
                .forEach(evento -> eventListModel.addElement(evento.getNombre() + " - " + formatoFecha.format(evento.getFecha())));
    }

    private void mostrarTodosEventos() {
        JFrame todosFrame = new JFrame("Todos los Eventos");
        todosFrame.setSize(300, 400);
        DefaultListModel<String> todosModel = new DefaultListModel<>();
        eventos.forEach(e -> todosModel.addElement(formatoFecha.format(e.getFecha()) + " - " + e.getDescripcion()));
        JList<String> todosList = new JList<>(todosModel);
        todosFrame.add(new JScrollPane(todosList));
        todosFrame.setVisible(true);
    }

    private void agregarEvento(String id) {
        Evento evento = null;
        if (id != null) {
            for (Evento unEvento : eventos) {
                if (unEvento.getId().equals(id)) {
                    evento = unEvento;
                    break;
                }
            }
        }

        String nombre = JOptionPane.showInputDialog("Nombre del evento:", (evento != null) ? evento.getNombre() : "");
        if (nombre == null || nombre.trim().isEmpty()) {
            return;
        }

        String fechaStr = JOptionPane.showInputDialog("Fecha del evento (dd/MM/yyyy HH:mm):", (evento != null) ? formatoFecha.format(evento.getFecha()) : "");
        Date fecha = null;
        try {
            fecha = formatoFecha.parse(fechaStr);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(frame, "Formato de fecha inválido.");
            return;
        }

        String ubicacion = JOptionPane.showInputDialog("Ubicación del evento:", (evento != null) ? evento.getUbicacion() : "");
        String descripcion = JOptionPane.showInputDialog("Descripción del evento:", (evento != null) ? evento.getDescripcion() : "");
        boolean esAudioVisual = JOptionPane.showConfirmDialog(frame, "¿Es un evento audiovisual?") == JOptionPane.YES_OPTION;
        boolean tieneCatering = JOptionPane.showConfirmDialog(frame, "¿Tiene catering?") == JOptionPane.YES_OPTION;

        if (evento == null) {
            evento = new Evento(ubicacion, nombre, descripcion, fecha, esAudioVisual, tieneCatering);
            int agregarInvitados = JOptionPane.showConfirmDialog(frame, "¿Deseas agregar invitados?");
            if (agregarInvitados == JOptionPane.YES_OPTION) {
                agregarInvitados(evento);
            }
            eventos.add(evento);
        } else {
            evento.setNombre(nombre);
            evento.setFecha(fecha);
            evento.setUbicacion(ubicacion);
            evento.setDescripcion(descripcion);
            evento.setEsAudioVisual(esAudioVisual);
            evento.setTieneCatering(tieneCatering);

            int modificarInvitados = JOptionPane.showConfirmDialog(frame, "¿Deseas modificar los invitados?");
            if (modificarInvitados == JOptionPane.YES_OPTION) {
                evento.getPersonasInvitadas().clear();
                agregarInvitados(evento);
            }
        }

        cargarEventosEnLista();
        calendario.guardarEventos(eventos);
    }

    private void agregarInvitados(Evento evento) {
        while (true) {
            String nombreInvitado = JOptionPane.showInputDialog("Nombre del invitado (deja vacío para terminar):");
            if (nombreInvitado == null || nombreInvitado.trim().isEmpty()) {
                break;
            }
            Persona persona = new Persona(nombreInvitado);
            evento.agregarInvitado(persona);
        }
    }


    private void mostrarDetalleEvento(int index) {
        if (index >= 0) {
            Evento evento = eventos.get(index);
            StringBuilder detalles = new StringBuilder("Detalles:\n");
            detalles.append("Nombre: ").append(evento.getNombre()).append("\n");
            detalles.append("Fecha: ").append(formatoFecha.format(evento.getFecha())).append("\n");
            detalles.append("Ubicación: ").append(evento.getUbicacion()).append("\n");
            detalles.append("Descripción: ").append(evento.getDescripcion()).append("\n");
            detalles.append("Audiovisual: ").append(evento.isEsAudioVisual() ? "Sí" : "No").append("\n");
            detalles.append("Catering: ").append(evento.isTieneCatering() ? "Sí" : "No").append("\n");
            detalles.append("Invitados: ");
            for (Persona persona : evento.getPersonasInvitadas()) {
                detalles.append(persona.getNombre()).append(", ");
            }
            JOptionPane.showMessageDialog(frame, detalles.toString());
        }
    }
}
