package com.insigma.mvc.controller.demo;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.DemoAc01;
import com.insigma.mvc.service.demo.DemoAc01Service;


/**
 * demo测试程序
 * @author wengsh
 *
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends MvcHelper<DemoAc01> {
	
	
	@Resource
	private DemoAc01Service demoAc01Service;

	
	/**
	 * 获取人员信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAc01List")
	@ResponseBody
	public AjaxReturnMsg<HashMap<String, Object>> apiGetAc01List(HttpServletRequest request,Model model, @RequestBody DemoAc01 ac01 ) throws Exception {
		return demoAc01Service.getDemoAc01List(ac01);
	}
	
	/**
	 * 通过id删除人员demo信息
	 * @param request
	 * @param model
	 * @param aac001
	 * @return
	 */
	@RequestMapping("/deletebyid/{id}")
	@ResponseBody
	public AjaxReturnMsg<String> deleteDemoDataById(HttpServletRequest request,Model model,@PathVariable String id){
		return demoAc01Service.deleteDemoById(id);
	}
	
	
	/**
	 * 跳转至编辑页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDemoById/{id}")
	@ResponseBody
	public AjaxReturnMsg<DemoAc01> getDemoById(HttpServletRequest request,Model model,@PathVariable String id) throws Exception {
		return demoAc01Service.getDemoById(id);
	}
	
	
	/**
	 * 跳转至查看页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDemoNameById/{id}")
	@ResponseBody
	public AjaxReturnMsg<DemoAc01> getDemoNameById(HttpServletRequest request,Model model,@PathVariable String id) throws Exception {
		return demoAc01Service.getDemoNameById(id);
	}
	
	
	/**
	 * 批量删除
	 * @param request
	 * @param model
	 * @param aac001
	 * @return
	 */
	@RequestMapping("/batdelete")
	@ResponseBody
	public AjaxReturnMsg<String> batDeleteDemodata(HttpServletRequest request,Model model,@RequestBody DemoAc01 ac01){
		return demoAc01Service.batDeleteDemoData(ac01);
	}
	
	
	/**
	 * 更新或保存
	 * @param request
	 * @return
	 */
	@RequestMapping("/savedata")
	@ResponseBody
	public AjaxReturnMsg<String> savedata(HttpServletRequest request,Model model,@RequestBody @Valid DemoAc01 ac01,BindingResult result) throws Exception {
		//检验输入
		if (result.hasErrors()){
			return validate(result);
		}
		return demoAc01Service.saveDemoData(ac01);
	}
	
	/**
	 * 更新个人图片编号
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatefile/{id}/{bus_uuid}")
	@ResponseBody
	public AjaxReturnMsg<String> updatefile(HttpServletRequest request,Model model,@PathVariable String id,@PathVariable String bus_uuid) throws Exception {
		DemoAc01 ac01=new DemoAc01();
		ac01.setAac001(id);
		ac01.setBus_uuid (bus_uuid);
		return demoAc01Service.updateDemoAc01DemBusUuid(ac01);
	}
	
	/**
	 * 删除个人图片
	 * @param request
	 * @return
	 */
	@RequestMapping("/deletefile/{id}/{bus_uuid}")
	@ResponseBody
	public AjaxReturnMsg<String> deletefile(HttpServletRequest request,Model model,@PathVariable String id,@PathVariable String bus_uuid) throws Exception {
		DemoAc01 ac01=new DemoAc01();
		ac01.setAac001(id);
		ac01.setBus_uuid(bus_uuid);
		return demoAc01Service.deletefile(ac01);
	}


}
