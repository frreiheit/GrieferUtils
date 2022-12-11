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
	public static Class       Boolean = new Class("Z");
	public static Class       Void = new Class("V");
	public static Class       Float = new Class("F");
	public static Class       Int = new Class("I");
	public static $String     String = new $String();
	public static $Integer    Integer = new $Integer();
	public static $Object     Object = new $Object();
	public static $List       List = new $List();
	public static $Collection Collection = new $Collection();

	// Minecraft v1.8.9
	public static $Block                Block = new $Block();
	public static $ChatLine             ChatLine = new $ChatLine();
	public static $Entity               Entity = new $Entity();
	public static $EntityLivingBase     EntityLivingBase = new $EntityLivingBase();
	public static $EntityPlayer         EntityPlayer = new $EntityPlayer();
	public static $EntityRenderer       EntityRenderer = new $EntityRenderer();
	public static $EnumParticleTypes    EnumParticleTypes = new $EnumParticleTypes();
	public static $FontRenderer         FontRenderer = new $FontRenderer();
	public static $GlStateManager       GlStateManager = new $GlStateManager();
	public static $GuiChest             GuiChest = new $GuiChest();
	public static $GuiIngame            GuiIngame = new $GuiIngame();
	public static $ChatRenderer         ChatRenderer = new $ChatRenderer();
	public static $IChatComponent       IChatComponent = new $IChatComponent();
	public static $NetHandlerPlayClient NetHandlerPlayClient = new $NetHandlerPlayClient();
	public static $Material             Material = new $Material();
	public static $Potion               Potion = new $Potion();
	public static $Packet               Packet = new $Packet();
	public static $RendererLivingEntity RendererLivingEntity = new $RendererLivingEntity();
	public static $ScoreObjective       ScoreObjective = new $ScoreObjective();
	public static $ScaledResolution     ScaledResolution = new $ScaledResolution();
	public static $WorldClient          WorldClient = new $WorldClient();

	// GrieferUtils
	public static $EventHandler      EventHandler = new $EventHandler();
	public static $TrueSight         TrueSight = new $TrueSight();
	public static $ScoreboardHandler ScoreBoardHandler = new $ScoreboardHandler();
	public static $MessageSkulls     MessageSkulls = new $MessageSkulls();

	// Mapping methods
	public static Class getClass(String mapping) {
		switch (mapping.toLowerCase()) {
			case "boolean": return Boolean;
			case "void": return Void;
			case "float": return Float;
			case "int": return Int;
		}
		for(Field f : Mappings.class.getDeclaredFields()) {
			if(!Class.class.isAssignableFrom(f.getType()))
				continue;

			Class mClass = Reflection.get(f, (Object) null);
			if(mClass.unobfuscated.equals(mapping.replace('.', '/')))
				return Reflection.get(f, (Object) null);
		}

		throw new RuntimeException("Could not find mapping class for '" + mapping + "' !");
	}

	// Mapping Classes
	public static class $String extends Class {
		private $String() { super("java/lang/String", null); }
	}
	public static class $Object extends Class {
		private $Object() { super("java/lang/Object", null); }
	}
	public static class $Integer extends Class {
		private $Integer() { super("java/lang/Integer", null); }
	}
	public static class $List extends Class {
		private $List() { super("java/util/List", null); }

		public Method size() { return new Method(null, "size", this, Int); }
	}
	public static class $Collection extends Class {
		private $Collection() { super("java/util/Collection", null); }
	}
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
	public static class $FontRenderer extends Class {
		private $FontRenderer() { super("net/minecraft/client/gui/FontRenderer", "avn"); }
	}
	public static class $GlStateManager extends Class {
		private $GlStateManager() { super("net/minecraft/client/renderer/GlStateManager", "bfl"); }

		public Method color() { return new Method("c", "color", this, Void, Float, Float, Float, Float); }
		public Method enableBlend() { return new Method("l", "enableBlend", this, Void); }
	}
	public static class $GuiIngame extends Class {
		private $GuiIngame() { super("net/minecraft/client/gui/GuiIngame", "avo"); }

		public Method getFontRenderer () { return new Method("f", "getFontRenderer", this, FontRenderer); }
		public Method renderScoreboard() { return new Method("a", "renderScoreboard", this, Void, ScoreObjective, ScaledResolution); }
	}
	public static class $GuiChest extends Class {
		private $GuiChest() { super("net/minecraft/client/gui/inventory/GuiChest", "ayr"); }

		public Method drawGuiContainerForegroundLayer() { return new Method("b", "drawGuiContainerForegroundLayer", this, Void, Int, Int); }
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
	public static class $Packet extends Class {
		private $Packet() { super("net/minecraft/network/Packet", "ff"); }
	}
	public static class $NetHandlerPlayClient extends Class {
		private $NetHandlerPlayClient() { super("net/minecraft/client/network/NetHandlerPlayClient", "bcy"); }

		public Method addToSendQueue() { return new Method("a", "addToSendQueue", this, Void, Packet); }
	}
	public static class $ScoreObjective extends Class {
		private $ScoreObjective() { super("net/minecraft/scoreboard/ScoreObjective", "auk"); }
	}
	public static class $ScaledResolution extends Class {
		private $ScaledResolution() { super("net/minecraft/client/gui/ScaledResolution", "avr"); }
	}
	public static class $EventHandler extends Class {
		public $EventHandler() { super("dev/l3g7/griefer_utils/event/EventHandler", null); }

		public Method shouldNotBeBurning() { return new Method(null, "shouldNotBeBurning", this, Boolean, Entity); }
		public Method shouldBeVisible() { return new Method(null, "shouldBeVisible", this, Boolean, Entity); }
		public Method modifyDisplayName() { return new Method(null, "modifyDisplayName", this, IChatComponent, IChatComponent, EntityPlayer); }
		public Method shouldRenderFog() { return new Method(null, "shouldRenderFog", this, Boolean, Int); }
		public Method shouldRenderBarrier() { return new Method(null, "shouldRenderBarrier", this, Boolean); }
		public Method shouldSendPacket() { return new Method(null, "shouldSendPacket", this, Boolean, Packet); }
		public Method drawGuiContainerForegroundLayer() { return new Method(null, "drawGuiContainerForegroundLayer", this, Void, GuiChest); }
		public Method addChatLine() { return new Method(null, "addChatLine", this, Void, String); }
	}
	public static class $TrueSight extends Class {
		public $TrueSight() { super("dev/l3g7/griefer_utils/features/tweaks/TrueSight", null); }

		public Method getRenderModelAlpha() { return new Method(null, "getRenderModelAlpha", this, Float, EntityLivingBase); }
	}
	public static class $EnumParticleTypes extends Class {
		public $EnumParticleTypes() { super("net/minecraft/util/EnumParticleTypes", "cy"); }

		public Field barrier = new Field("J", "BARRIER", this, this);
	}
	public static class $WorldClient extends Class {
		public $WorldClient() { super("net/minecraft/client/multiplayer/WorldClient", "bdb"); }

		public Method doVoidFogParticles() { return new Method("b", "doVoidFogParticles", this, Void, Int, Int, Int); }
	}
	public static class $ScoreboardHandler extends Class {
		public $ScoreboardHandler() { super("dev/l3g7/griefer_utils/features/tweaks/scoreboard_info/ScoreboardHandler", null); }

		public Method filterScores() { return new Method(null, "filterScores", this, Collection, Collection); }
		public Method shouldNotUnlockScoreboard() { return new Method(null, "shouldNotUnlockScoreboard", this, Boolean); }
	}
	public static class $ChatRenderer extends Class {
		public $ChatRenderer() { super("net/labymod/ingamechat/renderer/ChatRenderer", null); }

		public Method renderChat() { return new Method(null, "renderChat", this, Void, Int); }
		public Method addChatLine() { return new Method(null, "addChatLine", this, Void, String, Boolean, String, Object, Int, Int, Integer, Boolean); }
	}
	public static class $ChatLine extends Class {
		public $ChatLine() { super("net/labymod/ingamechat/renderer/ChatLine", null); }
	}
	public static class $MessageSkulls extends Class {
		public $MessageSkulls() { super("dev/l3g7/griefer_utils/features/tweaks/MessageSkulls", null); }

		public Method renderSkull() { return new Method(null, "renderSkull", this, Void, ChatLine, Int); }
	}
}
