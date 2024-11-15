/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;


import repositorios.SolicitudRepository;
import servicios.TipoCambioClient;
import entidades.Solicitud;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tipo-cambio")
public class TipoCambioController {

    private final SolicitudRepository solicitudRepository;
    private final TipoCambioClient tipoCambioClient;

    public TipoCambioController(SolicitudRepository solicitudRepository, TipoCambioClient tipoCambioClient) {
        this.solicitudRepository = solicitudRepository;
        this.tipoCambioClient = tipoCambioClient;
    }

    @GetMapping("/ultimo")
    public Solicitud obtenerUltimaSolicitud() {
        return solicitudRepository.findTopByOrderByFechaSolicitudDesc();
    }

    @GetMapping("/historial")
    public List<Solicitud> obtenerHistorial() {
        return solicitudRepository.findAll();
    }

    @GetMapping("/consultar")
    public Solicitud realizarSolicitud() {
        String respuesta = tipoCambioClient.obtenerTipoCambio();
        Solicitud nuevaSolicitud = new Solicitud();
        nuevaSolicitud.setNumeroSolicitud(UUID.randomUUID().toString());
        nuevaSolicitud.setRespuesta(respuesta);
        nuevaSolicitud.setFechaSolicitud(LocalDateTime.now());
        solicitudRepository.save(nuevaSolicitud);
        return nuevaSolicitud;
    }
}
