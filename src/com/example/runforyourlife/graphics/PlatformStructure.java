package com.example.runforyourlife.graphics;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class PlatformStructure 
{
	private Sprite							_platform;
	private Sprite							_grass;
	
	public PlatformStructure(    float pX
								, float pY
								, ITextureRegion platformTiledTextureRegion 
								, ITextureRegion grassTiledTextureRegion
								, VertexBufferObjectManager gpVertexBom
								, Enum_Platform platformType)
	{
		float								platPx;
		float								platPy;
		
		platPx = pX;
		platPy = pY;
		
		if (platformType.equals(Enum_Platform.SQUARE))
			platPy += 53;
		else if (platformType.equals(Enum_Platform.AIR))
		{
			platPy += 34;
			platPx+=15;
		}
		else if (platformType.equals(Enum_Platform.PILLAR))
		{
			platPy += 39;
			platPx+=15;
		}
		
		_platform = new Sprite(platPx, platPy, platformTiledTextureRegion, gpVertexBom);
		_grass = new Sprite(pX, pY, grassTiledTextureRegion, gpVertexBom);
	}

	public Sprite 							get_platform() 
	{
		return _platform;
	}

	public Sprite 							get_grass() 
	{
		return _grass;
	}
	
}
