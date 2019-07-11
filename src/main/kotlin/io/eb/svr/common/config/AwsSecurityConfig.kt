package io.eb.svr.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import software.amazon.awssdk.regions.Region.AP_NORTHEAST_2

/**
 * Create by lucy on 2019-07-06
 **/
@Configuration
@ConfigurationProperties(prefix = "aws")
@Component
class AwsSecurityConfig {
	var accessKeyId = "AKIA3GZICWVJ2IQ5TQ64"
	var secretAccessKey = "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"
	var region = AP_NORTHEAST_2
	var bucket = "fs.everyonebeauty.com"
	var cdnUrl = ""
}