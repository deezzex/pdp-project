package com.nerdysoft.pdpproject.controller;

import com.nerdysoft.pdpproject.entity.SuccessCriteria;
import com.nerdysoft.pdpproject.entity.dto.PlanDto;
import com.nerdysoft.pdpproject.entity.dto.SuccessCriteriaDto;
import com.nerdysoft.pdpproject.service.SuccessCriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/criteria")
public class SuccessCriteriaController {

    private final SuccessCriteriaService successCriteriaService;

    @Autowired
    public SuccessCriteriaController(SuccessCriteriaService successCriteriaService) {
        this.successCriteriaService = successCriteriaService;
    }

    @PostMapping("/add")
    public ResponseEntity<SuccessCriteriaDto> createSuccessCriteria(@RequestBody SuccessCriteriaDto successCriteriaDto){
        SuccessCriteria criteria = successCriteriaService.createCriteria(SuccessCriteria.from(successCriteriaDto));
        return new ResponseEntity<>(SuccessCriteriaDto.from(criteria), OK);
    }

    @GetMapping
    public ResponseEntity<List<SuccessCriteriaDto>> getCriteria(){
        List<SuccessCriteria> all = successCriteriaService.findAll();
        List<SuccessCriteriaDto> dtos = all.stream().map(SuccessCriteriaDto::from).collect(Collectors.toList());

        return new ResponseEntity<>(dtos, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessCriteriaDto> getOneCriteria(@PathVariable Long id){
        Optional<SuccessCriteria> maybeCriteria = successCriteriaService.findById(id);

        if (maybeCriteria.isPresent()){
            SuccessCriteria criteria = maybeCriteria.get();

            return new ResponseEntity<>(SuccessCriteriaDto.from(criteria), OK);
        }else
            return new ResponseEntity<>(NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessCriteriaDto> updateCriteria(@PathVariable Long id, @RequestBody SuccessCriteriaDto successCriteriaDto){
        SuccessCriteria criteria = successCriteriaService.update(id, SuccessCriteria.from(successCriteriaDto));

        if(Objects.nonNull(criteria.getId())){
            return new ResponseEntity<>(SuccessCriteriaDto.from(criteria), HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessCriteriaDto> removeCriteria(@PathVariable Long id){
        try {
            successCriteriaService.delete(id);
            return new ResponseEntity<>(OK);
        }catch (Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }



}
