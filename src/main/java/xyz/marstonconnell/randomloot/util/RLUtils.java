package xyz.marstonconnell.randomloot.util;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class RLUtils {
	
	public static String[] adjectivesU;
	public static String[] adjectivesS;
	public static String[] nounsS;
	public static String[] adjectivesSh;
	public static String[] nounsSh;
	public static String[] adjectivesP;
	public static String[] nounsP;
	public static String[] adjectivesA;
	public static String[] nounsA;
	public static String[] adjectivesB;
	public static String[] nounsB;
	private static Random rand = new Random();

	/**
	 * Replace a modifier in the {@link Multimap} with a copy that's had
	 * {@code multiplier} applied to its value.
	 *
	 * @param modifierMultimap The MultiMap
	 * @param attribute        The attribute being modified
	 * @param id               The ID of the modifier
	 * @param multiplier       The multiplier to apply
	 */
	public static void replaceModifier(final Multimap<String, AttributeModifier> modifierMultimap, final IAttribute attribute,
			final UUID id, final double multiplier) {
		// Get the modifiers for the specified attribute
		final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());

		// Find the modifier with the specified ID, if any
		final Optional<AttributeModifier> modifierOptional = modifiers.stream()
				.filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();

		modifierOptional.ifPresent(modifier -> { // If it exists,
			modifiers.remove(modifier); // Remove it
			modifiers.add(
					new AttributeModifier(modifier.getID(), modifier.getName(), multiplier, modifier.getOperation())); // Add
																														// the
																														// new
																														// modifier
		});
	}
	
	public static ItemStack assignDamage(ItemStack stack, double lowRange, double highRange) {
		NBTTagCompound nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new NBTTagCompound();
		}
		double dmg = rand.nextDouble() * (highRange - lowRange) + lowRange;
		System.out.println("New damage on sword: " + dmg);
		nbt.setDouble("rlmdamage", dmg);
		stack.setTag(nbt);

		return stack;
	}

	public static double getRLMDamage(ItemStack stack) {
		NBTTagCompound nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			return 0.0;
		}

		return nbt.getDouble("rlmdamage");
	}
	
	public static ItemStack setDigSpeed(ItemStack stack, float val ) {
		NBTTagCompound nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new NBTTagCompound();
		}
		nbt.setFloat("rlmdig", val);
		stack.setTag(nbt);

		return stack;
	}
	
	public static ItemStack assignDigSpeed(ItemStack stack, double lowRange, double highRange) {
		NBTTagCompound nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new NBTTagCompound();
		}
		float dmg = (float) (rand.nextDouble() * (highRange - lowRange) + lowRange);
		nbt.setFloat("rlmdig", dmg);
		stack.setTag(nbt);

		return stack;
	}

	public static float getRLMDigSpeed(ItemStack stack) {
		NBTTagCompound nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			return 0.0f;
		}

		return nbt.getFloat("rlmdig");
	}

	public static ItemStack assignSpeed(ItemStack stack, double lowRange, double highRange) {
		NBTTagCompound nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new NBTTagCompound();
		}
		double newSpeed = rand.nextDouble() * (highRange - lowRange) + lowRange;
		System.out.println("New sword speed: " + newSpeed);
		nbt.setDouble("rmlspeed", newSpeed);
		stack.setTag(nbt);

		return stack;
	}

	public static double getRLMSpeed(ItemStack stack) {
		NBTTagCompound nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			return 0.0;
		}

		return nbt.getDouble("rmlspeed");
	}
	
	public RLUtils() {

		adjectivesU = new String[]{ "Blazing", "Dark", "Dainty", "Jewel Encrusted", "Light", "Heavy", "Serious",
				"Enlightened", "Swift", "Titanic", "Crude", "Icy", "Mythic", "Epic", "Legendary", "Awesome",
				"Incredible", "Shocking", "Iron", "Titanium", "Soft", "Pretty", "Sweet", "Steel", "Elder", "Ancient",
				"Advanced", "Dreadful" };

		adjectivesS = new String[]{ "Fearsome", "Brutal", "Terrifying", "Harsh", "Barbaric", "Bloodthirsty", "Heartless",
				"Merciless", "Ruthless", "Savage", "Cold-Blooded", "Fearsome" };

		adjectivesSh = new String[]{ "Rusty", "Filthy", "Shining", "Powerful", "Ground-Breaking", "Sharpened",
				"Crackling" };

		adjectivesP = new String[]{ "Rusty", "Reliable", "Gritty", "Powerful", "Terra", "Hammering", "Sturdy" };

		nounsP = new String[]{ "Digger", "Terrablade", "Harbringer", "Earth Shatterer", "Crust-Breaker", "Hole-Puncher",
				"Point", "Swinger", "Mountain Mover", "Pickaxe", "Pick", "Gold Digger" };

		adjectivesB = new String[]{ "Quivering", "Heavy-Bolted", "Venomous", "Whistling", "Atuned", "Starstruck" };

		nounsB = new String[]{ "Bow", "Fletcher", "Slinger", "Bolt-Tosser", "Warp-Bow", "Piercer", "Hunting Bow",
				"Crossbow", "Basilisk", "Launcher" };

		adjectivesA = new String[]{ "Towering", "Wooden", "Fireman's", "Gracious", "Lumber", "Felling", "Swinging" };

		nounsA = new String[]{ "Chopper", "Axe", "Hatchet", "Splitter", "Tomahawk", "Tremor", "Greataxe", "War Axe",
				"Broadaxe", "Ravager", "Reaver", "Halberd" };

		nounsSh = new String[]{ "Spade", "Shovel", "Shatter", "Trowel", "Scoop", "Gravedigger", "Spoon" };

		nounsS = new String[]{ "Blade", "Sword", "Slasher", "Titan", "Killer", "Cleaver", "Knife", "Cutlass", "Nightmare",
				"Glaive", "Machete", "Saber", "Claymore", "Doomblade", "Defender", "Striker", "Crusader", "Skewer",
				"Chaos", "Infinity", "Broadsword", "Shortsword", "Architect", "Lance" };
	}
	
	public static String nameItem(String type) {
		String adj = null;
		String nn = null;

		if (type.equals("pickaxe")) {
			String[] adjs = mergeArrs(adjectivesP, adjectivesU);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsP[rand.nextInt(nounsP.length)];
		} else if (type.equals("sword")) {
			String[] adjs = mergeArrs(adjectivesS, adjectivesU);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsS[rand.nextInt(nounsS.length)];
		} else if (type.equals("shovel")) {
			String[] adjs = mergeArrs(adjectivesSh, adjectivesU);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsSh[rand.nextInt(nounsSh.length)];
		} else if (type.equals("axe")) {
			String[] adjs = mergeArrs(adjectivesA, adjectivesU);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsA[rand.nextInt(nounsA.length)];
		} else if (type.equals("bow")) {
			String[] adjs = mergeArrs(adjectivesB, adjectivesU);
			adj = adjs[rand.nextInt(adjs.length)];
			nn = nounsB[rand.nextInt(nounsB.length)];
		}
		return adj + " " + nn;
	}
	
	public static String[] mergeArrs(String[] a, String[] b) {
		int length = a.length + b.length;
		String[] result = new String[length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	public static ItemStack setRMLDamage(ItemStack stack, double dm) {
		NBTTagCompound nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new NBTTagCompound();
		}
		nbt.setDouble("rlmdamage", dm);
		stack.setTag(nbt);

		return stack;		
	}

	public static ItemStack setRMLSpeed(ItemStack stack, double dm) {
		NBTTagCompound nbt;
		if (stack.hasTag()) {
			nbt = stack.getTag();
		} else {
			nbt = new NBTTagCompound();
		}
		nbt.setDouble("rmlspeed", dm);
		stack.setTag(nbt);

		return stack;			
	}
}
