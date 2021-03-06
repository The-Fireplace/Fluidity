package the_fireplace.fluidity.compat;

import crazypants.enderio.ModObject;
import crazypants.enderio.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * @author The_Fireplace
 */
public class ElectricAdvantageEnderIO implements IModCompat {
    @Override
    public void preInit() {

    }

    @Override
    public void init() {
        OreDictionary.registerOre("silicon", new ItemStack(ModObject.itemMaterial.getItem(), 1, Material.SILICON.ordinal()));
        OreDictionary.registerOre("ingotSilicon", new ItemStack(ModObject.itemMaterial.getItem(), 1, Material.SILICON.ordinal()));
    }

    @Override
    public void postInit() {

    }

    @Override
    public void registerInvRenderers() {

    }
}
