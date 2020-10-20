# Plugin Releases

### Installation
- Locate `C:\Users\your_name\.runelite` or `%userprofile%./runelite`
- Create the **bexternalplugins** folder if it's not present
- Download any of my plugin jars
- Drag and drop into **bexternalplugins** folder and restart your client
- If you experience any issues with the plugins not working feel free to ask for help in **#support**

### To-Do List:
- Plugins:
    - Zulrah Plugin (WiP)
    - Player Scouter
    - Spellbook Resizer
    - Spell Effect Timers
- Updates:
    - Update Spawn Predictor (:gottago:)
    - Update ToB (Still cleaning up/removing useless stuff)
    - Update KPH (Could use a rewrite for a better system & might include Socket)
    - Fix Lizardmen Shaman Spawns Tick Counter
    - Completely re-make Olm Additions (Extremely outdated and could be converted to CoX Additions?)

### My Plugins List
| PLUGIN NAME | VERSION | DOWNLOAD | DETAILS |
| ------ | ------ | ------ | ------ |
| ToB Additions | 0.24 | [Click Here](https://github.com/losingticks/plugin-releases/raw/main/releases/tobadditions.jar) | [Click Here](#tob-additions) |
| Kills Per Hour | 0.2 | [Click Here](https://github.com/losingticks/plugin-releases/raw/main/releases/killsperhour.jar) | [Click Here](#kills-per-hour) |
| Fight Caves Spawn Predictor | 0.1 | [Click Here](https://github.com/losingticks/plugin-releases/raw/main/releases/spawnpredictor.jar) | [Click Here](#spawn-predictor) |
| Custom Swapper | 0.10 | [Click Here](https://github.com/losingticks/plugin-releases/raw/main/releases/menuentryswapper.jar) | [Click Here](#custom-swapper) |
| Nightmare Extended | 0.3 | [Click Here](https://github.com/losingticks/plugin-releases/raw/main/releases/znightmare.jar) | [Click Here](#nightmare-extended) |
| Animation Cooldown | 0.2 | [Click Here](https://github.com/losingticks/plugin-releases/raw/main/releases/animationcooldown.jar) | [Click Here](#animation-cooldown) |
| Grotesque Guardians | 0.3 | [Click Here](https://github.com/losingticks/plugin-releases/raw/main/releases/grotesqueguardians.jar) | [Click Here](#grotesque-guardians) |
| Olm Additions | 0.1 | [Click Here](https://github.com/losingticks/plugin-releases/raw/main/releases/olmadditions.jar) | [Click Here](#olm-additions) |
| Vorkath | 0.1 | [Click Here](https://github.com/losingticks/plugin-releases/raw/main/releases/vorkath.jar) | [Click Here](#vorkath) |
| Lizardmen Shaman | 0.1 | [Click Here](https://github.com/losingticks/plugin-releases/raw/main/releases/zshaman.jar) | [Click Here](#lizardmen-shaman) |
| Developer Tools | 0.1 | [Click Here](https://github.com/losingticks/plugin-releases/raw/main/releases/developertools.jar) | [Click Here](#developer-tools) |

### Old RuneLite Plugins List
| Plugin | VERSION | DOWNLOAD |
| ------ | ------ | ------ |
| Demonic Gorillas | 0.1 | [Click Here](https://github.com/losingticks/plugin-releases/raw/main/releases/demonicgorilla.jar) |
| Opponent Information | 0.1 | [Click Here](https://github.com/losingticks/plugin-releases/raw/main/releases/opponentinfo.jar) |

# Plugin Features/Changelogs

### ToB Additions
**Features**
```diff
Maiden:
- Nylo HP, Distance, and Freeze Timers
- Proc Threshold
- Blood Splat Tick Counter
- Heals Counter

Bloat:
- In-depth HP
- True Location

Nylo:
- (Boss) Attack/Switch Tick Counter
- Waves Wheelchair
- Nylo Explosion Ticks

Sotetseg:
- Attack Tick Counter
- Attack Counter
- Projectile Options
- Hats Options
- AoE Orb Options

Xarpus:
- Exhumed Timers
- Heals Counter
- Line of Sight

Verzik:
- P2 Lightning stuff
- Purple Crab stuff
- Yellows Tick Counter
- Green Ball Tick Counter
- Display who has the Green Ball
```
**Changelog**
```diff
+ 10/5/2020 - Added Dragon Darts to wheelchair
+ 10/5/2020 - Added P3 Xarpus Line of Sight
+ 10/11/2020 Added Maiden Nylo Freeze Timers
+ 10/11/2020 Added Maiden Heals Infobox to 'Maiden Heals'
+ 10/11/2020 Unspaghetti'd 'Bloat's In-depth HP'
+ 10/11/2020 Fixed Bloat's HP Bar removing the wrong widget
+ 10/11/2020 Fixed Bloat's HP Bar building a new widget post death
+ 10/11/2020 Fixed Bloat's HP Overlay displaying post death
+ 10/11/2020 Fixed Nylo Boss tick counters not being center
+ 10/11/2020 Fixed Sotetsegs attack counter displaying while room is inactive
+ 10/11/2020 Fixed Sotetsegs overlays displaying post death
+ 10/11/2020 Fixed Xarpus & Verzik not being reset
+ 10/11/2020 Fixed Xarpus showing healed chat messages whilst config is disabled
+ 10/11/2020 Changed Xarpus and Maiden 'Heals' config to be split into two chat messages
+ 10/11/2020 Fixed yellows showing post despawn
+ 10/11/2020 Updated/Removed a lot of outdated code
+ 10/11/2020 Plugin should reset on logout now
- 10/11/2020 Removed 'HP Overlay Color'
- 10/11/2020 Removed 'Verzik P3 Tank'
- 10/11/2020 Removed 'DPS Statistics'
- 10/11/2020 Removed 'Dif. Proj. Ticks'
- 10/11/2020 Removed 'Verzik Phase Highlights'
+ 10/19/2020 Added Nylo Boss Prayer Helper
```



### Kills Per Hour
Tracks your Kills Per Hour at bosses

**Click the start button right before you enter a room/instance or start a boss fight.**

> **FAQ:**

> Q.) Why is the kill duration/fastest kill/average kill slightly off? I got a faster time in-game but not on the panel.

> A.) The plugin works like this: If an NPC is within the render distance and contains the name of the boss in the whitelist START the timer. STOP the timer if that same boss has despawned. RuneScape deals with durations differently. But I am not going to waste my time to make every boss unique and just going to generalize it.

**Bosses that work but aren't whitelisted**

If you're whitelisting multiple bosses split each boss name with a comma.
- Example: `dagannoth supreme, dagannoth prime`
```
dagannoth supreme 
dagannoth prime 
dagannoth rex
skotizo
hespori
the mimic
bryophyta
obor
```
**Bosses/Activities that don't/won't work**
```
Gauntlet
ToB
CoX
Fight Caves
Inferno
Cave Kraken (for now)
Abyssal Sire
Barbarian Assault
Giant Mole
```
**Changelog**
```diff
+ 8/24/2020 - Added move-able overlay, with customizable settings to show on it. 
+ 8/24/2020 - Added a option to enable chat messages (displays KPH after each kill)
```



### Spawn Predictor
**Features**
```diff
- Lobby Rotation Information 
Displays the ongoing rotation for that time, and the upcoming rotation in x amount of time
- Display Current Wave
- Display Next Wave
```
**Notes**
- All of these features are automatic, you don't need to press any buttons or anything. 
- The plugin determines rotation on entrance and builds the entire fight caves before the first wave starts. 
- The ground markers are unique to the NPCs size & name, and they will update locations to the right spawn points every wave as they should.



### Custom Swapper
**Features**
```diff
- Custom Swaps
- Shift Click Custom Swaps
- Removing Options
```
**Syntax Configuration**

Configs are trimmed, so it allows for spaces inbetween commas

Examples: `guzzle,dwarven rock cake` & `guzzle, dwarven rock cake` will work

- Custom Swaps/Remove Options
    - Syntax: `option,target`
    - Example: `guzzle,dwarven rock cake`
- Swap menu options conditionally **OPTIONAL**
    - Syntax: `option,target,top_entry,top_target`
    - Example: `take,shark,take,*` 
    
> **Note:** This will swap the `take` option on `shark` when hovering over **anything** but the top left click entry **HAS** to be `take`

- Walk here prioritization when hovering over actors (Players and/or NPCs)
    - Syntax: `walk here,,top_entry,top_target`
    - Example: `walk here,,attack,cow*`
- Spell Swapping
    - Syntax: `*,*username*,cast,spell_name*`
    - Example: `*,*losing ticks*,cast,energy transfer*`

**Wildcard Configuration**
- Asterisks (*) as the target name: Swaps/Removes everything involving the set option
    - Example: `chop down,*` 
> **Note:** This will swap/remove every `chop down` option regardless of the tree name

- Asterisks (*) after the target or option name: This essentially means `contains` if the target or option contains said input swap/remove it.
    - Example: `attack,husk*`
    - Example Two: `att*,husk*`
    - Example Three: `tele*,max cape`
> **Note:** If you're swapping anything that involves NPCs or Players or Ground Items you need an asterisks after the target name.
    
- NPC/Player Prioritization
    - Syntax: `option_you_want_prioritized,target_you_want_prioritized`
    - Example: `attack,husk*`

If you have the plugin `Opponent Information` turned on with the option `Show opponents in menu` prefix target name with an asterisks
- Example: `attack,*husk*`
    
**Demonstration**
https://streamable.com/h5jtd8

**Changelog**
```diff
+ 9/20/2020 - option can now utilize the * wildcard
+ 9/20/2020 - Added support to priotize the "Walk Here" (see above)
+ 9/20/2020 - With this added support you can now priotize entries without unconditionally throwing them on top (see above)
```



### Nightmare Extended
**Features**
```diff
- Tick Counter
Ticks until Nightmare will attack you again
- Event Tick Counter
Flower Power, Surge (Until it runs), Parasites, and Wake up timer (Only if you saw death animation)
- Parasites (Displays on the parasites target)
- Surge settings 
- Prayer Helper (Overlay)
- Prayer Marker (Prayer Book)
Marks the correct prayer (even when cursed) to use as an overlay and/or in prayer book. Overlay will change colors dependent on if you're praying correctly
- Totem Highlights
- Totem HP
- Grasping Claws Highlight
- Grasping Claws render distance 
Because why render the entire room when you can render the ones around you
- Husk Highlight 
Green = Ranged Husk, and Blue = Magic Husk
- Husk Target Highlight
- Spores Tick Counter
- Spore Highlight
```
**Changelog**
```
Lost in transit... sadcat, sadgecry, prayge
```



### Animation Cooldown
**Features**
- Personalized presets to configure the tick counter above players head displaying when they will come off cooldown.
- Can be used for weapons and/or any animation you choose. Useful for PvMing and Skilling. 

Use the `Debug` option to fetch most of the data you'll need to setup the lists. This wont display ticks for animations only weapon speeds so i.e. burning a log. This mean you will have to know how long it is or look them up on Google. 

**Syntax Configuration**
- Weapon List
    - Syntax: `weapon_id,animation_id,ticks`
    - Example: `22325,8056,5`
- Animation List
    - Syntax: `animation_id,ticks`
    - Example: `8056,5`

**Changelog**
```
Lost in transit... sadcat, sadgecry, prayge
```

### Grotesque Guardians
**Features**
```diff
- Falling Rocks, Lightning, Stone Orb (AoE or Tile) Overlays
- Dawn/Dusk Tick Counter
- Dawn/Dusk Boss Highlights
Displays when each is immune/not immune or transitioning
- Dawn Healing Orbs Tick Counter
- Dusk step back warning for sun attack
- Prayer Helper
- Splits
```
**Changelog**
```
Lost in transit... sadcat, sadgecry, prayge
```



### Olm Additions
```diff
- Olm phase splits timer
- Melee hand crippled tick counter
- Burn/Acid tick counter 
Yourself only. Can thank RuneLite for this
- Prayer Helper 
Overlay changes color dependent on if you're praying correctly or not
```
> **Note:**

> Only works when there is 4 phases including the head phase

> If you're late into the Olm room the timer will be off (This is for teams only & If you use de0s plugins you'll know about this issue)

> If you end up dying in the Olm room the counter will be split into two chat messages for that corresponding phase. You will get one when you die showing how long you were alive for during that phase and one when the phase ends (If this happens the timer **can** occasionally be off no clue why). Pretty hard to combat this because of how RuneLite deals with NPCs and GameObjects, so if it happens just do some simple math and you'll be fine... or... just don't die



### Vorkath
**Features**
```diff
- Count Vorkaths attacks until next phase
- Ability to name which phase is next
- Ability to highlight the Zombified Spawn the entire duration it's alive with a color of your choosing
```


### Lizardmen Shaman
**Features**
```diff
- Shaman Tick Counter
- Spawn Tick Counter (Time until Explosion)
- Spawn Explosion Type (AoE or Tile)
```


### Developer Tools
**Features**
```diff
- Actor Orientation
- Detailed Projectile Overlay
- Projectile Inspector 
Displays all projectile events, and the details about that projectile
- Location
Displays more coordinates
- Tile Tooltip
Displays more coordinates
- Players Overlay
Shortened version from RuneLite
- NPCs Overlay
Shortened version from RuneLite, and includes NPC Index
- Menu Option Debug
Click a menu option to have a chat printout for everything about it
- NPC Event Inspector
Displays all NPC Events and details about said NPC
- Pathfinder
- Renderable Hider
Supports: Players/NPCs and Projectiles
```
> **Note:** If you'd like more options added to make plugin development easier without writing up quick test plugins to see everything or `log.debug`'ing everything and reading through spam let me know.


