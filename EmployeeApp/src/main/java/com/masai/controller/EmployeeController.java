package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.masai.model.Employee;
import com.masai.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/")
	public String viewHomePage(Model model) {
		//model.addAttribute("listEmployees", employeeService.getAllEmployees());
	
	//	return findPaginated(1, model);
		
		return findPaginated(1, "firstname", "asc", model);
	}
	
	@GetMapping("/showNewEmployeeFrom")
	public String showNewEmployeeFrom(Model model) {
		
		Employee employee=new Employee();
		model.addAttribute("employee", employee);
	
		return "new_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee( @ModelAttribute("employee") Employee employee ) {
		
		employeeService.saveEmployee(employee);
		
		return "redirect:/";
	}
	@GetMapping("/showFromForUpdate/{id}")
	public String showFromForUpdate(@PathVariable(value = "id") long id,Model model) {
		
		Employee employee=employeeService.getEmployeeById(id);
		
		model.addAttribute("employee", employee);
		
		return "Update_employee";
		
	}
	@GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {

        // call delete employee method 
        this.employeeService.deleteEmployeeById(id);
        return "redirect:/";
    }
	
	@GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,  @RequestParam("sortField") String sortField,
    	    @RequestParam("sortDir") String sortDir, Model model) {

      int pageSize=5;
      
      Page<Employee> page= employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
      List<Employee> listEmployees=page.getContent();
      
      model.addAttribute("currentPage", pageNo);
      model.addAttribute("totalPages", page.getTotalPages());
      model.addAttribute("totalItems", page.getTotalElements());
      
      
      model.addAttribute("sortField", sortField);
      model.addAttribute("sortDir", sortDir);
      model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
      model.addAttribute("listEmployees", listEmployees);
      
      
      
      return "index";
      
      
    }
	
}
