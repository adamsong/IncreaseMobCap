package ml.adamsogm.increasemobcap;

import net.minecraft.entity.EntityClassification;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Mod("increasemobcap")
public class IncreaseMobCap {
	private static final Logger LOGGER = LogManager.getLogger();
	
	public IncreaseMobCap() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.CONFIG);
		Config.loadConfig(Config.CONFIG, FMLPaths.CONFIGDIR.get().resolve("increasemobcap.toml"));
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
	}
	
	private void setup(final FMLCommonSetupEvent event)
	{
		
		for (EntityClassification classification : EntityClassification.values()) {
			LOGGER.error(String.format("%s: %d", classification.getName(), classification.getMaxNumberOfCreature()));
		}
		
		try {
			Field mobCap = ObfuscationReflectionHelper.findField(EntityClassification.class, "field_75606_e");
			mobCap.setAccessible(true);
			
			Field modifierField = Field.class.getDeclaredField("modifiers");
			modifierField.setAccessible(true);
			modifierField.setInt(mobCap, mobCap.getModifiers() & ~Modifier.FINAL);
			
			mobCap.setInt(EntityClassification.MONSTER, Config.MONSTER_CAP.get());
			mobCap.setInt(EntityClassification.CREATURE, Config.CREATURE_CAP.get());
			mobCap.setInt(EntityClassification.AMBIENT, Config.AMBIENT_CAP.get());
			mobCap.setInt(EntityClassification.WATER_CREATURE, Config.WATER_CREATURE_CAP.get());
			mobCap.setInt(EntityClassification.WATER_AMBIENT, Config.WATER_AMBIENT_CAP.get());
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		for (EntityClassification classification : EntityClassification.values()) {
			LOGGER.error(String.format("%s: %d", classification.getName(), classification.getMaxNumberOfCreature()));
		}
		
	}
}
