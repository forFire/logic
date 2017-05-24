package com.capcare.harbor.service.cache;

import module.util.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.capcare.harbor.model.PetFence;

@Component
public class PetFenceCache {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private static final String PET_FENCE = "pet_fence";

	public PetFence getPetFence(String deviceSn) {
		String json = (String) redisTemplate.opsForHash().get(PET_FENCE, deviceSn);
		if (json != null && !"".equals(json)) {
			return JsonUtils.str2Obj(json, PetFence.class);
		}
		return null;
	}

	public void setPetFence(String deviceSn, PetFence petFence) {
		String json = JsonUtils.obj2Str(petFence);
		redisTemplate.opsForHash().put(PET_FENCE, deviceSn, json);
	}
}
