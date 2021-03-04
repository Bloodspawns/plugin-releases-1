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
package net.runelite.client.plugins.tzhaarketrak.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Prayer;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.client.game.SpriteManager;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;

@Getter @Setter
@EqualsAndHashCode
public final class JadNode
{
	private NPC jad;
	private int ticks;
	private Prayer prayer;

	JadNode(NPC jad, int ticks, Prayer prayer)
	{
		this.jad = jad;
		this.ticks = ticks;
		this.prayer = prayer;
	}

	public void decTicks()
	{
		if (ticks <= 0)
		{
			return;
		}

		ticks--;
	}

	public boolean shouldRemove()
	{
		return ticks <= 0;
	}

	public @Nullable Widget getPrayerWidget(Client client)
	{
		int child;

		switch (prayer)
		{
			case PROTECT_FROM_MELEE:
				child = 19;
				break;
			case PROTECT_FROM_MAGIC:
				child = 17;
				break;
			case PROTECT_FROM_MISSILES:
				child = 18;
				break;
			default:
				child = -1;
		}

		if (child == -1)
		{
			return null;
		}

		return client.getWidget(WidgetID.PRAYER_GROUP_ID, child);
	}

	public @Nullable BufferedImage getSpriteImage(SpriteManager spriteManager)
	{
		int archive;

		switch (prayer)
		{
			case PROTECT_FROM_MELEE:
				archive = 129;
				break;
			case PROTECT_FROM_MAGIC:
				archive = 127;
				break;
			case PROTECT_FROM_MISSILES:
				archive = 128;
				break;
			default:
				archive = -1;
		}

		if (archive == -1)
		{
			return null;
		}

		return spriteManager.getSprite(archive, 0);
	}
}
