package the_fireplace.fluidity;

import com.google.common.collect.Lists;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import the_fireplace.fluidity.compat.*;
import the_fireplace.fluidity.proxy.CommonProxy;

import java.util.ArrayList;

/**
 * @author The_Fireplace
 */
@Mod(modid=Fluidity.MODID, name=Fluidity.MODNAME)
public class Fluidity {
	public static final String MODID = "fluidity";
	public static final String MODNAME = "Fluidity";
	public static String VERSION;
	public final ArrayList<String> supportedMods = Lists.newArrayList();
	@Instance(MODID)
	public static Fluidity instance;

	@SidedProxy(clientSide = "the_fireplace.fluidity.proxy.ClientProxy", serverSide = "the_fireplace.fluidity.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static final CreativeTabs tabFluidity = new TabFluidity();

	public boolean isClient=false;

	private void addSupported(){
		supportedMods.add("adobeblocks");
		supportedMods.add("basemetals");
		//supportedMods.add("Baubles");
		//supportedMods.add("cannibalism");
		supportedMods.add("cookingforblockheads");
		supportedMods.add("invtweaks");
		supportedMods.add("IronChest");
		supportedMods.add("moreanvils");
		supportedMods.add("realstonetools");
		//supportedMods.add("Thaumcraft");
		supportedMods.add("frt");
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		if(event.getSide().isClient())
			isClient=true;
		addSupported();
		IModCompat compat;

		if(Loader.isModLoaded("adobeblocks") && Loader.isModLoaded("frt")){
			compat = new AdobeBlocksFRT();
			compat.preInit();
		}
		/*if(Loader.isModLoaded("basemetals") && Loader.isModLoaded("cannibalism")){
			compat = new BaseMetalsCannibalism();
			compat.preInit();
		}*/
		if(Loader.isModLoaded("basemetals") && Loader.isModLoaded("moreanvils")){
			compat = new BaseMetalsMoreAnvils();
			compat.preInit();
		}
		/*if(Loader.isModLoaded("cannibalism") && Loader.isModLoaded("Thaumcraft")){
			compat = new CannibalismThaumcraft();
			compat.preInit();
		}*/
		if(Loader.isModLoaded("IronChest") && hasCommonMetals()){
			compat = new FluidityIronChests();
			compat.preInit();
		}
		/*if(Loader.isModLoaded("Thaumcraft") && Loader.isModLoaded("frt")){
			compat = new ThaumcraftFRT();
			compat.preInit();
		}*/
		overrideDescription(event);
	}
	@EventHandler
	public void init(FMLInitializationEvent event){
		IModCompat compat;
		if(Loader.isModLoaded("adobeblocks") && Loader.isModLoaded("frt")){
			compat = new AdobeBlocksFRT();
			compat.init();
			if(event.getSide().isClient())
				compat.registerInvRenderers();
		}
		/*if(Loader.isModLoaded("basemetals") && Loader.isModLoaded("cannibalism")){
			compat = new BaseMetalsCannibalism();
			compat.init();
			if(event.getSide().isClient())
				compat.registerInvRenderers();
		}*/
		if(Loader.isModLoaded("basemetals") && Loader.isModLoaded("moreanvils")){
			compat = new BaseMetalsMoreAnvils();
			compat.init();
			if(event.getSide().isClient())
				compat.registerInvRenderers();
		}
		/*if(Loader.isModLoaded("cannibalism") && Loader.isModLoaded("realstonetools")){
			compat = new CannibalismRealStoneTools();
			compat.init();
		}*/
		/*if(Loader.isModLoaded("cannibalism") && Loader.isModLoaded("Thaumcraft")){
			compat = new CannibalismThaumcraft();
			compat.init();
			if(event.getSide().isClient())
				compat.registerInvRenderers();
		}*/
		if(Loader.isModLoaded("IronChest") && hasCommonMetals()){
			compat = new FluidityIronChests();
			compat.init();
			if(event.getSide().isClient())
				compat.registerInvRenderers();
		}
		/*if(Loader.isModLoaded("Thaumcraft") && Loader.isModLoaded("frt")){
			compat = new ThaumcraftFRT();
			compat.init();
			if(event.getSide().isClient())
				compat.registerInvRenderers();
		}*/
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		IModCompat compat;
		if(Loader.isModLoaded("adobeblocks") && Loader.isModLoaded("cookingforblockheads")){
			compat = new AdobeBlocksCookingForBlockheads();
			compat.postInit();
		}
		if(Loader.isModLoaded("cookingforblockheads") && Loader.isModLoaded("frt")){
			compat = new CookingForBlockheadsFRT();
			compat.postInit();
		}
	}
	private final String[] metalArray = new String[]{"ingotBronze", "ingotInvar", "ingotElectrum", "ingotTin", "ingotBrass", "ingotLead", "ingotSteel", "ingotNickel"};
	private boolean hasCommonMetals(){
		for(String s:metalArray){
			if(!OreDictionary.getOres(s).isEmpty())
				return true;
		}
		return false;
	}
	private void overrideDescription(FMLPreInitializationEvent event){
		String mods = "";
		for(String mid:supportedMods)
			if(Loader.isModLoaded(mid))
				for(ModContainer mod:Loader.instance().getActiveModList())
					if(mid.equals(mod.getModId()))
						mods += "\n"+mod.getName();
		if(mods.equals(""))
			mods = mods.concat("\n"+ TextFormatting.RED+"none");
		event.getModMetadata().description = I18n.translateToLocal("fluidity.desc.line1")+"\n"
				+ I18n.translateToLocal("fluidity.desc.line2")
				+ mods;
	}
	public static final String LATEST = "https://dl.dropboxusercontent.com/s/532e9ihhlbnmr2j/latest.version?dl=0";
	public static final String DOWNLOADURL = "https://dl.dropboxusercontent.com/s/6jxiwwls1967f3b/url.txt?dl=0";
}
