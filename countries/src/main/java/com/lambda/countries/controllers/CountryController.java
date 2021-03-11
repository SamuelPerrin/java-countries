package com.lambda.countries.controllers;

import com.lambda.countries.models.Country;
import com.lambda.countries.repositories.CountryRepository;
//import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;

@RestController
public class CountryController {
    @Autowired
    CountryRepository corepo;

    private List<Country> filterCountries(List<Country> list, CheckCountry tester) {
        List<Country> filteredList = new ArrayList<>();
        for (Country c : list) {
            if (tester.test(c)) {
                filteredList.add(c);
            }
        }
        return filteredList;
    }

    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries() {
        List<Country> countries = new ArrayList<>();
        corepo.findAll()
                .iterator()
                .forEachRemaining(countries::add);
        countries.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> filterByFirstLetter(@PathVariable char letter) {
        List<Country> countries = new ArrayList<>();
        corepo.findAll()
                .iterator()
                .forEachRemaining(countries::add);
        List<Country> filteredList = filterCountries(countries, c -> c.getName().charAt(0) == letter);
        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }

    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> getTotalPop() {
        List<Country> countries = new ArrayList<>();
        corepo.findAll()
                .iterator()
                .forEachRemaining(countries::add);
        long total = 0;
        for (Country c : countries) {
            total += c.getPopulation();
        }
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> getMinPop() {
        List<Country> countries = new ArrayList<>();
        corepo.findAll()
                .iterator()
                .forEachRemaining(countries::add);
        int min = 1_000_000;
        for (Country c : countries) {
            if (c.getPopulation() < min) {
                min = (int) c.getPopulation();
            }
        }
        return new ResponseEntity<>(min, HttpStatus.OK);
    }

    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> getMaxPop() {
        List<Country> countries = new ArrayList<>();
        corepo.findAll()
                .iterator()
                .forEachRemaining(countries::add);
        int max = 1_000_000;
        for (Country c : countries) {
            if (c.getPopulation() > max) {
                max = (int) c.getPopulation();
            }
        }
        return new ResponseEntity<>(max, HttpStatus.OK);
    }
}
