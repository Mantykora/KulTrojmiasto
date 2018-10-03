package eu.mantykora.kultrjmiasto.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.mantykora.kultrjmiasto.R;

public enum CategoryEnum {
    NAUKA(83, "nauka", R.drawable.baseline_hdr_strong_black_24, 4),
    SZTUKA(51, "sztuka", R.drawable.baseline_portrait_black_24, 2),
    MUZYKA(35, "muzyka", R.drawable.baseline_music_note_black_24, 3),
    ROZRYWKA(69, "rozrywka", R.drawable.baseline_local_bar_black_24, 6),
    REKREACJA(77, "rekreacja", R.drawable.baseline_terrain_black_24, 7),
    INNE(96, "inne", R.drawable.baseline_more_black_24, 8),
    TEATR(19, "teatr", R.drawable.baseline_theaters_black_24, 0),
    KINO(1, "kino", R.drawable.baseline_videocam_black_24, 1),
    LITERATURA(61, "literatura", R.drawable.baseline_local_library_black_24, 5);

    static final List<CategoryEnum> enumValues;
    static final List<CategoryEnum> categoryValues;



    private static final Map<Integer, CategoryEnum> map = new HashMap<>();
    static {
        enumValues = new ArrayList<>(EnumSet.allOf(CategoryEnum.class));
        for (CategoryEnum value : enumValues) {
            map.put(value.code, value);
        }
    }

    private static final Map<Integer, Integer> positionMap = new HashMap<>();
    static {
        categoryValues = new ArrayList<>(EnumSet.allOf(CategoryEnum.class));
        for (CategoryEnum value : enumValues) {
            map.put(value.getPosition(), value);

        }
    }

    public static CategoryEnum forCode(int code) {
        return map.get(code);
    }

    public static CategoryEnum forPosition(int position) {
        return map.get(position);
    }

    private int code;

    public String getName() {
        return name;
    }

    private String name;
    private int drawable;

    public int getDrawable() {
        return drawable;
    }

    public int getPosition() {
        return position;
    }

    private int position;

    CategoryEnum(int code, String name, int drawable, int position) {
        this.code = code;
        this.name = name;
        this.drawable = drawable;
        this.position = position;
    }

    public int getCode() {
        return code;
    }



}
