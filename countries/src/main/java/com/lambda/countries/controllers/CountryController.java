package com.lambda.countries.controllers;

import com.lambda.countries.models.Country;
import com.lambda.countries.repositories.CountryRepository;
//import org.apache.coyote.Response;
import org.apache.coyote.Response;
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
    CountryRepository countryRepository;

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
        countryRepository.findAll()
                .iterator()
                .forEachRemaining(countries::add);
//        System.out.println("Running listAllCountries");
        countries.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> filterByFirstLetter(@PathVariable char letter) {
        List<Country> countries = new ArrayList<>();
        countryRepository.findAll()
                .iterator()
                .forEachRemaining(countries::add);
        List<Country> filteredList = filterCountries(countries, c -> c.getName().charAt(0) == letter);
        System.out.println("Running filterByFirstLetter with " + letter);
        filteredList.sort((c1,c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(filteredList, HttpStatus.OK);
    }

    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> getTotalPop() {
        List<Country> countries = new ArrayList<>();
        countryRepository.findAll()
                .iterator()
                .forEachRemaining(countries::add);
        long total = 0;
        for (Country c : countries) {
            total += c.getPopulation();
        }
        System.out.println("The Total Population is " + total);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> getMinPop() {
        List<Country> countries = new ArrayList<>();
        countryRepository.findAll()
                .iterator()
                .forEachRemaining(countries::add);
        int min = 1_000_000;
        Country result = countries.get(0);
        for (Country c : countries) {
            if (c.getPopulation() < result.getPopulation()) {
                min = (int) c.getPopulation();
                result = c;
            }
        }
        System.out.println("Running getMinPop with " + min);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> getMaxPop() {
        List<Country> countries = new ArrayList<>();
        countryRepository.findAll()
                .iterator()
                .forEachRemaining(countries::add);
        int max = 1_000_000;
        Country result = countries.get(0);
        for (Country c : countries) {
            if (c.getPopulation() > max) {
                max = (int) c.getPopulation();
                result = c;
            }
        }
        System.out.println("Running getMaxPop with " + max);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/population/median", produces={"application/json"})
    public ResponseEntity<?> getMedianPop() {
        List<Country> countries = new ArrayList<>();
        countryRepository.findAll()
                .iterator()
                .forEachRemaining(countries::add);
        countries.sort((c1, c2) -> (int) (c1.getPopulation() - c2.getPopulation()));
        int index = countries.size() / 2;
        return new ResponseEntity<>(countries.get(index), HttpStatus.OK);
    }
}
