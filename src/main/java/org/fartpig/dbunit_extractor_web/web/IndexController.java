package org.fartpig.dbunit_extractor_web.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fartpig.dbunit_extractor_web.consts.Constants;
import org.fartpig.dbunit_extractor_web.datatables.DataTablesRequest;
import org.fartpig.dbunit_extractor_web.datatables.DataTablesResponse;
import org.fartpig.dbunit_extractor_web.dto.DBConfigDTO;
import org.fartpig.dbunit_extractor_web.dto.SearchDTO;
import org.fartpig.dbunit_extractor_web.dto.TableSetDTO;
import org.fartpig.dbunit_extractor_web.model.DBAllConfig;
import org.fartpig.dbunit_extractor_web.model.DBConfig;
import org.fartpig.dbunit_extractor_web.model.DBType;
import org.fartpig.dbunit_extractor_web.service.DBConfigService;
import org.fartpig.dbunit_extractor_web.service.DataSetService;
import org.fartpig.dbunit_extractor_web.util.ResolverUtils;
import org.fartpig.dbunit_extractor_web.util.ViewUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class IndexController {

	@Autowired
	DataSetService dataSetService;

	@Autowired
	DBConfigService dbConfigService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	RequestMappingHandlerMapping requestMappingHandlerMapping;

	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	public String index(Model model, HttpServletRequest request) {
		return "index";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}

	@RequestMapping(value = "/list_configs", method = RequestMethod.GET)
	public String listConfigs(Model model) {
		Sort dbConfigOrder = ResolverUtils.createDBConfigSort();
		Pageable pageRequest = ResolverUtils.createPageable(0, Constants.PAGE_SIZE, dbConfigOrder);
		Page<DBConfig> pageResult = dbConfigService.getAllDBConfigForPage(pageRequest);
		model.addAttribute("result", pageResult.getContent());
		return "list_configs";
	}

	@ResponseBody
	@RequestMapping(value = "/ajax_list_configs", method = { RequestMethod.GET,
			RequestMethod.POST })
	public DataTablesResponse<List<String>> ajaxListConfigs(
			@RequestBody DataTablesRequest dataTablesRequest, HttpServletRequest request)
			throws UnsupportedEncodingException {

		Sort dbConfigOrder = ResolverUtils.createDBConfigSort();
		Pageable pageRequest = ResolverUtils.createPageableForOffset(
				dataTablesRequest.getDisplayStart(), dataTablesRequest.getDisplayLength(),
				dbConfigOrder);
		Page<DBConfig> pageResult = dbConfigService
				.getConfigStartWithName(dataTablesRequest.getSearchQuery(), pageRequest);

		DataTablesResponse<List<String>> dataTablesResponse = new DataTablesResponse<List<String>>();
		dataTablesResponse.setTotalDisplayRecords((int) pageResult.getTotalElements());
		dataTablesResponse.setTotalRecords((int) pageResult.getTotalElements());

		String contextPath = request.getContextPath();
		String editPath = String.format("%s/to_edit_config", contextPath);
		String removePath = String.format("%s/remove_config", contextPath);

		List<List<String>> data = new ArrayList<List<String>>();
		for (DBConfig aConfig : pageResult.getContent()) {
			String[] resultArray = new String[7];
			resultArray[0] = aConfig.getName();
			resultArray[1] = aConfig.getDbType();
			resultArray[2] = aConfig.getUrl();
			resultArray[3] = aConfig.getUser();
			resultArray[4] = aConfig.getPassword();
			resultArray[5] = aConfig.getDriverName();
			resultArray[6] = String.format(
					"<a href=\"%s?name=%s\"><span class=\"glyphicon glyphicon-edit\" aria-hidden=\"true\"></span></a>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"return confirm('"
							+ ViewUtils.getMessage(messageSource, "delete-ask", null)
							+ "?')\" href=\"%s?name=%s\"><span class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></span></a>",
					editPath, URLEncoder.encode(aConfig.getName(), "UTF-8"), removePath,
					URLEncoder.encode(aConfig.getName(), "UTF-8"));
			data.add(Arrays.asList(resultArray));
		}
		dataTablesResponse.setData(data);

		return dataTablesResponse;
	}

	@RequestMapping(value = "/to_add_config", method = RequestMethod.GET)
	public String toAddConfig(Model model) {
		model.addAttribute("dbTypes", DBType.values());
		return "add_config";
	}

	@RequestMapping(value = "/to_edit_config", method = RequestMethod.GET)
	public String toEditConfig(@RequestParam(value = "name", required = true) String name,
			Model model) {
		model.addAttribute("dbTypes", DBType.values());

		DBConfig dbConfig = dbConfigService.getConfigByName(name);

		DBConfigDTO dbConfigDto = new DBConfigDTO();
		BeanUtils.copyProperties(dbConfig, dbConfigDto);
		model.addAttribute("DBConfigDTO", dbConfigDto);
		return "edit_config";
	}

	@RequestMapping(value = "/add_config", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView addConfig(@ModelAttribute("DBConfigDTO") DBConfigDTO dbConfigDTO,
			Model model) {
		model.addAttribute("dbTypes", DBType.values());
		DBConfig dbConfig = new DBConfig();
		BeanUtils.copyProperties(dbConfigDTO, dbConfig);
		boolean result = dbConfigService.addDBConfig(dbConfig);
		if (result) {
			model.addAttribute("opResult", result);
			return new ModelAndView("redirect:/list_configs");
		} else {
			model.addAttribute("msg",
					ViewUtils.getMessage(messageSource, "add-db-config-fail", null));
			model.addAttribute("opResult", result);
			model.addAttribute("DBConfigDTO", dbConfigDTO);
			return new ModelAndView("add_config");
		}

	}

	@RequestMapping(value = "/test_config", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView testConfig(@ModelAttribute("DBConfigDTO") DBConfigDTO dbConfigDTO,
			Model model, @RequestParam("operation") String operation) {
		model.addAttribute("dbTypes", DBType.values());
		DBConfig dbConfig = new DBConfig();
		BeanUtils.copyProperties(dbConfigDTO, dbConfig);
		String msg = dbConfigService.testDBConfig(dbConfig);
		model.addAttribute("msg", msg);
		boolean isAdd = false;
		if ("add".equals(operation)) {
			isAdd = true;
		}
		if ("OK".equals(msg)) {
			model.addAttribute("opResult", true);
		} else {
			model.addAttribute("opResult", false);
		}

		if (isAdd) {
			return new ModelAndView("add_config");
		} else {
			return new ModelAndView("edit_config");
		}
	}

	@RequestMapping(value = "/edit_config", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView editConfig(@ModelAttribute("DBConfigDTO") DBConfigDTO dbConfigDTO,
			Model model) {
		model.addAttribute("dbTypes", DBType.values());
		DBConfig dbConfig = new DBConfig();
		BeanUtils.copyProperties(dbConfigDTO, dbConfig);
		boolean result = dbConfigService.updateDBConfig(dbConfig);
		if (result) {
			model.addAttribute("opResult", result);
			return new ModelAndView("redirect:/list_configs");
		} else {
			model.addAttribute("msg",
					ViewUtils.getMessage(messageSource, "edit-db-config-fail", null));
			model.addAttribute("opResult", result);
			model.addAttribute("DBConfigDTO", dbConfigDTO);
			return new ModelAndView("edit_config");
		}

	}

	@RequestMapping(value = "/remove_config", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView removeConfig(@RequestParam(value = "name", required = true) String name,
			Model model) {
		model.addAttribute("dbTypes", DBType.values());
		DBConfig dbConfig = new DBConfig();
		dbConfig.setName(name);
		boolean result = dbConfigService.removeDBConfig(dbConfig);
		model.addAttribute("opResult", result);
		return new ModelAndView("redirect:/list_configs");
	}

	@RequestMapping(value = "/list_dataset", method = RequestMethod.GET)
	public String listDataSet(Model model) {
		DBAllConfig allConfig = dbConfigService.getAllDBConfig();
		model.addAttribute("configs", allConfig.getConfigs());
		return "list_dataset";
	}

	@RequestMapping(value = "/search_dataset", method = { RequestMethod.GET, RequestMethod.POST })
	public String searchDataSet(@ModelAttribute("SearchDTO") SearchDTO searchDto, Model model) {
		DBAllConfig allConfig = dbConfigService.getAllDBConfig();
		model.addAttribute("configs", allConfig.getConfigs());

		try {
			TableSetDTO tableSetDto = dataSetService.findDataSet(searchDto);
			model.addAttribute("result", tableSetDto);
			model.addAttribute("searchDto", searchDto);
			if (tableSetDto.getTables().size() == 0) {
				model.addAttribute("opResult", false);
				model.addAttribute("msg",
						ViewUtils.getMessage(messageSource, "data-no-found", null));
			}
		} catch (Exception e) {
			model.addAttribute("searchDto", searchDto);
			model.addAttribute("opResult", false);
			model.addAttribute("msg", e.getMessage().replace("\n", "<br/>"));
		}
		return "list_dataset";
	}

	@RequestMapping(value = "/extract_dataset", method = RequestMethod.GET)
	public String extractDataSet(@ModelAttribute("SearchDTO") SearchDTO searchDto, Model model) {
		String result = dataSetService.extractDataSetBySearchDto(searchDto);
		model.addAttribute("result", ViewUtils.prettyPrintXml(result));
		model.addAttribute("searchDto", searchDto);
		return "dataset_xml";
	}

	@RequestMapping(value = "/extract_dataset_file", method = RequestMethod.GET)
	public void extractDataSetFile(@ModelAttribute("SearchDTO") SearchDTO searchDto, Model model,
			HttpServletResponse reponse) {
		try {
			reponse.setContentType("application/xml");
			reponse.setHeader("Content-Disposition", "attachment;filename=\"result.xml\"");
			dataSetService.extractDataSetBySearchDto(searchDto, reponse.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/change_session_language", method = RequestMethod.GET)
	public String changeSessionLanguage(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "lang") String lang) {
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		if ("zh".equals(lang)) {
			localeResolver.setLocale(request, response, new Locale("zh", "CN"));
		} else if ("en".equals(lang)) {
			localeResolver.setLocale(request, response, new Locale("en", "US"));
		}
		return "redirect:/";
	}

}
