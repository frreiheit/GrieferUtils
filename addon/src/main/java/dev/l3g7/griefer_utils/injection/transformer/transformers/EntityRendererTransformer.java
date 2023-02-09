/*
 * This file is part of GrieferUtils (https://github.com/L3g7/GrieferUtils).
 *
 * Copyright 2020-2023 L3g7
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.l3g7.griefer_utils.injection.transformer.transformers;

import dev.l3g7.griefer_utils.event.events.render.SetupFogEvent;
import dev.l3g7.griefer_utils.event.events.render.SetupFogEvent.FogType;
import dev.l3g7.griefer_utils.injection.transformer.Transformer;
import dev.l3g7.griefer_utils.injection.transformer.Transformer.Target;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraftforge.common.MinecraftForge;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

/**
 * Injects the {@link SetupFogEvent} into {@link EntityRenderer}.
 */
@Target("net.minecraft.client.renderer.EntityRenderer")
public class EntityRendererTransformer extends Transformer {

	@Override
	protected void process() {
		MethodNode method = getMethod("setupFog", "(IF)V");
		ListIterator<AbstractInsnNode> it = method.instructions.iterator();
		while (it.hasNext()) {
			AbstractInsnNode node = it.next();
			if (matches(node, GETSTATIC, "net/minecraft/potion/Potion", "blindness", "Lnet/minecraft/potion/Potion;")
				&& matches(it.next(), INVOKEVIRTUAL, "net/minecraft/entity/EntityLivingBase", "isPotionActive", "(Lnet/minecraft/potion/Potion;)Z"))
				inject(method, it.next(), ICONST_0);
			else if (matches(node, INVOKEVIRTUAL, "net/minecraft/block/Block", "getMaterial", "()Lnet/minecraft/block/material/Material;")) {
				AbstractInsnNode comparison = it.next();
				if (matches(comparison, GETSTATIC, "net/minecraft/block/material/Material", "water", "Lnet/minecraft/block/material/Material;"))
					inject(method, it.next(), ICONST_1);
				else if (matches(comparison, GETSTATIC, "net/minecraft/block/material/Material", "lava", "Lnet/minecraft/block/material/Material;"))
					inject(method, it.next(), ICONST_2);
			}
		}
	}

	private void inject(MethodNode method, AbstractInsnNode target, int type) {
		InsnList insns = new InsnList();
		insns.add(new InsnNode(type));
		insns.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(EntityRendererTransformer.class), "shouldRenderFog", "(I)Z", false));
		insns.add(new JumpInsnNode(IFEQ, ((JumpInsnNode) target).label));
		method.instructions.insert(target, insns);
	}

	public static boolean shouldRenderFog(int type) {
		return !MinecraftForge.EVENT_BUS.post(new SetupFogEvent(FogType.values()[type]));
	}

}