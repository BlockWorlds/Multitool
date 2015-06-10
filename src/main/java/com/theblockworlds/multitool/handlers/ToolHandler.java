package com.theblockworlds.multitool.handlers;

import java.util.ArrayList;
import java.util.List;

import com.theblockworlds.multitool.base.Tool;

public class ToolHandler {
	private static List<Tool> registeredTools = new ArrayList<Tool>();
	
	public static void registerTool(Tool t){
		registeredTools.add(t);
	}
}
