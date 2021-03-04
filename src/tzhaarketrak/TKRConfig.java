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

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.Color;

import static net.runelite.client.plugins.tzhaarketrak.TKRConfig.GROUP_NAME;

@ConfigGroup(GROUP_NAME)
public interface TKRConfig extends Config
{
	String GROUP_NAME = "tzhaarketrak";

	@ConfigItem(
			name = "Show Spawn Locations",
			keyName = "jcSpawnLocations",
			description = "Displays the spawn locations of each Jad for each mode",
			position = 0
	)
	default boolean shouldDisplaySpawnLocations() { return false; }

	@ConfigItem(
			name = "Spawn Locations Color",
			keyName = "jcSpawnLocationsColor",
			description = "Configures the color for 'Spawn Locations'",
			position = 1
	)
	@Alpha
	default Color getSpawnLocationsColor() { return Color.WHITE; }


	@ConfigItem(
			name = "Jad Marker",
			keyName = "jcJadMarker",
			description = "Displays tile highlights for each Jad",
			position = 2
	)
	default boolean shouldMarkJads() { return false; }

	@ConfigItem(
			name = "Jad Marker Color",
			keyName = "jcJadMarkerColor",
			description = "Configures the color for 'Jad Markers'",
			position = 3
	)
	@Alpha
	default Color getJadMarkerColor() { return Color.WHITE; }

	@ConfigItem(
			name = "Jad Tick Counter",
			keyName = "jcTickCounter",
			description = "Displays a tick counter for each Jad displaying their next time they'll attack",
			position = 4
	)
	default boolean shouldDisplayTickCounters() { return false; }

	@ConfigItem(
			name = "Prayer InfoBox",
			keyName = "jcPrayerIB",
			description = "Displays an InfoBox showing what to pray",
			position = 5
	)
	default boolean shouldDisplayPrayerIB() { return false; }

	@ConfigItem(
			name = "Prayer Marker",
			keyName = "jcPrayerMarker",
			description = "Marks the prayer in the prayer book showing what to pray",
			position = 6
	)
	default boolean shouldDisplayPrayerMarker() { return false; }

	@ConfigItem(
			name = "Prayer Guitar Hero",
			keyName = "jcPrayerGuitarHero",
			description = "lmao",
			position = 7
	)
	default boolean shouldDisplayGuitarHero() { return false; }
}
