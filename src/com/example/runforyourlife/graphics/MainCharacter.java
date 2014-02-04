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
	// ---------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------
    
    public MainCharacter(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
    {
        super(pX, pY, ResourcesManager.getInstance().gameTiledTextureRegionCharacter, vbo);
        createPhysics(camera, physicsWorld);
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
    private void 									createPhysics(final Camera camera, PhysicsWorld physicsWorld)
    {        
    	_mcharBody = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));
    	_mcharBody.setUserData("player");
    	_mcharBody.setFixedRotation(true);
        
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, _mcharBody, true, false)
        {
            @Override
            public void onUpdate(float pSecondsElapsed)
            {
                super.onUpdate(pSecondsElapsed);
                camera.onUpdate(0.1f);
                
                if (getY() <= 0)
                {                    
                    onDie();
                }
                
                if (_canRun)
                {    
                	_mcharBody.setLinearVelocity(0f, 7f);
                }
            }
        });
    }
    public void 									setRunning()
    {
        _canRun = true;
        this.animate(100);
    }
    public void 									jump()
    {
    	if (footContacts < 1)
    		return;
    	
    	_mcharBody.setLinearVelocity(0f, -60f);
    }
    public abstract void onDie();
}
