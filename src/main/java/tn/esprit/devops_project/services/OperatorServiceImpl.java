package tn.esprit.devops_project.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.exceptions.OperatorNotFoundException;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.services.Iservices.IOperatorService;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OperatorServiceImpl implements IOperatorService {

	OperatorRepository operatorRepository;
	@Override
	public List<Operator> retrieveAllOperators() {
		return (List<Operator>) operatorRepository.findAll();
	}

	@Override
	public Operator addOperator(Operator operator) {
		return operatorRepository.save(operator);
	}

	@Override
	public void deleteOperator(Long id) {
		operatorRepository.deleteById(id);
		
	}
	@Override

	public Operator retrieveOperator(Long idOperateur) {
		return operatorRepository.findById(idOperateur)
				.orElseThrow(() -> new OperatorNotFoundException("Operator not found"));
	}

	@Override
	public Operator updateOperator(Operator operator) {
		return operatorRepository.save(operator);
	}



}
