package xyz.marstonconnell.randomloot.util;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class Config {

	public static Common COMMON;
	public static ForgeConfigSpec COMMON_SPEC;

	public static void init() {
		Pair<Common, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = commonPair.getRight();
		COMMON = commonPair.getLeft();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
	}

	public static class Common {

		public ForgeConfigSpec.BooleanValue enableMobDrops;
		public ForgeConfigSpec.BooleanValue enableChests;
		public ForgeConfigSpec.IntValue chestRate;
		public ForgeConfigSpec.IntValue passiveRate;
		public ForgeConfigSpec.IntValue mobRate;
		public ForgeConfigSpec.IntValue bossRate;
		public ForgeConfigSpec.IntValue playerRate;

		public ForgeConfigSpec.BooleanValue swords;
		public ForgeConfigSpec.BooleanValue axes;
		public ForgeConfigSpec.BooleanValue pickaxes;
		public ForgeConfigSpec.BooleanValue shovels;
		
		public ForgeConfigSpec.DoubleValue tierOneMinDamage;
		public ForgeConfigSpec.DoubleValue tierOneMaxDamage;

		public ForgeConfigSpec.DoubleValue tierTwoMinDamage;
		public ForgeConfigSpec.DoubleValue tierTwoMaxDamage;
		
		public ForgeConfigSpec.DoubleValue tierThreeMinDamage;
		public ForgeConfigSpec.DoubleValue tierThreeMaxDamage;

		public ForgeConfigSpec.DoubleValue tierOneMinSpeed;
		public ForgeConfigSpec.DoubleValue tierOneMaxSpeed;

		public ForgeConfigSpec.DoubleValue tierTwoMinSpeed;
		public ForgeConfigSpec.DoubleValue tierTwoMaxSpeed;
		
		public ForgeConfigSpec.DoubleValue tierThreeMinSpeed;
		public ForgeConfigSpec.DoubleValue tierThreeMaxSpeed;
		
		public ForgeConfigSpec.DoubleValue tierOneMinDigSpeed;
		public ForgeConfigSpec.DoubleValue tierOneMaxDigSpeed;

		public ForgeConfigSpec.DoubleValue tierTwoMinDigSpeed;
		public ForgeConfigSpec.DoubleValue tierTwoMaxDigSpeed;
		
		public ForgeConfigSpec.DoubleValue tierThreeMinDigSpeed;
		public ForgeConfigSpec.DoubleValue tierThreeMaxDigSpeed;
		
		public ForgeConfigSpec.BooleanValue allowUnbreakable;

		private Common(ForgeConfigSpec.Builder builder) {
			builder.push("General");
			enableMobDrops = builder.comment("Enables or disables cases dropping from mobs.").define("drop_from_mobs",
					true);
			enableChests = builder.comment("Enables or disables cases spawning in chests.").define("spawn_in_chest",
					true);

			chestRate = builder.comment("The rate of cases spawning in chests.").defineInRange("chest_rate", 50,
					0, 100);
			
			passiveRate = builder.comment("The rate of cases dropping from animals.").defineInRange("passive_rate", 50,
					0, 1000);
			mobRate = builder.comment("The rate of cases dropping from monsters.").defineInRange("mob_rate", 50,
					0, 1000);
			bossRate = builder.comment("The rate of cases dropping from bosses.").defineInRange("boss_rate", 50,
					0, 1000);
			playerRate = builder.comment("The rate of cases dropping from players.").defineInRange("player_rate", 50,
					0, 1000);
			
			allowUnbreakable = builder.comment("Enables or disables unbreakable tools.").define("allow_unbreakable",
					true);

			swords = builder.comment("Enables or disables sword items.").define("allow_swords", true);
			axes = builder.comment("Enables or disables axe items.").define("allow_axes", true);
			shovels = builder.comment("Enables or disables shovel items.").define("allow_shovels", true);
			pickaxes = builder.comment("Enables or disables pickaxe items.").define("allow_pickaxes", true);

			tierOneMinDamage = builder.comment("The minimum damage of tier one").defineInRange("t1mindamage", 7.0,
					0.0, 100.0);
			tierOneMaxDamage = builder.comment("The maximum damage of tier one").defineInRange("t1maxdamage", 11.0,
					0.0, 100.0);
			
			tierTwoMinDamage = builder.comment("The minimum damage of tier two").defineInRange("t2mindamage", 10.0,
					0.0, 100.0);
			tierTwoMaxDamage = builder.comment("The maximum damage of tier two").defineInRange("t2maxdamage", 16.0,
					0.0, 100.0);
			
			tierThreeMinDamage = builder.comment("The minimum damage of tier three").defineInRange("t3mindamage", 15.0,
					0.0, 100.0);
			tierThreeMaxDamage = builder.comment("The maximum damage of tier three").defineInRange("t3maxdamage", 22.0,
					0.0, 100.0);
			
			
			tierOneMinSpeed = builder.comment("The minimum speed of tier one").defineInRange("t1minspeed", 0.1,
					0.0, 100.0);
			tierOneMaxSpeed = builder.comment("The maximum speed of tier one").defineInRange("t1maxspeed", 0.7,
					0.0, 100.0);
			
			tierTwoMinSpeed = builder.comment("The minimum speed of tier two").defineInRange("t2minspeed", 0.6,
					0.0, 100.0);
			tierTwoMaxSpeed = builder.comment("The maximum speed of tier two").defineInRange("t2maxspeed", 1.3,
					0.0, 100.0);
			
			tierThreeMinSpeed = builder.comment("The minimum speed of tier three").defineInRange("t3minspeed", 1.2,
					0.0, 100.0);
			tierThreeMaxSpeed = builder.comment("The maximum speed of tier three").defineInRange("t3maxspeed", 2.4,
					0.0, 100.0);
			
			tierOneMinDigSpeed = builder.comment("The minimum dig speed of tier one").defineInRange("t1mindigspeed", 1.0,
					0.0, 100.0);
			tierOneMaxDigSpeed = builder.comment("The maximum dig speed of tier one").defineInRange("t1maxdigspeed", 6.0,
					0.0, 100.0);
			
			tierTwoMinDigSpeed = builder.comment("The minimum dig speed of tier two").defineInRange("t2mindigspeed", 5.0,
					0.0, 100.0);
			tierTwoMaxDigSpeed = builder.comment("The maximum dig speed of tier two").defineInRange("t2maxdigspeed", 9.0,
					0.0, 100.0);
			
			tierThreeMaxDigSpeed = builder.comment("The minimum dig speed of tier three").defineInRange("t3mindigspeed", 8.0,
					0.0, 100.0);
			tierThreeMaxDigSpeed = builder.comment("The maximum dig speed of tier three").defineInRange("t3maxdigspeed", 16.0,
					0.0, 100.0);
			
			
			builder.pop();
		}
	}
}
