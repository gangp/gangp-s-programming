package com.cafe24.memory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/addtional")
public class AdditionalCostController {

	@GetMapping("/addtionalCostUpdate")
	public String addtionalCostUpdate() {
		
		return "addtionalcost/additionCostUpdate";
	}
	
	@PostMapping("/addtionalCostList")
	public String addtionalCost(Model model) {
		
		return "addtionalcost/additionalCostList";
	}
	
	@GetMapping("/addtionalCostList")
	public String addtionalCost() {
		
		return "addtionalcost/additionalCostList";
	}
	
	@GetMapping("/addtionalCostInsert")
	public String addtionalCostInsert() {
		
		return "addtionalcost/additionCostInsert";
	}
}
