package dev.l3g7.griefer_utils.features.item.generic_item_saver;

import dev.l3g7.griefer_utils.file_provider.FileProvider;
import dev.l3g7.griefer_utils.settings.ElementBuilder;
import dev.l3g7.griefer_utils.settings.elements.BooleanSetting;
import dev.l3g7.griefer_utils.util.misc.AddonsGuiWithCustomBackButton;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import static dev.l3g7.griefer_utils.util.MinecraftUtil.drawUtils;

public class ItemDisplaySetting extends ControlElement implements ElementBuilder<ItemDisplaySetting> {

	public final BooleanSetting drop;
	public final BooleanSetting leftclick;
	public final BooleanSetting rightclick;

	private final GuiButton button = new GuiButton(-2, 0, 0, 20, 20, "");
	private final IconStorage iconStorage = new IconStorage();
	private final ItemStack stack;
	private boolean hoveringDelete = false;
	private boolean hoveringEdit = false;

	public ItemDisplaySetting(ItemStack stack) {
		super("§cNo name set", null);
		setSettingEnabled(false);
		this.stack = stack;
		icon(stack);
		name(stack.getDisplayName());

		drop = new BooleanSetting()
			.name("Droppen unterbinden")
			.description("Ob das Droppen dieses Items unterbunden werden soll.")
			.defaultValue(true)
			.icon(Material.DROPPER);

		leftclick = new BooleanSetting()
			.name("Linksklicks unterbinden")
			.description("Ob Linksklicks mit diesem Item unterbunden werden soll.")
			.defaultValue(stack.isItemStackDamageable())
			.icon(Material.DIAMOND_SWORD);

		rightclick = new BooleanSetting()
			.name("Rechtsklicks unterbinden")
			.description("Ob Rechtsklicks mit diesem Item unterbunden werden soll.")
			.defaultValue(!stack.isItemStackDamageable())
			.icon(Material.BOW);

		subSettings(drop, leftclick, rightclick);
	}

	@Override
	public int getObjectWidth() {
		return 0;
	}

	@Override
	public IconStorage getIconStorage() {
		return iconStorage;
	}

	public ItemStack getStack() {
		return stack;
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (hoveringEdit) {
			button.playPressSound(mc.getSoundHandler());
			mc.displayGuiScreen(new AddonsGuiWithCustomBackButton(() -> FileProvider.getSingleton(GenericItemSaver.class).onChange(), this));
			return;
		}

		if (!hoveringDelete)
			return;

		button.playPressSound(mc.getSoundHandler());
		GenericItemSaver gis = FileProvider.getSingleton(GenericItemSaver.class);
		gis.getMainElement().getSubSettings().getElements().remove(this);
		gis.onChange();
	}

	@Override
	public void draw(int x, int y, int maxX, int maxY, int mouseX, int mouseY) {
		hideSubListButton();
		super.draw(x, y, maxX, maxY, mouseX, mouseY);
		drawIcon(x, y);

		mouseOver = mouseX > x && mouseX < maxX && mouseY > y && mouseY < maxY;

		int xPosition = maxX - 20;
		double yPosition = y + 4.5;

		hoveringDelete = mouseX >= xPosition && mouseY >= yPosition && mouseX <= xPosition + 15.5 && mouseY <= yPosition + 16;

		xPosition -= 20;

		hoveringEdit = mouseX >= xPosition && mouseY >= yPosition && mouseX <= xPosition + 15.5 && mouseY <= yPosition + 16;

		if (!mouseOver)
			return;

		mc.getTextureManager().bindTexture(new ResourceLocation("labymod/textures/misc/blocked.png"));
		drawUtils().drawTexture(maxX - (hoveringDelete ? 20 : 19), y + (hoveringDelete ? 3.5 : 4.5), 256, 256, hoveringDelete ? 16 : 14, hoveringDelete ? 16 : 14);

		mc.getTextureManager().bindTexture(new ResourceLocation("griefer_utils/icons/pencil.png"));
		drawUtils().drawTexture(maxX - (hoveringEdit ? 40 : 39), y + (hoveringEdit ? 3.5 : 4.5), 256, 256, hoveringEdit ? 16 : 14, hoveringEdit ? 16 : 14);
	}

}