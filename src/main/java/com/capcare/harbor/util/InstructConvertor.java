package com.capcare.harbor.util;

import java.util.HashMap;
import java.util.Map;

import module.util.JsonUtils;

import com.capcare.harbor.model.Fence;
import com.capcare.harbor.model.Instruct;
import com.capcare.harbor.model.SpeetInstructVo;
import com.capcare.harbor.vo.InstructType;
import com.capcare.harbor.vo.InstructVo;

public class InstructConvertor {

	/**
	 * 下发指令:上传间隔
	 */
	private static InstructVo tick(String deviceSn, int tick) {
		InstructVo protocol = new InstructVo();// 下发指令:上传间隔
		protocol.setDeviceSn(deviceSn);
		protocol.setInstructType(InstructType.SetTick);

		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("interval", tick);
		protocol.setCmdMap(cmdObj);
		return protocol;
	}

	/**
	 * 下发指令:sos号码
	 */
	private static InstructVo sos(String deviceSn, String sosNum) {
		InstructVo protocol = new InstructVo();// 下发指令:sos号码
		protocol.setDeviceSn(deviceSn);
		protocol.setInstructType(InstructType.SetSos);

		Map<String, Object> cmdObj = new HashMap<String, Object>();
		String[] sosNums = sosNum.split(",");
		cmdObj.put("nums", sosNums);

		protocol.setCmdMap(cmdObj);
		return protocol;
	}

	/**
	 * 下发指令:超速 最大值-超速开/关
	 */
	private static InstructVo hb(String deviceSn, int open, int max) {
		InstructVo protocol = new InstructVo();// 下发指令:超速最大值
		protocol.setDeviceSn(deviceSn);
		protocol.setInstructType(InstructType.SetSpeed);

		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("switch", open);
		cmdObj.put("min", 0);
		cmdObj.put("max", max);
		protocol.setCmdMap(cmdObj);
		return protocol;
	}

	/**
	 * 下发指令:围栏
	 */
	private static InstructVo fence(String deviceSn, Fence formFence) {
		InstructVo protocol = new InstructVo();// 下发指令
		protocol.setDeviceSn(deviceSn);
		protocol.setInstructType(InstructType.SetFence);

		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("condition", formFence.getOut());
		cmdObj.put("type", formFence.getType());
		if (Fence.TYPE_CIRCLE == formFence.getType()) {
			cmdObj.put("center", formFence.getCenter());
			cmdObj.put("radius", formFence.getRadius());
		} else if (Fence.TYPE_POLYGON == formFence.getType()) {
			cmdObj.put("region", JsonUtils.obj2Str(formFence.getRegion()));
		} else {
			throw new IllegalArgumentException("围栏类型错误:" + formFence.getType());
		}
		protocol.setCmdMap(cmdObj);
		return protocol;
	}

	/**
	 * 下发指令:重启
	 */
	private static InstructVo reset(String deviceSn) {
		InstructVo protocol = new InstructVo();// 下发指令
		protocol.setDeviceSn(deviceSn);
		protocol.setInstructType(InstructType.Reboot);
		return protocol;
	}

	/**
	 * 下发指令:恢复出厂
	 */
	private static InstructVo restore(String deviceSn) {
		InstructVo protocol = new InstructVo();// 下发指令
		protocol.setDeviceSn(deviceSn);
		protocol.setInstructType(InstructType.Restore);
		return protocol;
	}

	/**
	 * 下发指令:围栏开关
	 */
	private static InstructVo fenceSwitch(String deviceSn, int fenceSwitch) {
		InstructVo protocol = new InstructVo();// 下发指令:上传间隔

		protocol.setDeviceSn(deviceSn);
		protocol.setInstructType(InstructType.SetFenceSwitch);
		Map<String, Object> cmdMap = new HashMap<String, Object>();
		cmdMap.put("switch", fenceSwitch);

		protocol.setCmdMap(cmdMap);
		return protocol;
	}

	/**
	 * 下发指令:移动开关
	 */
	private static InstructVo moveSwitch(String deviceSn, int moveSwitch) {
		InstructVo protocol = new InstructVo();// 下发指令:上传间隔
		protocol.setDeviceSn(deviceSn);
		protocol.setInstructType(InstructType.SetMoveSwitch);

		Map<String, Object> cmdObj = new HashMap<String, Object>();
		cmdObj.put("switch", moveSwitch);

		protocol.setCmdMap(cmdObj);
		return protocol;
	}

	/**
	 * 下发指令
	 * 
	 * 上线时读取离线指令
	 */
	public static InstructVo instruct(Instruct instruct, String deviceSn) {
		int type = instruct.getType();
		if (1 == type) {
			Fence formFence = null;
			String value = instruct.getContent();
			formFence = JsonUtils.str2Obj(value, Fence.class);
			return fence(deviceSn, formFence);
		} else if (2 == type) {
			return tick(deviceSn, Integer.parseInt(instruct.getContent()));
		} else if (3 == type) {
			SpeetInstructVo siv = null;
			siv = JsonUtils.str2Obj(instruct.getContent(), SpeetInstructVo.class);
			return hb(deviceSn, siv.getOpen(), siv.getMax());
		} else if (4 == type) {
			return sos(deviceSn, instruct.getContent());
		} else if (5 == type) {
			return reset(deviceSn);
		} else if (6 == type) {
			return restore(deviceSn);
		} else if (7 == type) {
			return fenceSwitch(deviceSn, Integer.parseInt(instruct.getContent()));
		} else if (8 == type) {
			return moveSwitch(deviceSn, Integer.parseInt(instruct.getContent()));
		} else {
			throw new IllegalArgumentException("指令类型错误");
		}
	}

}
