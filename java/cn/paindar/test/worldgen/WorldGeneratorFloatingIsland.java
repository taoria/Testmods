package cn.paindar.test.worldgen;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import java.util.Random;

/**
 * Created by Paindar on 2016/8/19.
 */
class IslandGenerator{
	private int wid;
	private int hei;
	private int len;
	private int IslandType;
	private int gay[][][];
	public static final int ISLAND_TYPE_ROUND=1;
	public static final int ISLAND_TYPE_SQURE=2;
	public static final int ISLAND_TYPE_TRIAN=3;
	
	public IslandGenerator(int len,int hei,int type){
		this.wid=this.len=len;
		this.hei=hei;
		gay  = new int[hei][len][len];
		if(type==IslandGenerator.ISLAND_TYPE_ROUND){
			int decay[][][] = new int[hei][len][len];
			int cX= len/2;
			int cZ= len/2;
			int cY = (hei*2)/3;
			for(int y=hei-1;y>=0;y--){
				for(int x=0;x<len;x++){
					for(int z=0;z<len;z++){
						if(y==hei-1){
							decay[y][x][z]=len/2-1-(int)(Math.random()+0.2);
						}else{
							decay[y][x][z]=decay[y+1][x][z]-(int)(Math.random()+0.7);
						}
					}
				}
			}
			for(int x=0;x<len;x++)
				for(int z=0;z<len;z++)
					decay[hei-1][x][z]=decay[hei-1][x][z]-=(int)(Math.random()*3+0.7);
			for(int y=hei-1;y>=0;y--){
				for(int x=0;x<len;x++){
					for(int z=0;z<len;z++){
						double cmp  = Math.round(Math.sqrt((x-cX)*(x-cX)+(z-cZ)*(z-cZ)))-decay[y][x][z];
						if(cmp<=0){
							if(y>=hei-3){
								gay[y][x][z]=1;
							}else{
								if(cmp<=-1)
								gay[y][x][z]=2;
								else gay[y][x][z]=1;
							}
							
						}else{
							gay[y][x][z]=0;
						}
					}
				}
			}
		}
			
	}
	public int [][][] GetGeneratedIsland(){
		return gay;
	}
}
public class WorldGeneratorFloatingIsland implements IWorldGenerator
{

    private static boolean exc=false;
    private static float lerpf(float low,float high,float rate)
    {
        return low+(high-low)* MathHelper.sin(rate*1.57f);
    }

//    private static float getGray(int gray[][][],int x,int y,int z, int strX,int strY,int strZ,int endX,int endY,int endZ,int maxG)
//    {
//
//        if(gray[y][x][z]==maxG)
//        {
//            if(x<=strX || x>=endX || y<=strY || y>=endY || z<=strZ || z>=endZ)
//            {
//                if(z!=strZ && z!=endZ)
//                    gray[y][x][z]=(int)(maxG*MathHelper.sin(1.57f*(getGray(gray,x,y,endZ,strX,strY,strZ,endX,endY,endZ,maxG)*(endZ-z)+getGray(gray,x,y,strZ,strX,strY,strZ,endX,endY,endZ,maxG)*(z-strZ))/((endZ-strZ)*maxG)));
//                else if(y!=strY && y!=endY)
//                    gray[y][x][z]=(int)(maxG*MathHelper.sin(1.57f*(getGray(gray,x,endY,z,strX,strY,strZ,endX,endY,endZ,maxG)*(endY-y)+getGray(gray,x,strY,z,strX,strY,strZ,endX,endY,endZ,maxG)*(y-strY))/((endY-strY)*maxG)));
//                else if(x!=strX&& x!=endX)
//                    gray[y][x][z]=(int)(maxG*MathHelper.sin(1.57f*(getGray(gray,endX,y,z,strX,strY,strZ,endX,endY,endZ,maxG)*(endX-x)+getGray(gray,strX,y,z,strX,strY,strZ,endX,endY,endZ,maxG)*(x-strX))/((endX-strX)*maxG)));
//                else
//                    gray[y][x][z]=maxG-1;
//            }
//            else
//            {
//                 gray[y][x][z]=(int)(
//                     getGray(gray,x,y+1,z,strX,strY,strZ,endX,endY,endZ,maxG)
//                    +getGray(gray,x,y-1,z,strX,strY,strZ,endX,endY,endZ,maxG)
//                    +getGray(gray,x+1,y,z,strX,strY,strZ,endX,endY,endZ,maxG)
//                    +getGray(gray,x-1,y,z,strX,strY,strZ,endX,endY,endZ,maxG)
//                    +getGray(gray,x,y,z+1,strX,strY,strZ,endX,endY,endZ,maxG)
//                    +getGray(gray,x,y,z-1,strX,strY,strZ,endX,endY,endZ,maxG))/6;
//            }
//        }
//        return gray[y][x][z];
//    }
//    private static void fill(int gray[][][], int strX,int strY,int strZ,int endX,int endY,int endZ,int maxG)
//    {
//        for(int x=strX;x<=endX;x++)
//        {
//            for(int y=strY;y<=endY;y++)
//            {
//                for (int z=strZ;z<=endZ;z++)
//                {
//                    if(gray[y][x][z]==maxG)
//                    {
//                        getGray(gray,x,y,z,strX,strY,strZ,endX,endY,endZ,maxG);
//                    }
//                }
//            }
//        }
//    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        //
        int x=chunkX*16+8,y=255,z=chunkZ*16+8;
        Chunk chunk=world.getChunkFromChunkCoords(chunkX,chunkZ);
        if( MathHelper.getRandomDoubleInRange(random,0,1f)>=0.02f)
        {
            return;
        }
        for(;y>2;y--)
        {
            Block block =world.getBlock(x,y,z);
            if(!block.isAir(world,x,y,z))
            {
                break;
            }
        }
        if(y<=50||y>150)return;
        y+=40;
        
        int MAXSIZE=16,MaxGray=1000;
        int grayLvl[][][]=new IslandGenerator(16,8,1).GetGeneratedIsland();
        exc=true;
        if(exc)
        {
            for (int ay = 0; ay < grayLvl.length; ay++) {
                for (int ax = 0; ax < grayLvl[0].length; ax++) {
                    for (int az =0; az <  grayLvl[0][0].length; az++) {
                        if (grayLvl[ay][ax][az] == 1) {
                            world.setBlock(x + ax - 8, y + ay - 8, z + az - 8, Blocks.dirt);
                          //  System.out.println("test");
                        }
                        if (grayLvl[ay][ax][az] == 2) {
                            world.setBlock(x + ax - 8, y + ay - 8, z + az - 8, Blocks.stone);
                          //  System.out.println("test");
                        }
                    }
                    
                }
            }
            System.out.println("test");
        }
        System.out.println("("+x+","+y+","+z+")");
    }
}
