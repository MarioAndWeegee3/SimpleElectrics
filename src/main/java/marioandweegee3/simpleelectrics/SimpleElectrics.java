package marioandweegee3.simpleelectrics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import marioandweegee3.simpleelectrics.common.items.DustItems;
import marioandweegee3.simpleelectrics.common.items.IngotItems;
import marioandweegee3.simpleelectrics.common.items.NuggetItems;
import marioandweegee3.simpleelectrics.common.items.tools.IronTools;
import marioandweegee3.simpleelectrics.common.items.tools.SteelTools;
import marioandweegee3.simpleelectrics.common.tool_types.Axe;
import marioandweegee3.simpleelectrics.common.tool_types.Hammer;
import marioandweegee3.simpleelectrics.common.tool_types.Hoe;
import marioandweegee3.simpleelectrics.common.tool_types.Knife;
import marioandweegee3.simpleelectrics.common.tool_types.Pickaxe;
import marioandweegee3.simpleelectrics.common.tool_types.Shovel;
import marioandweegee3.simpleelectrics.common.tool_types.Sword;
import marioandweegee3.simpleelectrics.common.blocks.ResourceBlocks;
import marioandweegee3.simpleelectrics.common.item_groups.SEMachines;
import marioandweegee3.simpleelectrics.common.item_groups.SEMaterials;
import marioandweegee3.simpleelectrics.common.item_groups.SETools;
import marioandweegee3.simpleelectrics.common.item_tiers.Tiers;

@SuppressWarnings("unused")
@Mod(SimpleElectrics.modid)
public class SimpleElectrics {
	public static final String modid = "simpleelectrics";
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static SEMachines machines;
	private static SEMaterials materials;
	private static SETools tools;
	
	public SimpleElectrics() {
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		//FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		LOGGER.info("Attempting to register...");
		MinecraftForge.EVENT_BUS.register(this);
		LOGGER.info("Registered!");
	}
	
	//private void setup(final FMLCommonSetupEvent event) {
		
	//}
	
	//private void clientRegistries(final FMLClientSetupEvent event){
        
    //}
	
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
			ResourceBlocks.steel_block = createBlock(Material.IRON, 5.0f, SoundType.METAL, "steel_block")
		);
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
			DustItems.diamond_dust = createItem(materials,"diamond_dust"),
			DustItems.iron_dust = createItem(materials,"iron_dust"),
			DustItems.steel_dust = createItem(materials,"steel_dust"),
			DustItems.gold_dust = createItem(materials,"gold_dust"),
			DustItems.quartz_dust = createItem(materials,"quartz_dust"),
			DustItems.red_iron_dust = createItem(materials,"red_iron_dust"),
			
			IngotItems.steel_ingot = createItem(materials, "steel_ingot"),
			IngotItems.red_iron_ingot = createItem(materials, "red_iron_ingot"),
			
			NuggetItems.steel_nugget = createItem(materials, "steel_nugget"),
				
			ResourceBlocks.steel_block_item = createItemBlock(ResourceBlocks.steel_block, materials),
				
			IronTools.iron_hammer = Hammer.create(ItemTier.IRON, tools, "iron_hammer"),
			IronTools.iron_knife = Knife.create(ItemTier.IRON, 1, tools, "iron_knife"),
				
			SteelTools.steel_axe = Axe.create(Tiers.STEEL, 2.0f, tools, "steel_axe"),
			SteelTools.steel_hammer = Hammer.create(Tiers.STEEL, tools, "steel_hammer"),
			SteelTools.steel_shovel = Shovel.create(Tiers.STEEL, tools, "steel_shovel"),
			SteelTools.steel_hoe = Hoe.create(Tiers.STEEL, tools, "steel_hoe"),
			SteelTools.steel_pickaxe = Pickaxe.create(Tiers.STEEL, tools, "steel_hoe"),
			SteelTools.steel_sword = Sword.create(Tiers.STEEL, 0, tools, "steel_sword"),
			SteelTools.steel_knife = Knife.create(Tiers.STEEL, -1, tools, "steel_knife")
		);
	}
	
	
	public static ItemBlock createItemBlock(Block block, ItemGroup group) {
		return (ItemBlock) new ItemBlock(block, createProperties(group)).setRegistryName(block.getRegistryName());
	}
	
	public static Block createBlock(Material mat, float hardness, SoundType sType, String name) {
		return new Block(Block.Properties.create(mat).hardnessAndResistance(hardness).sound(sType)).setRegistryName(name);
	}
	
	public static Item createItem(ItemGroup group, String name){
        return new Item(createProperties(group)).setRegistryName(location(name));
    }
	
	public static Item.Properties createProperties(ItemGroup group){
		return new Item.Properties().group(group);
	}
	
	public static ResourceLocation location(String name) {
		return new ResourceLocation(modid,name);
	}
	
	
}
