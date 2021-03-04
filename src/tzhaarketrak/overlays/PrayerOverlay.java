/*
 * Copyright (c) 2021, Nicole <github.com/losingticks>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.tzhaarketrak.overlays;

import lombok.val;
import net.runelite.api.Client;
import net.runelite.api.Prayer;
import net.runelite.api.VarClientInt;
import net.runelite.api.widgets.Widget;
import net.runelite.client.plugins.tzhaarketrak.TKRConfig;
import net.runelite.client.plugins.tzhaarketrak.TKRPlugin;
import net.runelite.client.plugins.tzhaarketrak.util.JadNode;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;

public class PrayerOverlay extends Overlay
{
	private final Client client;
	private final TKRPlugin plugin;
	private final TKRConfig config;

	@Inject
	PrayerOverlay(Client client, TKRPlugin plugin, TKRConfig config)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(OverlayPriority.HIGH);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (plugin.getBit() == 0 || client.getVar(VarClientInt.INVENTORY_TAB) != 5)
		{
			return null;
		}

		val module = plugin.getModule();

		if (module == null)
		{
			return null;
		}

		if (config.shouldDisplayPrayerMarker() || config.shouldDisplayGuitarHero())
		{
			var deferred = module.getDeferredActive();

			if (deferred.peek() != null)
			{
				JadNode closest = deferred.element().getKey();

				if (config.shouldDisplayPrayerMarker())
				{
					markPrayer(client, graphics, closest);
				}

				if (config.shouldDisplayGuitarHero())
				{
					deferred.forEach(p ->
					{
						val node = p.getKey();
						boolean isClosest = closest.equals(node);

						val widget = node.getPrayerWidget(client);
						if (widget == null)
						{
							return;
						}

						val ticks = node.getTicks();
						Color color = (ticks == 6 && isClosest) ? Color.RED : Color.ORANGE;

						// ripped from oprs thx hehe rawr
						Rectangle bounds = widget.getBounds();
						int x = (int) bounds.getX();
						x += (bounds.getWidth() / 2) - 5;
						int y = (int) bounds.getY() - (ticks - 5) * 60 - 5;
						y += 60 - ((plugin.getLastTick() + 600 - System.currentTimeMillis()) / 600.0 * 60);
						Rectangle box = new Rectangle(10, 5);
						box.translate(x, y);
						renderFilledPolygon(graphics, box, color);
					});
				}
			}
		}

		return null;
	}

	public static void renderFilledPolygon(Graphics2D graphics, Shape poly, Color color)
	{
		graphics.setColor(color);
		final Stroke originalStroke = graphics.getStroke();
		graphics.setStroke(new BasicStroke(2));
		graphics.draw(poly);
		graphics.fill(poly);
		graphics.setStroke(originalStroke);
	}

	private static void markPrayer(Client client, Graphics2D graphics, JadNode n)
	{
		Prayer prayer = n.getPrayer();
		Widget widget = n.getPrayerWidget(client);

		if (prayer == null || widget == null)
		{
			return;
		}

		graphics.setColor(client.isPrayerActive(prayer) ? Color.GREEN : Color.RED);
		graphics.setStroke(new BasicStroke(1));
		graphics.draw(widget.getBounds());
	}
}
