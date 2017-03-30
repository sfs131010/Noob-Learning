package micdoodle8.mods.galacticraft.planets.Kepler186F.blocks;

import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc;
import micdoodle8.mods.galacticraft.core.items.ItemBlockGC;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.Kepler186.items.ItemBlockKepler186F;
import micdoodle8.mods.galacticraft.planets.Kepler186.items.ItemBlockShortRangeTelepad;
import micdoodle8.mods.galacticraft.planets.Kepler186.items.ItemBlockWalkway;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

public class Kepler186FBlocks
{
    public static Block blockWalkway;
    public static Block blockBasic;
    //	public static Block machineFrame;
    public static Block beamReflector;
    public static Block beamReceiver;
    public static Block shortRangeTelepad;
    public static Block fakeTelepad;
    public static Block treasureChestTier2;
    public static Block treasureChestTier3;
    public static Block blockDenseIce;
	public static Block blockMinerBase;
	public static Block minerBaseFull;

    public static void initBlocks()
    {
        Kepler186FBlocks.treasureChestTier2 = new BlockTier2TreasureChest("treasure_t2");
        Kepler186FBlocks.treasureChestTier3 = new BlockTier3TreasureChest("treasure_t3");
        Kepler186FBlocks.blockWalkway = new BlockWalkway("walkway");
        Kepler186FBlocks.blockBasic = new BlockBasicKepler186F("Kepler186F_block");
        Kepler186FBlocks.beamReflector = new BlockBeamReflector("beam_reflector");
        Kepler186FBlocks.beamReceiver = new BlockBeamReceiver("beam_receiver");
        Kepler186FBlocks.shortRangeTelepad = new BlockShortRangeTelepad("telepad_short");
        Kepler186FBlocks.fakeTelepad = new BlockTelepadFake("telepad_fake");
        Kepler186FBlocks.blockDenseIce = new BlockIceKepler186F("dense_ice");
        Kepler186FBlocks.blockMinerBase = new BlockMinerBase("miner_base");
        Kepler186FBlocks.minerBaseFull = new BlockMinerBaseFull("miner_base_full");

        GCBlocks.hiddenBlocks.add(Kepler186FBlocks.fakeTelepad);
        GCBlocks.hiddenBlocks.add(Kepler186FBlocks.minerBaseFull);
    }

    public static void registerBlock(Block block, Class<? extends ItemBlock> itemClass)
    {
        String name = block.getUnlocalizedName().substring(5);
        GCCoreUtil.registerGalacticraftBlock(name, block);
        GameRegistry.registerBlock(block, itemClass, name);
        GCBlocks.registerSorted(block);
    }

    public static void registerBlocks()
    {
        registerBlock(Kepler186FBlocks.treasureChestTier2, ItemBlockDesc.class);
        registerBlock(Kepler186FBlocks.treasureChestTier3, ItemBlockDesc.class);
        registerBlock(Kepler186FBlocks.blockBasic, ItemBlockKepler186F.class);
        registerBlock(Kepler186FBlocks.blockWalkway, ItemBlockWalkway.class);
        registerBlock(Kepler186FBlocks.beamReflector, ItemBlockDesc.class);
        registerBlock(Kepler186FBlocks.beamReceiver, ItemBlockDesc.class);
        registerBlock(Kepler186FBlocks.shortRangeTelepad, ItemBlockShortRangeTelepad.class);
        registerBlock(Kepler186FBlocks.fakeTelepad, ItemBlockGC.class);
        registerBlock(Kepler186FBlocks.blockDenseIce, ItemBlockGC.class);
       	registerBlock(Kepler186FBlocks.blockMinerBase, ItemBlockDesc.class);
       	registerBlock(Kepler186FBlocks.minerBaseFull, ItemBlockDesc.class);
    }

    private static void setHarvestLevel(Block block, String toolClass, int level, int meta)
    {
        block.setHarvestLevel(toolClass, level, block.getStateFromMeta(meta));
    }

    private static void setHarvestLevel(Block block, String toolClass, int level)
    {
        block.setHarvestLevel(toolClass, level);
    }
    
    public static void setHarvestLevels()
    {
        setHarvestLevel(Kepler186FBlocks.blockBasic, "pickaxe", 0, 0);   //Rock
        setHarvestLevel(Kepler186FBlocks.blockBasic, "pickaxe", 0, 1);   //Rock
        setHarvestLevel(Kepler186FBlocks.blockBasic, "pickaxe", 0, 2);   //Rock
        setHarvestLevel(Kepler186FBlocks.blockBasic, "pickaxe", 2, 3);   //Aluminium
        setHarvestLevel(Kepler186FBlocks.blockBasic, "pickaxe", 3, 4);   //Ilmenite
        setHarvestLevel(Kepler186FBlocks.blockBasic, "pickaxe", 2, 5);   //Iron
    }

    public static void oreDictRegistration()
    {
        OreDictionary.registerOre("oreAluminum", new ItemStack(Kepler186FBlocks.blockBasic, 1, 3));
        OreDictionary.registerOre("oreAluminium", new ItemStack(Kepler186FBlocks.blockBasic, 1, 3));
        OreDictionary.registerOre("oreNaturalAluminum", new ItemStack(Kepler186FBlocks.blockBasic, 1, 3));
		OreDictionary.registerOre("oreIlmenite", new ItemStack(Kepler186FBlocks.blockBasic, 1, 4));
        OreDictionary.registerOre("oreIron", new ItemStack(Kepler186FBlocks.blockBasic, 1, 5));
    }
}
