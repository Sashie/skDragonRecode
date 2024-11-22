
# SkDragon Recode

SkDragon Recode is a powerful and flexible **[Skript](https://github.com/SkriptLang/Skript)** addon designed for creating advanced particle effects and visuals in Minecraft. This recoded version introduces easier syntax, compatibility with Minecraft versions 1.16.5+, and plans for expanding functionality beyond particles in the future.

## Features

- **Advanced Particle Effects:** Create spirals, arcs, lines, and more with customizable parameters.
- **Simplified Syntax:** Intuitive and streamlined commands for easier integration into Skript projects.
- **Version Compatibility:** Fully tested and compatible with Minecraft versions **1.16.5 and above**.
- **Future Expansion:** Plans to introduce features beyond particles to enhance server scripting.

---

## Basic Usage

### **Important Note**  
For every particle effect, you must set a **unique ID (`uniqueid`)**.  
This ID is mandatory to register, modify, or manipulate the effect later.  
Without a `uniqueid`, the effect cannot be properly managed.  

**Example:** `register new particle effect circle with id "mycircle"`

> If an effect doesn't use a particular property such as radius/rotation vector etc.. , there should be a console message indicating this, including the effect's ID name.
> Every property has a default value, meaning you don't always need to specify every property. If a property is not specified, it will automatically use its default value.

--- 

## Simple effects

#### 1. Drawing a Circle Effect
```skript
command /circle:
    trigger:
        register new particle effect circle with id "mycircle.%player%":
            set solid mode of effect to true
            set density of effect to 20
            set displacement vector of effect to vector(0,2,0) # Moves the circle 2 blocks above the player's position
            set radius of effect to 1 # Defines the radius of the circle
            set rotation vector of effect to vector(90,0,0) # Rotates the circle 90° along the X-axis

            #every other change here doesn't require you to specify the effect's id.

        start particle effect "mycircle.%player%" at player repeating with an interval of 1 tick
```

#### 1.1 Dynamically editing the circle effect after it has started
```skript
command /editmycircle:
    trigger:
        set solid mode of effect "mycircle.%player%" to false
        set density of effect "mycircle.%player%" to 5
        set displacement vector of effect "mycircle.%player%" to vector(0,1.5,0) 
        set radius of effect "mycircle.%player%" to 1.5 
        set rotation vector of effect "mycircle.%player%" to vector(0,90,0) 
```
#### 2 Coloring our circle/using a different particle
> **Note:**  
> - Only the "dust","mob spell" and "mob spell ambient" particle types ("dust" is referred to as "redstone" in earlier minecraft versions) supports customizable colors.
> - In rare cases where Mojang changes or removes particle names, it's up to the Skript developer to update the names accordingly.

> - Depending on the particle effect you are using, there may be multiple particles that you can customize, such as "1st particle," "2nd particle," etc.

```skript
set the 1st particle red value of the particle effect "mycircle.%player%" to 255
set the 1st particle blue value of the particle effect "mycircle.%player%" to 255
set the 1st particle green value of the particle effect "mycircle.%player%" to 255
```

```skript
set the 1st particle color of the particle effect "mycircle.%player%" to custom color using rgb 255, 255, 0
set the 1st particle color of the particle effect "mycircle.%player%" to gradient between custom color using rgb 255, 0, 0 and custom color using rgb 0, 255, 0 with 10 steps
```

```skript
set 1st particle of effect "mycircle.%player%" to flame
```

#### Hiding or showing our circle to specific players
```
set {_players::*} to players of effect "mycircle.%player%"
set clientside players of effect "mycircle.%player%" to {_players::*}
add player to clientside players of effect "mycircle.%player%"
remove player from clientside players of effect "mycircle.%player%"
delete clientside players of effect "mycircle.%player%"
```
> **Note:**  
> - If the list is deleted, all players can see the effect again.  
> - Using this expression ensures the effect no longer plays globally, it is only visible to the specified player list.

---

## Complex effects

There are many complex effects, with more planned for future updates. For this example, we will use the most iconic one: the wings effect.

> Some of the complex effects also uses the same properties as simple effects, with some additional properties for enhanced customization.

#### 1. Drawing a wings Effect
```skript
command /wings:
    trigger:
        register new particle effect wings.%player% with id "mywings.%player%":
            set style of effect to 1 

            set the 1st particle color of the particle effect to custom color using rgb 0, 0, 0
            set the 2nd particle color of the particle effect to custom color using rgb 255, 0, 0
            set the 3rd particle color of the particle effect to custom color using rgb 0, 0, 0

            set 1st extra value of effect to 0.2 # Height of the particles (default is 0.2)
            set 2nd extra value of effect to 0.2 # Distance between each particle (default is 0.2)
            set 3rd extra value of effect to 0.2 # Distance from the player's back (default is 0.2)

            set wing angle of effect to 0 # Adjusts the tilt or spread of the wings, (default is 0)
            set wing flap range of effect to 20 # How far the wings will flap back and forth, (default is 20)
            set wing flap step of effect to 0.3 # How fast the wings flap, (default is 0.3)

        start particle effect "mywings.%player%" at player repeating with an interval of 1 tick
```

#### 1.1 Dynamically editing the wings effect after it has started
```skript
command /editmywings:
    trigger:
        set the 1st particle color of the particle effect "mywings.%player%" to custom color using rgb 0, 0, 0
        set the 2nd particle color of the particle effect "mywings.%player%" to custom color using rgb 255, 0, 0
        set the 3rd particle color of the particle effect  "mywings.%player%"to custom color using rgb 0, 0, 0

        set 1st extra value of effect "mywings.%player%" to 0.5 
        set 2nd extra value of effect "mywings.%player%" to 0.3 
        set 3rd extra value of effect "mywings.%player%" to 0.4 

        set wing angle of effect "mywings.%player%" to 10
        set wing flap range of effect "mywings.%player%" to 22 
        set wing flap step of effect "mywings.%player%" to 0.5
```

> Many complex effects have their own unique properties, which will be documented in the wiki once it becomes available.


--- 

## Syntax Documentation

> Detailed syntax descriptions will be added here as the wiki is completed.

For now, check out the [SkUnity docs](https://docs.skunity.com/syntax/search/addon:skdragon+) for syntax and limited examples.

[![Get on skUnity](https://docs.skunity.com/skunity/library/Docs/Assets/assets/images/buttons/v1/get_on_docs_3.png)](https://docs.skunity.com/syntax/search/addon:skDragon)

---

## Contributing

We welcome contributions! If you have ideas for new features, bug fixes, or performance improvements, feel free to [submit issues directly on GitHub](https://github.com/Sashie/skDragonRecode/issues).

---

## Support

For questions, support, or to showcase your creations:
- Join the [Skript Chat Discord](https://discord.gg/w6CyYkAWa4) and navigate to the `#skdragon` channel.
- [Submit issues directly on GitHub](https://github.com/Sashie/skDragonRecode/issues).

---

