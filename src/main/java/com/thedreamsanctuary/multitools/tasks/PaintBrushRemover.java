package com.thedreamsanctuary.multitools.tasks;

import com.thedreamsanctuary.multitools.MultiTools;
import com.thedreamsanctuary.multitools.tools.PaintBrush;

public class PaintBrushRemover implements Runnable {
	private PaintBrush paintBrush;

	public PaintBrushRemover(MultiTools plugin) {
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
