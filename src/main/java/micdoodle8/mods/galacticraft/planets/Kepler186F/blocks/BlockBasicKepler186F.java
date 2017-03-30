ackage micdoodle8.mods.galacticraft.planets.Kepler186F.blocks;

import micdoodle8.mods.galacticraft.api.block.IDetectableResource;
import micdoodle8.mods.galacticraft.api.block.IPlantableBlock;
import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import micdoodle8.mods.galacticraft.planets.Kepler186F.items.Kepler186FItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockBasicKEPLER186F extends Block implements IDetectableResource, IPlantableBlock, ITerraformableBlock, ISortableBlock
{
    public static final PropertyEnum BASIC_TYPE = PropertyEnum.create("basicTypeKEPLER186F", EnumBlockBasic.class);

    public enum EnumBlockBasic implements IStringSerializable
    {
        KEPLER186F_0(0, "Kepler186F_rock_0"),
        KEPLER186F_1(1, "Kepler186F_rock_1"),
        KEPLER186F_2(2, "Kepler186F_rock_2"),
        ORE_ALUMINUM(3, "ore_aluminum_Kepler186F"),
        ORE_ILMENITE(4, "ore_ilmenite_Kepler186F"),
        ORE_IRON(5, "ore_iron_Kepler186F");

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
        public String getName() {
            return this.name;
        }
    }

    public BlockBasicKepler186F(String assetName)
    {
        super(Material.rock);
        this.blockHardness = 3.0F;
        this.setUnlocalizedName(assetName);
    }

    /*@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcons = new IIcon[6];
        this.blockIcons[0] = par1IconRegister.registerIcon(GalacticraftPlanets.TEXTURE_PREFIX + "Kepler186F0");
        this.blockIcons[1] = par1IconRegister.registerIcon(GalacticraftPlanets.TEXTURE_PREFIX + "Kepler186F1");
        this.blockIcons[2] = par1IconRegister.registerIcon(GalacticraftPlanets.TEXTURE_PREFIX + "Kepler186F2");
        this.blockIcons[3] = par1IconRegister.registerIcon(GalacticraftPlanets.TEXTURE_PREFIX + "oreAluminum");
        this.blockIcons[4] = par1IconRegister.registerIcon(GalacticraftPlanets.TEXTURE_PREFIX + "oreIlmenite");
        this.blockIcons[5] = par1IconRegister.registerIcon(GalacticraftPlanets.TEXTURE_PREFIX + "oreIron");
        this.blockIcon = this.blockIcons[0];
    }*/

    @SideOnly(Side.CLIENT)
    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GalacticraftCore.galacticraftBlocksTab;
    }

    /*@SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta >= this.blockIcons.length)
        {
            return this.blockIcon;
        }
        return this.blockIcons[meta];
    }*/

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        if (state.getValue(BASIC_TYPE) == EnumBlockBasic.ORE_ILMENITE && world instanceof World)
        {
            ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

            int count = quantityDropped(state, fortune, ((World)world).rand);
            for (int i = 0; i < count; i++)
            {
                ret.add(new ItemStack(Kepler186FItems.basicItem, 1, 3));
            }
            count = quantityDropped(state, fortune, ((World)world).rand);
            for (int i = 0; i < count; i++)
            {
                ret.add(new ItemStack(Kepler186FItems.basicItem, 1, 4));
            }
            return ret;
        }


        return super.getDrops(world, pos, state, fortune);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        switch (getMetaFromState(state))
        {
        case 4:
            return 0;
        default:
            return getMetaFromState(state);
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        switch (getMetaFromState(state))
        {
        case 4:
            if (fortune >= 1)
            {
                return (random.nextFloat() < fortune * 0.29F - 0.25F) ? 2 : 1;
            }
        default:
            return 1;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        int var4;

        for (var4 = 0; var4 < EnumBlockBasic.values().length; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

    @Override
    public boolean isValueable(IBlockState state)
    {
        switch (this.getMetaFromState(state))
        {
        case 3:
        case 4:
        case 5:
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
    public boolean isTerraformable(World world, BlockPos pos)
    {
        return false;
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player)
    {
        IBlockState state = world.getBlockState(pos);
        if (getMetaFromState(state) == 4)
        {
            return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(state));
        }

        return super.getPickBlock(target, world, pos, player);
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
        case 3:
        case 4:
        case 5:
            return EnumSortCategoryBlock.ORE;
        }
        return EnumSortCategoryBlock.GENERAL;
    }
}