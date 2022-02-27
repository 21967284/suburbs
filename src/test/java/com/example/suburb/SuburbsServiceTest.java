package com.example.suburb;

import com.example.suburb.model.PostcodeRange;
import com.example.suburb.model.Suburb;
import com.example.suburb.model.SuburbsInformation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SuburbsServiceTest {

    @Mock
    SuburbRepository suburbRepository;

    @InjectMocks
    SuburbService suburbService;

    @Test
    public void testSaveSuburbs_whenGivenOneSuburbInList_thenSaveToRepository() {
        Suburb perth = Suburb.builder()
                .name("Perth")
                .postcode(6000)
                .build();

        when(suburbRepository.saveAllAndFlush(any(List.class))).thenReturn(Arrays.asList(perth));

        suburbService.saveSuburbs(Arrays.asList(perth));

        verify(suburbRepository).saveAllAndFlush(any(List.class));
    }

    @Test
    public void testSaveSuburbs_whenGivenMultipleSuburbsInList_thenSaveToRepository() {
        Suburb perth = Suburb.builder()
                .name("Perth")
                .postcode(6000)
                .build();
        Suburb sydney = Suburb.builder()
                .name("Sydney")
                .postcode(2000)
                .build();

        when(suburbRepository.saveAllAndFlush(any(List.class))).thenReturn(Arrays.asList(perth, sydney));

        suburbService.saveSuburbs(Arrays.asList(perth, sydney));

        verify(suburbRepository).saveAllAndFlush(any(List.class));
    }

    @Test()
    public void testRetrieveSuburb_whenGivenInvalidPostcodeRange_thenThrowException() {
        PostcodeRange postcodeRange = PostcodeRange.builder()
                .start(2002)
                .end(2000)
                .build();

        assertThrows(IllegalStateException.class, () -> {
            suburbService.retrieveSuburbs(postcodeRange);
        });
    }

    @Test()
    public void testRetrieveSuburb_whenGivenValidPostCodeRanges_thenReturnSummarisedInformation() {
        PostcodeRange postcodeRange = PostcodeRange.builder()
                .start(2000)
                .end(2001)
                .build();

        Suburb sydneyCbd = Suburb.builder()
                .name("Sydney CBD")
                .postcode(2000)
                .build();

        Suburb sydney = Suburb.builder()
                .name("Sydney")
                .postcode(2001)
                .build();

        when(suburbRepository.findAllByPostcode(2000)).thenReturn(
                Optional.of(Arrays.asList(sydneyCbd))
        );

        when(suburbRepository.findAllByPostcode(2001)).thenReturn(
                Optional.of(Arrays.asList(sydney))
        );

        SuburbsInformation result = suburbService.retrieveSuburbs(postcodeRange);

        SuburbsInformation expectedResult = SuburbsInformation.builder()
                .totalNoOfCharacters(16)
                .names(Arrays.asList("Sydney", "Sydney CBD"))
                .build();

        assertEquals(result, expectedResult);
    }

    @Test()
    public void testRetrieveSuburb_whenGivenValidPostCodeRangesWithTwoSuburbs_thenReturnSummarisedInformation() {
        PostcodeRange postcodeRange = PostcodeRange.builder()
                .start(2000)
                .end(2000)
                .build();

        Suburb sydneyCbd = Suburb.builder()
                .name("Sydney CBD")
                .postcode(2000)
                .build();

        Suburb barrangaroo = Suburb.builder()
                .name("Barangaroo")
                .postcode(2000)
                .build();

        when(suburbRepository.findAllByPostcode(2000)).thenReturn(
                Optional.of(Arrays.asList(sydneyCbd, barrangaroo))
        );

        SuburbsInformation result = suburbService.retrieveSuburbs(postcodeRange);

        SuburbsInformation expectedResult = SuburbsInformation.builder()
                .totalNoOfCharacters(20)
                .names(Arrays.asList("Barangaroo", "Sydney CBD"))
                .build();

        assertEquals(result, expectedResult);
    }
}
