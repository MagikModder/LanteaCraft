package pcl.lc.items;

import pcl.lc.LanteaCraft;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTransportRingActivator extends Item {

	public ItemTransportRingActivator(int id) {
		super(id);
		LanguageRegistry.instance().addStringLocalization("item.transportRingActivator.name", "en_US",
				"Ring Transporter Activator (WIP - DO NOT USE)");
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected String getIconString() {
		return LanteaCraft.getAssetKey() + ":transport_ring_activator_" + LanteaCraft.getProxy().getRenderMode();
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4,
			int par5, int par6, int par7, float par8, float par9, float par10) {
		onItemClicked(par1ItemStack, par3World, par2EntityPlayer);
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		onItemClicked(par1ItemStack, par2World, par3EntityPlayer);
		return par1ItemStack;
	}
	
	private void onItemClicked(ItemStack stackOf, World world, EntityPlayer player) {
		
	}

}