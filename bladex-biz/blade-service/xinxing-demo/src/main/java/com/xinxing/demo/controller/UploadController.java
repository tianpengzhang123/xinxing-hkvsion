package com.xinxing.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.boot.file.LocalFile;
import org.springblade.core.oss.MinioTemplate;
import org.springblade.core.oss.model.BladeFile;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * UploadController
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/notice/upload")
@Tag(name = "对象存储接口", description = "oss上传测试")
public class UploadController extends BladeController {

	private final MinioTemplate minioTemplate;

	/**
	 * minio上传demo
	 *
	 * @param file 上传文件
	 * @return BladeFile
	 */
	@SneakyThrows
	@PostMapping("put-object")
	public R<BladeFile> putMinioObject(@RequestParam MultipartFile file) {
		BladeFile bladeFile = minioTemplate.putFile(file);
		return R.data(bladeFile);
	}

	/**
	 * 上传本地文件
	 *
	 * @param file 上传文件
	 * @return LocalFile
	 */
	@SneakyThrows
	@PostMapping("put-local-object")
	public R<LocalFile> putLocalObject(@RequestParam MultipartFile file) {
		LocalFile localFile = getFile(file);
		localFile.transfer();
		return R.data(localFile);
	}

}
