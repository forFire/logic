package com.capcare.harbor.service.cache;

import javax.annotation.Resource;

import module.util.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.capcare.harbor.dao.CellDao;
import com.capcare.harbor.model.Cell;

@Component
public class CellCache {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private CellDao cellDao;

	private static final String CELL_ALL = "cell_all";
		
	public Cell getCellData(int mnc, int lac, int cellId) {
		String key = mnc+"_"+lac+"_"+cellId;
		Object obj = redisTemplate.opsForHash().get(CELL_ALL, key);
		if(obj != null){
			return JsonUtils.str2Obj((String)obj, Cell.class);
		}
		Cell cell = cellDao.getCell(mnc, lac, cellId);
		if(cell != null){
			redisTemplate.opsForHash().put(CELL_ALL, key, JsonUtils.obj2Str(cell));
			return cell;
		}
		return null;
	}
	
}
