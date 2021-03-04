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

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.val;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.Prayer;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.PriorityBlockingQueue;

public final class JadModule
{
	private final static Map<Integer, Prayer> sapm = ImmutableMap.of(
			7590, Prayer.PROTECT_FROM_MELEE,
			7592, Prayer.PROTECT_FROM_MAGIC,
			7593, Prayer.PROTECT_FROM_MISSILES
	);

	private static final int JAD_ID = NpcID.JALTOKJAD_10623;

	private final Map<Integer, JadNode> active;
	private final Stack<NPC> cache;

	@Getter
	private final Queue<Pair<JadNode, Byte>> deferredActive;

	public JadModule()
	{
		this.active = new HashMap<>();
		this.cache = new Stack<>();

		// double the capacity incase lag and multiple get added
		this.deferredActive = new PriorityBlockingQueue<>(
				12, Comparator.comparing(Pair::getValue, Comparator.naturalOrder())
		);
	}

	public void cacheThis(NPC n)
	{
		if (n == null || n.getId() != JAD_ID)
		{
			return;
		}

		cache.push(n);
	}

	public void rm(NPC n)
	{
		if (!active.isEmpty())
		{
			active.keySet().removeIf(i -> i == n.getIndex());
		}

		if (!cache.isEmpty())
		{
			cache.removeIf(cn -> cn.getIndex() == n.getIndex());
		}
	}

	public void dec()
	{
		if (active.isEmpty())
		{
			return;
		}

		active.values().removeIf(JadNode::shouldRemove);
		active.values().forEach(JadNode::decTicks);

		if (deferredActive.peek() != null)
		{
			deferredActive.removeIf(p -> p.getKey().getTicks() <= 5 && p.getValue() <= 0);
			deferredActive.forEach(p -> p.setValue((byte) (p.getValue() - 1)));
		}
	}

	public void entry(NPC n)
	{
		if (!cache.contains(n) || !sapm.containsKey(n.getAnimation()))
		{
			return;
		}

		val idx = n.getIndex();
		val anim = n.getAnimation();

		JadNode node = new JadNode(n, 9, sapm.get(anim));
		active.put(idx, node);

		testQueue(deferredActive, node, idx);
	}

	private static void testQueue(Queue<Pair<JadNode, Byte>> q, JadNode n, int idx)
	{
		if (!q.isEmpty())
		{
			// rm if it already exists in case player lags
			q.removeIf(p -> p.getKey().getJad().getIndex() == idx);
		}

		q.offer(new MutablePair<>(n, (byte) 3));
	}

	public boolean canDisplaySpawns()
	{
		return active.isEmpty() && cache.isEmpty();
	}

	public Collection<JadNode> getNodes()
	{
		return active.values();
	}
}
