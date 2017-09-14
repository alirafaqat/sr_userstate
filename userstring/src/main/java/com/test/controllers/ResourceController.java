package com.test.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.entity.ResourceEntity;
import com.test.util.ResourceUtils;

@RestController
public class ResourceController {
	
	@Autowired
	ResourceUtils resourceUtils;
	
	private ResourceEntity resourceEntity = new ResourceEntity("asdf1lhjklj34h5kj23456k23jhn4k5j2nb");

	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
	public ResourceController() {
		logger.info("* ***** *** *** *** ** Controller created");
	}

	@RequestMapping(value = "/chars", method = RequestMethod.POST)
	public ResourceEntity postState(@RequestParam(value="character") Character chars, @RequestParam(value="amount") Integer amount) {
		resourceEntity.appendData(chars, amount);
		return resourceEntity;
	}

	@RequestMapping("/state")
	public String getState() {
		return resourceEntity.getData();
	}

	@RequestMapping("/sum")
	public Integer getSum() {
		String data = resourceEntity.getData();
		return resourceUtils.addDigitsInString(data);
	}

	@RequestMapping("/chars")
	public String getChars() {
		return resourceEntity.getData().replaceAll("\\d", "");
	}
	
	@RequestMapping(value = "/chars/{character}", method = RequestMethod.DELETE)
	public ResourceEntity deleteChars(@RequestParam(value="character") Character chars) {
		String data = resourceEntity.getData();
		resourceEntity.setData(resourceUtils.removeLastOccuranceOfChar(data, chars));
		;
		return resourceEntity;
	}

}
