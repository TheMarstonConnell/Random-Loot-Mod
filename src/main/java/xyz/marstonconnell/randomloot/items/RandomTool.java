package xyz.marstonconnell.randomloot.items;

import net.minecraft.item.ItemStack;

public interface RandomTool {
	int variant = 0;
	public void randomizeVariant();
	public int getItemTexture(ItemStack stack);
	public ItemStack assignType(ItemStack stack, int variant);
	

	
}
