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
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.entity.User;
import com.mcgj.service.IUserService;
import com.mcgj.utils.CommonUtil;
import com.mcgj.utils.MessageUtil;
import com.mcgj.web.dto.ResultDTO;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractBaseController {
	/**
	 * ��¼��־
	 */
	Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private IUserService userService;
	

	// @RequestMapping(value="/tset/{id}",method = RequestMethod.GET)
	@RequestMapping("/test")
	@ResponseBody
	public Object aa(HttpServletRequest request, HttpServletResponse response) {
		// request.get
		String str = response.getHeader("user-token");
		System.out.println("�����û���token" + str);
		// System.out.println("���Է�����������������������"+id);
		return null;
	}

	/**
	 * �û���¼���� ���ڿ�������ÿ�λ�ȡ��sessionId����ͬ������ʹ��token�����û��ж�
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
			//��ȡ�û�������Ͳ���ϵͳ
			String[] osAndBrowserInfo = CommonUtil.getOsAndBrowserInfo(request);
			user.setOs(osAndBrowserInfo[0]);//����ϵͳ
			user.setBrowser(osAndBrowserInfo[1]);//�����
			user.setIp(request.getLocalAddr());//��ȡip
			User record = this.userService.login(user);
			return new ResultDTO(MessageUtil.MSG_LOGIN_SUCCESS,true,record);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return new ResultDTO(e.getMessage(),false,null);
		}
	}

	/**
	 * �û�ע�᷽��
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
	 * �����û�id��ѯ�û�δ������Ϣ��������
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
	 * �����û�id���ղص���������
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
				System.out.println("ģ������ʧ��");
			}
			InputStream is = null;
			if (templateType == 1) {
//				file = new File(templatePath);
				is =UserController.class.getClassLoader().getResourceAsStream("template/ȫ������ģ��.xlsx");
			}
			if (templateType == 2) {
				is = UserController.class.getClassLoader().getResourceAsStream("template/ȫ������ģ��.xlsx");
			}
			if (templateType == 3) {
				is = UserController.class.getClassLoader().getResourceAsStream("template/ȫ������ģ��.xlsx");
			}
			response.setHeader("Content-Disposition", "attachment;fileName="+URLEncoder.encode("ȫ������ģ��.xlsx", "UTF-8"));//�����ļ�����
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
			File mkdir = new File(templatePath);//��ȡ�ļ���
			if(!mkdir.exists()){//�ļ��в����ڽ��д���
				mkdir.mkdir();
			}
			templatePath=templatePath+"\\�û��ϴ�ģ��"+new Date().getTime()+".xls";
			System.out.println(templatePath);
			File template  = new File(templatePath);//����ģ���ļ�
			template.createNewFile();//�����ļ�
			InputStream is = file.getInputStream();//�����û��ϴ��ļ���
			FileOutputStream fos  = new FileOutputStream(template);//ת��Ϊ�ļ�������
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
				return;//��Ϊָ����׺����
			}
//			Workbook wk = new HSSFWorkbook(is);
//			System.out.println(workbook.getNumberOfSheets());
//			Sheet sh =workbook.getSheet("��ͼ���ֲ��б�");
//			System.out.println(sh);
//			System.out.println(sh.getNumMergedRegions());
			Map<String,ArrayList<String>> list = ExcelUtil.readData("D://ȫ������ģ��-������.xlsx", "������ʩ����������");
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
		Map<String,ArrayList<String>> list = ExcelUtil.readData("D://ȫ������ģ��-������.xlsx", 1);
//		List<ArrayList<String>> list = ExcelUtil.readData("D://�ն˹���20180103.xls",0);
		System.out.println(list);
	}
	*/
	/**
	 * ����****
	 * �ж��û��Ƿ���ڣ������ڽ��д�����Ȼ�󷵻��û���Ϣ
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
	

	
}
