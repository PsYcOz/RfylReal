package com.example.runforyourlife.graphics;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Vector2;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.example.runforyourlife.ResourcesManager;

public abstract class MainCharacter extends AnimatedSprite 
{
	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------
	    
	public Body 									_mcharBody;
	private boolean 								_canRun = false;
	private int 									footContacts = 0;
	private float									increase = 0.0f;
	// ---------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------
    
    public MainCharacter(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
    {
        super(pX, pY, ResourcesManager.getInstance().gameTiledTextureRegionCharacter, vbo);
        camera.setChaseEntity(this);
    }
    
    // ---------------------------------------------
    // METHODS
    // ---------------------------------------------
    public void 									increaseFootContacts()
    {
        footContacts++;
    }

    public void 									decreaseFootContacts()
    {
        footContacts--;
    }
    
    public void 									setRunning()
    {
        _canRun = true;
        _mcharBody.setLinearVelocity(7.5f+increase, 0f);
        this.animate(100);
    }
    public void									increaseSpeed()
    {
    	increase+=0.5;
    }
    public void 									jump()
    {
    	if (footContacts < 1)
    		return;
    	
    	_mcharBody.setLinearVelocity(8f+increase, -13.5f);
    }
    public abstract void onDie();
}
