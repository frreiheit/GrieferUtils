package dev.l3g7.griefer_utils.asm.mappings;

import dev.l3g7.griefer_utils.asm.mappings.MappingNode.Class;
import dev.l3g7.griefer_utils.util.Reflection;

import java.lang.reflect.Field;

/**
 * Generated by SimpleMCMappings v0.1
 */
@SuppressWarnings("unused")
public class Mappings {

	// Java
	public static Class Boolean = new Class("Z");
	public static Class Void = new Class("V");
	public static Class Float = new Class("F");
	public static Class Int = new Class("I");

	// Minecraft v1.8.9
	public static $Block                Block = new $Block();
	public static $Entity               Entity = new $Entity();
	public static $EntityLivingBase     EntityLivingBase = new $EntityLivingBase();
	public static $EntityPlayer         EntityPlayer = new $EntityPlayer();
	public static $EntityRenderer       EntityRenderer = new $EntityRenderer();
	public static $GlStateManager       GlStateManager = new $GlStateManager();
	public static $IChatComponent       IChatComponent = new $IChatComponent();
	public static $Material             Material = new $Material();
	public static $Potion               Potion = new $Potion();
	public static $RendererLivingEntity RendererLivingEntity = new $RendererLivingEntity();

	// GrieferUtils
	public static $EventHandler EventHandler = new $EventHandler();
	public static $TrueSight    TrueSight = new $TrueSight();

	// Mapping methods
	public static Class getClass(String mapping) {
		switch (mapping.toLowerCase()) {
			case "boolean": return Boolean;
			case "void": return Void;
			case "float": return Float;
			case "int": return Int;
		}
		for(Field f : Mappings.class.getDeclaredFields()) {
			Class mClass = Reflection.get(f, (Object) null);
			if(mClass.toString().equals(mapping.replace('.', '/')))
				return Reflection.get(f, (Object) null);
		}

		throw new RuntimeException("Could not find mapping class for '" + mapping + "' !");
	}

	public static boolean obfuscated = false;

	// Mapping Classes
	public static class $Block extends Class {
		private $Block() { super("net/minecraft/block/Block", "afh"); }

		public Method getMaterial() { return new Method("t", "getMaterial", this, Material); }
	}
	public static class $Entity extends Class {
		private $Entity() { super("net/minecraft/entity/Entity", "pk"); }

		public Method isBurning() { return new Method("at", "isBurning", this, Boolean); }
		public Method isInvisibleToPlayer() { return new Method("f", "isInvisibleToPlayer", this, Boolean, EntityPlayer); }
	}
	public static class $EntityLivingBase extends Class {
		private $EntityLivingBase() { super("net/minecraft/entity/EntityLivingBase", "pr"); }

		public Method isPotionActive() { return new Method("a", "isPotionActive", this, Boolean, Potion); }
	}
	public static class $EntityPlayer extends Class {
		private $EntityPlayer() { super("net/minecraft/entity/player/EntityPlayer", "wn"); }

		public Method getDisplayName() { return new Method("f_", "getDisplayName", this, IChatComponent); }
		public Method isInvisibleToPlayer() { return new Method("f", "isInvisibleToPlayer", this, Boolean, EntityPlayer); }
	}
	public static class $EntityRenderer extends Class {
		private $EntityRenderer() { super("net/minecraft/client/renderer/EntityRenderer", "bfk"); }

		public Method setupFog() { return new Method("a", "setupFog", this, Void, Int, Float); }
	}
	public static class $GlStateManager extends Class {
		private $GlStateManager() { super("net/minecraft/client/renderer/GlStateManager", "bfl"); }

		public Method color() { return new Method("c", "color", this, Void, Float, Float, Float, Float); }
	}
	public static class $IChatComponent extends Class {
		private $IChatComponent() { super("net/minecraft/util/IChatComponent", "eu"); }
	}
	public static class $Material extends Class {
		private $Material() { super("net/minecraft/block/material/Material", "arm"); }

		public final Field water = new Field("h", "water", this, this);
		public final Field lava = new Field("i", "lava", this, this);
	}
	public static class $Potion extends Class {
		private $Potion() { super("net/minecraft/potion/Potion", "pe"); }

		public final Field blindness = new Field("q", "blindness", this, this);
	}
	public static class $RendererLivingEntity extends Class {
		private $RendererLivingEntity() { super("net/minecraft/client/renderer/entity/RendererLivingEntity", "bjl"); }

		public Method renderModel() { return new Method("a", "renderModel", this, Void, EntityLivingBase, Float, Float, Float, Float, Float, Float); }
	}
	public static class $EventHandler extends Class {
		public $EventHandler() { super("dev/l3g7/griefer_utils/event/EventHandler", null); }

		public Method shouldNotBeBurning() { return new Method(null, "shouldNotBeBurning", this, Boolean, Entity); }
		public Method shouldBeVisible() { return new Method(null, "shouldBeVisible", this, Boolean, Entity); }
		public Method modifyDisplayName() { return new Method(null, "modifyDisplayName", this, IChatComponent, IChatComponent, EntityPlayer); }
		public Method shouldRenderFog() { return new Method(null, "shouldRenderFog", this, Boolean, Int); }
	}
	public static class $TrueSight extends Class {
		public $TrueSight() { super("dev/l3g7/griefer_utils/features/tweaks/TrueSight", null); }

		public Method getRenderModelAlpha() { return new Method(null, "getRenderModelAlpha", this, Float); }
	}
}
