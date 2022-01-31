package com.nerdysoft.pdpproject.controller;

import com.nerdysoft.pdpproject.entity.SuccessCriteria;
import com.nerdysoft.pdpproject.entity.dto.SCDto;
import com.nerdysoft.pdpproject.service.SuccessCriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/criteria")
@Validated
public class SuccessCriteriaController {

    private final SuccessCriteriaService successCriteriaService;

    @Autowired
    public SuccessCriteriaController(SuccessCriteriaService successCriteriaService) {
        this.successCriteriaService = successCriteriaService;
    }

    @PostMapping("/add")
    public ResponseEntity<SCDto> createSuccessCriteria(@Valid @RequestBody SCDto successCriteriaDto){
        SuccessCriteria criteria = successCriteriaService.createCriteria(SuccessCriteria.from(successCriteriaDto));
        return new ResponseEntity<>(SCDto.from(criteria), OK);
    }

    @GetMapping
    public ResponseEntity<List<SCDto>> getCriteria(){
        List<SuccessCriteria> all = successCriteriaService.findAll();
        List<SCDto> dtos = all.stream().map(SCDto::from).collect(Collectors.toList());

        return new ResponseEntity<>(dtos, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SCDto> getOneCriteria(@PathVariable @Min(1) Long id){
        Optional<SuccessCriteria> maybeCriteria = successCriteriaService.findById(id);

        if (maybeCriteria.isPresent()){
            SuccessCriteria criteria = maybeCriteria.get();

            return new ResponseEntity<>(SCDto.from(criteria), OK);
        }else
            return new ResponseEntity<>(NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SCDto> updateCriteria(@PathVariable @Min(1) Long id, @Valid @RequestBody SCDto successCriteriaDto){
        SuccessCriteria criteria = successCriteriaService.update(id, SuccessCriteria.from(successCriteriaDto));

        if(Objects.nonNull(criteria.getId())){
            return new ResponseEntity<>(SCDto.from(criteria), HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SCDto> removeCriteria(@PathVariable @Min(1) Long id){
        try {
            successCriteriaService.delete(id);
            return new ResponseEntity<>(OK);
        }catch (Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }



}
