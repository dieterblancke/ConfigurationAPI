ConfigurationAPI
=========

## Features:
- **Spigot & BungeeCord support**
- **Multiple storage types**, JSON & YAML
- **Similar to the Bukkit Configuration API**, no need to learn a complete new API
- **Support for Section Lists**
- **Serialization support**
    - **Bukkit**: [See Bukkit API](https://bukkit.gamepedia.com/Configuration_API_Reference#Serializing_and_Deserializing_Objects), although the API explained below also works
    - **BungeeCord**: see below

## Usage
### Repository
```xml
<repository>
  <id>dieterblancke</id>
    <url>https://repo.dieterblancke.xyz/artifactory/dieterblancke-public/</url>
</repository>
```

### Dependency
```xml
<dependency>
    <groupId>com.dbsoftwares.configuration</groupId>
    <artifactId>ConfigurationAPI</artifactId>
    <version>1.3.1</version>
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
### Creating default file
You can use this if the file doesn't exist, if it does, use the copyDefaults method instead (see below).
```java
// Assuming this is in the onEnable method ...
File configFile = new File(getDataFolder(), "config.yml");

if (!configFile.exists()) {
    // on BungeeCord, use getResource instead
    IConfiguration.createDefaultFile(getResourceAsStream("config.yml"), configFile);
}

IConfiguration config = IConfiguration.loadYamlConfiguration(configFile);
```

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

### Object Serialization
In order to store objects other then the default supported ones, you will have to use the Object Serialization API.
For this you will need to implement the ConfigurationSerializable into your classes, 
aswell as having a static "deserialize" method.

You can find a simple example of how to use this API below:
#### Registering
```java
ConfigurationSerialization.registerClass(ServerInfo.class);
ConfigurationSerialization.registerClass(PingData.class);
```

#### Usage
You should only do this after registration
```java
IConfiguration configuration; // TODO: initialize variable

// set in configuration
configuration.set("info", new ServerInfo("test", "127.0.0.1:25665", 0, 20));

// get from configuration (casting is REQUIRED)
ServerInfo info = configuration.get("info");

// do stuff with it
```

#### ServerInfo.class:
```java
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

import com.dbsoftwares.configuration.serialization.ConfigurationSerializable;
import com.dbsoftwares.configuration.serialization.SerializableAs;

@SerializableAs("ServerInfo")
public class ServerInfo implements ConfigurationSerializable {

    private String name;
    private String ip;
    private int count;
    private int max;
    private PingData lastPing;

    public ServerInfo(String name, String ip, int count, int max) {
        this.name = name;
        this.ip = ip;
        this.count = count;
        this.max = max;
    }

    public static ServerInfo deserialize(Map<String, Object> map) {
        String name = (String) map.get("name");
        String ip = (String) map.get("ip");
        int count = ((Number) map.get("count")).intValue();
        int max = ((Number) map.get("max")).intValue();

        ServerInfo info = new ServerInfo(name, ip, count, max);

        if (map.containsKey("lastPing")) {
            PingData ping = (PingData) map.get("lastPing");
            info.setLastPing(ping);
        }

        return info;
    }

    public void ping() {
        // TODO: create ping request
    }

    public void setLastPing(PingData lastPing) {
        this.lastPing = lastPing;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();

        result.put("name", name);
        result.put("ip", ip);
        result.put("count", count);
        result.put("max", max);
        result.put("lastPing", lastPing);

        return result;
    }
}
```

#### PingData.class
```java
import com.dbsoftwares.configuration.serialization.ConfigurationSerializable;
import com.dbsoftwares.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SerializableAs("PingData")
public class PingData implements ConfigurationSerializable {

    private boolean online;
    private String motd;

    public PingData(boolean online, String motd) {
        this.online = online;
        this.motd = motd;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();

        result.put("online", online);
        result.put("motd", motd);

        return result;
    }

    public static PingData deserialize(Map<String, Object> map) {
        boolean online = (Boolean) map.get("online");
        String motd = (String) map.get("motd");

        return new PingData(online, motd);
    }
}
```