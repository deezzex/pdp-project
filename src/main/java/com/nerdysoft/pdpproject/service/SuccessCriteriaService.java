package com.nerdysoft.pdpproject.service;

import com.nerdysoft.pdpproject.entity.SuccessCriteria;
import com.nerdysoft.pdpproject.repository.SuccessCriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SuccessCriteriaService {

    private final SuccessCriteriaRepository repository;

    @Autowired
    public SuccessCriteriaService(SuccessCriteriaRepository repository) {
        this.repository = repository;
    }

    public SuccessCriteria createCriteria(SuccessCriteria successCriteriaData){
        return repository.save(successCriteriaData);
    }

    public Optional<SuccessCriteria> findById(Long id){
        return repository.findById(id);
    }

    public List<SuccessCriteria> findAll(){
        return repository.findAll();
    }

    @Transactional
    public SuccessCriteria update(Long id, SuccessCriteria successCriteriaData){
        Optional<SuccessCriteria> maybeCriteria = repository.findById(id);

        if (maybeCriteria.isPresent()){
            SuccessCriteria successCriteria = maybeCriteria.get();

            successCriteria.setTitle(successCriteriaData.getTitle());
            successCriteria.setDescription(successCriteriaData.getDescription());
            successCriteria.setCriteriaStatus(successCriteriaData.getCriteriaStatus());

            repository.save(successCriteria);

            return successCriteria;
        }else
            return new SuccessCriteria();

    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
