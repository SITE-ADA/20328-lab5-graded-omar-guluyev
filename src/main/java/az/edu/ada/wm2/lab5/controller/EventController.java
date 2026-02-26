package az.edu.ada.wm2.lab5.controller;

import az.edu.ada.wm2.lab5.model.Event;
import az.edu.ada.wm2.lab5.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        try {
            return new ResponseEntity<>(eventService.createEvent(event), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        try {
            return new ResponseEntity<>(eventService.getAllEvents(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable UUID id) {
        try {
            return new ResponseEntity<>(eventService.getEventById(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        try {
            eventService.deleteEvent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // FULL UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable UUID id, @RequestBody Event event) {
        try {
            return new ResponseEntity<>(eventService.updateEvent(id, event), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // PARTIAL UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<Event> partialUpdateEvent(@PathVariable UUID id, @RequestBody Event partialEvent) {
        try {
            return new ResponseEntity<>(eventService.partialUpdateEvent(id, partialEvent), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // FILTER BY DATE
    @GetMapping("/filter/date")
    public ResponseEntity<List<Event>> filterByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        try {
            return new ResponseEntity<>(eventService.getEventsByDateRange(start, end), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // FILTER BY PRICE
    @GetMapping("/filter/price")
    public ResponseEntity<List<Event>> filterByPrice(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {

        try {
            return new ResponseEntity<>(eventService.getEventsByPriceRange(min, max), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
