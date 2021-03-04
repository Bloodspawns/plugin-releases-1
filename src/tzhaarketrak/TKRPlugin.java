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
package net.runelite.client.plugins.tzhaarketrak;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.val;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.kit.KitType;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.tzhaarketrak.overlays.PrayerIBOverlay;
import net.runelite.client.plugins.tzhaarketrak.overlays.PrayerOverlay;
import net.runelite.client.plugins.tzhaarketrak.overlays.SceneOverlay;
import net.runelite.client.plugins.tzhaarketrak.util.ChallengeMode;
import net.runelite.client.plugins.tzhaarketrak.util.JadModule;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@PluginDescriptor(
		name = "[z] Tzhaar-Ket-Rak",
		description = "All-in-one plugin to aid you through the Jad minigame",
		tags = {"fight caves", "inferno", "jad", "nibbler", "zhuri/nicole"},
		enabledByDefault = false
)
public class TKRPlugin extends Plugin
{
	@Inject private Client client;
	@Inject private OverlayManager overlayManager; // TODO -> eventually only add overlays if inside & option is on, else keep it removed
	@Inject private SceneOverlay sceneOverlay;
	@Inject private PrayerIBOverlay prayerIBOverlay;
	@Inject private PrayerOverlay prayerMarker;
	@Inject private TKRConfig config;

	@Provides
	TKRConfig provideConfig(ConfigManager cm)
	{
		return cm.getConfig(TKRConfig.class);
	}

	private static final int LOBBY = 10063;
	private static final int INSIDE = 9043;
	private static final int CM_VARB = 11878; // 0 = out, 1 = in
	private static final int HEALER_ID = NpcID.YTHURKOT_10624;

	@Getter private ChallengeMode mode = null;
	@Getter private JadModule module = null;
	@Getter private int bit = 0;
	@Getter private long lastTick = 0;

	@Getter private final List<NPC> healers = new ArrayList<>();

	@Override
	protected void startUp()
	{
		overlayManager.add(sceneOverlay);
		overlayManager.add(prayerIBOverlay);
		overlayManager.add(prayerMarker);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(sceneOverlay);
		overlayManager.remove(prayerIBOverlay);
		overlayManager.remove(prayerMarker);
	}

	private void reset()
	{
		mode = null;
		module = null;
		bit = 0;
		lastTick = 0;
		healers.clear();
	}

	@Subscribe
	private void onVarbitChanged(VarbitChanged e)
	{
		val cm = client.getVarbitValue(CM_VARB);

		if (bit != cm)
		{
			bit = cm;

			if (bit == 0)
			{
				reset();
			}
		}
	}

	@Subscribe
	private void onChatMessage(ChatMessage e)
	{
		if (e.getType() != ChatMessageType.GAMEMESSAGE)
		{
			return;
		}

		mode = ChallengeMode.of(e.getMessage());

		if (mode != null)
		{
			module = new JadModule();
		}
	}

	@Subscribe
	private void onNpcSpawned(NpcSpawned e)
	{
		if (bit == 0 || module == null)
		{
			return;
		}

		val npc = e.getNpc();

		if (npc.getId() == HEALER_ID)
		{
			healers.add(npc);
			return;
		}

		module.cacheThis(npc);
	}

	@Subscribe
	private void onNpcDespawned(NpcDespawned e)
	{
		if (module == null)
		{
			return;
		}

		val npc = e.getNpc();

		if (!healers.isEmpty())
		{
			healers.remove(npc);
		}

		module.rm(npc);
	}

	@Subscribe
	private void onGameTick(GameTick e)
	{
		if (bit == 0 || module == null)
		{
			return;
		}

		lastTick = System.currentTimeMillis();
		module.dec();
	}

	@Subscribe
	private void onAnimationChanged(AnimationChanged e)
	{
		if (bit == 0 || module == null || !(e.getActor() instanceof NPC))
		{
			return;
		}

		val npc = (NPC) e.getActor();
		module.entry(npc);
	}

	@Subscribe
	private void onClientTick(ClientTick ignored)
	{
		if (bit == 0 || module == null || !config.shouldPrioritizeHealers())
		{
			return;
		}

		List<MenuEntry> keep = new ArrayList<>();
		val entries = client.getMenuEntries();

		for (var e : entries)
		{
			val target = Text.removeTags(e.getTarget()).toLowerCase();

			if ((e.getType() == MenuAction.SPELL_CAST_ON_NPC.getId() && (target.startsWith("ice b") || target.startsWith("blood b"))
					|| swapUsingChins(client, e.getOption())))
			{
				val npc = client.getCachedNPCs()[e.getIdentifier()];

				if (npc != null && npc.getId() != HEALER_ID)
				{
					continue;
				}
			}

			keep.add(e);
		}

		client.setMenuEntries(keep.toArray(new MenuEntry[0]));
	}

	private static boolean swapUsingChins(Client client, String option)
	{
		if (!option.equalsIgnoreCase("attack"))
		{
			return false;
		}

		val p = client.getLocalPlayer();
		val c = p != null ? p.getPlayerComposition() : null;

		if (c == null)
		{
			return false;
		}

		val id = c.getEquipmentId(KitType.WEAPON);
		return id == 11959 || id == 10034 || id == 10033;
	}
}
