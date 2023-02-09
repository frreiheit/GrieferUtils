package dev.l3g7.griefer_utils.features.item.item_info.info_suppliers;

import com.google.common.collect.ImmutableList;
import dev.l3g7.griefer_utils.features.item.item_info.ItemInfo;
import dev.l3g7.griefer_utils.file_provider.Singleton;
import dev.l3g7.griefer_utils.settings.ElementBuilder.MainElement;
import dev.l3g7.griefer_utils.settings.elements.BooleanSetting;
import dev.l3g7.griefer_utils.util.ItemUtil;
import net.labymod.utils.Material;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

@Singleton
public class ItemVerifier extends ItemInfo.ItemInfoSupplier {

	private static final String BAUMEISTER_PICKE_LORE = "Gut geeignet für Tunnelsysteme...";
	private static final String ABRISSSCHAUFEL_LORE = "Diese Schaufel kann ganze Hügel abtragen...";
	private static final ImmutableList<String> VERIFIED_ITEM_LORE = ImmutableList.of("§aDiese Item ist echt ✔");
	private static final int PREFIX_LENGTH = "§7Signiert von §a".length();

	@MainElement
	private final BooleanSetting enabled = new BooleanSetting()
		.name("Item-Verifizierer")
		.description("Zeigt unter manchen Items an, ob sie echt sind." +
			"\n§7(Ob sie eine Signatur von AbgegrieftHD haben oder eine Baumeister Picke / Geheime Abrissschaufel sind)")
		.icon(Material.NETHER_STAR);

	@Override
	public List<String> getToolTip(ItemStack itemStack) {
		List<String> lore = ItemUtil.getLore(itemStack);
		if (lore.isEmpty())
			return Collections.emptyList();

		String firstLoreLine = lore.get(0).replaceAll("§.", "");
		if (firstLoreLine.equals(BAUMEISTER_PICKE_LORE) || firstLoreLine.equals(ABRISSSCHAUFEL_LORE))
			return VERIFIED_ITEM_LORE;

		for (int i = lore.size() - 1; i >= 0; i--) {
			String line = lore.get(i);
			if (!line.startsWith("§7Signiert von §a"))
				continue;

			String substring = line.substring(PREFIX_LENGTH, line.indexOf(' ', PREFIX_LENGTH));
			if (substring.equals("AbgegrieftHD"))
				return VERIFIED_ITEM_LORE;
		}

		return Collections.emptyList();
	}



}