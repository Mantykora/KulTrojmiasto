package pl.com.mantykora.kultrjmiasto.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CategoryEnum {
    NAUKA(83, "nauka"),
    SZTUKA(51, "sztuka"),
    MUZYKA(35, "muzyka"),
    ROZRYWKA(69, "rozrywka"),
    REKREACJA(77, "rekreacja"),
    INNE(96, "inne"),
    TEATR(19, "teatr"),
    KINO(1, "kino"),
    LITERATURA(61, "literatura");

    static List<CategoryEnum> enumValues;

    private static Map<Integer, CategoryEnum> map = new HashMap<>();
    static {
        enumValues = new ArrayList<>(EnumSet.allOf(CategoryEnum.class));
        for (CategoryEnum value : enumValues) {
            map.put(value.code, value);
        }
    }

    public static CategoryEnum forCode(int code) {
        return map.get(code);
    }

    private int code;

    public String getName() {
        return name;
    }

    private String name;

    CategoryEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }
}
