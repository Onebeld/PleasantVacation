package com.onebeld.pleasantvacation.controller;

import com.onebeld.pleasantvacation.dto.TripDto;
import com.onebeld.pleasantvacation.entity.Trip;
import com.onebeld.pleasantvacation.repository.TripRepository;
import com.onebeld.pleasantvacation.service.impl.TripServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/** Контроллер, отвечающий за отображение веб-страницы с путевками */
@Controller
@RequestMapping("/hub")
public class HubController {
    private final TripServiceImpl tripService;

    /**
     * Создает экземпляр контроллера
     * @param tripRepository Репозиторий для работы с данными о путевках
     */
    HubController(TripRepository tripRepository) {
        this.tripService = new TripServiceImpl(tripRepository);
    }

    /**
     * Возвращает страницу, в которой отображаются путевки
     * @return Шаблон страницы с путевками
     */
    @RequestMapping
    public String hub() {
        return "hub";
    }

    /**
     * Получает список путевок
     * @param page Номер страницы
     * @param elementsInPage Количество элементов на одной странице
     * @return Список путевок
     */
    @GetMapping("/trips")
    @ResponseBody
    List<TripDto> getAllTrips(@RequestParam int page, @RequestParam int elementsInPage) {
        List<TripDto> trips = new ArrayList<>();

        Page<Trip> tripsPage = tripService.findAllTrips(PageRequest.of(page, elementsInPage));

        for (Trip trip : tripsPage) {
            trips.add(new TripDto(trip.getId(), trip.getName(), trip.getDescription(), trip.getCity(), trip.getCountry(), trip.getStartDate(), trip.getEndDate(), trip.getPrice(), trip.isAllInclusive()));
        }

        return trips;
    }
}
