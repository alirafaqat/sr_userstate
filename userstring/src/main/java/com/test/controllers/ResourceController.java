package com.test.controllers;

import static com.test.validator.TextValidator.validateSingleAlphaNumeric;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.entity.ResourceEntity;
import com.test.util.ResourceUtils;

@RestController
public class ResourceController {

	@Autowired
	ResourceUtils resourceUtils;

	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

	public ResourceController() {
		logger.info("* ***** *** *** *** ** Controller created");
	}

	@RequestMapping(value = "/chars", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String>  postState(@RequestParam(value = "character") String chars,
			@RequestParam(value = "amount") Integer amount, HttpServletRequest request, HttpServletResponse response) {
		ResourceEntity resourceEntity = getUserState(request);
		if(!validateSingleAlphaNumeric(chars) || resourceEntity.getData().length() + amount > 200) {
			return new ResponseEntity("BAD_REQUEST", HttpStatus.BAD_REQUEST);
		}
		resourceEntity.appendData(chars.charAt(0), amount);

		logger.info("userID: \"" + getUserId(request) + "\", added: \"" + chars + "\", " + amount 
				+ " times");
		return new ResponseEntity(resourceEntity, HttpStatus.OK);
	}

	@RequestMapping("/state")
	public String getState(HttpServletRequest request) {
		ResourceEntity resourceEntity = getUserState(request);
		logger.info("userID: \"" + getUserId(request) + ", viewed current statte");
		return resourceEntity.getData();
	}

	@RequestMapping("/sum")
	public Integer getSum(HttpServletRequest request) {
		ResourceEntity resourceEntity = getUserState(request);
		String data = resourceEntity.getData();
		logger.info("userID: \"" + getUserId(request) + ", requested sum.");
		return resourceUtils.addDigitsInString(data);
	}

	@RequestMapping("/chars")
	public String getChars(HttpServletRequest request) {
		ResourceEntity resourceEntity = getUserState(request);
		logger.info("userID: \"" + getUserId(request) + ", viewed charachters.");
		return resourceEntity.getData().replaceAll("\\d", "");
	}

	@RequestMapping(value = "/chars/{character}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteChars(@PathVariable("character") String chars, HttpServletRequest request) {
		if(!validateSingleAlphaNumeric(chars)) {
			return new ResponseEntity("BAD_REQUEST", HttpStatus.BAD_REQUEST);
		}

		ResourceEntity resourceEntity = getUserState(request);
		String data = resourceEntity.getData();
		resourceEntity.setData(resourceUtils.removeLastOccuranceOfChar(data, chars.charAt(0)));
		logger.info("userID: \"" + getUserId(request) + ", deleted: \"" + chars + "\"");
		return new ResponseEntity(resourceEntity.getData(), HttpStatus.OK);
	}

//	@RequestMapping("/error")
//    public String error(HttpServletResponse response) {
//        return "" + HttpServletResponse.SC_NOT_FOUND;
//    }
//
	private String getUserId(HttpServletRequest request) {
		return request.getSession().getId();
	}

	private ResourceEntity getUserState(HttpServletRequest request) {
		ResourceEntity entity = (ResourceEntity) request.getSession().getAttribute("resourceEntity");

		if (entity == null) {
			entity = new ResourceEntity();
			request.getSession().setAttribute("resourceEntity", entity);
		}

		return entity;
	}

}
