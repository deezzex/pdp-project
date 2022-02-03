package com.nerdysoft.pdpproject.service;

import com.nerdysoft.pdpproject.entity.Goal;
import com.nerdysoft.pdpproject.entity.Plan;
import com.nerdysoft.pdpproject.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    private final PlanRepository repository;
    private final GoalService goalService;

    @Autowired
    public PlanService(PlanRepository planRepository, GoalService goalService) {
        this.repository = planRepository;
        this.goalService = goalService;
    }

    public Plan create(Plan planData){
        return repository.save(planData);
    }

    public Optional<Plan> findById(Long id){
        return repository.findById(id);
    }

    public List<Plan> findAll(){
        return repository.findAll();
    }

    @Transactional
    public Plan update(Long id, Plan planData){
        Optional<Plan> maybePlan = repository.findById(id);

        if (maybePlan.isPresent()){
            Plan plan = maybePlan.get();

            plan.setStartDate(planData.getStartDate());
            plan.setEndDate(planData.getEndDate());
            plan.setDescription(planData.getDescription());
            plan.setPlanStatus(planData.getPlanStatus());

            return plan;
        }else
            return new Plan();
    }

    public boolean delete(Long id){
        Optional<Plan> byId = repository.findById(id);

        if (byId.isPresent()){
            Plan plan = byId.get();
            repository.delete(plan);
        }
        Optional<Plan> removed = repository.findById(id);

        return removed.isEmpty();
    }

    @Transactional
    public Plan addGoalToPlan(Long planId, Long goalId){
        Optional<Plan> maybePlan = findById(planId);
        Optional<Goal> maybeGoal = goalService.findById(goalId);
        if (maybeGoal.isPresent() && maybePlan.isPresent()){
            Goal goal = maybeGoal.get();
            Plan plan = maybePlan.get();

            plan.addGoal(goal);

            return plan;
        }else
            return new Plan();
    }

    @Transactional
    public Plan removeGoalFromPlan(Long planId, Long goalId){
        Optional<Plan> maybePlan = findById(planId);
        Optional<Goal> maybeGoal = goalService.findById(goalId);
        if (maybeGoal.isPresent() && maybePlan.isPresent()){
            Goal goal = maybeGoal.get();
            Plan plan = maybePlan.get();

            plan.removeGoal(goal);

            return plan;
        }else
            return new Plan();
    }
}
