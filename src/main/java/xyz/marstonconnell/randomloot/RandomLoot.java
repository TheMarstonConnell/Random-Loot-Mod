package xyz.marstonconnell.randomloot;

import net.minecraft.block.Block;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.command.impl.RecipeCommand;
import net.minecraft.data.RecipeProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.RecipeType;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xyz.marstonconnell.randomloot.init.ItemList;
import xyz.marstonconnell.randomloot.items.AxeItem;
import xyz.marstonconnell.randomloot.items.CaseItem;
import xyz.marstonconnell.randomloot.items.PickaxeItem;
import xyz.marstonconnell.randomloot.items.ShovelItem;
import xyz.marstonconnell.randomloot.items.SwordItem;
import xyz.marstonconnell.randomloot.util.RLUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("randomloot")
public class RandomLoot {
	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "randomloot";
	public static final RLUtils utils = new RLUtils();

	public RandomLoot() {

		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		// Register the enqueueIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		// Register the processIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		// Register the doClientStuff method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {

	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		// do something that can only be done on the client
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
		// some example code to dispatch IMC to another mod
	}

	private void processIMC(final InterModProcessEvent event) {

	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		// do something when the server starts
	}

	// You can use EventBusSubscriber to automatically subscribe events on the
	// contained class (this is subscribing to the MOD
	// Event bus for receiving Registry Events)
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
			LOGGER.info("HELLO from Register Block");
		}

		@SubscribeEvent
		public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
			LOGGER.info("HELLO from Register Item");

			itemRegistryEvent.getRegistry().registerAll(ItemList.basicCase = new CaseItem("basic_case", 0),
					ItemList.betterCase = new CaseItem("golden_case", 1),
					ItemList.bestCase = new CaseItem("titan_case", 2), ItemList.rlSword = new SwordItem(20),
					ItemList.rlAxe = new AxeItem(6), ItemList.rlPickaxe = new PickaxeItem(12),
					ItemList.rlShovel = new ShovelItem(6));

		}
		
		@SubscribeEvent
		public void onHarvest(BlockEvent.HarvestDropsEvent event) {
			EntityPlayer player = event.getHarvester();

			if (player != null && !player.world.isRemote) {
				List<Item> tools = new ArrayList<Item>();
				tools.add(ItemList.rlAxe);
				tools.add(ItemList.rlPickaxe);
				tools.add(ItemList.rlShovel);

				ItemStack item = player.getHeldItemMainhand();

				if (tools.contains(item.getItem())) {
					List<ItemStack> smelted = new ArrayList<ItemStack>();

					for (ItemStack drop : event.getDrops()) {
						ItemStack smeltResult = drop;

						if (smeltResult != null && smeltResult != ItemStack.EMPTY)
							smelted.add(smeltResult.copy());
						else
							smelted.add(drop.copy());
					}

					NBTTagCompound compound = (item.hasTag()) ? item.getTag() : new NBTTagCompound();
					int t1 = compound.getInt("T1");
					int t2 = compound.getInt("T2");
					int t3 = compound.getInt("T3");
					if (t1 == 8 || t2 == 8 || t3 == 8) {

						event.getDrops().clear();
						event.getDrops().addAll(smelted);

					}

				}
			}
		}
	}
}
