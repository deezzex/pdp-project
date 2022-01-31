package com.nerdysoft.pdpproject.controller;

import com.nerdysoft.pdpproject.entity.Goal;
import com.nerdysoft.pdpproject.entity.SuccessCriteria;
import com.nerdysoft.pdpproject.entity.dto.GoalDto;
import com.nerdysoft.pdpproject.entity.dto.SCDto;
import com.nerdysoft.pdpproject.service.GoalService;
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
@RequestMapping("/goal")
@Validated
public class GoalController {

    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/add")
    public ResponseEntity<GoalDto> createGoal(@Valid @RequestBody GoalDto goalDto){
        Goal goal = goalService.createGoal(Goal.from(goalDto));
        return new ResponseEntity<>(GoalDto.from(goal), OK);
    }

    @GetMapping
    public ResponseEntity<List<GoalDto>> getAllGoals(){
        List<Goal> goals = goalService.findAll();

        List<GoalDto> goalDtos = goals.stream().map(GoalDto::from).collect(Collectors.toList());

        return new ResponseEntity<>(goalDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalDto> getGoal(@PathVariable @Min(1) Long id){
        Optional<Goal> maybeGoal = goalService.findById(id);

        if (maybeGoal.isPresent()){
            Goal goal = maybeGoal.get();

            return new ResponseEntity<>(GoalDto.from(goal), OK);
        }else
            return new ResponseEntity<>(NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalDto> updateGoal(@PathVariable @Min(1) Long id, @RequestBody @Valid GoalDto goalDto){
        Goal goal = goalService.update(id, Goal.from(goalDto));

        if(Objects.nonNull(goal.getId())){
            return new ResponseEntity<>(GoalDto.from(goal), HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GoalDto> removeGoal(@PathVariable @Min(1) Long id){
        try {
            goalService.delete(id);
            return new ResponseEntity<>(OK);
        }catch (Exception e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{goalId}/add/{criteriaId}")
    public ResponseEntity<GoalDto> addCriteriaToGoal(@PathVariable @Min(1) Long goalId, @PathVariable @Min(1) Long criteriaId){
        Goal goal = goalService.addCriteriaToGoal(goalId, criteriaId);

        return new ResponseEntity<>(GoalDto.from(goal), OK);
    }

    @DeleteMapping("/{goalId}/delete/{criteriaId}")
    public ResponseEntity<GoalDto> deleteCriteriaFromGoal(@PathVariable @Min(1) Long goalId, @PathVariable @Min(1) Long criteriaId){
        Goal goal = goalService.removeCriteriaFromGoal(goalId, criteriaId);

        return new ResponseEntity<>(GoalDto.from(goal), OK);
    }
}
