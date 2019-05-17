package com.marioandweegee3.simpleelectrics;

import com.google.common.eventbus.EventBus;

import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTier;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.marioandweegee3.simpleelectrics.common.items.BasicItems;
import com.marioandweegee3.simpleelectrics.common.blocks.BasicBlocks;
import com.marioandweegee3.simpleelectrics.item_groups.Electrics;
import com.marioandweegee3.simpleelectrics.item_groups.SEMaterials;
import com.marioandweegee3.simpleelectrics.item_groups.SETools;
import com.marioandweegee3.simpleelectrics.common.items.Tools;
import com.marioandweegee3.simpleelectrics.common.tool_materials.Materials;
import com.marioandweegee3.simpleelectrics.common.items.tool_types.Axe;
import com.marioandweegee3.simpleelectrics.common.items.tool_types.Pickaxe;
import com.marioandweegee3.simpleelectrics.common.items.tool_types.Hammer;
import com.marioandweegee3.simpleelectrics.common.items.tool_types.Knife;
import com.marioandweegee3.simpleelectrics.common.blocks.GrinderBlock;
import com.marioandweegee3.simpleelectrics.util.GuiHandler;

@Mod("simpleelectrics")
public class SimpleElectrics{
    public static final String modid = "simpleelectrics";
    public static SimpleElectrics instance;

    public static final ItemGroup electrics = new Electrics();
    public static final ItemGroup materials = new SEMaterials();
    public static final ItemGroup tools = new SETools();

    public static final Logger logger = LogManager.getLogger(modid);

    public SimpleElectrics(){
        instance = this;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
        registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> GuiHandler::openGui);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static <T> void registerExtensionPoint(ExtensionPoint<T> point, Supplier<T> extension) {
		ModLoadingContext.get().<T>registerExtensionPoint(point, extension);
    }

    private void setup(final FMLCommonSetupEvent event){

    }

    private void clientRegistries(final FMLClientSetupEvent event){
        
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents{
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event){
            event.getRegistry().registerAll(
                BasicItems.steel_ingot = createItem(materials, "steel_ingot"),
                BasicItems.red_iron_ingot = createItem(materials, "red_iron_ingot"),
                BasicItems.steel_nugget = createItem(materials, "steel_nugget"),
                BasicItems.iron_dust = createItem(materials, "iron_dust"),
                BasicItems.steel_dust = createItem(materials, "steel_dust"),
                BasicItems.red_iron_dust = createItem(materials, "red_iron_dust"),
                BasicItems.gold_dust = createItem(materials, "gold_dust"),
                BasicItems.diamond_dust = createItem(materials, "diamond_dust"),
                BasicItems.quartz_dust = createItem(materials, "quartz_dust"),

                Tools.steel_axe = new Axe(Materials.steel,2.0f,-3.0f,createProperties(tools)).setRegistryName(location("steel_axe")),
                Tools.steel_pickaxe = new Pickaxe(Materials.steel,-2,-2.8f,createProperties(tools)).setRegistryName(location("steel_pickaxe")),
                Tools.steel_shovel = new ItemSpade(Materials.steel,-2.0f,-2.8f,createProperties(tools)).setRegistryName(location("steel_shovel")),
                Tools.steel_hoe = new ItemHoe(Materials.steel,4.0f,createProperties(tools)).setRegistryName(location("steel_hoe")),
                Tools.steel_sword = new ItemSword(Materials.steel,0,-2.4f,createProperties(tools)).setRegistryName(location("steel_sword")),
                Tools.steel_hammer = new Hammer(Materials.steel,createProperties(tools)).setRegistryName(location("steel_hammer")),

                Tools.iron_hammer = new Hammer(ItemTier.IRON,createProperties(tools)).setRegistryName("iron_hammer"),
                Tools.iron_knife = new Knife(ItemTier.IRON,1,createProperties(tools)).setRegistryName("iron_knife"),

                BasicItems.steel_block = new ItemBlock(BasicBlocks.steel_block, createProperties(electrics)).setRegistryName(BasicBlocks.steel_block.getRegistryName()),
                BasicItems.grinder = new ItemBlock(GrinderBlock.grinder_off, createProperties(electrics)).setRegistryName(GrinderBlock.grinder_off.getRegistryName())
            );
        }

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event){
            event.getRegistry().registerAll(
                BasicBlocks.steel_block = new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0f).sound(SoundType.METAL)).setRegistryName(location("steel_block")),
                GrinderBlock.grinder_off.setRegistryName(location("grinder")),
                GrinderBlock.grinder_on.setRegistryName(location("grinder_on"))
            );
        }

        private static Item createItem(ItemGroup group, String name){
            return new Item(createProperties(group)).setRegistryName(location(name));
        }

        private static Item.Properties createProperties(ItemGroup group){
            return new Item.Properties().group(group);
        }

        private static ResourceLocation location(String name){
            return new ResourceLocation(modid, name);
        }
    }
}
