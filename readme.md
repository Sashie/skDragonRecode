Here’s the updated **`README.md`** with the corrected circle example included and the license section removed:

---

# SkDragon Recode

SkDragon Recode is a powerful and flexible **[Skript](https://github.com/SkriptLang/Skript)** addon designed for creating advanced particle effects and visuals in Minecraft. This recoded version introduces easier syntax, compatibility with Minecraft versions 1.16.5+, and plans for expanding functionality beyond particles in the future.

## Features

- **Advanced Particle Effects:** Create spirals, arcs, lines, and more with customizable parameters.
- **Simplified Syntax:** Intuitive and streamlined commands for easier integration into Skript projects.
- **Version Compatibility:** Fully tested and compatible with Minecraft versions **1.16.5 and above**.
- **Future Expansion:** Plans to introduce features beyond particles to enhance server scripting.

---

## Basic Usage

### Example Commands

#### Drawing a Circle Effect
```skript
command /circle:
    trigger:
        register new particle effect circle with id "mycircle":
            set solid mode of effect to true
            set density of effect to 20
            set displacement vector of effect to vector(0,2,0) # Moves the circle 2 blocks above the player's position
            set radius of effect to 1 # Defines the radius of the circle
            set rotation vector of effect to vector(90,0,0) # Rotates the circle 90° along the X-axis
        start particle effect "mycircle" at player repeating with an interval of 1 tick
```

---

## Syntax Documentation

> Detailed syntax descriptions will be added here as the wiki is completed.

For now, check out the [wiki](#) for syntax examples and explanations.

---

## Contributing

We welcome contributions! If you have ideas for new features, bug fixes, or performance improvements, feel free to [submit issues directly on GitHub](https://github.com/Sashie/skDragonRecode/issues).

---

## Support

For questions, support, or to showcase your creations:
- Join the [Skript Chat Discord](https://discord.gg/w6CyYkAWa4) and navigate to the `#skdragon` channel.
- [Submit issues directly on GitHub](https://github.com/Sashie/skDragonRecode/issues).

---

Let me know if you’d like to include additional examples or changes!