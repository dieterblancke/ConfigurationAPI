ConfigurationAPI [![Build Status](https://ci.dbsoftwares.eu/job/ConfigurationAPI/badge/icon)](https://ci.dbsoftwares.eu/job/ConfigurationAPI)
=========

## Features:
- **Spigot & BungeeCord support**
- **Multiple storage types**, JSON & YAML
- **Similar to the Bukkit Configuration API**, no need to learn a complete new API
- **Support for Section Lists**

## API
You can find [our javadoc here](https://ci.dbsoftwares.eu/job/ConfigurationAPI/javadoc/).

### YAML File example
```
// Creating new configuration instance
IConfiguration yamlExample = IConfiguration.loadYamlConfiguration(new File(getDataFolder(), "yamlExample.yml"));
​
// Loading configuration defaults from plugin resources
try {
    yamlExample.copyDefaults(IConfiguration.loadYamlConfiguration(getResourceAsStream("yamlExample.yml")));
} catch (IOException e) {
    System.out.println("Could not load configuration defaults: ");
    e.printStackTrace();
}
​
// Get a String from the config
String test = yamlExample.getString("test");
​
// Set a value in the config
yamlExample.set("test.test2", 16);
​
// Save the config
try {
    yamlExample.save();
} catch (IOException e) {
    System.out.println("Could not save configuration: ");
    e.printStackTrace();
}
​
// Reload config (for when something manually got changed in the configuration)
// Reloading is NOT REQUIRED AFTER SAVING
try {
    yamlExample.reload();
} catch (IOException e) {
    System.out.println("Could not reload configuration: ");
    e.printStackTrace();
}
```

### JSON File example
```
// Creating new configuration instance
IConfiguration jsonExample = IConfiguration.loadJsonConfiguration(new File(getDataFolder(), "jsonExample.json"));
​
// Loading configuration defaults from plugin resources
try {
    jsonExample.copyDefaults(IConfiguration.loadJsonConfiguration(getResourceAsStream("jsonExample.json")));
} catch (IOException e) {
    System.out.println("Could not load configuration defaults: ");
    e.printStackTrace();
}
​
// Get a String from the config
String test = jsonExample.getString("test");
​
// Set a value in the config
jsonExample.set("test.test2", 16);
​
// Save the config
try {
    jsonExample.save();
} catch (IOException e) {
    System.out.println("Could not save configuration: ");
    e.printStackTrace();
}
​
// Reload config (for when something manually got changed in the configuration)
// Reloading is NOT REQUIRED AFTER SAVING
try {
    jsonExample.reload();
} catch (IOException e) {
    System.out.println("Could not reload configuration: ");
    e.printStackTrace();
}
```