package ml.adamsogm.increasemobcap;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

import static net.minecraft.entity.EntityClassification.*;

@Mod.EventBusSubscriber
public class Config {
	
	public static ForgeConfigSpec.ConfigValue<Integer> MONSTER_CAP;
	public static ForgeConfigSpec.ConfigValue<Integer> CREATURE_CAP;
	public static ForgeConfigSpec.ConfigValue<Integer> AMBIENT_CAP;
	public static ForgeConfigSpec.ConfigValue<Integer> WATER_CREATURE_CAP;
	public static ForgeConfigSpec.ConfigValue<Integer> WATER_AMBIENT_CAP;
	
	public static ForgeConfigSpec CONFIG;
	public static ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
	
	static {
		CONFIG_BUILDER.comment("Mob Caps").push("mobcaps");
		
		MONSTER_CAP = CONFIG_BUILDER.comment("Mob cap for monsters").define("monster", MONSTER.getMaxNumberOfCreature());
		CREATURE_CAP = CONFIG_BUILDER.comment("Mob cap for creatures").define("creature", CREATURE.getMaxNumberOfCreature());
		AMBIENT_CAP = CONFIG_BUILDER.comment("Mob cap for ambient").define("ambient", AMBIENT.getMaxNumberOfCreature());
		WATER_CREATURE_CAP = CONFIG_BUILDER.comment("Mob cap for water creatures").define("water_creature", WATER_CREATURE.getMaxNumberOfCreature());
		WATER_AMBIENT_CAP = CONFIG_BUILDER.comment("Mob cap for ambient water creatures").define("water_ambient", WATER_AMBIENT.getMaxNumberOfCreature());
		CONFIG_BUILDER.pop();
		
		CONFIG = CONFIG_BUILDER.build();
	}
	
	public static void loadConfig(ForgeConfigSpec spec, Path path) {
		final CommentedFileConfig configData = CommentedFileConfig.builder(path)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();
		
		configData.load();
		spec.setConfig(configData);
	}
}
