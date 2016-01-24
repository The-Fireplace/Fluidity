package the_fireplace.fluidity.enums;

import static cpw.mods.ironchest.IronChestType.DIAMOND;
import static cpw.mods.ironchest.IronChestType.GOLD;
import static cpw.mods.ironchest.IronChestType.IRON;
import static cpw.mods.ironchest.IronChestType.SILVER;
import static the_fireplace.fluidity.enums.FluidityIronChestType.BRASS;
import static the_fireplace.fluidity.enums.FluidityIronChestType.BRONZE;
import static the_fireplace.fluidity.enums.FluidityIronChestType.COLDIRON;
import static the_fireplace.fluidity.enums.FluidityIronChestType.ELECTRUM;
import static the_fireplace.fluidity.enums.FluidityIronChestType.INVAR;
import static the_fireplace.fluidity.enums.FluidityIronChestType.LEAD;
import static the_fireplace.fluidity.enums.FluidityIronChestType.NICKEL;
import static the_fireplace.fluidity.enums.FluidityIronChestType.STEEL;
import static the_fireplace.fluidity.enums.FluidityIronChestType.TIN;

import cpw.mods.ironchest.IronChestType;
import cpw.mods.ironchest.client.ModelHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import the_fireplace.fluidity.Fluidity;
import the_fireplace.fluidity.items.FluidityItemChestChanger;

public enum FluidityIronChestChangerType {
	BRONZEGOLD(BRONZE, GOLD, "bronzeGoldUpgrade", "mmm", "msm", "mmm"),
	BRONZESILVER(BRONZE, SILVER, "bronzeSilverUpgrade", "mGm", "GsG", "mGm"),
	TINSILVER(TIN, SILVER, "tinSilverUpgrade", "mmm", "msm", "mmm"),
	TINIRON(TIN, IRON, "tinIronUpgrade", "mGm", "GsG", "mGm"),
	BRASSSILVER(BRASS, SILVER, "brassSilverUpgrade", "mmm", "msm", "mmm"),
	BRASSIRON(BRASS, IRON, "brassIronUpgrade", "mGm", "GsG", "mGm"),
	LEADGOLD(LEAD, GOLD, "leadGoldUpgrade", "mGm", "GsG", "mGm"),
	STEELGOLD(STEEL, GOLD, "steelGoldUpgrade", "mGm", "GsG", "mGm"),
	NICKELGOLD(NICKEL, GOLD, "nickelGoldUpgrade", "mGm", "GsG", "mGm"),
	COLDIRONGOLD(COLDIRON, GOLD, "coldironGoldUpgrade", "mGm", "GsG", "mGm"),
	INVARDIAMOND(INVAR, DIAMOND, "invarDiamondUpgrade", "GGG", "msm", "GGG"),
	ELECTRUMDIAMOND(ELECTRUM, DIAMOND, "electrumDiamondUpgrade", "GGG", "msm", "GGG");

	private FluidityIronChestType source;
	private IronChestType target;
	public String sourcename;
	public String targetname;
	public String itemName;
	public FluidityItemChestChanger item;
	private String[] recipe;

	FluidityIronChestChangerType(FluidityIronChestType source, IronChestType target, String itemName, String... recipe)
	{
		this.source = source;
		this.target = target;
		sourcename = source.getName();
		targetname = target.getName();
		this.itemName = itemName;
		this.recipe = recipe;
	}

	public boolean canUpgrade(FluidityIronChestType from)
	{
		return from == this.source;
	}

	public int getTarget()
	{
		return this.target.ordinal();
	}

	public FluidityItemChestChanger buildItem()
	{
		item = new FluidityItemChestChanger(this);
		GameRegistry.registerItem(item, itemName);
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			ModelHelper.registerItem(item, Fluidity.MODID+":" + itemName);
		return item;
	}

	public void addRecipes()
	{
		for (String sourceMat : source.getMatList())
		{
			for (String targetMat : target.getMatList())
			{
				Object targetMaterial = FluidityIronChestType.translateOreName(targetMat);
				Object sourceMaterial = FluidityIronChestType.translateOreName(sourceMat);
				FluidityIronChestType.addRecipe(new ItemStack(item), recipe, 'm', targetMaterial, 's', sourceMaterial, 'G', Blocks.glass, 'O', Blocks.obsidian);
			}
		}
	}

	public static void buildItems()
	{
		for (FluidityIronChestChangerType type : values())
		{
			type.buildItem();
		}
	}

	public static void generateRecipes()
	{
		for (FluidityIronChestChangerType item : values())
		{
			item.addRecipes();
		}
	}
}