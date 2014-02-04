package com.example.runforyourlife.graphics;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Platform extends Sprite
{

	public Platform(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) 
	{
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
	}

}
