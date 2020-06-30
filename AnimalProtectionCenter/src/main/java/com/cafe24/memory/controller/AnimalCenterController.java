package com.cafe24.memory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.memory.domain.AnimalCenter;
import com.cafe24.memory.domain.AnimalDisposal;
import com.cafe24.memory.domain.AnimalType;
import com.cafe24.memory.domain.ReportManger;
import com.cafe24.memory.domain.SearchReportAnimal;
import com.cafe24.memory.domain.Staff;
import com.cafe24.memory.service.AnimalCenterService;
import com.cafe24.memory.service.AnimalDisposalService;
import com.cafe24.memory.service.AnimalHealthService;
import com.cafe24.memory.service.StorageService;

@Controller
@RequestMapping("/animalcenter")
public class AnimalCenterController {
	
	@Autowired private AnimalHealthService animalHealthService;
	@Autowired private AnimalCenterService animalCenterService;
	@Autowired private AnimalDisposalService animalDisposalService;
	@Autowired private StorageService storageService;
	
	
	private final static Logger logger = 
			LoggerFactory.getLogger(SpringBootApplication.class);
	
	//animal center inset
	@GetMapping("/animalcenterinsert")
	public String insertAnimalCenterForm() {
		return "animalcenter/animalCenterInsert";
	}
	
	@PostMapping("/animalcenterinsert")
	public String insertAnimalCenter(Model model, AnimalType atype, Staff staff, AnimalCenter animal,SearchReportAnimal searchRe
			, @RequestParam("animalPicture2") MultipartFile file) {
		
		String reCode = searchRe.getSearchReportCode();
		ReportManger rm = new ReportManger();
		if( reCode != null || !"".equals(reCode)) {
			rm.setAcceptCode(animalCenterService.searchReportManager(reCode));
		}
		storageService.store(file);
		animal.setAnimalPicture(file.getOriginalFilename());
		animal.setReportManger(rm);
		animal.setStaff(staff);
		animal.setAnimalType(atype);
		animalCenterService.insertAnimalCenter(animal);
		return "redirect:/animalcenter/animalcenterlist";
	}
	
	//animal center list 
	@GetMapping("/animalcenterlist")
	public String listAnimalCenter(@RequestParam(name="send_type", required = false) String send_type ,Model model) {
		model.addAttribute("Cnt", animalCenterService.selectCenterCnt());
		Map<Integer, Object> spaceMap = new HashMap<Integer, Object>();
		Map<Integer, Object> disposalMap = new HashMap<Integer, Object>();
		List<String> space = null;
		List<String> disposal = null;
		List<AnimalCenter> AClist = null;
		if(send_type != null && !"".equals(send_type)) {
			AClist = animalCenterService.selectAnimalCenter(send_type);
			space = animalCenterService.selectProtectAnimalCenter(send_type);
			disposal = animalCenterService.selectDisposalAnimalCenter(send_type);
		}else {
			AClist = animalCenterService.selectAnimalCenter();
			space = animalCenterService.selectProtectAnimalCenter();
			disposal = animalCenterService.selectDisposalAnimalCenter();
		}
		for(int i = 0; i < space.size(); i++) {
			spaceMap.put(i, space.get(i));
		}
		for(int i = 0; i < disposal.size(); i++) {
			disposalMap.put(i, disposal.get(i));
		}
		model.addAttribute("AClist", AClist);
		model.addAttribute("proNum", spaceMap);
		model.addAttribute("disResult", disposalMap);
		
		return "animalcenter/animalCenterList";
	}
	
	//animal center update
	@GetMapping("/animalcenterupdate")
	public String updateAnimalCenterForm(
			@RequestParam(name="send_code", required = false) String send_code, Model model) {
		AnimalCenter ac = animalCenterService.selectCenterAnimal(send_code);
		model.addAttribute("searchReportCode",animalCenterService.getsearchReportCode(send_code));
		model.addAttribute("ac", ac);
		model.addAttribute("aniPicture", "/files/" + ac.getAnimalPicture());
		return "animalcenter/animalCenterUpdate";
	}
	
	@PostMapping("/animalcenterupdate")
	public String updateAnimalCenter(AnimalType atype, AnimalCenter animal,SearchReportAnimal searchRe
			, @RequestParam("animalPicture2") MultipartFile file) {
		ReportManger rm = new ReportManger();
		if(searchRe != null) {
			rm.setAcceptCode(animalCenterService.searchReportManager(searchRe.getSearchReportCode()));
		}
		if(file != null && !"".equals(file.getOriginalFilename())) {
			storageService.deleteFile(animal.getAnimalPicture());
			storageService.store(file);
			animal.setAnimalPicture(file.getOriginalFilename());
		}
		animal.setReportManger(rm);
		animal.setAnimalType(atype);
		animalCenterService.updateAnimalCenter(animal);
		
		return "redirect:/animalcenter/animalcenterlist";
	}
	
	//animal center delete
	@GetMapping("/animalcenterdelete")
	public String deleteAnimalCenter(@RequestParam(name="send_code", required = false) String send_code
			,@RequestParam(name="send_pic", required = false) String send_pic) {
		try {
			animalCenterService.deleteAnimalCenter(send_code);
			storageService.deleteFile(send_pic);
		} catch (Exception e) {
			System.out.println("센터 동물 삭제 실패");
		}
		return "redirect:/animalcenter/animalcenterlist";
	}
	
	//animal page
	@GetMapping("/animalcenterpage")
	public String pageAnimalCenter(
			@RequestParam(name="send_code", required = false) String send_code, Model model) {
		AnimalCenter ac = animalCenterService.selectCenterAnimal(send_code);
		model.addAttribute("aInfo", ac);
		model.addAttribute("sInfo",animalCenterService.selectReportCodeAnimal(send_code));
		model.addAttribute("hlist", animalHealthService.selectAnimalHealthInfo(send_code));
		model.addAttribute("dlist", animalDisposalService.selectDisposal(send_code));
		model.addAttribute("aniPicture", "/files/" + ac.getAnimalPicture());
		//데이트 포맷
		/*
		 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 * model.addAttribute("adopteeDate",
		 * format.format(ad.getAdoptee().getAdopteeDate()));
		 * model.addAttribute("returnDate",
		 * format.format(ad.getCenterReturn().getCenterReturnDate()));
		 */
		return "animalcenter/animalCenterPage";
	}
	
}
