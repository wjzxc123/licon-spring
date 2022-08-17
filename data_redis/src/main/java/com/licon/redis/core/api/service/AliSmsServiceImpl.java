package com.licon.redis.core.api.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import com.licon.redis.core.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/17 16:48
 */
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = AliSmsServiceImpl.PREFIX,name = "name",havingValue = "ali")
@Service
public class AliSmsServiceImpl implements SmsService{

	public static final String PREFIX = AppProperties.APP_PREFIX+".sms-provider";

	private final Client smsClient;
	private final AppProperties appProperties;

	@Override
	public void send(String mobile, String msg) {
		JSONObject json = new JSONObject();
		json.put("code",msg);
		SendSmsRequest sendSmsRequest = new SendSmsRequest()
				.setSignName(appProperties.getSmsProvider().getAli().getSignName())
				.setTemplateCode(appProperties.getSmsProvider().getAli().getTemplateCode())
				.setTemplateParam(json.toJSONString())
				.setPhoneNumbers(mobile);

		RuntimeOptions runtime = new RuntimeOptions();
		try {
			SendSmsResponse sendSmsResponse = smsClient.sendSmsWithOptions(sendSmsRequest, runtime);
			log.info("发送结果：{}",sendSmsResponse.body.getMessage());
		} catch (TeaException error) {
			// 如有需要，请打印 error
			log.error("发送短信异常:{}",error.message);
		} catch (Exception _error) {
			TeaException error = new TeaException(_error.getMessage(), _error);
			// 如有需要，请打印 error
			log.error("发送短信异常:{}",error.message);
		}
	}
}
