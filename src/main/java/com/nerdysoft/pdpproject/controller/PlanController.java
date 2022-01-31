package com.nerdysoft.pdpproject.controller;

import com.nerdysoft.pdpproject.entity.Goal;
import com.nerdysoft.pdpproject.entity.Plan;
import com.nerdysoft.pdpproject.entity.dto.GoalDto;
import com.nerdysoft.pdpproject.entity.dto.PlanDto;
import com.nerdysoft.pdpproject.service.PlanService;
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

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/plan")
@Validated
public class PlanController {

    private final PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping("/add")
    public ResponseEntity<PlanDto> createPlan(@Valid @RequestBody PlanDto planDto){
        Plan plan = planService.create(Plan.from(planDto));
        return new ResponseEntity<>(PlanDto.from(plan), OK);
    }

    @GetMapping
    public ResponseEntity<List<PlanDto>> getAllPlans(){
        List<Plan> plans = planService.findAll();

        List<PlanDto> planDtos = plans.stream().map(PlanDto::from).collect(Collectors.toList());

        return new ResponseEntity<>(planDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanDto> getPlan(@PathVariable @Min(1) Long id){
        Optional<Plan> maybePlan = planService.findById(id);

        if (maybePlan.isPresent()){
            Plan plan = maybePlan.get();

            return new ResponseEntity<>(PlanDto.from(plan), OK);
        }else
            return new ResponseEntity<>(NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanDto> updatePlan(@PathVariable @Min(1) Long id, @Valid @RequestBody PlanDto planDto){
        Plan plan = planService.update(id, Plan.from(planDto));

        if(Objects.nonNull(plan.getId())){
            return new ResponseEntity<>(PlanDto.from(plan), HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Plan> removePlan(@PathVariable @Min(1) Long id){
        try{
            planService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{planId}/add/{goalId}")
    public ResponseEntity<PlanDto> addCriteriaToGoal(@PathVariable @Min(1) Long planId, @PathVariable @Min(1) Long goalId){
        Plan plan = planService.addGoalToPlan(planId, goalId);

        return new ResponseEntity<>(PlanDto.from(plan), OK);
    }

    @DeleteMapping("/{planId}/delete/{goalId}")
    public ResponseEntity<PlanDto> deleteCriteriaFromGoal(@PathVariable @Min(1) Long planId, @PathVariable @Min(1) Long goalId){
        Plan plan = planService.removeCriteriaFromGoal(planId, goalId);

        return new ResponseEntity<>(PlanDto.from(plan), OK);
    }
}
