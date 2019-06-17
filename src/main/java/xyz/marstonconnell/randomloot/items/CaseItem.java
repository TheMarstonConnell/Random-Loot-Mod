package xyz.marstonconnell.randomloot.items;

import java.util.Random;

import com.electronwill.nightconfig.core.io.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.TextComponentMessageFormatHandler;
import xyz.marstonconnell.randomloot.RandomLoot;
import xyz.marstonconnell.randomloot.init.ItemList;
import xyz.marstonconnell.randomloot.util.Config;
import xyz.marstonconnell.randomloot.util.NetworkHandler;
import xyz.marstonconnell.randomloot.util.RLUtils;
import xyz.marstonconnell.randomloot.util.WeightedChooser;

public class CaseItem extends Item {
	Random rand;
	WeightedChooser<RandomTool> wc;
	WeightedChooser<Integer> lvlChoice;

	public int level = 0;

	public CaseItem(String name, int level) {
		super(new Properties().group(ItemGroup.TOOLS));
		setRegistryName(RandomLoot.MODID, name);
		rand = new Random();
		wc = new WeightedChooser<RandomTool>();
		lvlChoice = new WeightedChooser<Integer>();

		wc.addChoice(ItemList.rlSword, 32);
		wc.addChoice(ItemList.rlAxe, 15);
		wc.addChoice(ItemList.rlPickaxe, 24);
		wc.addChoice(ItemList.rlShovel, 10);
		this.level = level;

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		wc = new WeightedChooser<RandomTool>();
		wc.addChoice(ItemList.rlSword, 32);
		wc.addChoice(ItemList.rlAxe, 15);
		wc.addChoice(ItemList.rlPickaxe, 24);
		wc.addChoice(ItemList.rlShovel, 10);
		if (!worldIn.isRemote) {

			NetworkHandler.getNewItem();

		}

		// for (int countparticles = 0; countparticles <= 25; ++countparticles) {
		// worldIn.spawnParticle(CLOUD, playerIn.posX, playerIn.posY + 2, playerIn.posZ,
		// 0.1D * getNegOrPos(), 0.1D * getNegOrPos(), 0.1D * getNegOrPos());
		// }
		worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.PLAYERS, 1.0F,
				1.0F);

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	private TextFormatting getItemNameColor(int level) {
		switch (level) {
		case 0:
			return TextFormatting.WHITE;

		case 1:
			return TextFormatting.GOLD;

		case 2:
			return TextFormatting.LIGHT_PURPLE;

		}

		return null;

	}

	public ItemStack getItem(World worldIn, int tier) {

		lvlChoice.clear();
		switch (tier) {
		case 0:
			lvlChoice.addChoice(0, 70);
			lvlChoice.addChoice(1, 25);
			lvlChoice.addChoice(2, 5);
			break;
		case 1:
			lvlChoice.addChoice(0, 50);
			lvlChoice.addChoice(1, 35);
			lvlChoice.addChoice(2, 15);
			break;
		case 2:
			lvlChoice.addChoice(0, 25);
			lvlChoice.addChoice(1, 45);
			lvlChoice.addChoice(2, 30);
			break;
		}

		int level = lvlChoice.getRandomObject();

		Item rt;
		ItemStack i = null;

		rt = (Item) wc.getRandomObject();

		if (rt instanceof SwordItem) {
			SwordItem is = (SwordItem) rt;
			is.randomizeVariant();
			i = new ItemStack(is);
			is.assignType(i, is.variant);

			switch (level) {

			case 0:
				RLUtils.assignDamage(i, Config.COMMON.tierOneMinDamage.get(), Config.COMMON.tierOneMaxDamage.get());
				RLUtils.assignSpeed(i, Config.COMMON.tierOneMinSpeed.get(), Config.COMMON.tierOneMaxSpeed.get());
				break;
			case 1:
				RLUtils.assignDamage(i, Config.COMMON.tierTwoMinDamage.get(), Config.COMMON.tierTwoMaxDamage.get());
				RLUtils.assignSpeed(i, Config.COMMON.tierTwoMinSpeed.get(), Config.COMMON.tierTwoMaxSpeed.get());
				break;
			case 2:
				RLUtils.assignDamage(i, Config.COMMON.tierThreeMinDamage.get(), Config.COMMON.tierThreeMaxDamage.get());
				RLUtils.assignSpeed(i, Config.COMMON.tierThreeMinSpeed.get(), Config.COMMON.tierThreeMaxSpeed.get());
				break;
			}
			is.assignType(i);
			is.setLore(i);
			i.setDisplayName(new TextComponentString(getItemNameColor(level) + RLUtils.nameItem("sword")));
		} else if (rt instanceof PickaxeItem) {
			PickaxeItem is = (PickaxeItem) rt;
			is.randomizeVariant();
			i = new ItemStack(is);
			is.assignType(i, is.variant);
			switch (level) {
			case 0:
				RLUtils.assignDamage(i, Config.COMMON.tierOneMinDamage.get() / 3 * 3, Config.COMMON.tierOneMaxDamage.get()/ 3 * 3);
				RLUtils.assignSpeed(i, Config.COMMON.tierOneMinSpeed.get() / 3 * 3, Config.COMMON.tierOneMaxSpeed.get() /3 * 3);
				RLUtils.assignDigSpeed(i, Config.COMMON.tierOneMinDigSpeed.get(), Config.COMMON.tierOneMaxDigSpeed.get());
				break;
			case 1:
				RLUtils.assignDamage(i, Config.COMMON.tierTwoMinDamage.get()/ 3 * 3, Config.COMMON.tierTwoMaxDamage.get()/ 3 * 3);
				RLUtils.assignSpeed(i, Config.COMMON.tierTwoMinSpeed.get() / 3 * 3, Config.COMMON.tierTwoMaxSpeed.get() / 3 * 3);
				RLUtils.assignDigSpeed(i, Config.COMMON.tierTwoMinDigSpeed.get(), Config.COMMON.tierTwoMaxDigSpeed.get());
				break;
			case 2:
				RLUtils.assignDamage(i, Config.COMMON.tierThreeMinDamage.get()/ 3 * 3, Config.COMMON.tierThreeMaxDamage.get()/ 3 * 3);
				RLUtils.assignSpeed(i, Config.COMMON.tierThreeMinSpeed.get() / 3 * 3, Config.COMMON.tierThreeMaxSpeed.get() / 3 * 3);
				RLUtils.assignDigSpeed(i, Config.COMMON.tierThreeMinDigSpeed.get(), Config.COMMON.tierThreeMaxDigSpeed.get());
				break;
			}
			is.assignType(i);
			is.setLore(i);
			i.setDisplayName(new TextComponentString(getItemNameColor(level) + RLUtils.nameItem("pickaxe")));
		} else if (rt instanceof AxeItem) {
			AxeItem is = (AxeItem) rt;
			is.randomizeVariant();
			i = new ItemStack(is);
			switch (level) {
			case 0:
				RLUtils.assignDamage(i, Config.COMMON.tierOneMinDamage.get() / 2 * 2, Config.COMMON.tierOneMaxDamage.get() / 2 * 2);
				RLUtils.assignSpeed(i, Config.COMMON.tierOneMinSpeed.get() / 3 * 3, Config.COMMON.tierOneMaxSpeed.get() /3 * 3);
				RLUtils.assignDigSpeed(i, Config.COMMON.tierOneMinDigSpeed.get(), Config.COMMON.tierOneMaxDigSpeed.get());
				break;
			case 1:
				RLUtils.assignDamage(i, Config.COMMON.tierTwoMinDamage.get()/ 2 * 2, Config.COMMON.tierTwoMaxDamage.get()/ 2 * 2);
				RLUtils.assignSpeed(i, Config.COMMON.tierTwoMinSpeed.get() / 3 * 3, Config.COMMON.tierTwoMaxSpeed.get() / 3 * 3);
				RLUtils.assignDigSpeed(i, Config.COMMON.tierTwoMinDigSpeed.get() * 2, Config.COMMON.tierTwoMaxDigSpeed.get() * 2);
				break;
			case 2:
				RLUtils.assignDamage(i, Config.COMMON.tierThreeMinDamage.get()/ 2 * 2, Config.COMMON.tierThreeMaxDamage.get()/ 2 * 2);
				RLUtils.assignSpeed(i, Config.COMMON.tierThreeMinSpeed.get() / 3 * 3, Config.COMMON.tierThreeMaxSpeed.get() / 3 * 3);
				RLUtils.assignDigSpeed(i, Config.COMMON.tierThreeMinDigSpeed.get() * 2, Config.COMMON.tierThreeMaxDigSpeed.get() * 2);
				break;
			}
			is.assignType(i);
			is.setLore(i);
			is.assignType(i, is.variant);

			i.setDisplayName(new TextComponentString(getItemNameColor(level) + RLUtils.nameItem("axe")));
		} else if (rt instanceof ShovelItem) {
			ShovelItem is = (ShovelItem) rt;
			is.randomizeVariant();
			i = new ItemStack(is);

			is.assignType(i, is.variant);
			switch (level) {
			case 0:
				RLUtils.assignDamage(i, Config.COMMON.tierOneMinDamage.get() / 3 * 3, Config.COMMON.tierOneMaxDamage.get()/ 3 * 3);
				RLUtils.assignSpeed(i, Config.COMMON.tierOneMinSpeed.get() / 3 * 3, Config.COMMON.tierOneMaxSpeed.get() /3 * 3);
				RLUtils.assignDigSpeed(i, Config.COMMON.tierOneMinDigSpeed.get(), Config.COMMON.tierOneMaxDigSpeed.get());
				break;
			case 1:
				RLUtils.assignDamage(i, Config.COMMON.tierTwoMinDamage.get()/ 3 * 3, Config.COMMON.tierTwoMaxDamage.get()/ 3 * 3);
				RLUtils.assignSpeed(i, Config.COMMON.tierTwoMinSpeed.get() / 3 * 3, Config.COMMON.tierTwoMaxSpeed.get() / 3 * 3);
				RLUtils.assignDigSpeed(i, Config.COMMON.tierTwoMinDigSpeed.get(), Config.COMMON.tierTwoMaxDigSpeed.get());
				break;
			case 2:
				RLUtils.assignDamage(i, Config.COMMON.tierThreeMinDamage.get()/ 3 * 3, Config.COMMON.tierThreeMaxDamage.get()/ 3 * 3);
				RLUtils.assignSpeed(i, Config.COMMON.tierThreeMinSpeed.get() / 3 * 3, Config.COMMON.tierThreeMaxSpeed.get() / 3 * 3);
				RLUtils.assignDigSpeed(i, Config.COMMON.tierThreeMinDigSpeed.get(), Config.COMMON.tierThreeMaxDigSpeed.get());
				break;
			}
			is.assignType(i);
			is.setLore(i);
			i.setDisplayName(new TextComponentString(getItemNameColor(level) + RLUtils.nameItem("shovel")));
		}

		return i;

	}

}
