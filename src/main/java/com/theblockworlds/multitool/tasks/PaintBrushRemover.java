package com.theblockworlds.multitool.tasks;

import com.theblockworlds.multitool.Multitool;
import com.theblockworlds.multitool.tools.PaintBrush;

public class PaintBrushRemover implements Runnable {
	private PaintBrush paintBrush;

	public PaintBrushRemover(Multitool plugin) {
		try {
			 paintBrush = (PaintBrush) plugin.getToolHandler().getTool("PaintBrush");
		}
		catch (Exception e) {
		}
	}
	
	public void run() {
		if (paintBrush != null) {
			paintBrush.updatePaintBrushes();
		}
	}
}
