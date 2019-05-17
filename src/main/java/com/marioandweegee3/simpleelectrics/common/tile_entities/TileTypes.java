package com.marioandweegee3.simpleelectrics.common.tile_entities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.rmi.registry.RegistryHandler;

import com.marioandweegee3.simpleelectrics.SimpleElectrics;
import com.marioandweegee3.simpleelectrics.common.tile_entities.grinder.GrinderTile;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ObjectHolder(SimpleElectrics.modid)
public class TileTypes{
    public static final TileEntityType<GrinderTile> GRINDER = RegistryHandler.build("grinder", TileEntityType.Builder.create(GrinderTile::new));

    @Mod.EventBusSubscriber(modid=SimpleElectrics.modid,bus= Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryHandler {
		private static final Logger LOGGER = LogManager.getLogger();

		@SubscribeEvent
		public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
			TileEntityType<?>[] tets = new TileEntityType[] {
					GRINDER
			};

			event.getRegistry().registerAll(tets);
		}

		private static <T extends TileEntity> TileEntityType<T> build(final String name, TileEntityType.Builder<T> builder) {
			Type<?> type = null;
			try {
				type = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(1631)).getChoiceType(TypeReferences.BLOCK_ENTITY, name);
			} catch(IllegalArgumentException e) {
				if(SharedConstants.developmentMode) {
					throw e;
				}

				LOGGER.warn("No data fixer registered for block entity {}", name);
			}

			TileEntityType<T> tet = builder.build(type);
			tet.setRegistryName(SimpleElectrics.modid, name);
			LOGGER.info("Registered Tile Entity Type with name: " + name);
			return tet;
		}
    }
}