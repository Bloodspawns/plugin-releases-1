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
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.tzhaarketrak.TKRConfig;
import net.runelite.client.plugins.tzhaarketrak.TKRPlugin;
import net.runelite.client.plugins.tzhaarketrak.util.JadNode;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.InfoBoxComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class PrayerIBOverlay extends OverlayPanel
{
	private final Client client;
	private final SpriteManager spriteManager;
	private final TKRPlugin plugin;
	private final TKRConfig config;

	private static final int DIMENSION = 40;

	@Inject
	PrayerIBOverlay(Client client, SpriteManager spriteManager, TKRPlugin plugin, TKRConfig config)
	{
		this.client = client;
		this.spriteManager = spriteManager;
		this.plugin = plugin;
		this.config = config;
		setResizable(false);
		setPriority(OverlayPriority.HIGH);
		setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
		panelComponent.setPreferredSize(new Dimension(DIMENSION, 0));
		panelComponent.setBorder(new Rectangle());
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (plugin.getBit() == 0 || !config.shouldDisplayPrayerIB())
		{
			return null;
		}

		val module = plugin.getModule();

		if (module == null)
		{
			return null;
		}

		var deferred = module.getDeferredActive();

		if (deferred.peek() != null)
		{
			JadNode closest = deferred.element().getKey();
			displayPrayer(graphics, client, spriteManager, panelComponent, closest);
		}

		return super.render(graphics);
	}

	private static void displayPrayer(Graphics2D graphics, Client client, SpriteManager spriteManager, PanelComponent component, JadNode n)
	{
		Prayer prayer = n.getPrayer();
		BufferedImage sprite = n.getSpriteImage(spriteManager);

		if (prayer == null || sprite == null)
		{
			return;
		}

		var ib = new InfoBoxComponent();
		ib.setImage(sprite);

		Color c = client.isPrayerActive(prayer) ? Color.GREEN : Color.RED;
		ib.setBackgroundColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 25));
		ib.setPreferredSize(new Dimension(40, 40));

		component.getChildren().add(ib);
		component.render(graphics);
	}
}
