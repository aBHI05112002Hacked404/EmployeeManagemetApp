package com.masai.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.masai.Dao.EmployeeRepo;
import com.masai.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeRepo Erepo;
	
	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return Erepo.findAll();
	}

	@Override
	public void saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		Erepo.save(employee);
	}

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> opt=Erepo.findById(id);
		
		Employee employee=null;
		
		if(opt.isPresent()) {
			employee=opt.get();
		}
		else {
			throw new RuntimeException("Employee not find: ");
		}
		return employee;
	}

	@Override
	public void deleteEmployeeById(long id) {
		// TODO Auto-generated method stub
		Erepo.deleteById(id);
	}

	@Override
	public Page<Employee> findPaginated(int pagNo, int pagSize, String sortField, String sortDirection) {
		// TODO Auto-generated method stub
		 Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
		 Sort.by(sortField).descending();
		Pageable pageable=PageRequest.of(pagNo-1, pagSize,sort);
		
		return Erepo.findAll(pageable);
	}

}
