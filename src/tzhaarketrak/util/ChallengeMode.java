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

import com.google.common.collect.Lists;
import lombok.Getter;
import net.runelite.api.coords.LocalPoint;

import java.util.List;

@Getter
public enum ChallengeMode
{
	ONE(
			new LocalPoint(7104, 7104)
	),
	TWO(
			new LocalPoint(7104, 7104),
			new LocalPoint(7104, 5184)
	),
	THREE(
			new LocalPoint(6208, 5696),
			new LocalPoint(7104, 7104),
			new LocalPoint(8000, 5696)
	),
	FOUR(
			new LocalPoint(6464, 5440),
			new LocalPoint(6464, 6848),
			new LocalPoint(7744, 5440),
			new LocalPoint(7744, 6848)
	),
	FIVE(
			new LocalPoint(6208, 6336),
			new LocalPoint(6592, 5440),
			new LocalPoint(7104, 7104),
			new LocalPoint(8000, 6336),
			new LocalPoint(7616, 5440)
	),
	SIX(
			new LocalPoint(6208, 5696),
			new LocalPoint(6208, 6592),
			new LocalPoint(7104, 5184),
			new LocalPoint(7104, 7104),
			new LocalPoint(8000, 5696),
			new LocalPoint(8000, 6592)
	);

	private final List<LocalPoint> points;

	ChallengeMode(LocalPoint... points)
	{
		this.points = Lists.newArrayList(points);
	}

	public static ChallengeMode of(String chatMessage)
	{
		switch (chatMessage)
		{
			case "You enter the Inferno for TzHaar-Ket-Rak's First Challenge.":
				return ONE;
			case "You enter the Inferno for TzHaar-Ket-Rak's Second Challenge.":
				return TWO;
			case "You enter the Inferno for TzHaar-Ket-Rak's Third Challenge.":
				return THREE;
			case "You enter the Inferno for TzHaar-Ket-Rak's Fourth Challenge.":
				return FOUR;
			case "You enter the Inferno for TzHaar-Ket-Rak's Fifth Challenge.":
				return FIVE;
			case "You enter the Inferno for TzHaar-Ket-Rak's Sixth Challenge.":
				return SIX;
			default: return null;
		}
	}
}
