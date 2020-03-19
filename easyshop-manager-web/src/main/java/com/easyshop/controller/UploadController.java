package com.easyshop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.easyshop.pojo.Result;
import com.easyshop.utils.FastDFSClient;

/**
 * 负责文件的异步上传
 * 
 * @author 陶毅
 *
 */
@Controller
public class UploadController {

	@Value("${FILE_SERVER_URL}")
	private String FILE_SERVER_URL;// 去config/application.properties配置文件中获取 地址 FILE_SERVER_URL=http://192.168.138.132/

	@RequestMapping("/uploadimg")
	@ResponseBody
	public Result upload(MultipartFile file) {
		// 1、取文件的扩展名
		String originalFilename = file.getOriginalFilename();
		String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		try {
			// 2、创建一个 FastDFS 的客户端
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
			// 3、执行上传处理
			String path = fastDFSClient.uploadFile(file.getBytes(), extName);
			// 4、拼接返回的 url 和 ip 地址，拼装成完整的 url
			String url = FILE_SERVER_URL + path;
			return new Result(true, url); //返回一个成功的地址
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "上传失败");
		}
	}

}
