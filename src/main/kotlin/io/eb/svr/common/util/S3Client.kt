package io.eb.svr.common.util

import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import io.eb.svr.common.config.AwsSecurityConfig
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.model.PutObjectRequest

/**
 * Create by lucy on 2019-07-06
 **/
@Service
class S3Client(
	@Autowired private val awsSecretKey: AwsSecurityConfig
) { //	private lateinit var s3client: AmazonS3
	companion object : KLogging()

	fun upload(upFile: MultipartFile) : String {
		logger.info { "S3 upload accessKey:"+ awsSecretKey.accessKeyId+" secretAccessKey:"+awsSecretKey.secretAccessKey+" awsSecretKey.region"}
		val awsCreds = AwsBasicCredentials.create(
			awsSecretKey.accessKeyId,
			awsSecretKey.secretAccessKey)
		val s32 = S3Client.builder()
			.region(Region.of("ap-northeast-2"))
			.credentialsProvider(StaticCredentialsProvider.create(awsCreds))
			.build()
		var fileUrl = ""
		try {
			val fileName = generateFileName(upFile)
			logger.info("Now will upload file {}", fileName)
			logger.info("Target bucket: {}", awsSecretKey.bucket)
			fileUrl = awsSecretKey.cdnUrl + "/"  + fileName
			var cType = "image/jpeg"
			if (fileName.endsWith(".jpg")) {
				cType = "image/jpeg"
			}
			if (fileName.endsWith(".png")) {
				cType = "image/png"
			}
			if (fileName.endsWith(".gif")) {
				cType = "image/gif"
			}
			s32.putObject(PutObjectRequest.builder().bucket(awsSecretKey.bucket).key(fileName).contentType(cType)
				.build(), RequestBody.fromBytes(upFile.bytes))
		} catch (e: Exception) {
			logger.info("upload error: {}", e)
		}

		return fileUrl
	}

	private fun generateFileName(multiPart: MultipartFile): String {
//		return "shop/image/"+multiPart.originalFilename!!.replace(" ", "_").replace("/", "_")
		return multiPart.originalFilename!!.replace(" ", "_").replace("/", "_")
	}
}