package me.sashie.skdragon.util.color;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;

/**
 * Class based on JavaFX Colors
 *
 */
public class Colors extends Color {

	public static final Color ALICEBLUE = new Color(240, 248, 255);
	public static final Color ANTIQUEWHITE = new Color(250, 235, 215);
	public static final Color AQUA = new Color(0, 255, 255);
	public static final Color AQUAMARINE = new Color(127, 255, 212);
	public static final Color AZURE = new Color(240, 255, 255);
	public static final Color BEIGE = new Color(245, 245, 220);
	public static final Color BISQUE = new Color(255, 228, 196);
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color BLANCHEDALMOND = new Color(255, 235, 205);
	public static final Color BLUE = new Color(0, 0, 255);
	public static final Color BLUEBERRY = new Color(6, 79, 186);
	public static final Color BLUEVIOLET = new Color(138, 43, 226);
	public static final Color BROWN = new Color(165, 42, 42);
	public static final Color BURLYWOOD = new Color(222, 184, 135);
	public static final Color CADETBLUE = new Color(95, 158, 160);
	public static final Color CHARTREUSE = new Color(127, 255, 0);
	public static final Color CHARCOAL = new Color(34, 34, 34);
	public static final Color CHOCOLATE = new Color(210, 105, 30);
	public static final Color CORAL = new Color(255, 127, 80);
	public static final Color CORNFLOWERBLUE = new Color(100, 149, 237);
	public static final Color CORNSILK = new Color(255, 248, 220);
	public static final Color CRIMSON = new Color(220, 20, 60);
	public static final Color CYAN = new Color(0, 255, 255);
	public static final Color DARKBLUE = new Color(0, 0, 139);
	public static final Color DARKCYAN = new Color(0, 139, 139);
	public static final Color DARKGOLDENROD = new Color(184, 134, 11);
	public static final Color DARKGRAY = new Color(169, 169, 169);
	public static final Color DARKGREEN = new Color(0, 100, 0);
	public static final Color DARKGREY = new Color(169, 169, 169);
	public static final Color DARKKHAKI = new Color(189, 183, 107);
	public static final Color DARKMAGENTA = new Color(139, 0, 139);
	public static final Color DARKOLIVEGREEN = new Color(85, 107, 47);
	public static final Color DARKORANGE = new Color(255, 140, 0);
	public static final Color DARKORCHID = new Color(153, 50, 204);
	public static final Color DARKRED = new Color(139, 0, 0);
	public static final Color DARKSALMON = new Color(233, 150, 122);
	public static final Color DARKSEAGREEN = new Color(143, 188, 143);
	public static final Color DARKSLATEBLUE = new Color(72, 61, 139);
	public static final Color DARKSLATEGRAY = new Color(47, 79, 79);
	public static final Color DARKSLATEGREY = new Color(47, 79, 79);
	public static final Color DARKTURQUOISE = new Color(0, 206, 209);
	public static final Color DARKVIOLET = new Color(148, 0, 211);
	public static final Color DEEPPINK = new Color(255, 20, 147);
	public static final Color DEEPSKYBLUE = new Color(0, 191, 255);
	public static final Color DENIM = new Color(67, 114, 170);
	public static final Color DIMGRAY = new Color(105, 105, 105);
	public static final Color DIMGREY = new Color(105, 105, 105);
	public static final Color DODGERBLUE = new Color(30, 144, 255);
	public static final Color FIREBRICK = new Color(178, 34, 34);
	public static final Color FLORALWHITE = new Color(255, 250, 240);
	public static final Color FORESTGREEN = new Color(34, 139, 34);
	public static final Color FUCHSIA = new Color(255, 0, 255);
	public static final Color GAINSBORO = new Color(220, 220, 220);
	public static final Color GHOSTWHITE = new Color(248, 248, 255);
	public static final Color GOLD = new Color(255, 215, 0);
	public static final Color GOLDENROD = new Color(218, 165, 32);
	public static final Color GRAY = new Color(128, 128, 128);
	public static final Color GREEN = new Color(0, 128, 0);
	public static final Color GREENYELLOW = new Color(173, 255, 47);
	public static final Color GREY = new Color(128, 128, 128);
	public static final Color HONEYDEW = new Color(240, 255, 240);
	public static final Color HOTPINK = new Color(255, 105, 180);
	public static final Color HUNTERGREEN = new Color(53, 94, 59);
	public static final Color INDIANRED = new Color(205, 92, 92);
	public static final Color INDIGO = new Color(75, 0, 130);
	public static final Color IVORY = new Color(255, 255, 240);
	public static final Color KHAKI = new Color(240, 230, 140);
	public static final Color LAVENDER = new Color(230, 230, 250);
	public static final Color LAVENDERBLUSH = new Color(255, 240, 245);
	public static final Color LAWNGREEN = new Color(124, 252, 0);
	public static final Color LEMONCHIFFON = new Color(255, 250, 205);
	public static final Color LIGHTBLUE = new Color(173, 216, 230);
	public static final Color LIGHTCORAL = new Color(240, 128, 128);
	public static final Color LIGHTCYAN = new Color(224, 255, 255);
	public static final Color LIGHTGOLDENRODYELLOW = new Color(250, 250, 210);
	public static final Color LIGHTGRAY = new Color(211, 211, 211);
	public static final Color LIGHTGREEN = new Color(144, 238, 144);
	public static final Color LIGHTGREY = new Color(211, 211, 211);
	public static final Color LIGHTPINK = new Color(255, 182, 193);
	public static final Color LIGHTSALMON = new Color(255, 160, 122);
	public static final Color LIGHTSEAGREEN = new Color(32, 178, 170);
	public static final Color LIGHTSKYBLUE = new Color(135, 206, 250);
	public static final Color LIGHTSLATEGRAY = new Color(119, 136, 153);
	public static final Color LIGHTSLATEGREY = new Color(119, 136, 153);
	public static final Color LIGHTSTEELBLUE = new Color(176, 196, 222);
	public static final Color LIGHTTEAL = new Color(28, 160, 170);
	public static final Color LIGHTYELLOW = new Color(255, 255, 224);
	public static final Color LIME = new Color(0, 255, 0);
	public static final Color LIMEGREEN = new Color(50, 205, 50);
	public static final Color LINEN = new Color(250, 240, 230);
	public static final Color MAGENTA = new Color(255, 0, 255);
	public static final Color MAROON = new Color(128, 0, 0);
	public static final Color MAROON2 = new Color(80, 4, 28);
	public static final Color MEDIUMAQUAMARINE = new Color(102, 205, 170);
	public static final Color MEDIUMBLUE = new Color(0, 0, 205);
	public static final Color MEDIUMORCHID = new Color(186, 85, 211);
	public static final Color MEDIUMPURPLE = new Color(147, 112, 219);
	public static final Color MEDIUMSEAGREEN = new Color(60, 179, 113);
	public static final Color MEDIUMSLATEBLUE = new Color(123, 104, 238);
	public static final Color MEDIUMSPRINGGREEN = new Color(0, 250, 154);
	public static final Color MEDIUMTURQUOISE = new Color(72, 209, 204);
	public static final Color MEDIUMVIOLETRED = new Color(199, 21, 133);
	public static final Color MIDNIGHTBLUE = new Color(25, 25, 112);
	public static final Color MINTCREAM = new Color(245, 255, 250);
	public static final Color MISTYROSE = new Color(255, 228, 225);
	public static final Color MOCCASIN = new Color(255, 228, 181);
	public static final Color NAVAJOWHITE = new Color(255, 222, 173);
	public static final Color NAVY = new Color(0, 0, 128);
	public static final Color OLDLACE = new Color(253, 245, 230);
	public static final Color OLIVE = new Color(128, 128, 0);
	public static final Color OLIVEDRAB = new Color(107, 142, 35);
	public static final Color ORANGE = new Color(255, 165, 0);
	public static final Color ORANGERED = new Color(255, 69, 0);
	public static final Color ORCHID = new Color(218, 112, 214);
	public static final Color PALEGOLDENROD = new Color(238, 232, 170);
	public static final Color PALEGREEN = new Color(152, 251, 152);
	public static final Color PALETURQUOISE = new Color(175, 238, 238);
	public static final Color PALEVIOLETRED = new Color(219, 112, 147);
	public static final Color PAPAYAWHIP = new Color(255, 239, 213);
	public static final Color PEACHPUFF = new Color(255, 218, 185);
	public static final Color PERU = new Color(205, 133, 63);
	public static final Color PINK = new Color(255, 192, 203);
	public static final Color PLUM = new Color(221, 160, 221);
	public static final Color POWDERBLUE = new Color(176, 224, 230);
	public static final Color PURPLE = new Color(128, 0, 128);
	public static final Color RED = new Color(255, 0, 0);
	public static final Color ROBINEGGBLUE = new Color(141, 218, 247);
	public static final Color ROSYBROWN = new Color(188, 143, 143);
	public static final Color ROYALBLUE = new Color(65, 105, 225);
	public static final Color SADDLEBROWN = new Color(139, 69, 19);
	public static final Color SALMON = new Color(250, 128, 114);
	public static final Color SANDYBROWN = new Color(244, 164, 96);
	public static final Color SEAFOAMGREEN = new Color(77, 226, 140);
	public static final Color SEAGREEN = new Color(46, 139, 87);
	public static final Color SEASHELL = new Color(255, 245, 238);
	public static final Color SIENNA = new Color(160, 82, 45);
	public static final Color SILVER = new Color(192, 192, 192);
	public static final Color SKYBLUE = new Color(135, 206, 235);
	public static final Color SLATEBLUE = new Color(106, 90, 205);
	public static final Color SLATEGRAY = new Color(112, 128, 144);
	public static final Color SLATEGREY = new Color(112, 128, 144);
	public static final Color SNOW = new Color(255, 250, 250);
	public static final Color SPRINGGREEN = new Color(0, 255, 127);
	public static final Color STEELBLUE = new Color(70, 130, 180);
	public static final Color TAN = new Color(210, 180, 140);
	public static final Color TEAL = new Color(0, 128, 128);
	public static final Color THISTLE = new Color(216, 191, 216);
	public static final Color TOMATO = new Color(255, 99, 71);
	public static final Color TURQUOISE = new Color(64, 224, 208);
	public static final Color VIOLET = new Color(238, 130, 238);
	public static final Color WHEAT = new Color(245, 222, 179);
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color WHITESMOKE = new Color(245, 245, 245);
	public static final Color YELLOW = new Color(255, 255, 0);
	public static final Color YELLOWGREEN = new Color(154, 205, 50);
	// TODO add more colors eventually

	private static HashMap<String, Color> colors = new HashMap<>();

	static {
		try {
			for (Field color : Colors.class.getDeclaredFields()) {
				color.setAccessible(true);
				if (color.getType() == Color.class) {
					colors.put(color.getName().toLowerCase(Locale.ENGLISH).replace("_", " "), (Colors) color.get(null));
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static Color valueOf(String name) {
		return name == null ? null : colors.get(name.toLowerCase(Locale.ENGLISH));
	}

	public Colors(int r, int g, int b) {
		super(r, g, b);
	}

}
