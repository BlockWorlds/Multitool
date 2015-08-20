# Multitool

__Permissions:__
- multitools.world.\<world\>
- multitools.use.paintbrush           
- multitools.use.jackhammer           
- multitools.use.duplicator
- multitools.use.sledgehammer
- multitools.use.datawrench
- multitools.ranged.paintbrush
- multitools.ranged.jackhammer
- multitools.ranged.duplicator
- multitools.ranged.sledgehammer
- multitools.ranged.datawrench
- multitools.command.get.paintbrush
- multitools.command.get.jackhammer
- multitools.command.get.duplicator
- multitools.command.get.sledgehammer
- multitools.command.get.datawrench
- multitools.command.list
- multitools.command.toggleall
- multitools.command.togglerange

__Commands:__
- /multitools longrange - Toggles long range for tools
- /multitools alledit - Toggles editing for all blocks or just solids
- /multitools list - Prints list of all tools available
- /multitools get \<tool\>|all - Gives you the specified tool

_Command Alias:_
- multitools: mt, tools
- longrange : range
- alledit: all
- get: give

_Notes:_

When spawning a tool with "/multitools get ...", simply provide the name of the tool without spaces, capital letters does not matter. Example: "/multitools get paintbrush"

Doing "/multitools get all" will give you all the tools you have permission to.

__The Tools' Ability:__
- Jackhammer: 
 - Left click: Remove the block you aim at instanty. 
 - Right click: Remove the block you aim at instanty without physcis applied.
- Data Wrench:
 - Left click: Scroll down through 'data values/damages values/labels' of a block.
 - Right click: Scroll up through 'data values/damages values/labels' of a block.
- Sledgehammer:
 - Left click: Push the block away from you.
 - Right click: Drag the block towards you.
 - Sneak and right/left click: Force the push or drag even if there is a block in the way.
- PaintBrush:
 - Left click: Select the paint material of the block you aim at.
 - Right click: Paints the block you aim at with the paint material.
- Duplicator:
 - Left click: Gives you an item of the block you aim at in your inventory.
 - Right click: Gives you a stack of the block you aim at in your inventory.

_Notes:_

Due to the nature of Minecraft 1.8 Data Wrench and Dupcliator may not be able to preform correctly on all blocks.

Paint Brushes left in offline or non-player inventories will forget their paint material after 10 minutes. This is to prevent the server from keeping track of large amounts of unused paint brushes.
