package com.nerdysoft.pdpproject.service;

import com.nerdysoft.pdpproject.entity.Goal;
import com.nerdysoft.pdpproject.entity.SuccessCriteria;
import com.nerdysoft.pdpproject.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService {
    private final GoalRepository repository;
    private final SuccessCriteriaService successCriteriaService;

    @Autowired
    public GoalService(GoalRepository repository, SuccessCriteriaService successCriteriaService) {
        this.repository = repository;
        this.successCriteriaService = successCriteriaService;
    }

    public Goal createGoal(Goal goalData){
        return repository.save(goalData);
    }


    public Optional<Goal> findById(Long id){
        return repository.findById(id);
    }

    public List<Goal> findAll(){
        return repository.findAll();
    }

    @Transactional
    public Goal update(Long id, Goal goalData){
        Optional<Goal> maybeGoal = repository.findById(id);

        if (maybeGoal.isPresent()){
            Goal goal = maybeGoal.get();

            goal.setTitle(goalData.getTitle());
            goal.setDescription(goalData.getDescription());
            goal.setStartDate(goalData.getStartDate());
            goal.setPlanedEndDate(goalData.getPlanedEndDate());
            goal.setEndDate(goalData.getEndDate());
            goal.setGoalPriority(goalData.getGoalPriority());
            goal.setGoalStatus(goalData.getGoalStatus());
            goal.setComment(goalData.getComment());
            goal.setMark(goalData.getMark());

            return goal;
        }else
            return new Goal();
    }

    public void delete(Long id){
        repository.deleteById(id);
    }


    @Transactional
    public Goal addCriteriaToGoal(Long goalId, Long criteriaId){
        Optional<Goal> maybeGoal = findById(goalId);
        Optional<SuccessCriteria> maybeCriteria = successCriteriaService.findById(criteriaId);
        if (maybeGoal.isPresent() && maybeCriteria.isPresent()){
            Goal goal = maybeGoal.get();
            SuccessCriteria criteria = maybeCriteria.get();

            goal.addCriteria(criteria);

            return goal;
        }else
            return new Goal();
    }

    @Transactional
    public Goal removeCriteriaFromGoal(Long goalId, Long criteriaId){
        Optional<Goal> maybeGoal = findById(goalId);
        Optional<SuccessCriteria> maybeCriteria = successCriteriaService.findById(criteriaId);
        if (maybeGoal.isPresent() && maybeCriteria.isPresent()){
            Goal goal = maybeGoal.get();
            SuccessCriteria criteria = maybeCriteria.get();

            goal.removeCriteria(criteria);

            return goal;
        }else
            return new Goal();
    }
}
