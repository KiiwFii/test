package me.andrewdisco.rektanticheat.utils;

import java.io.File;

import me.andrewdisco.rektanticheat.AntiCheat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	File userFile;
	FileConfiguration userConfig;

	public Config(String name){
		userFile = new File(AntiCheat.getInstance().getDataFolder() + File.separator, name + ".yml");
		userConfig = YamlConfiguration.loadConfiguration(userFile);
	}

	public void makeConfigFile(){
		if ( !userFile.exists() ) {
			try {
				userConfig.save(userFile);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	public FileConfiguration getConfigFile(){
		return userConfig;
	}

	public void saveConfigFile(){
		try {
			getConfigFile().save(userFile);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}