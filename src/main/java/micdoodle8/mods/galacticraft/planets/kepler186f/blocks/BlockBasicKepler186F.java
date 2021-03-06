package micdoodle8.mods.galacticraft.planets.kepler186f.blocks;

import com.google.common.base.Predicate;
import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.IPlantableBlock;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import micdoodle8.mods.galacticraft.planets.GalacticraftPlanets;
import micdoodle8.mods.galacticraft.planets.kepler186f.Kepler186FModule;
import micdoodle8.mods.galacticraft.planets.kepler186f.entities.EntityCreeperBoss;
import micdoodle8.mods.galacticraft.planets.kepler186f.items.Kepler186FItems;
import micdoodle8.mods.galacticraft.planets.kepler186f.tile.TileEntityDungeonSpawnerKepler186F;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockBasicKepler186F extends Block implements IDetectableResource, IPlantableBlock, ITileEntityProvider, ITerraformableBlock, ISortableBlock
{
    public static final PropertyEnum BASIC_TYPE = PropertyEnum.create("basicTypekKepler186F", EnumBlockBasic.class);

    public enum EnumBlockBasic implements IStringSerializable
    {
        ORE_COPPER(0, "ore_copper_kepler186f"),
        ORE_TIN(1, "ore_tin_kepler186f"),
        ORE_DESH(2, "ore_desh_kepler186f"),
        ORE_IRON(3, "ore_iron_kepler186f"),
        COBBLESTONE(4, "cobblestone"),
        SURFACE(5, "kepler186f_surface"),
        MIDDLE(6, "kepler186f_middle"),
        DUNGEON_BRICK(7, "dungeon_brick"),
        DESH_BLOCK(8, "desh_block"),
        KEPLER186F_STONE(9, "kepler186f_stone"),
        DUNGEON_SPAWNER(10, "dungeon_spawner");

        private final int meta;
        private final String name;

        private EnumBlockBasic(int meta, String name)
        {
            this.meta = meta;
            this.name = name;
        }

        public int getMeta()
        {
            return this.meta;
        }

        public static EnumBlockBasic byMetadata(int meta)
        {
            return values()[meta];
        }

        @Override
        public String getName()
        {
            return this.name;
        }
    }

//    @SideOnly(Side.CLIENT)
//    private IIcon[] kepler186fBlockIcons;

    //Metadata values:
    //0 copper ore, 1 tin ore, 2 desh ore, 3 iron ore
    //4 cobblestone, 5 top (surface rock), 6 middle, 7 dungeon brick
    //8 desh decoration block
    //9 Kepler186F stone
    //10 dungeon spawner (invisible)

    public BlockBasicKepler186F(String assetName)
    {
        super(Material.rock);
        this.setUnlocalizedName(assetName);
    }

    @Override
    public MapColor getMapColor(IBlockState state)
    {
        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.DUNGEON_BRICK)
        {
            return MapColor.greenColor;
        }
        else if (state.getValue(BASIC_TYPE) == EnumBlockBasic.SURFACE)
        {
            return MapColor.dirtColor;
        }

        return MapColor.redColor;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.DUNGEON_SPAWNER)
        {
            return null;
        }

        return super.getCollisionBoundingBox(worldIn, pos, state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos);
        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.DUNGEON_SPAWNER)
        {
            return AxisAlignedBB.fromBounds(pos.getX() + 0.0D, pos.getY() + 0.0D, pos.getZ() + 0.0D, pos.getX() + 0.0D, pos.getY() + 0.0D, pos.getZ() + 0.0D);
        }

        return super.getSelectedBoundingBox(worldIn, pos);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
    {
        IBlockState state = world.getBlockState(pos);

        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.DUNGEON_SPAWNER)
        {
            return 10000.0F;
        }

        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.DUNGEON_BRICK)
        {
            return 40.0F;
        }

        return super.getExplosionResistance(world, pos, exploder, explosion);
    }

    /*@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.kepler186fBlockIcons = new IIcon[11];
        this.kepler186fBlockIcons[0] = par1IconRegister.registerIcon(Kepler186FModule.TEXTURE_PREFIX + "cobblestone");
        this.kepler186fBlockIcons[1] = par1IconRegister.registerIcon(Kepler186FModule.TEXTURE_PREFIX + "decoration_desh");
        this.kepler186fBlockIcons[2] = par1IconRegister.registerIcon(Kepler186FModule.TEXTURE_PREFIX + "middle");
        this.kepler186fBlockIcons[3] = par1IconRegister.registerIcon(Kepler186FModule.TEXTURE_PREFIX + "brick");
        this.kepler186fBlockIcons[4] = par1IconRegister.registerIcon(Kepler186FModule.TEXTURE_PREFIX + "top");
        this.kepler186fBlockIcons[5] = par1IconRegister.registerIcon(Kepler186FModule.TEXTURE_PREFIX + "copper");
        this.kepler186fBlockIcons[6] = par1IconRegister.registerIcon(Kepler186FModule.TEXTURE_PREFIX + "desh");
        this.kepler186fBlockIcons[7] = par1IconRegister.registerIcon(Kepler186FModule.TEXTURE_PREFIX + "tin");
        this.kepler186fBlockIcons[8] = par1IconRegister.registerIcon(Kepler186FModule.TEXTURE_PREFIX + "bottom");
        this.kepler186fBlockIcons[9] = par1IconRegister.registerIcon(Kepler186FModule.TEXTURE_PREFIX + "iron");
        this.kepler186fBlockIcons[10] = par1IconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "blank");
    }*/

    @SideOnly(Side.CLIENT)
    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GalacticraftCore.galacticraftBlocksTab;
    }

    @Override
    public float getBlockHardness(World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos);

        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.DUNGEON_BRICK)
        {
            return 4.0F;
        }

        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.DUNGEON_SPAWNER)
        {
            return -1.0F;
        }

        return this.blockHardness;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.DUNGEON_SPAWNER)
        {
            return new TileEntityDungeonSpawnerKepler186F();
        }

        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return null;
    }

    @Override
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        IBlockState state = world.getBlockState(pos);
        int meta = state.getBlock().getMetaFromState(state);
        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.DUNGEON_SPAWNER)
        {
            return false;
        }

        return super.canHarvestBlock(world, pos, player);
    }

    /*@Override
    public IIcon getIcon(int side, int meta)
    {
        switch (meta)
        {
        case 0:
            return this.kepler186fBlockIcons[5];
        case 1:
            return this.kepler186fBlockIcons[7];
        case 2:
            return this.kepler186fBlockIcons[6];
        case 3:
            return this.kepler186fBlockIcons[9];
        case 4:
            return this.kepler186fBlockIcons[0];
        case 5:
            return this.kepler186fBlockIcons[4];
        case 6:
            return this.kepler186fBlockIcons[2];
        case 7:
            return this.kepler186fBlockIcons[3];
        case 8:
            return this.kepler186fBlockIcons[1];
        case 9:
            return this.kepler186fBlockIcons[8];
        case 10:
            return this.kepler186fBlockIcons[10];
        }
        return this.kepler186fBlockIcons[1];
    }*/

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.ORE_DESH)
        {
            return Kepler186FItems.kepler186fItemBasic;
        }
        else if (state.getValue(BASIC_TYPE) == EnumBlockBasic.DUNGEON_SPAWNER)
        {
            return Item.getItemFromBlock(Blocks.air);
        }

        return Item.getItemFromBlock(this);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        int meta = state.getBlock().getMetaFromState(state);
        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.KEPLER186F_STONE)
        {
            return 4;
        }
        else if (state.getValue(BASIC_TYPE) == EnumBlockBasic.ORE_DESH)
        {
            return 0;
        }
        else
        {
            return meta;
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.DUNGEON_SPAWNER)
        {
            return 0;
        }
        else if (state.getValue(BASIC_TYPE) == EnumBlockBasic.ORE_DESH && fortune >= 1)
        {
            return (random.nextFloat() < fortune * 0.29F - 0.25F) ? 2 : 1;
        }

        return 1;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (EnumBlockBasic blockBasic : EnumBlockBasic.values())
        {
            if (blockBasic != EnumBlockBasic.DUNGEON_SPAWNER)
            {
                par3List.add(new ItemStack(par1, 1, blockBasic.getMeta()));
            }
        }
    }

    @Override
    public boolean isValueable(IBlockState state)
    {
        switch (this.getMetaFromState(state))
        {
        case 0:
        case 1:
        case 2:
        case 3:
            return true;
        default:
            return false;
        }
    }

    @Override
    public boolean canSustainPlant(IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
    {
        return false;
    }

    @Override
    public int requiredLiquidBlocksNearby()
    {
        return 4;
    }

    @Override
    public boolean isPlantable(int metadata)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (rand.nextInt(10) == 0)
        {
            if (state.getValue(BASIC_TYPE) == EnumBlockBasic.DUNGEON_BRICK)
            {
                GalacticraftPlanets.spawnParticle("sludgeDrip", new Vector3(pos.getX() + rand.nextDouble(), pos.getY(), pos.getZ() + rand.nextDouble()), new Vector3(0, 0, 0));

                if (rand.nextInt(100) == 0)
                {
                    worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), GalacticraftCore.TEXTURE_PREFIX + "ambience.singledrip", 1, 0.8F + rand.nextFloat() / 5.0F, false);
                }
            }
        }
    }

    @Override
    public boolean isTerraformable(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return state.getValue(BASIC_TYPE) == EnumBlockBasic.SURFACE && !world.getBlockState(pos.up()).getBlock().isFullCube();
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        return state.getValue(BASIC_TYPE) != EnumBlockBasic.DUNGEON_SPAWNER;
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player)
    {
        IBlockState state = world.getBlockState(pos);
        int metadata = state.getBlock().getMetaFromState(state);
        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.ORE_DESH)
        {
            return new ItemStack(Item.getItemFromBlock(this), 1, metadata);
        }
        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.KEPLER186F_STONE)
        {
            return new ItemStack(Item.getItemFromBlock(this), 1, metadata);
        }
        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.DUNGEON_SPAWNER)
        {
            return null;
        }

        return super.getPickBlock(target, world, pos, player);
    }
    
    @Override
    public boolean isReplaceableOreGen(World world, BlockPos pos, Predicate<IBlockState> target)
    {
        if (target != Blocks.stone) return false;
        IBlockState state = world.getBlockState(pos);
    	return (state.getValue(BASIC_TYPE) == EnumBlockBasic.MIDDLE || state.getValue(BASIC_TYPE) == EnumBlockBasic.KEPLER186F_STONE);
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return state.getBlock().getMetaFromState(state) == 10;
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(BASIC_TYPE, EnumBlockBasic.byMetadata(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((EnumBlockBasic)state.getValue(BASIC_TYPE)).getMeta();
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, BASIC_TYPE);
    }

    @Override
    public EnumSortCategoryBlock getCategory(int meta)
    {
        switch (meta)
        {
        case 0:
        case 1:
        case 2:
        case 3:
            return EnumSortCategoryBlock.ORE;
        case 7:
            return EnumSortCategoryBlock.BRICKS;
        case 8:
            return EnumSortCategoryBlock.INGOT_BLOCK;
        }
        return EnumSortCategoryBlock.GENERAL;
    }
}
