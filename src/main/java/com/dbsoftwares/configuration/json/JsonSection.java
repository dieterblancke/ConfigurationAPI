package com.dbsoftwares.configuration.json;

import com.dbsoftwares.configuration.api.ISection;
import com.dbsoftwares.configuration.api.ISpigotSection;
import com.dbsoftwares.configuration.api.Utils;
import com.dbsoftwares.configuration.yaml.bukkit.SpigotSection;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class JsonSection implements ISection {

    protected LinkedHashMap<String, Object> values = new LinkedHashMap<>();
    private String prefix;
    private SpigotSection spigot;

    public JsonSection() {
        if (Utils.isBukkit()) {
            spigot = new SpigotSection(this);
        }
    }

    public JsonSection(final Map<String, Object> values) {
        this();
        loadIntoSections(values, this);
    }

    @SuppressWarnings("unchecked")
    protected void loadIntoSections(final Map<String, Object> values, final ISection section) {
        for (final Map.Entry<String, Object> entry : values.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();

            if (value instanceof Map) {
                loadIntoSections((LinkedHashMap<String, Object>) value, section.createSection(key));
            } else if (value instanceof List) {
                final List list = (List) value;
                final List<ISection> sections = new ArrayList<>();

                for (Object object : list) {
                    if (object instanceof Map) {
                        sections.add(new JsonSection((Map<String, Object>) object));
                    } else {
                        sections.clear();
                        section.set(key, value);
                        break;
                    }
                }

                if (!sections.isEmpty()) {
                    section.set(key, sections);
                }
            } else {
                section.set(key, value);
            }
        }
    }

    @Override
    public boolean exists(String path) {
        return values.containsKey(path);
    }

    @Override
    public void set(String path, Object value) {
        values.put(path, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String path, T def) {
        return (T) values.getOrDefault(path, def);
    }

    @Override
    public <T> T get(String path) {
        return get(path, null);
    }

    @Override
    public boolean isString(String path) {
        Object object = get(path);
        return object instanceof String;
    }

    @Override
    public String getString(String path) {
        return (String) values.getOrDefault(path, null);
    }

    @Override
    public String getString(String path, String def) {
        String result = getString(path);

        if (result == null) {
            update(path, def, false);
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isBoolean(String path) {
        Object object = get(path);
        return object instanceof Boolean;
    }

    @Override
    public Boolean getBoolean(String path) {
        return (Boolean) values.getOrDefault(path, null);
    }

    @Override
    public Boolean getBoolean(String path, boolean def) {
        Boolean result = getBoolean(path);

        if (result == null) {
            update(path, def, false);
        }

        return result == null ? def : result;
    }

    @Override
    public boolean isInteger(String path) {
        return isNumber(path);
    }

    @Override
    public Integer getInteger(String path) {
        Number number = getNumber(path);
        return number == null ? null : number.intValue();
    }

    @Override
    public Integer getInteger(String path, Integer def) {
        Integer result = getInteger(path);

        if (result == null) {
            update(path, def, false);
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isNumber(String path) {
        Object object = get(path);
        return object instanceof Number;
    }

    @Override
    public Number getNumber(String path) {
        return (Number) values.getOrDefault(path, null);
    }

    @Override
    public Number getNumber(String path, Number def) {
        Number result = getNumber(path);

        if (result == null) {
            update(path, def, false);
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isDouble(String path) {
        return isNumber(path);
    }

    @Override
    public Double getDouble(String path) {
        Number number = getNumber(path);
        return number == null ? null : number.doubleValue();
    }

    @Override
    public Double getDouble(String path, Double def) {
        Double result = getDouble(path);

        if (result == null) {
            update(path, def, false);
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isLong(String path) {
        return isNumber(path);
    }

    @Override
    public Long getLong(String path) {
        Number number = getNumber(path);
        return number == null ? null : number.longValue();
    }

    @Override
    public Long getLong(String path, Long def) {
        Long result = getLong(path);

        if (result == null) {
            update(path, def, false);
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isFloat(String path) {
        return isNumber(path);
    }

    @Override
    public Float getFloat(String path) {
        Number number = getNumber(path);
        return number == null ? null : number.floatValue();
    }

    @Override
    public Float getFloat(String path, Float def) {
        Float result = getFloat(path);

        if (result == null) {
            update(path, def, false);
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isByte(String path) {
        return isNumber(path);
    }

    @Override
    public Byte getByte(String path) {
        Number number = getNumber(path);
        return number == null ? null : number.byteValue();
    }

    @Override
    public Byte getByte(String path, Byte def) {
        Byte result = getByte(path);

        if (result == null) {
            update(path, def, false);
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isShort(String path) {
        return isNumber(path);
    }

    @Override
    public Short getShort(String path) {
        Number number = getNumber(path);
        return number == null ? null : number.shortValue();
    }

    @Override
    public Short getShort(String path, Short def) {
        Short result = getShort(path);

        if (result == null) {
            update(path, def, false);
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isBigInteger(String path) {
        Object object = get(path);
        return object instanceof BigInteger;
    }

    @Override
    public BigInteger getBigInteger(String path) {
        return (BigInteger) values.getOrDefault(path, null);
    }

    @Override
    public BigInteger getBigInteger(String path, BigInteger def) {
        BigInteger result = getBigInteger(path);

        if (result == null) {
            update(path, def, false);
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isBigDecimal(String path) {
        Object object = get(path);
        return object instanceof BigDecimal;
    }

    @Override
    public BigDecimal getBigDecimal(String path) {
        return (BigDecimal) values.getOrDefault(path, null);
    }

    @Override
    public BigDecimal getBigDecimal(String path, BigDecimal def) {
        BigDecimal result = getBigDecimal(path);

        if (result == null) {
            update(path, def, false);
        }
        return result == null ? def : result;
    }

    @Override
    public boolean isList(String path) {
        Object object = get(path);
        return object instanceof List;
    }

    @Override
    public List getList(String path) {
        return (List) values.getOrDefault(path, null);
    }

    @Override
    public List getList(String path, List def) {
        List result = getList(path);

        if (result == null) {
            update(path, def, false);
        }

        return result == null ? def : result;
    }

    @Override
    public List<String> getStringList(String path) {
        List list = getList(path);

        if (list == null) {
            return null;
        }
        List<String> strList = new ArrayList<>();
        for (Object object : list) {
            strList.add(object.toString());
        }
        return strList;
    }

    @Override
    public List<String> getStringList(String path, List<String> def) {
        List<String> result = getStringList(path);

        if (result == null) {
            update(path, def, false);
        }

        return result == null ? def : result;
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        List list = getList(path);

        if (list == null) {
            return null;
        }
        List<Integer> intList = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number) {
                intList.add(((Number) object).intValue());
            } else if (Utils.isInteger(object)) {
                intList.add(Integer.parseInt(object.toString()));
            }
        }
        return intList;
    }

    @Override
    public List<Integer> getIntegerList(String path, List<Integer> def) {
        List<Integer> result = getIntegerList(path);

        if (result == null) {
            update(path, def, false);
        }

        return result == null ? def : result;
    }

    @Override
    public List<Double> getDoubleList(String path) {
        List list = getList(path);

        if (list == null) {
            return null;
        }
        List<Double> dList = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number) {
                dList.add(((Number) object).doubleValue());
            } else if (Utils.isDouble(object)) {
                dList.add(Double.parseDouble(object.toString()));
            }
        }
        return dList;
    }

    @Override
    public List<Double> getDoubleList(String path, List<Double> def) {
        List<Double> result = getDoubleList(path);

        if (result == null) {
            update(path, def, false);
        }

        return result == null ? def : result;
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        List list = getList(path);

        if (list == null) {
            return null;
        }
        List<Boolean> bList = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Boolean) {
                bList.add((Boolean) object);
            } else if (Utils.isBoolean(object)) {
                bList.add(Boolean.parseBoolean(object.toString()));
            }
        }
        return bList;
    }

    @Override
    public List<Boolean> getBooleanList(String path, List<Boolean> def) {
        List<Boolean> result = getBooleanList(path);

        if (result == null) {
            update(path, def, false);
        }

        return result == null ? def : result;
    }

    @Override
    public List<Long> getLongList(String path) {
        List list = getList(path);

        if (list == null) {
            return null;
        }
        List<Long> longList = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number) {
                longList.add(((Number) object).longValue());
            } else if (Utils.isLong(object)) {
                longList.add(Long.parseLong(object.toString()));
            }
        }
        return longList;
    }

    @Override
    public List<Long> getLongList(String path, List<Long> def) {
        List<Long> result = getLongList(path);

        if (result == null) {
            update(path, def, false);
        }

        return result == null ? def : result;
    }

    @Override
    public List<Byte> getByteList(String path) {
        List list = getList(path);

        if (list == null) {
            return null;
        }
        List<Byte> bList = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number) {
                bList.add(((Number) object).byteValue());
            } else if (Utils.isByte(object)) {
                bList.add(Byte.parseByte(object.toString()));
            }
        }
        return bList;
    }

    @Override
    public List<Byte> getByteList(String path, List<Byte> def) {
        List<Byte> result = getByteList(path);

        if (result == null) {
            update(path, def, false);
        }

        return result == null ? def : result;
    }

    @Override
    public List<Short> getShortList(String path) {
        List list = getList(path);

        if (list == null) {
            return null;
        }
        List<Short> shortList = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number) {
                shortList.add(((Number) object).shortValue());
            } else if (Utils.isShort(object)) {
                shortList.add(Short.parseShort(object.toString()));
            }
        }
        return shortList;
    }

    @Override
    public List<Short> getShortList(String path, List<Short> def) {
        List<Short> result = getShortList(path);

        if (result == null) {
            update(path, def, false);
        }

        return result == null ? def : result;
    }

    @Override
    public List<Float> getFloatList(String path) {
        List list = getList(path);

        if (list == null) {
            return null;
        }
        List<Float> floatList = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number) {
                floatList.add(((Number) object).floatValue());
            } else if (Utils.isFloat(object)) {
                floatList.add(Float.parseFloat(object.toString()));
            }
        }
        return floatList;
    }

    @Override
    public List<Float> getFloatList(String path, List<Float> def) {
        List<Float> result = getFloatList(path);

        if (result == null) {
            update(path, def, false);
        }

        return result == null ? def : result;
    }

    @Override
    public List<Number> getNumberList(String path) {
        List list = getList(path);

        if (list == null) {
            return null;
        }
        List<Number> numberList = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof Number) {
                numberList.add((Number) object);
            } else if (Utils.isInteger(object)) {
                numberList.add(Integer.parseInt(object.toString()));
            } else if (Utils.isShort(object)) {
                numberList.add(Short.parseShort(object.toString()));
            } else if (Utils.isByte(object)) {
                numberList.add(Byte.parseByte(object.toString()));
            } else if (Utils.isDouble(object)) {
                numberList.add(Double.parseDouble(object.toString()));
            } else if (Utils.isLong(object)) {
                numberList.add(Long.parseLong(object.toString()));
            } else if (Utils.isFloat(object)) {
                numberList.add(Float.parseFloat(object.toString()));
            }
        }
        return numberList;
    }

    @Override
    public List<Number> getNumberList(String path, List<Number> def) {
        List<Number> result = getNumberList(path);

        if (result == null) {
            update(path, def, false);
        }

        return result == null ? def : result;
    }

    @Override
    public List<BigInteger> getBigIntegerList(String path) {
        List list = getList(path);

        if (list == null) {
            return null;
        }
        List<BigInteger> biList = new ArrayList<>();
        for (Object object : list) {
            if (Utils.isBigInteger(object.toString())) {
                biList.add(new BigInteger(object.toString()));
            }
        }
        return biList;
    }

    @Override
    public List<BigInteger> getBigIntegerList(String path, List<BigInteger> def) {
        List<BigInteger> result = getBigIntegerList(path);

        if (result == null) {
            update(path, def, false);
        }

        return result == null ? def : result;
    }

    @Override
    public List<BigDecimal> getBigDecimalList(String path) {
        List list = getList(path);

        if (list == null) {
            return null;
        }
        List<BigDecimal> bdList = new ArrayList<>();
        for (Object object : list) {
            if (Utils.isBigDecimal(object.toString())) {
                bdList.add(new BigDecimal(object.toString()));
            }
        }
        return bdList;
    }

    @Override
    public List<BigDecimal> getBigDecimalList(String path, List<BigDecimal> def) {
        List<BigDecimal> result = getBigDecimalList(path);

        if (result == null) {
            update(path, def, false);
        }

        return result == null ? def : result;
    }

    @Override
    public List<ISection> getSectionList(String path) {
        List list = getList(path);

        if (list == null) {
            return null;
        }
        List<ISection> sections = new ArrayList<>();
        for (Object object : list) {
            if (object instanceof ISection) {
                sections.add((ISection) object);
            }
        }
        return sections;
    }

    public void update(String path, Object value, boolean overwrite) {
        if (!values.containsKey(path)) {
            values.put(path, value);
        } else if (values.containsKey(path) && overwrite) {
            values.put(path, value);
        }
    }

    @Override
    public boolean isSection(String path) {
        Object object = get(path);
        return object instanceof ISection;
    }

    @Override
    public ISection getSection(String section) {
        if (section.isEmpty()) {
            return this;
        }

        return this.get(section, new JsonSection(new LinkedHashMap<>()));
    }

    @Override
    public ISection createSection(String key) {
        ISection section = new JsonSection(new LinkedHashMap<>());
        values.put(key, section);
        return section;
    }

    @Override
    public Set<String> getKeys() {
        return getKeys(false);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        if (deep) {
            // todo: change
            return values.keySet();
        } else {
            return values.keySet();
        }
    }

    @Override
    public Set<String> getKeys(String path) {
        Set<String> keys = new HashSet<>();

        for (String key : getKeys()) {
            if (key.startsWith(path)) {
                keys.add(key);
            }
        }
        return keys;
    }

    @Override
    public Map<String, Object> getValues() {
        return values;
    }

    @Override
    public ISpigotSection spigot() {
        return spigot;
    }
}