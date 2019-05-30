package xyz.marstonconnell.randomloot.items;

import java.text.DecimalFormat;
import java.util.Random;

import com.google.common.collect.Multimap;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import xyz.marstonconnell.randomloot.RandomLoot;
import xyz.marstonconnell.randomloot.util.RLUtils;
import xyz.marstonconnell.randomloot.util.ToolMaterialList;

public class AxeItem extends ItemAxe implements RandomTool {
	int variants = 0;
	int variant = 0;
	Random rand;
	static int tCount = 11;


	public AxeItem(int variants) {
		super(ToolMaterialList.random, 10, 8, new Properties());
		this.variants = variants;
		rand = new Random();
		randomizeVariant();
		setRegistryName(RandomLoot.MODID, "axe");
		this.addPropertyOverride(new ResourceLocation("model"), new IItemPropertyGetter() {

			@Override
			public float call(ItemStack p_call_1_, World p_call_2_, EntityLivingBase p_call_3_) {
				return getItemTexture(p_call_1_);
			}

		});
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		Material material = state.getMaterial();
		return material != Material.WOOD && material != Material.PLANTS && material != Material.VINE
				? super.getDestroySpeed(stack, state)
				: RLUtils.getRLMDigSpeed(stack);

	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(final EntityEquipmentSlot slot,
			final ItemStack stack) {
		final Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);

		if (slot == EntityEquipmentSlot.MAINHAND) {
			RLUtils.replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER,
					RLUtils.getRLMDamage(stack));
			RLUtils.replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER,
					(RLUtils.getRLMSpeed(stack) - 3));
			// getRLMSpeed(stack)
		}

		return modifiers;
	}

	public void randomizeVariant() {
		variant = rand.nextInt(variants);

	}
	
	public ItemStack assignType(ItemStack stack) {
		Random rand = new Random();
		NBTTagCompound nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new NBTTagCompound();
		}

		int t1 = 0, t2 = 0, t3 = 0, traits = 0;
		int ts = rand.nextInt(8);
		switch (ts) {
		case 0:
		case 1:
			break;
		case 2:
		case 3:
		case 4:
			traits = 1;
			break;
		case 5:
		case 6:
			traits = 2;
			break;
		case 7:
			traits = 3;
			break;
		}

		if (traits == 1) {
			t1 = rand.nextInt(tCount) + 1;
		} else if (traits == 2) {
			t1 = rand.nextInt(tCount) + 1;

			rand.setSeed(t1);
			t2 = rand.nextInt(tCount) + 1;

		} else if (traits == 3) {
			t1 = rand.nextInt(tCount) + 1;

			rand.setSeed(t1);
			t2 = rand.nextInt(tCount) + 1;

			rand.setSeed(t2);
			t3 = rand.nextInt(tCount) + 1;

		}

		if ((t1 == 9 || t2 == 9 || t3 == 9)) {
			nbt.setBoolean("Unbreakable", true);
		}

		nbt.setInt("T1", t1);
		nbt.setInt("T2", t2);
		nbt.setInt("T3", t3);

		nbt.setInt("Lvl", 1);
		nbt.setInt("lvlXp", 256);
		nbt.setInt("Xp", 0);
		nbt.setInt("HideFlags", 2);

		stack.setTag(nbt);

		return stack;
	}
	
	public void setLore(ItemStack stack) {

		// System.out.println(digSpeed);

		NBTTagCompound compound;
		if (stack.hasTag()) {
			compound = stack.getTag();
		} else {
			compound = new NBTTagCompound();
		}

		TextFormatting color = null;
		switch (compound.getInt("rarity")) {
		case 1:
			color = TextFormatting.WHITE;
			break;
		case 2:
			color = TextFormatting.GOLD;
			break;
		case 3:
			color = TextFormatting.LIGHT_PURPLE;
			break;

		}

		NBTTagList lore = new NBTTagList();

		DecimalFormat f = new DecimalFormat("##.00");
		lore.add(new NBTTagString(TextFormatting.GRAY + "Mining Speed: " + f.format(RLUtils.getRLMDigSpeed(stack))));
		lore.add(new NBTTagString(""));
		int t1 = compound.getInt("T1");
		int t2 = compound.getInt("T2");
		int t3 = compound.getInt("T3");

		if (t1 == 1 || t2 == 1 || t3 == 1) {
			// lore.appendTag(new NBTTagString(TextFormatting.DARK_GREEN +
			// "Poisonous"));
		}
		if (t1 == 2 || t2 == 2 || t3 == 2) {
			// lore.appendTag(new NBTTagString(TextFormatting.DARK_GRAY +
			// "Weakening"));
		}
		if (t1 == 3 || t2 == 3 || t3 == 3) {
			// lore.appendTag(new NBTTagString(TextFormatting.DARK_GRAY +
			// "Withering"));
		}
		if (t1 == 4 || t2 == 4 || t3 == 4) {
			// lore.appendTag(new NBTTagString(TextFormatting.DARK_GRAY +
			// "Blinding"));
		}
		if (t1 == 5 || t2 == 5 || t3 == 5) {
			// lore.appendTag(new NBTTagString(TextFormatting.DARK_RED +
			// "Starving"));
		}
		if (t1 == 6 || t2 == 6 || t3 == 6) {
			// lore.appendTag(new NBTTagString(TextFormatting.GOLD +
			// "Levitating"));
		}
		if (t1 == 7 || t2 == 7 || t3 == 7) {
			lore.add(new NBTTagString(TextFormatting.DARK_GREEN + "Filling"));
		}
		if (t1 == 8 || t2 == 8 || t3 == 8) {
//			lore.add(new NBTTagString(TextFormatting.DARK_RED + "Auto-Smelt"));
		}
		if ((t1 == 9 || t2 == 9 || t3 == 9)) {
			lore.add(new NBTTagString(TextFormatting.GRAY + "Fortified"));
		}
		if (t1 == 10 || t2 == 10 || t3 == 10) {
			lore.add(new NBTTagString(TextFormatting.RED + "Explosive"));
		}
		if (t1 == 11 || t2 == 11 || t3 == 11) {
			lore.add(new NBTTagString(TextFormatting.YELLOW + "Hasty"));
		}

		lore.add(new NBTTagString(""));
		lore.add(new NBTTagString(TextFormatting.GRAY + "Level " + compound.getInt("Lvl")));
		lore.add(new NBTTagString(
				TextFormatting.GRAY + "" + compound.getInt("Xp") + "/" + compound.getInt("lvlXp") + " Xp"));

		NBTTagCompound display = new NBTTagCompound();
		display.setTag("Lore", lore);
		compound.setTag("display", display);


	}
	
	private void upgrade(ItemStack stack) {
		NBTTagCompound compound = (stack.hasTag()) ? stack.getTag() : new NBTTagCompound();
		NBTTagList modifiers = new NBTTagList();
		int lvl = compound.getInt("Lvl");
		lvl++;
		compound.setInt("Lvl", lvl);
		float ds = RLUtils.getRLMDigSpeed(stack);
		ds += Math.random() * 2;
		RLUtils.setDigSpeed(stack, ds);

		int lvlXp = compound.getInt("lvlXp");
		compound.setInt("Xp", 0);
		compound.setInt("lvlXp", lvlXp = (int) (lvlXp + (lvlXp / 2)));

		compound.setTag("AttributeModifiers", modifiers);
		stack.setTag(compound);
		setLore(stack);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {

		NBTTagCompound nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new NBTTagCompound();
		}

		int xp = nbt.getInt("Xp");
		int lvlXp = nbt.getInt("lvlXp");

		if (nbt.hasKey("Xp")) {
			nbt.setInt("Xp", nbt.getInt("Xp") + 1);
		} else {
			nbt.setInt("Xp", 1);
		}

		if (xp >= lvlXp) {
			upgrade(stack);

		}

		int t1 = nbt.getInt("T1");
		int t2 = nbt.getInt("T2");
		int t3 = nbt.getInt("T3");

		if (t1 == 1 || t2 == 1 || t3 == 1) {

		}
		if (t1 == 2 || t2 == 2 || t3 == 2) {

		}
		if (t1 == 3 || t2 == 3 || t3 == 3) {

		}
		if (t1 == 4 || t2 == 4 || t3 == 4) {

		}
		if (t1 == 5 || t2 == 5 || t3 == 5) {

		}
		if (t1 == 6 || t2 == 6 || t3 == 6) {

		}
		if (t1 == 7 || t2 == 7 || t3 == 7) {
			// PotionEffect(MobEffects.SATURATION, 30 * 20, 0));
			Random rand = new Random();
			switch (rand.nextInt(6)) {
			case 4:
			case 5:
				((EntityPlayer) entityLiving).getFoodStats().addStats((ItemFood) Items.BEETROOT,
						new ItemStack(Items.BEETROOT));
			}

		}
		if (t1 == 8 || t2 == 8 || t3 == 8) {

		}
		if (t1 == 10 || t2 == 10 || t3 == 10) {

			if (!worldIn.isRemote) {
				float f = 4.0F;
				worldIn.createExplosion(entityLiving, pos.getX(), pos.getY(), pos.getZ(), 4.0F, true);

			}

		}
		if (t1 == 11 || t2 == 11 || t3 == 11) {
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.HASTE, 5 * 20, 1));
		}

		stack.setTag(nbt);
		setLore(stack);

		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}

	public int getItemTexture(ItemStack stack) {
		NBTTagCompound nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			return 0;
		}

		return nbt.getInt("model");
	}

	public ItemStack assignType(ItemStack stack, int variant) {
		NBTTagCompound nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new NBTTagCompound();
		}

		nbt.setInt("model", variant);
		stack.setTag(nbt);

		return stack;

	}

}
