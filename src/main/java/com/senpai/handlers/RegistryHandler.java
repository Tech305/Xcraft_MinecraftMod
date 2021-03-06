package com.senpai.handlers;

import com.senpai.Main;
import com.senpai.commands.CommandDimensionTeleport;
import com.senpai.gui.GuiHandler;
import com.senpai.init.BiomeInit;
import com.senpai.init.BlockInit;
import com.senpai.init.DimensionInit;
import com.senpai.init.EntityInit;
import com.senpai.init.ItemInit;
import com.senpai.interfaces.IHasModel;
import com.senpai.world.gen.WorldGenCustomOres;
import com.senpai.world.gen.WorldGenCustomStructures;
import com.senpai.world.types.WorldTypeCopper;
import com.senpai.world.types.WorldTypeCustom;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class RegistryHandler 
{
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
		TileEntityHandler.registerTileEntities();
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : ItemInit.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : BlockInit.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}
	}
	
	public static void preInitRegistries()
	{
		GameRegistry.registerWorldGenerator(new WorldGenCustomOres(), 0);
		GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 0);
		
		BiomeInit.registerBiomes();
		DimensionInit.registerDimensions();
		
		EntityInit.registerEntities();
		RenderHandler.registerEntityRenders();
	}
	
	public static void initRegistries()
	{
		SoundsHandler.registerSounds();
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
	}
	
	public static void postInitRegistries()
	{
		WorldType COPPER = new WorldTypeCopper();
		WorldType CUSTOM = new WorldTypeCustom();
	}
	
	public static void serverRegistries(FMLServerStartingEvent event) 
	{
		event.registerServerCommand(new CommandDimensionTeleport());
	}
}
