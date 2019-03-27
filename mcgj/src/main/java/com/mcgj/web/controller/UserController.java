package com.mcgj.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.entity.User;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.service.IUserService;
import com.mcgj.utils.CommonUtil;
import com.mcgj.utils.MessageUtil;
import com.mcgj.web.dto.ResultDTO;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractBaseController {
	/**
	 * 记录日志
	 */
	Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private IUserService userService;
	
	/**
	 * 用户登录方法 由于跨域请求，每次获取的sessionId都不同，所以使用token进行用户判断
	 * 
	 * @param res
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/login",method={RequestMethod.POST})
	@ResponseBody
	public ResultDTO login(HttpServletResponse response,
			HttpServletRequest request, User user, HttpSession session,
			@PathParam("userName") String userName) {
		try {
			//获取用户浏览器和操作系统
			String[] osAndBrowserInfo = CommonUtil.getOsAndBrowserInfo(request);
			user.setOs(osAndBrowserInfo[0]);//操作系统
			user.setBrowser(osAndBrowserInfo[1]);//浏览器
			user.setIp(request.getLocalAddr());//获取ip
			User record = this.userService.login(user);
			return new ResultDTO(MessageUtil.MSG_LOGIN_SUCCESS,true,record);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return new ResultDTO(e.getMessage(),false,null);
		}
	}

	/**
	 * 用户注册方法
	 * 
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public ResultDTO register(HttpServletRequest request,
			HttpServletResponse response, User user) {
		ResultDTO result = new ResultDTO();
		try {
			userService.register(user);
			result.setMessage(MessageUtil.MSG_REGISTER_SUCCESS);
			result.setResult(user);
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
	}
	/**
	 * 根据用户id查询用户未读的消息数据数量
	 * @return
	 */
	@RequestMapping("/selectUserUnreadMessageCountByUserId")
	@ResponseBody
	public ResultDTO selectUserUnreadMessageCountByUserId(Integer userId){
		ResultDTO result = new ResultDTO();
		try {
//			userService.register(user);
			result.setResult(userService.selectUserUnreadMessageCountByUserId(userId));
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
	}
	
	/**
	 * 判断用户token是否有效
	 * @return
	 */
	@RequestMapping(value="/selectUserIsEffective")
	@ResponseBody
	public ResultDTO selectUserIsEffective(@RequestParam("token") String token){
		Object user = RedisHashUtil.get(token);
		if(user != null){
			return new ResultDTO(MessageUtil.MSG_QUERY_SUCCESS, true, null);
		}else{
			return new ResultDTO(MessageUtil.MSG_QUERY_ERROR, false, null);
		}
	}
	
	/**
	 * 根据用户id其收藏的帖子数据
	 * @return
	 */
	@RequestMapping("/selectCollectionConversationChildByUserId")
	@ResponseBody
	public ResultDTO selectCollectionConversationChildByUserId(Integer userId){
		ResultDTO result = new ResultDTO();
		try {
			result.setResult(userService.selectCollectionConversationChildByUserId(userId));
			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
	}

	
	/*
	@RequestMapping("/download")
	public void download(HttpServletRequest request,
			HttpServletResponse response, Integer templateType) {
		try {
			if (templateType > 3 || templateType <= 0) {
				System.out.println("模板下载失败");
			}
			InputStream is = null;
			if (templateType == 1) {
//				file = new File(templatePath);
				is =UserController.class.getClassLoader().getResourceAsStream("template/全国环卫模板.xlsx");
			}
			if (templateType == 2) {
				is = UserController.class.getClassLoader().getResourceAsStream("template/全国环卫模板.xlsx");
			}
			if (templateType == 3) {
				is = UserController.class.getClassLoader().getResourceAsStream("template/全国环卫模板.xlsx");
			}
			response.setHeader("Content-Disposition", "attachment;fileName="+URLEncoder.encode("全国环卫模板.xlsx", "UTF-8"));//设置文件标题
			OutputStream os = response.getOutputStream();
			byte[] bts = new byte[100];
			while((is.read(bts)) != -1){
				os.write(bts);
			}
			os.flush();
			os.close();
			is.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	@RequestMapping("/upLoad")
	public void upLoad(HttpServletRequest request,HttpServletResponse response,MultipartFile file){
		try{
			String templatePath = "E://userUpTemplate";
			System.out.println(templatePath);
			File mkdir = new File(templatePath);//获取文件夹
			if(!mkdir.exists()){//文件夹不存在进行创建
				mkdir.mkdir();
			}
			templatePath=templatePath+"\\用户上传模板"+new Date().getTime()+".xls";
			System.out.println(templatePath);
			File template  = new File(templatePath);//创建模板文件
			template.createNewFile();//创建文件
			InputStream is = file.getInputStream();//接收用户上传文件流
			FileOutputStream fos  = new FileOutputStream(template);//转换为文件输入流
			byte[] bts = new byte[100];
			while((is.read(bts))!= -1){
				fos.write(bts);
			}
			fos.flush();
			is.close();
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	
	@RequestMapping("/readTemplate")
	public void readTemplate(HttpServletRequest request,HttpServletResponse response,MultipartFile file){
		try {
			if(file == null){
				return;
			}
			Workbook workbook = null;
			System.out.println(file.getOriginalFilename());
			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".")+1;
			String extendName = fileName.substring(index,fileName.length());
			InputStream is = file.getInputStream();
			if(extendName.equals("xlsx")){
				workbook = new XSSFWorkbook(is);
			}
			if(extendName.equals("xls")){
				workbook = new HSSFWorkbook(is);
			}
			if(workbook == null){
				return;//不为指定后缀数据
			}
//			Workbook wk = new HSSFWorkbook(is);
//			System.out.println(workbook.getNumberOfSheets());
//			Sheet sh =workbook.getSheet("地图与轮播列表");
//			System.out.println(sh);
//			System.out.println(sh.getNumMergedRegions());
			Map<String,ArrayList<String>> list = ExcelUtil.readData("D://全国环卫模板-有数据.xlsx", "环卫设施类型数量表");
			System.out.println(list);
//			List<ArrayList<String>> list = UserController.read(workbook, 0);
//			System.out.println(list);
//			System.out.println(sh.get);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Map<String,ArrayList<String>> list = ExcelUtil.readData("D://全国环卫模板-有数据.xlsx", 1);
//		List<ArrayList<String>> list = ExcelUtil.readData("D://终端管理20180103.xls",0);
		System.out.println(list);
	}
	*/
	/**
	 * 爬虫****
	 * 判断用户是否存在，不存在进行创建，然后返回用户信息
	 * @return
	 */
	@RequestMapping("/selectIsExists")
	@ResponseBody
	public Integer selectIsExists(String userName,String photo){
//		ResultDTO result = new ResultDTO();
		try {
			Integer selectIsExists = userService.selectIsExists(userName, photo);
//			result.setResult(userService.selectCollectionConversationChildByUserId(userId));
//			result.setMessage(MessageUtil.MSG_QUERY_SUCCESS);
//			result.setSuccess(true);
//			return result;
			return selectIsExists;
		} catch (Exception e) {
			e.printStackTrace();
//			result.setSuccess(false);
//			result.setMessage(e.getMessage());
			return 1;
		}
	}
	
	public static void main(String[] args) {
		
	}
	
}
