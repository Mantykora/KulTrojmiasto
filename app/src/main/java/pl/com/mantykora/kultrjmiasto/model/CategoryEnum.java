package pl.com.mantykora.kultrjmiasto.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.com.mantykora.kultrjmiasto.R;

public enum CategoryEnum {
    NAUKA(83, "nauka", R.drawable.baseline_hdr_strong_black_24),
    SZTUKA(51, "sztuka", R.drawable.baseline_portrait_black_24),
    MUZYKA(35, "muzyka", R.drawable.baseline_music_note_black_24),
    ROZRYWKA(69, "rozrywka", R.drawable.baseline_local_bar_black_24),
    REKREACJA(77, "rekreacja", R.drawable.baseline_terrain_black_24),
    INNE(96, "inne", R.drawable.baseline_more_black_24),
    TEATR(19, "teatr", R.drawable.baseline_theaters_black_24),
    KINO(1, "kino", R.drawable.baseline_videocam_black_24),
    LITERATURA(61, "literatura", R.drawable.baseline_local_library_black_24);

    static final List<CategoryEnum> enumValues;



    private static final Map<Integer, CategoryEnum> map = new HashMap<>();
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
    private int drawable;

    public int getDrawable() {
        return drawable;
    }

    CategoryEnum(int code, String name, int drawable) {
        this.code = code;
        this.name = name;
        this.drawable = drawable;
    }

    public int getCode() {
        return code;
    }



}
