ConfigurationAPI [![Build Status](https://ci.dbsoftwares.eu/job/ConfigurationAPI/badge/icon)](https://ci.dbsoftwares.eu/job/ConfigurationAPI)
=========

## Features:
- **Spigot & BungeeCord support**
- **Multiple storage types**, JSON & YAML
- **Similar to the Bukkit Configuration API**, no need to learn a complete new API
- **Support for Section Lists**

## Usage
### Repository
```xml
<repository>
    <id>dbsoftwares-repo</id>
    <name>DBSoftwares Repository</name>
    <url>https://nexus.dbsoftwares.eu/repository/dbsoftwares/</url>
</repository>
```

### Dependency
```xml
<dependency>
    <groupId>com.dbsoftwares.configuration</groupId>
    <artifactId>ConfigurationAPI</artifactId>
    <version>1.0.6</version>
    <scope>compile</scope>
</dependency>
```

### Shade Configuration
This is to compile the code into your jar, also the classes are being relocated into another package.
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.1.0</version>
            <configuration>
                <relocations>
                    <relocation>
                        <pattern>com.dbsoftwares.configuration</pattern>
                        <!-- Replace the package below with your package -->
                        <shadedPattern>example.package.configuration</shadedPattern>
                    </relocation>
                </relocations>
            </configuration>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

## API
You can find [our javadoc here](https://ci.dbsoftwares.eu/job/ConfigurationAPI/javadoc/).

### YAML File example
```java
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
```java
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