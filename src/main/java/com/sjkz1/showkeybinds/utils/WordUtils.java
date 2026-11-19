package com.sjkz1.showkeybinds.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WordUtils
{
    private static final Map<String, String> ABBR_MAP = new HashMap<String, String>();

    static
    {
        // Names as shown by older / readable translations
        ABBR_MAP.put("left control", "lft ctrl");
        ABBR_MAP.put("left shift", "lft shft");
        ABBR_MAP.put("right control", "rgh ctrl");
        ABBR_MAP.put("right shift", "rgh shft");
        ABBR_MAP.put("enter", "ent");
        ABBR_MAP.put("delete", "del");
        ABBR_MAP.put("escape", "esc");

        // Names produced by LWJGL 2 (Keyboard.getKeyName) on Minecraft 1.12.2
        ABBR_MAP.put("lcontrol", "lft ctrl");
        ABBR_MAP.put("lshift", "lft shft");
        ABBR_MAP.put("rcontrol", "rgh ctrl");
        ABBR_MAP.put("rshift", "rgh shft");
        ABBR_MAP.put("return", "ent");
    }

    public static String convert(String word)
    {
        if (word == null) return null;
        String lower = word.toLowerCase(Locale.ROOT);
        String abbreviation = ABBR_MAP.get(lower);
        return abbreviation != null ? abbreviation : lower;
    }

    public static String capitalize(String word)
    {
        if (word == null || word.isEmpty()) return word;
        return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase(Locale.ROOT);
    }
}
