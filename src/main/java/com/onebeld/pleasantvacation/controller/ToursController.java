package com.onebeld.pleasantvacation.controller;

import com.onebeld.pleasantvacation.dto.trip.TripDto;
import com.onebeld.pleasantvacation.entity.Trip;
import com.onebeld.pleasantvacation.repository.TripRepository;
import com.onebeld.pleasantvacation.service.impl.TripServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
 
@Controller
@RequestMapping("/tours")
public class ToursController {
    private final TripServiceImpl tripService;

    /**
     * Создает экземпляр контроллера
     * @param tripRepository Репозиторий для работы с данными о путевках
     */
    ToursController(TripRepository tripRepository) {
        this.tripService = new TripServiceImpl(tripRepository);
    }

    @RequestMapping
    public String hub() {
        return "tours";
    }

    @RequestMapping("/{id}")
    public String tour(@PathVariable String id, Model model) {
        Optional<Trip> trip = tripService.findById(Long.parseLong(id));

        if (trip.isPresent()) {
            TripDto tripDto = new TripDto(trip.get());

            model.addAttribute("trip", tripDto);

            return "trip";
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found");
        }
    }
}
