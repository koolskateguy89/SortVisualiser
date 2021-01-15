package sv3.utils;

import static sv3.utils.Utils.sleep;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.scene.shape.Rectangle;

class Deselector {

	private Deselector() { }

	private static Queue<Rectangle> q = new LinkedBlockingQueue<>();

	/*
	 * FIXME: configure thread sleep time to work for all sort, not just either QuickSort or
	 *        the other
	 *
	 *        (when delay is more than about 25 seconds, QuickSort deselecting does not work properly,
	 *        but when delay is less than about 20 seconds, QuickSort deselecting works somewhat, but
	 *        the deselecting for all other sorts won't work at all - it'll be too quick)
	 */
	static {
		Thread thread = new Thread(() -> {
			while (true) {
				if (Utils.deselect != Utils.DEFAULT_COLOUR) {
					q.clear();
				}
				if (!q.isEmpty()) {
					q.poll().setFill(Utils.deselect);
				}
				sleep(20);
			}
		});
		thread.setName("Deselector thread");
		thread.setDaemon(true);
		thread.start();
	}

	public static void queue(Rectangle r) {
		q.offer(r);
	}

	public static void queueAll(Rectangle... rectangles) {
		for (Rectangle rect : rectangles) {
			q.offer(rect);
		}
	}

}
