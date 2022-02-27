package com.example.suburb;

import com.example.suburb.model.PostcodeRange;
import com.example.suburb.model.Suburb;
import com.example.suburb.model.SuburbsInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuburbService {

    private final SuburbRepository suburbsRepository;

    @Autowired
    public SuburbService(SuburbRepository suburbsRepository) {
        this.suburbsRepository = suburbsRepository;
    }

    public List<Suburb> saveSuburbs(List<Suburb> suburbs) {
        return suburbsRepository.saveAllAndFlush(suburbs);
    }

    public SuburbsInformation retrieveSuburbs(PostcodeRange postcodeRange) {
        int startPostCode = postcodeRange.getStart();
        int endPostCode = postcodeRange.getEnd();

        List<Suburb> suburbsList = new ArrayList<>();
        int totalNoOfCharacters = 0;

        if (startPostCode > endPostCode) {
            throw new IllegalStateException("Start post code is greater than end post code");
        }

        for (int postCode = startPostCode; postCode <= endPostCode; postCode++) {
            Optional<List<Suburb>> suburbs = suburbsRepository.findAllByPostcode(postCode);

            if (suburbs.isPresent()) {
                for (Suburb suburb : suburbs.get()) {
                    suburbsList.add(suburb);
                }
            }
        }

        List<String> sortedSuburbsList = suburbsList.stream()
                .sorted(Comparator.comparing(Suburb::getName))
                .map(Suburb::getName)
                .collect(Collectors.toList());

        totalNoOfCharacters = suburbsList.stream()
                .reduce(0, (subtotal, suburb) -> subtotal + suburb.getName().length(), Integer::sum);

        return SuburbsInformation.builder()
                .names(sortedSuburbsList)
                .totalNoOfCharacters(totalNoOfCharacters)
                .build();
    }
}
