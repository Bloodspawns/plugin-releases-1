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
import net.runelite.api.NPC;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.client.plugins.tzhaarketrak.TKRConfig;
import net.runelite.client.plugins.tzhaarketrak.TKRPlugin;
import net.runelite.client.plugins.tzhaarketrak.util.JadModule;
import net.runelite.client.plugins.tzhaarketrak.util.JadNode;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.util.ColorUtil;

import javax.inject.Inject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.Collection;

public class SceneOverlay extends Overlay
{
	private final Client client;
	private final TKRPlugin plugin;
	private final TKRConfig config;

	@Inject
	SceneOverlay(Client client, TKRPlugin plugin, TKRConfig config)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setLayer(OverlayLayer.ABOVE_SCENE);
		setPosition(OverlayPosition.DYNAMIC);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (plugin.getBit() == 0)
		{
			return null;
		}

		JadModule module = plugin.getModule();

		if (module == null)
		{
			return null;
		}

		if (config.shouldDisplaySpawnLocations() && module.canDisplaySpawns())
		{
			plugin.getMode().getPoints().forEach(l ->
			{
				val poly = Perspective.getCanvasTileAreaPoly(client, l, 5);
				renderPolygon(graphics, poly, config.getSpawnLocationsColor());
			});
		}
		else if (config.shouldMarkJads() || config.shouldDisplayTickCounters())
		{
			graphics.setFont(FontManager.getRunescapeBoldFont());
			Collection<JadNode> nodes = module.getNodes();

			nodes.forEach(n ->
			{
				NPC jad = n.getJad();

				if (config.shouldMarkJads())
				{
					val poly = Perspective.getCanvasTileAreaPoly(client, jad.getLocalLocation(), 5);
					renderPolygon(graphics, poly, config.getJadMarkerColor());
				}

				if (config.shouldDisplayTickCounters())
				{
					val loc = Perspective.getCanvasTextLocation(client, graphics, jad.getLocalLocation(), "#", 25);
					renderTextLocation(graphics, loc, n.getTicks());
				}
			});
		}

		return null;
	}

	private static void renderPolygon(Graphics2D graphics, Shape polygon, Color color)
	{
		if (polygon == null)
		{
			return;
		}

		graphics.setColor(color);
		graphics.setStroke(new BasicStroke(1));
		graphics.draw(polygon);
		graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 15));
		graphics.fill(polygon);
	}

	private static void renderTextLocation(Graphics2D graphics, Point txtLoc, int ticks)
	{
		if (txtLoc == null || ticks <= 0)
		{
			return;
		}

		String text = Integer.toString(ticks);

		int x = txtLoc.getX();
		int y = txtLoc.getY();

		graphics.setColor(Color.BLACK);
		graphics.drawString(text, x + 1, y + 1);

		graphics.setColor(ColorUtil.colorWithAlpha(Color.WHITE, 0xFF));
		graphics.drawString(text, x, y);
	}
}
