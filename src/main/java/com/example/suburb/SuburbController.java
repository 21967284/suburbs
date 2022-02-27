package com.example.suburb;

import com.example.suburb.model.PostcodeRange;
import com.example.suburb.model.Suburb;
import com.example.suburb.model.SuburbsInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/suburb")
public class SuburbController {

    private final SuburbService suburbsService;

    @Autowired
    public SuburbController(SuburbService suburbsService) {
        this.suburbsService = suburbsService;
    }

    @PostMapping("/save")
    public List<Suburb> saveSuburbs(@RequestBody List<Suburb> suburbs) {
        return suburbsService.saveSuburbs(suburbs);
    }

    @PostMapping(
            value = "/retrieve",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public SuburbsInformation retrieveSuburbsInformation(@RequestBody PostcodeRange postcodeRange) {
        return suburbsService.retrieveSuburbs(postcodeRange);
    }
}
