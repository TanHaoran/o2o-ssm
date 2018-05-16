package com.jerry.o2o.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jerry.o2o.entity.Area;
import com.jerry.o2o.service.AreaService;

@Controller
@RequestMapping("/superAdmin")
public class AreaController {

	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "/listArea", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listArea() {
		Map<String, Object> model = new HashMap<>();
		List<Area> list = new ArrayList<>();

		try {
			list = areaService.getAreaList();
			model.put("rows", list);
			model.put("total", list.size());
		} catch (Exception e) {
			e.printStackTrace();
			model.put("success", false);
			model.put("errMsg", e.toString());
		}

		return model;
	}

}
